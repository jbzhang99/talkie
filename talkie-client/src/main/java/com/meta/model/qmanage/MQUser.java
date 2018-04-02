package com.meta.model.qmanage;

import com.meta.model.MBaseEntity;
import com.meta.model.user.MUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Transient;

/**
 * Created by llin on 2017/9/29.
 */
@ApiModel(value = "Q币用户基本信息", description = "Q币用户基本信息")
public class MQUser extends MBaseEntity {


    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 用户表 一对一
     */
    @ApiModelProperty(value = "用户表  一对一")
    private MUser mUser;

    /**
     * 余额
     */
    @ApiModelProperty(value = "余额")
    private Double balance;


    /**
     * 已分配 的余额
     *
     * @return
     */
    @ApiModelProperty(value = "已分配 的余额")
    private Double alreadyBalance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public MUser getmUser() {
        return mUser;
    }

    public void setmUser(MUser mUser) {
        this.mUser = mUser;
    }

    public Double getAlreadyBalance() {
        return alreadyBalance;
    }

    public void setAlreadyBalance(Double alreadyBalance) {
        this.alreadyBalance = alreadyBalance;
    }
}
