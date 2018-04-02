package com.meta.model.qmanage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meta.model.BaseEntity;
import com.meta.model.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by llin on 2017/9/29.
 */
@Entity
@Table(name = "talkie_q_general_agent_event")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "Q币总代操作事件信息", description = "Q币总代操作事件基本信息")
public class QGeneralAgentEvent extends BaseEntity {


    /**
     * 用户表 多对一
     */
    @ApiModelProperty(value = "用户表  多对一")
    private User user;

    /**
     * 事件类型  1== 划分 , 2== 回收 3==充值
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



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "type", length = 3)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "value")
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Column(name = "remark", length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "traget_user_id", length = 32)
    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }

    @Column(name = "trarget_account", length = 32)
    public String getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
    }

    @Column(name = "source_user_id", length = 32)
    public Long getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(Long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    @Column(name = "source_account", length = 32)
    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

}
