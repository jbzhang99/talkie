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
 * @author:lhq
 * @date:2017/12/8 16:28
 */
@Entity
@Table(name = "talkie_t_baidu_map")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "终端定位模型--基于百度地图", discriminator = "终端定位模型--基于百度地图")
public class BaiDuMap extends BaseEntity {


    /**
     * 类型    0== GPS ,1==基站 ，，2== WIFI
     */
    @ApiModelProperty(value = "类型 ")
    private String type;

    /**
     * 多对一  终端设备
     */
    @ApiModelProperty(value = "多对一，终端")

    private Terminal terminal;

    /**
     * IP
     *
     * @return
     */
    @ApiModelProperty(value = "IP")
    private String ip;

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
    private Double longitude;

    @Column(name = "type", length = 3, nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Column(name = "ip", length = 255)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Column(name = "latitude", length = 255)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Column(name = "longitude", length = 255)
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
