package com.vtgo.vn.admin.aerospike.handler;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Key;
import com.aerospike.client.listener.WriteListener;
import com.vtgo.vn.admin.aerospike.DatabaseMsg;
import com.vtgo.vn.admin.constant.DatabaseConstants;
import org.apache.log4j.Logger;

public class AerospikePutHandler implements WriteListener {

    private static final Logger logger = Logger.getLogger(AerospikePutHandler.class);

    public AerospikePutHandler() {
    }

    private DatabaseMsg msg;


    @Override
    public void onSuccess(Key key) {
        msg.setErrorCode(DatabaseConstants.ResultCode.SUCCESS);
        msg.setResultText(DatabaseConstants.ResultText.SUCCESS);
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
