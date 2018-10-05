package com.vtgo.vn.admin.aerospike.handler;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Key;
import com.aerospike.client.listener.ExecuteListener;
import com.vtgo.vn.admin.aerospike.DatabaseMsg;
import org.apache.log4j.Logger;

import java.util.Map;

public class AerospikeExecuteHandler implements ExecuteListener {

    private static final Logger logger = Logger.getLogger(AerospikeExecuteHandler.class);

    public AerospikeExecuteHandler() {
    }
        private DatabaseMsg msg;
    @Override
    public void onSuccess(Key key, Object ret) {
        try {
            if(ret == null){
                logger.info("Query for key "+key.userKey.toString()+" not found");
            }
            logger.info("In query aerospike success for key:"+key.userKey.toString());
            msg.setKey(key);
            Map mapRet = (Map)ret;
            Integer resultCode = (Integer)mapRet.get("ResultCode");
            String resultText = (String)mapRet.get("ResultText");
            msg.setErrorCode(resultCode);
            msg.setResultText(resultText);
            msg.setExecuteResult(mapRet);

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
