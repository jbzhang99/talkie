package com.meta.model.totolInfo;

import com.meta.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * Created by llin on 2017/9/29.
 */
@ApiModel(value = "企业统计模型", description = "企业统计模型")
public class EnterPriseTotalCountInfo  {

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
     * 群组个数
     */
    @ApiModelProperty(value = "群组个数")
    private Integer totalGroup ;



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

    public Integer getTotalGroup() {
        return totalGroup;
    }

    public void setTotalGroup(Integer totalGroup) {
        this.totalGroup = totalGroup;
    }
}
