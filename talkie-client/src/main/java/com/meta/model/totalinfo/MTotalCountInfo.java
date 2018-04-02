package com.meta.model.totalinfo;

import com.meta.model.MBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by llin on 2017/9/29.
 */
@ApiModel(value = "总代统计模型", description = "总代统计模型")
public class MTotalCountInfo   {

    /**
     * 代理总数
     */
    @ApiModelProperty("代理总数")
    private Integer totalMerchant;


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


    public Integer getTotalMerchant() {
        return totalMerchant;
    }

    public void setTotalMerchant(Integer totalMerchant) {
        this.totalMerchant = totalMerchant;
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
}
