package com.meta.model.totalinfo;

import com.meta.model.MBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lhq on 2017/9/29.
 */
@ApiModel(value = "代理统计模型", description = "代理统计模型")
public class MMerchantTotalCountInfo {


    /**
     * 企业总数
     */
    @ApiModelProperty("企业总数")
    private Integer totalEnterPrise;

    /**
     * 注册用户总数
     */
    @ApiModelProperty("注册用户总数")
    private Integer totalReginUser;
    /**
     * 使用用户总数
     */
    @ApiModelProperty("使用用户总数")
    private Integer totalUsedUser;
    /**
     * 在线用户总数
     */
    private Integer totalOnlineUser;
    /**
     * 子代理统计
     */
    @ApiModelProperty("子代理统计")
    private Integer totalSecMerchant;


    public Integer getTotalEnterPrise() {
        return totalEnterPrise;
    }

    public void setTotalEnterPrise(Integer totalEnterPrise) {
        this.totalEnterPrise = totalEnterPrise;
    }

    public Integer getTotalReginUser() {
        return totalReginUser;
    }

    public void setTotalReginUser(Integer totalReginUser) {
        this.totalReginUser = totalReginUser;
    }

    public Integer getTotalUsedUser() {
        return totalUsedUser;
    }

    public void setTotalUsedUser(Integer totalUsedUser) {
        this.totalUsedUser = totalUsedUser;
    }

    public Integer getTotalOnlineUser() {
        return totalOnlineUser;
    }

    public void setTotalOnlineUser(Integer totalOnlineUser) {
        this.totalOnlineUser = totalOnlineUser;
    }



    public Integer getTotalSecMerchant() {
        return totalSecMerchant;
    }

    public void setTotalSecMerchant(Integer totalSecMerchant) {
        this.totalSecMerchant = totalSecMerchant;
    }


}
