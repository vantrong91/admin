/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.userinfo.BO;

import com.aerospike.client.Bin;
import com.aerospike.client.Record;
import com.aerospike.client.query.ResultSet;
import com.vtgo.vn.admin.base.BaseObject;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author viett
 */
public class Product implements BaseObject, Serializable {

    private static final Logger logger = Logger.getLogger(Product.class);

    private String nameProduct;
    private String volumn;
    private String stylePackage;
    private String numberProduct;

    public Map toMap() {
        Map<String, String> ret = new HashMap<>();
        ret.put("Name", nameProduct);
        ret.put("Volumn", volumn);
        ret.put("Package", stylePackage);
        ret.put("Quantity", numberProduct);
        return ret;
    }

    public boolean parseFromMap(Map data) {
        try {
            this.numberProduct = (String) data.get("Quantity");
            this.stylePackage = (String) data.get("Package");
            this.volumn = (String) data.get("Volumn");
            this.nameProduct = (String) data.get("Name");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getVolumn() {
        return volumn;
    }

    public void setVolumn(String volumn) {
        this.volumn = volumn;
    }

    public String getStylePackage() {
        return stylePackage;
    }

    public void setStylePackage(String stylePackage) {
        this.stylePackage = stylePackage;
    }

    public String getNumberProduct() {
        return numberProduct;
    }

    public void setNumberProduct(String numberProduct) {
        this.numberProduct = numberProduct;
    }

    public boolean parse(ResultSet rs) {
        //TODO Not using because data store in aerospike
        return false;
    }

    @Override
    public boolean parse(Record record) {
        return false;
    }

    @Override
    public boolean parse(Map<String, Object> map) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Bin[] toBins() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
