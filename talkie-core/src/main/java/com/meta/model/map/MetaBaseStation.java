package com.meta.model.map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meta.model.BaseEntity;
import com.meta.model.terminal.Terminal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * create by lhq
 * create date on  18-1-30上午11:41
 *
 * @version 1.0
 **/
@Entity
@Table(name = "talkie_t_meta_base_station")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "美拓基站模型", discriminator = "美拓基站模型")
public class MetaBaseStation extends BaseEntity {


    /**
     * 类型   0== GPS ,1==基站 ，，2== WIFI
     */
    @ApiModelProperty(value = "类型 0== GPS ,1==基站 ，，2== WIFI ")
    private String type;

    /**
     * carrieroperator 运营商
     */
    private Long carrieroperator;

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
     * cellid
     */
    @ApiModelProperty(value = "cellid ")
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
     * nid
     */
    @ApiModelProperty(value = "nid ")
    private String nid;
    /**
     * sid
     */
    @ApiModelProperty(value = "sid ")
    private String sid;


    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;

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
     * 多对一  终端设备
     */
    @ApiModelProperty(value = "多对一，终端")

    private Terminal terminal;


    /**
     * raggio  	覆盖范围，单位M
     */
    private String raggio;

    /**
     * terminalid
     *
     * @return
     */
    @Transient
    @ApiModelProperty("terminalId")
    private Long terminalId;

    @Column(name = "type", length = 3)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "terminal_id")
    @JsonIgnore
    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    @Column(name = "raggio", length = 50)
    public String getRaggio() {
        return raggio;
    }

    public void setRaggio(String raggio) {
        this.raggio = raggio;
    }

    @Column(name = "carrier_operator", length = 3)
    public Long getCarrieroperator() {
        return carrieroperator;
    }

    public void setCarrieroperator(Long carrieroperator) {
        this.carrieroperator = carrieroperator;
    }

    @Transient
    public Long getTerminalId() {
        return terminalId;
    }
    @Transient
    public void setTerminalId(Long terminalId) {
        this.terminalId = terminalId;
    }
}
