package com.meta.model.accountant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meta.model.BaseEntity;
import com.meta.model.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by lhq on 2017/11/10.
 */
@Entity
@Table(name = "talkie_t_accountant_event")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "会计基本操作信息", description = "会计基本操作信息")
public class AccountantEvent extends BaseEntity {


    /**
     * 用户事件类型, 1= 用户登录,,2= 用户登出,3= 新增用户, 4 =删除用户 , 5= 修改用户,6= 新增群组,  7 == 修改群组, 8== 新增好友 ,9== 删除好友
     */
    @ApiModelProperty(value = "用户事件类型")
    @NotNull(message = "用户事件类型不能为空")
    private Integer type;

    /**
     * 用户   多对一
     */
    @ApiModelProperty(value = "用户  多对一   ,,其中USERID  == USER的主键 ")
    private User user;
    /**
     * ip
     */
    @ApiModelProperty("ip")
    private String ip;

    /**
     * 被操作方
     */
    @ApiModelProperty(value = "被操作方account")
    private String byUserAccount;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String remark;



    @Column(name = "type", length = 3 ,nullable = false)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "ip", length = 200)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Column(name = "remark", length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "by_user_account", length = 32)
    public String getByUserAccount() {
        return byUserAccount;
    }

    public void setByUserAccount(String byUserAccount) {
        this.byUserAccount = byUserAccount;
    }



}
