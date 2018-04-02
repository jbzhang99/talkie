package com.meta.model.enterprise;

import com.meta.model.MBaseEntity;
import com.meta.model.user.MUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by lhq on 2017/11/10.
 */
@ApiModel(value = "企业基本操作信息", description = "企业基本操作信息")
public class MEnterpriseEvent extends MBaseEntity {

    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 用户事件类型, 1= 用户登录,,2= 用户登出,3= 新增用户, 4 =删除用户 , 5= 修改用户,6= 新增群组,  7 == 修改群组, 8== 新增好友 ,9== 删除好友
     */
    @ApiModelProperty(value = "用户事件类型")
    private Integer type;


    @ApiModelProperty(value = "类型 中文名")
    private  String typeName;

    /**
     * 用户   多对一
     */
    @ApiModelProperty(value = "用户  多对一   ,,其中USERID  == USER的主键 ")
    private MUser user;
    /**
     * ip
     */
    @ApiModelProperty("ip")
    private String ip;


    /**
     * 被操作方
     */
    @ApiModelProperty(value = "被操作方account")
    private String  byUserAccount;
    /**

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public MUser getUser() {
        return user;
    }

    public void setUser(MUser user) {
        this.user = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getByUserAccount() {
        return byUserAccount;
    }

    public void setByUserAccount(String byUserAccount) {
        this.byUserAccount = byUserAccount;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
