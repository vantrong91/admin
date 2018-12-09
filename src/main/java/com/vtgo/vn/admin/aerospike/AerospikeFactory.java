package com.vtgo.vn.admin.aerospike;

import com.aerospike.client.*;
import com.aerospike.client.async.AsyncClient;
import com.aerospike.client.async.AsyncClientPolicy;
import com.aerospike.client.listener.ExecuteListener;
import com.aerospike.client.listener.RecordListener;
import com.aerospike.client.listener.WriteListener;
import com.aerospike.client.policy.*;
import com.aerospike.client.query.Filter;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.ResultSet;
import com.aerospike.client.query.Statement;
import com.aerospike.client.task.RegisterTask;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class AerospikeFactory {

    private static final Logger logger = Logger.getLogger(AerospikeFactory.class);
    //<editor-fold desc="Define Variables">
    public static AsyncClient client = null;
    private static AsyncClientPolicy policy = null;
    public BatchPolicy batchPolicy = null;
    public QueryPolicy queryPolicy = null;
    public WritePolicy writePolicy = null;
    public ScanPolicy scanPolicy = null;
    public WritePolicy updatePolicy = null;
    public WritePolicy onlyUpdatePolicy = null;
    protected AerospikeClient aerospikeClient;

    public WritePolicy onlyCreatePolicy = null;

    //FIXME load queryPolicy from config
    {
        policy = new AsyncClientPolicy();
        batchPolicy = new BatchPolicy();
        queryPolicy = new QueryPolicy();
        //WritePolicy retry
        writePolicy = new WritePolicy();
        writePolicy.maxRetries = 10;
        writePolicy.timeout = 1000;
        writePolicy.timeoutDelay = 100;
        writePolicy.retryOnTimeout = true;
        writePolicy.sendKey = true;
        
        

        scanPolicy = new ScanPolicy();

        onlyCreatePolicy = new WritePolicy();
        onlyCreatePolicy.sendKey = true;
        onlyCreatePolicy.maxRetries = 10;
        onlyCreatePolicy.timeout = 1000;
        onlyCreatePolicy.timeoutDelay = 100;
        onlyCreatePolicy.retryOnTimeout = true;
        onlyCreatePolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;

        updatePolicy = new WritePolicy();
        updatePolicy.sendKey = true;
        updatePolicy.maxRetries = 10;
        updatePolicy.timeout = 5000;
        updatePolicy.timeoutDelay = 100;
        updatePolicy.retryOnTimeout = true;
        updatePolicy.recordExistsAction = RecordExistsAction.UPDATE;

        onlyUpdatePolicy = new WritePolicy();
        onlyUpdatePolicy.sendKey = true;
        onlyUpdatePolicy.maxRetries = 10;
        onlyUpdatePolicy.timeout = 5000;
        onlyUpdatePolicy.timeoutDelay = 100;
        onlyUpdatePolicy.retryOnTimeout = true;
        onlyUpdatePolicy.recordExistsAction = RecordExistsAction.UPDATE_ONLY;
    }

    //</editor-fold>
    //<editor-fold desc="Singleton Aerospike Instance">
    private static AerospikeFactory instance = null;

    public static synchronized AerospikeFactory getInstance() {
        if (instance == null) {
            instance = new AerospikeFactory();
        }
        return instance;
    }
    //</editor-fold>

    public static void loadConfig(String config) {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(new File(config)));
            Host[] hosts = Host.parseHosts(prop.getProperty("host", "103.90.220.148"), Integer.valueOf(prop.getProperty("port", "3000")));
            System.getProperties().setProperty("lua.dir", prop.getProperty("lua.dir", "../lua-function/"));
            //FIXME fix policy with config file
            client = new AsyncClient(policy, hosts);
            RegisterTask task = client.register(null, "../lua-function/FILTER_RECORD.lua", "FILTER_RECORD.lua", Language.LUA);
            task.waitTillComplete();
        } catch (IOException e) {
            logger.error(e, e);
        }
    }

