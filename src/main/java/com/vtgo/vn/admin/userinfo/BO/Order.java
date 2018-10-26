/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.BO;

import com.aerospike.client.Bin;
import com.aerospike.client.Record;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vtgo.vn.admin.base.BaseObject;
import com.vtgo.vn.admin.base.BaseRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author viett
 */
public class Order extends BaseRequest<Object> implements BaseObject {

    private static final Logger logger = Logger.getLogger(Order.class);
    private String orderId;
    private Long accountIdDriver;
    private String srcAddress;
    private Double srcLat;
    private Double srcLong;
    private String dstAddress;
    private Double dstLat;
    private Double dstLong;
    private Long weight;
    private Long deliverTime;
    private Long toDeliverTime;
    private Long receiveTime;
    private Long toReceiveTime;
    private Long state;
    private List<Product> lstProduct = new ArrayList<>();
    private Map<String, Object> orderComplete;
    private List<String> productImgLst;
    private String uRLFolderProductImg;
    private String quotationId;
    private List<OrderHistory> lstOrderHis = new ArrayList<>();
    private String orderName;
    private Long typeCar;
    private Double sumProduct;
    private Double sumVolume;
    private String note;
    private Integer productType;
    private Double wantPrice;
    private String goodStatus;

    // thoi gian boc hang va giao hang thuc te
    private Long realDeliverTime;
    private Long realToDeliverTime;
    private Long realReceiveTime;
    private Long realToReceiveTime;

    private String completeCode;
    private String loadingCode;
    private Long accountId;

    //Tiền thanh toán    
    private Long paid;

    public Order() {
    }

    @JsonIgnore
    @Override

