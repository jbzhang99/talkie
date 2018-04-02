package com.meta.model.group;

import com.meta.model.MBaseEntity;
import com.meta.model.user.MUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 * Created by llin on 2017/9/28.
 */
@ApiModel(value = "组基本信息", description = "组基本信息")
public class MGroup extends MBaseEntity {


    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 父ID
     */
    @ApiModelProperty(value = "父ID")
    private Long parentId;

    /**
     * 组名
     */
    @ApiModelProperty(name = "组名")
    private String name;

    /**
     * 用户    多对一
     */
    @ApiModelProperty(value = "用户   多对一")
    private MUser mUser;


    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 类型  1==普通组 .2==临时组. 3== 嵌套组
     */
    @ApiModelProperty(value = "类型")
    private String type;
    @ApiModelProperty(value = "类型中文名")
    private String typeName;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 加入密码  (临时组用)
     */
    @ApiModelProperty(value = "加入密码,,临时组用")
    private String inPutPassword;

    /**
     * 频道ID
     */
    @ApiModelProperty(value = "频道ID")
    private String channel;

    /**
     * 激活状态   1==激活, 2 == 灭活
     */
    @ApiModelProperty(value = "激活状态 1 == 激活 , 2== 灭活")
    private String status;
    @ApiModelProperty(value = "状态中文名")
    private String statusName;
    /**
     * 负责人手机
     */
    @ApiModelProperty(value = "负责人手机")
    private String phone;

    /**
     * 1== 企业建立  2== 二级管理建立
     */
    @ApiModelProperty(value = "1== 企业建立  2== 二级管理建立")
    private String level;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public MUser getmUser() {
        return mUser;
    }

    public void setmUser(MUser mUser) {
        this.mUser = mUser;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getInPutPassword() {
        return inPutPassword;
    }

    public void setInPutPassword(String inPutPassword) {
        this.inPutPassword = inPutPassword;
    }
}
