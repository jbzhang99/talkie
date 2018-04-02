package com.meta.model.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meta.model.BaseEntity;
import com.meta.model.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 * Created by llin on 2017/9/28.
 */
@Entity
@Table(name = "talkie_t_group")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "组基本信息", description = "组基本信息")
public class Group extends BaseEntity {


    /**
     * 父ID
     */
    @ApiModelProperty(value = "父ID")
    private Long parentId;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态 1== 正常，2==欠费， 3== 暂停")
    @NotBlank(message = "状态 不能为空")
    private String status;


    /**
     * 组名
     */
    @ApiModelProperty(name = "组名")
    @NotBlank(message = "组名不能为空")
    private String name;

    /**
     * 用户    多对一
     */
    @ApiModelProperty(value = "用户   多对一")
    private User user;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 类型  1==普通 组 .2==临时组. 3== 嵌套组
     */
    @ApiModelProperty(value = "类型")
    private Integer type;

    /**
     * 加入密码  (临时组用)
     */
    @ApiModelProperty(value = "加入密码,,临时组用")
    private String inPutPassword;

    /**
     * 退出密码(临时组用)
     */
    @ApiModelProperty(value = "退出密码,.临时组用")
    private String outPutPassword;

    /**
     * 频道ID
     */
    @ApiModelProperty(value = "频道ID")
    private String channel;

    /**
     * 1== 企业建立  2== 二级管理建立
     */
    @ApiModelProperty(value = "1== 企业建立  2== 二级管理建立")
    private String level;

    /**
     * 负责人手机
     */
    @ApiModelProperty(value = "负责人手机")
    private String phone;


    @Column(name = "parent_id", length = 32)
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Column(name = "name", length = 50, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //user的主键   非表里的USERID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "remark", length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "type", length = 3)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    @Column(name = "channel", length = 32)
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }


    @Column(name = "phone", length = 11)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Column(name = "status", length = 3, nullable = false)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "level", length = 3)
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Column(name = "password", length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