    public boolean parse(Record record) {
        try {
            this.accountIdDriver = record.getLong("AccIdDriver");
            this.srcAddress = record.getString("SrcAddress");
            this.srcLat = record.getDouble("SrcLat");
            this.srcLong = record.getDouble("SrcLong");
            this.dstAddress = record.getString("DstAddress");
            this.dstLat = record.getDouble("DstLat");
            this.dstLong = record.getDouble("DstLong");
            this.orderId = record.getString("OrderId");
            this.weight = record.getLong("Weight");
            this.deliverTime = record.getLong("DeliverTime ");
            this.toDeliverTime = record.getLong("ToDeliverTime");
            this.receiveTime = record.getLong("ReceiveTime");
            this.toReceiveTime = record.getLong("OReceiveTime");
            this.state = record.getLong("State");
            this.goodStatus = record.getString("GoodsStatus");

            this.lstProduct = (List<Product>) record.getList("LstProduct");
            this.orderComplete = (Map<String, Object>) record.getMap("OrderComplete");
            this.productImgLst = (List<String>) record.getList("ProductImgLst");
            this.uRLFolderProductImg = record.getString("URLFolderProductImg");
            this.quotationId = record.getString("QuotationId");
            this.lstOrderHis = (List<OrderHistory>) record.getList("LstOrderHis");
            this.orderName = record.getString("orderName");
            this.typeCar = record.getLong("TypeCar");
            this.sumProduct = record.getDouble("SumProduct");
            this.sumVolume = record.getDouble("SumVolume");
            this.note = record.getString("Note");
            this.productType = record.getInt("ProductType");
            this.wantPrice = record.getDouble("WantPrice");

            this.realDeliverTime = record.getLong("RealDeliverTime");
            this.realToDeliverTime = record.getLong("ToRealDeliTime");
            this.realReceiveTime = record.getLong("RealReceiveTime");
            this.realToReceiveTime = record.getLong("RealToReceiveTime");
            this.paid = record.getLong("Paid");
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @JsonIgnore
    @Override
    public boolean parse(Map map) {
        try {
            this.orderId = (String) map.get("OrderId");
            this.accountIdDriver = (Long) map.get("AccIdDriver");
            this.srcAddress = (String) map.get("SrcAddress");
            this.srcLat = (Double) map.get("SrcLat");
            this.srcLong = (Double) map.get("SrcLong");
            this.dstAddress = (String) map.get("DstAddress");
            this.dstLat = Double.parseDouble(String.valueOf(map.get("DstLat")));
            this.dstLong = Double.parseDouble(String.valueOf(map.get("DstLong")));
            this.weight = (Long) map.get("Weight");
            this.deliverTime = (Long) map.get("DeliverTime");
            this.toDeliverTime = (Long) map.get("ToDeliverTime");
            this.receiveTime = (Long) map.get("ReceiveTime");
            this.toReceiveTime = (Long) map.get("ToReceiveTime");
            this.state = (Long) map.get("State");
            this.lstProduct = (List<Product>) map.get("Products");
            this.orderComplete = (Map<String, Object>) map.get("OrderComplete");
            this.productImgLst = (List<String>) map.get("ProductImgLst");
            this.uRLFolderProductImg = (String) map.get("URLFolderImg");
            this.lstOrderHis = (List<OrderHistory>) map.get("OrderHistory");
            this.orderName = (String) map.get("OrderName");
            this.typeCar = (Long) map.get("TypeCar");
            this.sumProduct = Double.parseDouble(String.valueOf(map.get("SumProduct")));
            this.sumVolume = Double.parseDouble(String.valueOf(map.get("SumVolume")));
            this.note = (String) map.get("Note");
            this.productType = (Integer) map.get("ProductType");
            this.wantPrice = (Double) map.get("WantPrice");
            this.goodStatus = (String) map.get("GoodsStatus");

            this.realDeliverTime = (Long) map.get("RealDeliTime");
            this.realToDeliverTime = (Long) map.get("ToRealDeliTime");
            this.realReceiveTime = (Long) map.get("RealReceTime");
            this.realToReceiveTime = (Long) map.get("RealToReceTime");

            this.quotationId = (String) map.get("QuotationId");
            this.completeCode = (String) map.get("CompleteCode");
            this.loadingCode = (String) map.get("LoadingCode");
            this.accountId = (Long) map.get("AccountId");

            this.paid = (Long) map.get("Paid");

            return true;
        } catch (Exception ex) {
//            logger.error(ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public Bin[] toBins() {
        try {
            List<Bin> bins = new ArrayList<>();
            bins.add(new Bin("OrderId", orderId));
            bins.add(new Bin("AccIdDriver", accountIdDriver));
            bins.add(new Bin("SrcAddress", srcAddress));
            bins.add(new Bin("SrcLat", srcLat));
            bins.add(new Bin("SrcLong", srcLong));
            bins.add(new Bin("DstAddress", dstAddress));
            bins.add(new Bin("DstLat", dstLat));
            bins.add(new Bin("DstLong", dstLong));
            bins.add(new Bin("Weight", weight));
            bins.add(new Bin("DeliverTime", deliverTime));
            bins.add(new Bin("ToDeliverTime", toDeliverTime));
            bins.add(new Bin("ReceiveTime", receiveTime));
            bins.add(new Bin("ToReceiveTime", toReceiveTime));
            bins.add(new Bin("State", state));
            bins.add(new Bin("Products", lstProduct));
            bins.add(new Bin("OrderComplete", orderComplete));
            bins.add(new Bin("ProductImgLst", productImgLst));
            bins.add(new Bin("URLFolderImg", uRLFolderProductImg));
            bins.add(new Bin("OrderHistory", lstOrderHis));
            bins.add(new Bin("OrderName", orderName));
            bins.add(new Bin("TypeCar", typeCar));
            bins.add(new Bin("SumProduct", sumProduct));
            bins.add(new Bin("SumVolume", sumVolume));
            bins.add(new Bin("Note", note));
            bins.add(new Bin("ProductType", productType));
            bins.add(new Bin("WantPrice", wantPrice));
            bins.add(new Bin("GoodsStatus", goodStatus));

            bins.add(new Bin("RealDeliTime", realDeliverTime));
            bins.add(new Bin("ToRealDeliTime", realToDeliverTime));
            bins.add(new Bin("RealReceTime", realReceiveTime));
            bins.add(new Bin("RealToReceTime", realToReceiveTime));

            bins.add(new Bin("QuotationId", quotationId));
            bins.add(new Bin("CompleteCode", completeCode));
            bins.add(new Bin("LoadingCode", loadingCode));
            bins.add(new Bin("AccountId", accountId));

            bins.add(new Bin("Paid", paid));

            return bins.toArray(new Bin[bins.size()]);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", accountIdDriver=" + accountIdDriver + ", paid= " + paid + ", srcAddress=" + srcAddress + ", srcLat=" + srcLat + ", srcLong=" + srcLong + ", dstAddress=" + dstAddress + ", dstLat=" + dstLat + ", dstLong=" + dstLong + ", weight=" + weight + ", deliverTime=" + deliverTime + ", toDeliverTime=" + toDeliverTime + ", receiveTime=" + receiveTime + ", toReceiveTime=" + toReceiveTime + ", state=" + state + ", lstProduct=" + lstProduct + ", orderComplete=" + orderComplete + ", productImgLst=" + productImgLst + ", uRLFolderProductImg=" + uRLFolderProductImg + ", quotationId=" + quotationId + ", lstOrderHis=" + lstOrderHis + ", orderName=" + orderName + ", typeCar=" + typeCar + ", sumProduct=" + sumProduct + ", sumVolume=" + sumVolume + ", note=" + note + ", productType=" + productType + ", wantPrice=" + wantPrice + ", goodStatus=" + goodStatus + ", realDeliverTime=" + realDeliverTime + ", realToDeliverTime=" + realToDeliverTime + ", realReceiveTime=" + realReceiveTime + ", realToReceiveTime=" + realToReceiveTime + ", completeCode=" + completeCode + ", loadingCode=" + loadingCode + ", accountId=" + accountId + '}';
    }

    public Long getPaid() {
        return paid;
    }

    public void setPaid(Long paid) {
        this.paid = paid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getAccountIdDriver() {
        return accountIdDriver;
    }

    public void setAccountIdDriver(Long accountIdDriver) {
        this.accountIdDriver = accountIdDriver;
    }

    public String getSrcAddress() {
        return srcAddress;
    }

    public void setSrcAddress(String srcAddress) {
        this.srcAddress = srcAddress;
    }

    public Double getSrcLat() {
        return srcLat;
    }

    public void setSrcLat(Double srcLat) {
        this.srcLat = srcLat;
    }

    public Double getSrcLong() {
        return srcLong;
    }

    public void setSrcLong(Double srcLong) {
        this.srcLong = srcLong;
    }

    public String getDstAddress() {
        return dstAddress;
    }

    public void setDstAddress(String dstAddress) {
        this.dstAddress = dstAddress;
    }

    public Double getDstLat() {
        return dstLat;
    }

    public void setDstLat(Double dstLat) {
        this.dstLat = dstLat;
    }

    public Double getDstLong() {
        return dstLong;
    }

    public void setDstLong(Double dstLong) {
        this.dstLong = dstLong;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Long deliverTime) {
        this.deliverTime = deliverTime;
    }

    public Long getToDeliverTime() {
        return toDeliverTime;
    }

    public void setToDeliverTime(Long toDeliverTime) {
        this.toDeliverTime = toDeliverTime;
    }

    public Long getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Long receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Long getToReceiveTime() {
        return toReceiveTime;
    }

    public void setToReceiveTime(Long toReceiveTime) {
        this.toReceiveTime = toReceiveTime;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public List<Product> getLstProduct() {
        return lstProduct;
    }

    public void setLstProduct(List<Product> lstProduct) {
        this.lstProduct = lstProduct;
    }

    public Map<String, Object> getOrderComplete() {
        return orderComplete;
    }

    public void setOrderComplete(Map<String, Object> orderComplete) {
        this.orderComplete = orderComplete;
    }

    public List<String> getProductImgLst() {
        return productImgLst;
    }

    public void setProductImgLst(List<String> productImgLst) {
        this.productImgLst = productImgLst;
    }

    public String getuRLFolderProductImg() {
        return uRLFolderProductImg;
    }

    public void setuRLFolderProductImg(String uRLFolderProductImg) {
        this.uRLFolderProductImg = uRLFolderProductImg;
    }

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    public List<OrderHistory> getLstOrderHis() {
        return lstOrderHis;
    }

    public void setLstOrderHis(List<OrderHistory> lstOrderHis) {
        this.lstOrderHis = lstOrderHis;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Long getTypeCar() {
        return typeCar;
    }

    public void setTypeCar(Long typeCar) {
        this.typeCar = typeCar;
    }

    public Double getSumProduct() {
        return sumProduct;
    }

    public void setSumProduct(Double sumProduct) {
        this.sumProduct = sumProduct;
    }

    public Double getSumVolume() {
        return sumVolume;
    }

    public void setSumVolume(Double sumVolume) {
        this.sumVolume = sumVolume;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Double getWantPrice() {
        return wantPrice;
    }

    public void setWantPrice(Double wantPrice) {
        this.wantPrice = wantPrice;
    }

    public String getGoodStatus() {
        return goodStatus;
    }

    public void setGoodStatus(String goodStatus) {
        this.goodStatus = goodStatus;
    }

    public Long getRealDeliverTime() {
        return realDeliverTime;
    }

    public void setRealDeliverTime(Long realDeliverTime) {
        this.realDeliverTime = realDeliverTime;
    }

    public Long getRealToDeliverTime() {
        return realToDeliverTime;
    }

    public void setRealToDeliverTime(Long realToDeliverTime) {
        this.realToDeliverTime = realToDeliverTime;
    }

    public Long getRealReceiveTime() {
        return realReceiveTime;
    }

    public void setRealReceiveTime(Long realReceiveTime) {
        this.realReceiveTime = realReceiveTime;
    }

    public Long getRealToReceiveTime() {
        return realToReceiveTime;
    }

    public void setRealToReceiveTime(Long realToReceiveTime) {
        this.realToReceiveTime = realToReceiveTime;
    }

    public String getCompleteCode() {
        return completeCode;
    }

    public void setCompleteCode(String completeCode) {
        this.completeCode = completeCode;
    }

    public String getLoadingCode() {
        return loadingCode;
    }

    public void setLoadingCode(String loadingCode) {
        this.loadingCode = loadingCode;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

}
