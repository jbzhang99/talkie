package com.meta.model.totolInfo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author:lhq
 * @date:2017/12/4 17:20
 */
public class TotalCountInfo {


    private  Integer totalMerchant;
    /**
     * 注册用户总数
     */
    @ApiModelProperty("注册用户总数")
    private Integer totalReginUser ;
    /**
     * 使用用户总数
     */
    @ApiModelProperty("使用用户总数")
    private Integer totalUsedUser ;
    /**
     * 在线用户总数
     */
    private Integer totalOnlineUser ;


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
