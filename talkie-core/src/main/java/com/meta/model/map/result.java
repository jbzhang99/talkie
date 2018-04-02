package com.meta.model.map;

/**
 * create by lhq
 * create date on  18-1-30下午4:56
 * 聚合数据 电信基站deom
 *
 * @version 1.0
 **/
public class result {
    private String sid;
    private String nid;
    private String bid;  //sellid
    private String lat;
    private String lon;
    private String o_lat;
    private String o_lon;
    private String address;
    private String raggio;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getO_lat() {
        return o_lat;
    }

    public void setO_lat(String o_lat) {
        this.o_lat = o_lat;
    }

    public String getO_lon() {
        return o_lon;
    }

    public void setO_lon(String o_lon) {
        this.o_lon = o_lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRaggio() {
        return raggio;
    }

    public void setRaggio(String raggio) {
        this.raggio = raggio;
    }
}
