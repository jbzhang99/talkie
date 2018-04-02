package com.meta.model.qmanage;

import com.meta.model.MBaseEntity;
import com.meta.model.user.MUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * Created by llin on 2017/9/29.
 */
@ApiModel(value = "Q币二级管理操作事件信息", description = "Q币二级管理操作事件基本信息")
public class MQSecEnterPriseEvent extends MBaseEntity {

    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 用户表 多对一
     */
    @ApiModelProperty(value = "用户表  多对一")
    private MUser user;

    /**
     * 事件类型  1== 划分 , 2== 回收
     */
    @ApiModelProperty(value = "事件类型  1== 划分 , 2== 回收")
    private Integer type;

    /**
     * 值 (转入/转出 ...Q币值)
     */
    @ApiModelProperty(value = "值 (转入/转出 ...Q币值)")
    private Double value;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;


    /**
     * 目标USERID
     */
    @ApiModelProperty(value = "目标USERID,,向目标转入")
    private Long targetUserId;

    /**
     * 目标Account
     */
    @ApiModelProperty(value = "目标USER,,向目标转入")
    private String targetAccount;


    /**
     * 转出方,.来源方的USERID
     */
    @ApiModelProperty(value = "转出方,.来源方的USERID")
    private Long sourceUserId;

    /**
     * 转出方,.来源方的USER
     */
    @ApiModelProperty(value = "转出方,.来源方的USERID")
    private String sourceAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MUser getUser() {
        return user;
    }

    public void setUser(MUser user) {
        this.user = user;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
    }

    public Long getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(Long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }
}
