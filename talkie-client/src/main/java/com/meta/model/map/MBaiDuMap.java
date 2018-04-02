package com.meta.model.map;

import com.meta.model.MBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author:lhq
 * @date:2017/12/8 16:28
 */
@ApiModel(value = "终端定位模型--基于百度地图", discriminator = "终端定位模型--基于百度地图")
public class MBaiDuMap extends MBaseEntity {


    /**
     * 类型    0== GPS ,1==基站 ，，2== WIFI
     */
    @ApiModelProperty(value = "类型 ")
    private String type;


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
