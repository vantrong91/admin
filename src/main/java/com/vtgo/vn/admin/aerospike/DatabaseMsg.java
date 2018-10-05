package com.vtgo.vn.admin.aerospike;

import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.query.RecordSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseMsg {

    private Key key;
    private Record record;
    private List<Record> lstRecord;
    private RecordSet recordSet;
    private String resultText;
    private int errorCode;
//    private int status;

    private Map executeResult;

    public List<Record> getLstRecord() {
        if(lstRecord == null){
            lstRecord = new ArrayList<Record>();
        }
        return lstRecord;
    }

    public void setLstRecord(List<Record> lstRecord) {
        this.lstRecord = lstRecord;
    }

    public RecordSet getRecordSet() {
        return recordSet;
    }

    public void setRecordSet(RecordSet recordSet) {
        this.recordSet = recordSet;
    }

    public Map getExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(Map executeResult) {
        this.executeResult = executeResult;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }
}
