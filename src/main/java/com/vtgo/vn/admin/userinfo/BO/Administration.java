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
 * @author tvhdh
 */
public class Administration extends BaseRequest<Object> implements BaseObject{
    
    private static final Logger log = Logger.getLogger(Administration.class.getName());
    
    private Long pk;
    private Long id_cha;
    private Long maHC;
    private Long dmKhuVuc_ID;
    private Long capDo;
    private Long maTinh;
    private Long maHuyen;
    private String tenDinhDanh;
    private String tenDayDu;
    private String vietTat;
    
    @JsonIgnore
    @Override
    public boolean parse(Record record) {
        try {
            this.pk = record.getLong("PK");
            this.id_cha = record.getLong("ID_CHA");
            this.maHC = record.getLong("MAHC");
            this.dmKhuVuc_ID = record.getLong("DMKHUVUC_ID");
            this.capDo = record.getLong("CAPDO");
            this.maTinh = record.getLong("MATINH");
            this.maHuyen = record.getLong("MAHUYEN");
            this.tenDinhDanh = record.getString("TENDINHDANH");
            this.tenDayDu = record.getString("TENDAYDU");
            this.vietTat = record.getString("VIETTAT");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }
        return true;
    }
    @JsonIgnore
    @Override
    public boolean parse(Map map) {
        try {
            this.pk = (Long) map.get("PK");
            this.id_cha = (Long) map.get("ID_CHA");
            this.maHC = (Long) map.get("MAHC");
            this.dmKhuVuc_ID = (Long) map.get("DMKHUVUC_ID");
            this.capDo = (Long) map.get("CAPDO");
            this.maTinh = (Long) map.get("MATINH");
            this.maHuyen = (Long) map.get("MAHUYEN");
            this.tenDinhDanh = (String) map.get("TENDINHDANH");
            this.tenDayDu = (String) map.get("TENDAYDU");
            this.vietTat =(String) map.get("VIETTAT");
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }
    }
    
    @JsonIgnore
    @Override
    public Bin[] toBins() {
        List<Bin> bins = new ArrayList<>();
        bins.add(new Bin("PK", pk));
        bins.add(new Bin("ID_CHA", id_cha));
        bins.add(new Bin("MAHC", maHC));
        bins.add(new Bin("DMKHUVUC_ID", dmKhuVuc_ID));
        bins.add(new Bin("CAPDO", capDo));
        bins.add(new Bin("MATINH", maTinh));
        bins.add(new Bin("MAHUYEN", maHuyen));
        bins.add(new Bin("TENDINHDANH", tenDinhDanh));
        bins.add(new Bin("TENDAYDU", tenDayDu));
        bins.add(new Bin("VIETTAT", vietTat));
        return bins.toArray(new Bin[bins.size()]);
    }

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public Long getId_cha() {
        return id_cha;
    }

    public void setId_cha(Long id_cha) {
        this.id_cha = id_cha;
    }

    public Long getMaHC() {
        return maHC;
    }

    public void setMaHC(Long maHC) {
        this.maHC = maHC;
    }

    public Long getDmKhuVuc_ID() {
        return dmKhuVuc_ID;
    }

    public void setDmKhuVuc_ID(Long dmKhuVuc_ID) {
        this.dmKhuVuc_ID = dmKhuVuc_ID;
    }

    public Long getCapDo() {
        return capDo;
    }

    public void setCapDo(Long capDo) {
        this.capDo = capDo;
    }

    public Long getMaTinh() {
        return maTinh;
    }

    public void setMaTinh(Long maTinh) {
        this.maTinh = maTinh;
    }

    public Long getMaHuyen() {
        return maHuyen;
    }

    public void setMaHuyen(Long maHuyen) {
        this.maHuyen = maHuyen;
    }

    public String getTenDinhDanh() {
        return tenDinhDanh;
    }

    public void setTenDinhDanh(String tenDinhDanh) {
        this.tenDinhDanh = tenDinhDanh;
    }

    public String getTenDayDu() {
        return tenDayDu;
    }

    public void setTenDayDu(String tenDayDu) {
        this.tenDayDu = tenDayDu;
    }

    public String getVietTat() {
        return vietTat;
    }

    public void setVietTat(String vietTat) {
        this.vietTat = vietTat;
    }
}
