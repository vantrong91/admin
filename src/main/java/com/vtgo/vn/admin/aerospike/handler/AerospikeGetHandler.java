package com.vtgo.vn.admin.aerospike.handler;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.listener.RecordListener;
import com.vtgo.vn.admin.aerospike.DatabaseMsg;
import com.vtgo.vn.admin.constant.DatabaseConstants;
import org.apache.log4j.Logger;

public class AerospikeGetHandler implements RecordListener {

    private static final Logger logger = Logger.getLogger(AerospikeGetHandler.class);

    public AerospikeGetHandler() {
    }
        private DatabaseMsg msg;

    @Override
    public void onSuccess(Key key, Record record) {
        try {
            if(record == null){
                logger.info("Query for key "+key.userKey.toString()+" not found");
            }
            logger.info("In query aerospike success for key:"+key.userKey.toString());
            msg.setErrorCode(DatabaseConstants.ResultCode.SUCCESS);
            msg.setResultText(DatabaseConstants.ResultText.SUCCESS);
            msg.setKey(key);
            msg.setRecord(record);
        }catch (Exception ex){
            logger.error(ex,ex);
        }

    }

    @Override
    public void onFailure(AerospikeException e) {
        try {
            logger.info("In query aerospike fail:"+msg.toString());
            msg.setErrorCode(e.getResultCode());
            msg.setResultText(e.getMessage());
        }catch (Exception ex){
            logger.error(ex,ex);
        }
    }
}