//    public AerospikeFactory() {
//        loadConfig("../etc/aerospike.conf");
//    }
    public Record query(String namespace, String set, String key, String... bins) {
        if (bins == null || bins.length == 0) {
            return client.get(queryPolicy, new Key(namespace, set, key));
        } else {
            return client.get(queryPolicy, new Key(namespace, set, key), bins);
        }
    }

    public Record query(String namespace, String set, long key, String... bins) {
        if (bins == null || bins.length == 0) {
            return client.get(queryPolicy, new Key(namespace, set, key));
        } else {
            return client.get(queryPolicy, new Key(namespace, set, key), bins);
        }
    }

    public Record[] query(String namespace, String set, List<String> lstKey, String... bins) {
        List<Key> keys = new ArrayList<>();
        for (String key : lstKey) {
            keys.add(new Key(namespace, set, key));
        }
        if (bins == null || bins.length == 0) {
            return client.get(batchPolicy, keys.toArray(new Key[keys.size()]));
        } else {
            return client.get(batchPolicy, keys.toArray(new Key[keys.size()]), bins);
        }
    }

    public void query(RecordListener listener, String namespace, String set, String key, String... bins) {
        if (bins == null || bins.length == 0) {
            client.get(queryPolicy, listener, new Key(namespace, set, key));
        } else {
            client.get(queryPolicy, listener, new Key(namespace, set, key), bins);
        }
    }

    public RecordSet queryByIndex(String namespace, String set, String binName, String indexName, String indexValue) {
        try {
            Statement statement = new Statement();
            statement.setNamespace(namespace);
            statement.setSetName(set);
            statement.setIndexName(indexName);
            //Filter
            Filter filter = Filter.equal(binName, indexValue);
            statement.setFilters(filter);
            //Fixme Using query policy after
            return client.query(queryPolicy, statement);
        } catch (AerospikeException e) {
            logger.error(e, e);
            return null;
        }
    }

    public RecordSet queryByIndex(String namespace, String set, String binName, String indexName, long indexValue) {
        try {
            Statement statement = new Statement();
            statement.setNamespace(namespace);
            statement.setSetName(set);
            statement.setIndexName(indexName);
            //Filter
            Filter filter = Filter.equal(binName, indexValue);
            statement.setFilters(filter);
            //Fixme Using query policy after
            return client.query(queryPolicy, statement);
        } catch (AerospikeException e) {
            logger.error(e, e);
            return null;
        }
    }

    public void update(String namespace, String set, String key, Bin... bins) throws AerospikeException {
        client.put(null, new Key(namespace, set, key), bins);
    }

    public void update(WritePolicy writePolicy, String namespace, String set, String key, Bin... bins) throws AerospikeException {
        client.put(writePolicy, new Key(namespace, set, key), bins);
    }

    public void update(WritePolicy writePolicy, String namespace, String set, long key, Bin... bins) throws AerospikeException {
        client.put(writePolicy, new Key(namespace, set, key), bins);
    }

    public void delete(WritePolicy writePolicy, String namespace, String set, long key) throws AerospikeException {
        client.delete(writePolicy, new Key(namespace, set, key));
    }

    public void delete(WritePolicy writePolicy, String namespace, String set, String key) throws AerospikeException {
        client.delete(writePolicy, new Key(namespace, set, key));
    }

    public void update(WriteListener listener, WritePolicy writePolicy, String namespace, String set, String key, Bin... bins) {

        client.put(writePolicy, listener, new Key(namespace, set, key), bins);
    }

    public void update(WriteListener listener, WritePolicy writePolicy, String namespace, String set, long key, Bin... bins) {

        client.put(writePolicy, listener, new Key(namespace, set, key), bins);
    }

    public void execute(ExecuteListener listener, WritePolicy writePolicy, String namespace, String set, String key,
            String module, String function, Value... values) {
        client.execute(writePolicy, listener, new Key(namespace, set, key), module, function, values);
    }

    public void execute(ExecuteListener listener, WritePolicy writePolicy, String namespace, String set, Long key,
            String module, String function, Value... values) {
        client.execute(writePolicy, listener, new Key(namespace, set, key), module, function, values);
    }

    public Object execute(WritePolicy writePolicy, String namespace, String set, String key,
            String module, String function, Value... values) throws AerospikeException {
        return client.execute(writePolicy, new Key(namespace, set, key), module, function, values);
    }

    public ResultSet aggregate(QueryPolicy policy, String namespace, String set, String module,
            String function, Value... values) throws AerospikeException {
        Statement statement = new Statement();
        statement.setNamespace(namespace);
        statement.setSetName(set);
        statement.setAggregateFunction(module, function, values);
        return client.queryAggregate(queryPolicy, statement);
    }

    public ResultSet aggregate(QueryPolicy policy, String namespace, String set, String module,
            String function, Filter filter, Value... values) throws AerospikeException {
        Statement statement = new Statement();
        statement.setNamespace(namespace);
        statement.setSetName(set);
        statement.setFilters(filter);
        statement.setAggregateFunction(module, function, values);
        return client.queryAggregate(queryPolicy, statement);
    }
    boolean completed;

    protected void resetComplete() {
        completed = false;
    }

    protected synchronized void waitTillComplete() {
        while (!completed) {
            try {
                super.wait(10000l);
            } catch (InterruptedException ie) {
                logger.error(ie, ie);
            }
        }
    }

    protected synchronized void notifyComplete() {
        completed = true;
        super.notify();
    }
}
