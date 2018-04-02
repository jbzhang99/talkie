package com.meta.model;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author:
 * @Date: 2016/12/12
 * @Time: 14:58
 * <p>
 * Description: 公共基础的对象属性，返回对象中均包含如下基础属性
 */
public class MBaseEntity implements Serializable {

    /**
     * 语言
     */
    private String language;
    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    private String createDate;

    /**
     * 使用者id
     */
    @ApiModelProperty(value = "创建人")
    private Long createUser;

    @ApiModelProperty(value = "创建人名称")
    private String createUserName;

    /**
     * 修改日期
     */
    @ApiModelProperty(value = "修改日期")
    private String modifyDate;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private Long modifyUser;

    @ApiModelProperty(value = "修改人名称")
    private String modifyUserName;

    /**
     * token
     */
    @ApiModelProperty(value = "token")
    private String Token;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Long modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getModifyUserName() {
        return modifyUserName;
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }



}
