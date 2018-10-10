/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.BO;

import com.aerospike.client.Bin;
import com.aerospike.client.Record;
import com.vtgo.vn.admin.base.BaseObject;
import com.vtgo.vn.admin.base.BaseRequest;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author viett
 */
public class BalanceHis extends BaseRequest<Object> implements BaseObject {

    private static final Logger logger = Logger.getLogger(BalanceHis.class);

    private Long hisId;
    private Long accountId;
    private Long hisType;
    private String hisContent;
    private String iP;
    private Long balanceBefor;
    private Long balanceAfter;
    private Long amount;

    @Override
    public boolean parse(Record record) {
        return false;
    }

    @Override
    public boolean parse(Map<String, Object> map) {
        try {
            this.hisId = (Long) map.get("HisId");
            this.accountId = (Long) map.get("AccountId");
            this.hisType = (Long) map.get("HisType");
            this.hisContent = (String) map.get("HisContent");
            this.iP = (String) map.get("IP");
            this.balanceBefor = (Long) map.get("BalanceBefor");
            this.balanceAfter = (Long) map.get("BalanceAfter");
            this.amount = (Long) map.get("Amount");
            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public Bin[] toBins() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Long getHisId() {
        return hisId;
    }

    public void setHisId(Long hisId) {
        this.hisId = hisId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getHisType() {
        return hisType;
    }

    public void setHisType(Long hisType) {
        this.hisType = hisType;
    }

    public String getHisContent() {
        return hisContent;
    }

    public void setHisContent(String hisContent) {
        this.hisContent = hisContent;
    }

    public String getiP() {
        return iP;
    }

    public void setiP(String iP) {
        this.iP = iP;
    }

    public Long getBalanceBefor() {
        return balanceBefor;
    }

    public void setBalanceBefor(Long balanceBefor) {
        this.balanceBefor = balanceBefor;
    }

    public Long getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(Long balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "BalanceHis{" + "hisId=" + hisId + ", accountId=" + accountId + ", hisType=" + hisType + ", hisContent=" + hisContent + ", iP=" + iP + ", balanceBefor=" + balanceBefor + ", balanceAfter=" + balanceAfter + ", amount=" + amount + '}';
    }
}
