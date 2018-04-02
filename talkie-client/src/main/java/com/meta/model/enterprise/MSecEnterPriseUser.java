package com.meta.model.enterprise;

import com.meta.model.MBaseEntity;
import com.meta.model.user.MUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * Created by lhq on 2017/11/22.
 */
@ApiModel(value = "二级管理与用户基本信息", description = "二级管理与用户信息")
public class MSecEnterPriseUser extends MBaseEntity {

    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long byUserId;


    /**
     * 二级管理的ID;
     */
    @ApiModelProperty(value = "二级管理的ID;")
    private Long secEnterPriseId;

    /**
     * 一对多
     *
     * @return
     */
    private MUser user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getByUserId() {
        return byUserId;
    }

    public void setByUserId(Long byUserId) {
        this.byUserId = byUserId;
    }

    public Long getSecEnterPriseId() {
        return secEnterPriseId;
    }

    public void setSecEnterPriseId(Long secEnterPriseId) {
        this.secEnterPriseId = secEnterPriseId;
    }

    public MUser getUser() {
        return user;
    }

    public void setUser(MUser user) {
        this.user = user;
    }
}
