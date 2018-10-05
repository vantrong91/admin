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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
public class Quotation extends BaseRequest<Object> implements BaseObject {
    private String quotationId;
    private String orderId;
    private Long vehicleId;
    private Long driverId;
    private Long price;
    private Long state;
    private Double vat;
    private Double commission;
    private Double finesAmount;
    private Double delayPrice;
    private Double receivePrice;
    private Double deliverPrice;
    private Double driverVatTax;
    private Double perDriverTax;
    private Double reserveDriver;
    private Double reserveOrder;
    private Long receiveTime;
    private Long toReceiveTime;
    private Long deliverTime;
    private Long toDeliverTime;
    private Long numQuotation;
    
    
    private static final Logger logger = Logger.getLogger(Quotation.class);

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }
    
    public Double getFinesAmount() {
        return finesAmount;
    }

    public void setFinesAmount(Double finesAmount) {
        this.finesAmount = finesAmount;
    }

    public Double getDelayPrice() {
        return delayPrice;
    }

    public void setDelayPrice(Double delayPrice) {
        this.delayPrice = delayPrice;
    }

    public Double getReceivePrice() {
        return receivePrice;
    }

    public void setReceivePrice(Double receivePrice) {
        this.receivePrice = receivePrice;
    }

    public Double getDeliverPrice() {
        return deliverPrice;
    }

    public void setDeliverPrice(Double deliverPrice) {
        this.deliverPrice = deliverPrice;
    }

    public Double getDriverVatTax() {
        return driverVatTax;
    }

    public void setDriverVatTax(Double driverVatTax) {
        this.driverVatTax = driverVatTax;
    }

    public Double getPerDriverTax() {
        return perDriverTax;
    }

    public void setPerDriverTax(Double perDriverTax) {
        this.perDriverTax = perDriverTax;
    }

    public Double getReserveDriver() {
        return reserveDriver;
    }

    public void setReserveDriver(Double reserveDriver) {
        this.reserveDriver = reserveDriver;
    }

    public Double getReserveOrder() {
        return reserveOrder;
    }

    public void setReserveOrder(Double reserveOrder) {
        this.reserveOrder = reserveOrder;
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

    public Long getNumQuotation() {
        return numQuotation;
    }

    public void setNumQuotation(Long numQuotation) {
        this.numQuotation = numQuotation;
    }

    @JsonIgnore
    @Override
    public boolean parse(Record record) {
        try {
            this.quotationId = record.getString("QuotationId");        
            this.orderId = record.getString("OrderId");
            this.vehicleId = record.getLong("VehicleId");
            this.driverId = record.getLong("DriverId");
            this.price = record.getLong("Price");
            this.state = record.getLong("State");
            this.vat = record.getDouble("Vat");
            this.commission = record.getDouble("Commission");
            this.finesAmount = record.getDouble("FinesAmount");
            this.delayPrice = record.getDouble("DelayPrice");
            this.receivePrice = record.getDouble("ReceivePrice");
            this.deliverPrice = record.getDouble("DeliverPrice");
            this.driverVatTax = record.getDouble("DriverVatTax");
            this.perDriverTax = record.getDouble("PerDriverTax");
            this.reserveDriver = record.getDouble("ReserveDriver");
            this.reserveOrder = record.getDouble("ReserveOrder");
            this.receiveTime = record.getLong("ReceiveTime");
            this.toReceiveTime = record.getLong("ToReceiveTime");
            this.deliverTime = record.getLong("DeliverTime");
            this.toDeliverTime = record.getLong("ToDeliverTime");
            this.numQuotation = record.getLong("NumQuotation");            
        } catch (Exception ex) {
            logger.error(ex, ex);
            return false;
        }
        return true;
    }
    
    
    @JsonIgnore
    @Override
    public Bin[] toBins() {
        try {
            List<Bin> bins = new ArrayList<>();
            bins.add(new Bin("QuotationId", quotationId));
            bins.add(new Bin("OrderId", orderId));
            bins.add(new Bin("VehicleId", vehicleId));
            bins.add(new Bin("DriverId", driverId));
            bins.add(new Bin("Price", price));
            bins.add(new Bin("State", state));
            bins.add(new Bin("Vat", vat));
            bins.add(new Bin("Commission", commission));
            bins.add(new Bin("FinesAmount", finesAmount));
            bins.add(new Bin("DelayPrice", delayPrice));
            bins.add(new Bin("ReceivePrice", receivePrice));
            bins.add(new Bin("DeliverPrice", deliverPrice));
            bins.add(new Bin("DriverVatTax", driverVatTax));
            bins.add(new Bin("PerDriverTax", perDriverTax));
            bins.add(new Bin("ReserveDriver", reserveDriver));
            bins.add(new Bin("ReserveOrder", reserveOrder));
            bins.add(new Bin("ReceiveTime", receiveTime));
            bins.add(new Bin("ToReceiveTime", toReceiveTime));
            bins.add(new Bin("DeliverTime", deliverTime));
            bins.add(new Bin("ToDeliverTime", toDeliverTime));
            bins.add(new Bin("NumQuotation", numQuotation));
            
            return bins.toArray(new Bin[bins.size()]);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }
    
    
    @JsonIgnore
    @Override
    public boolean parse(Map map) {
       try {
            this.quotationId = (String) map.get("QuotationId");
            this.orderId = (String) map.get("OrderId");
            this.vehicleId = (Long) map.get("VehicleId");
            this.driverId = (Long) map.get("DriverId");
            this.price = (Long) map.get("Price");
            this.state = (Long) map.get("State");
            this.vat = (Double) map.get("Vat");
            this.commission = (Double) map.get("Commission");
            this.finesAmount = (Double) map.get("FinesAmount");
            this.delayPrice = (Double) map.get("DelayPrice");
            this.receivePrice = (Double) map.get("ReceivePrice");
            this.deliverPrice = (Double) map.get("DeliverPrice");
            this.driverVatTax = (Double) map.get("DriverVatTax");
            this.perDriverTax = (Double) map.get("PerDriverTax");
            this.reserveDriver = (Double) map.get("ReserveDriver");
            this.reserveOrder = (Double) map.get("ReserveOrder");
            this.receiveTime = (Long) map.get("ReceiveTime");
            this.toReceiveTime = (Long) map.get("ToReceiveTime");
            this.deliverTime = (Long) map.get("DeliverTime");
            this.toDeliverTime = (Long) map.get("ToDeliverTime");
            this.numQuotation = (Long) map.get("NumQuotation");
            return true;
        } catch (Exception ex) {
            logger.error(ex, ex);
            return false;
        }
    }
    
    
}
