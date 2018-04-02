package com.meta.model.map;

import com.meta.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * create by lhq
 * create date on  18-1-30上午11:41
 *
 * @version 1.0
 **/
@Entity
@Table(name = "talkie_t_base_station")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "基站模型", discriminator = "基站模型")
public class BaseStation extends BaseEntity {


    /**
     * 纬度
     *
     * @return
     */
    @ApiModelProperty(value = "纬度")
    private Double oLatitude;

    /**
     * 经度
     *
     * @return
     */
    @ApiModelProperty(value = "经度 ")
    private Double oLongitude;


    /**
     * 纬度
     *
     * @return
     */
    @ApiModelProperty(value = "纬度")
    private Double latitude;

    /**
     * 经度
     *
     * @return
     */
    @ApiModelProperty(value = "经度 ")
    private Double longitude;


    /**
     * cellid 基站号
     */
    @ApiModelProperty(value = "cellid  基站号")
    private String cellId;

    /**
     * lac
     */
    @ApiModelProperty(value = "lac ")
    private String lac;


    /**
     * mnc
     */
    @ApiModelProperty(value = "mnc ")
    private String mnc;
    /**
     * nid NID网络识别码（各地区1-3个）
     */
    @ApiModelProperty(value = "nid NID网络识别码（各地区1-3个） ")
    private String nid;
    /**
     * sid  	SID系统识别码（各地区一个）
     */
    @ApiModelProperty(value = "sid  \tSID系统识别码（各地区一个） ")
    private String sid;


    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;


    /**
     * raggio  	覆盖范围，单位M
     */
    private String raggio;

    @Column(name = "o_latitude", length = 100)
    public Double getoLatitude() {
        return oLatitude;
    }

    public void setoLatitude(Double oLatitude) {
        this.oLatitude = oLatitude;
    }

    @Column(name = "o_longitude", length = 100)
    public Double getoLongitude() {
        return oLongitude;
    }

    public void setoLongitude(Double oLongitude) {
        this.oLongitude = oLongitude;
    }


    @Column(name = "latitude", length = 100)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Column(name = "longitude", length = 100)
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    @Column(name = "cell_id", length = 32)
    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    @Column(name = "lac", length = 32)
    public String getLac() {
        return lac;
    }

    public void setLac(String lac) {
        this.lac = lac;
    }

    @Column(name = "mnc", length = 32)
    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    @Column(name = "nid", length = 32)
    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    @Column(name = "sid", length = 32)
    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    @Column(name = "address", length = 500)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "raggio", length = 50)
    public String getRaggio() {
        return raggio;
    }

    public void setRaggio(String raggio) {
        this.raggio = raggio;
    }
}
