package com.meta.model.heweather;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author:lhq
 * @date:2017/12/15 10:43
 */
public class MHeWeather {

    /**
     * 温度
     */
    private String heat;

    /**
     * 天气情况
     */
    private String weatherThing;

    /**
     * 风向等
     */
    private String wind;


    /**
     * 空气质量
     */

    private  String qlty;

    /**
     * pm25
     * @return
     */
    private  String pm25;
    public String getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }

    public String getWeatherThing() {
        return weatherThing;
    }

    public void setWeatherThing(String weatherThing) {
        this.weatherThing = weatherThing;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getQlty() {
        return qlty;
    }

    public void setQlty(String qlty) {
        this.qlty = qlty;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }
}
