package com.meta.model.datatotal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * create by lhq
 * create date on  18-3-1上午10:46
 * 根据年份及月分统计各个代理商的划/回记录
 *
 * @version 1.0
 **/
@ApiModel(value = "根据年份及月分统计各个代理商的划/回记录  ", description = "根据年份及月分统计各个代理商的划/回记录  ")
public class MQMerchantTotal {
    /**
     * 用户名称
     *
     * @return
     */
    @ApiModelProperty("用户名称")
    private String name;

    /**
     * 手机
     *
     * @return
     */
    @ApiModelProperty("手机")
    private String phone;

    /**
     * 账户
     *
     * @return
     */
    @ApiModelProperty("账户")
    private String account;

    /**
     * 划分
     */
    @ApiModelProperty("划分")
    private Double place;

    /**
     * 回收
     */
    @ApiModelProperty("回收")
    private Double recover;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Double getPlace() {
        return place;
    }

    public void setPlace(Double place) {
        this.place = place;
    }

    public Double getRecover() {
        return recover;
    }

    public void setRecover(Double recover) {
        this.recover = recover;
    }
}
