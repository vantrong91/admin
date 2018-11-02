/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.base;

import com.aerospike.client.Bin;
import com.aerospike.client.Record;
import com.aerospike.client.listener.WriteListener;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.query.RecordSet;
import com.vtgo.vn.admin.aerospike.AerospikeFactory;

/**
 *
 * @author HP
 */
public class BaseController {

    public Record getById(String namespace, String set, Long key, String... bins) throws Exception {
        Record rec = AerospikeFactory.getInstance().query(namespace, set, key, bins);
        return rec;
    }
    
    public Record getById(String namespace, String set, String key, String... bins) throws Exception {
        Record rec = AerospikeFactory.getInstance().query(namespace, set, key, bins);
        return rec;
    }

    public RecordSet getByIdx(String namespace, String set, String binName, String indexName, String indexValue) throws Exception {
        RecordSet rec = AerospikeFactory.getInstance().queryByIndex(namespace, set, binName, indexName, indexValue);
        return rec;
    }

    public void update(WritePolicy writePolicy, String namespace, String set, String key, Bin... bins) throws Exception {
        AerospikeFactory.getInstance().update(writePolicy, namespace, set, key, bins);
    }

    public void update(WritePolicy writePolicy, String namespace, String set, Long key, Bin... bins) throws Exception {
        AerospikeFactory.getInstance().update(writePolicy, namespace, set, key, bins);
    }

    public void update(WriteListener listener, WritePolicy writePolicy, String namespace, String set, String key, Bin... bins) throws Exception {
        AerospikeFactory.getInstance().update(listener, writePolicy, namespace, set, key, bins);
    }

    public void updateStringKey(String namespace, String set, String key, Bin... bins) throws Exception {
        AerospikeFactory.getInstance().update(namespace, set, key, bins);
    }

    public void delete(WritePolicy writePolicy, String namespace, String set, long key) throws Exception {
        AerospikeFactory.getInstance().delete(writePolicy, namespace, set, key);
    }

    public void delete(WritePolicy writePolicy, String namespace, String set, String key) throws Exception {
        AerospikeFactory.getInstance().delete(writePolicy, namespace, set, key);
    }
}
