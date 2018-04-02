package com.meta.sync.model.group;

import com.meta.sync.model.user.SyncUser;


public class SyncGroup {

    private Long id;

    /**
     * 父ID
     */

    private Long parentId;

    /**
     * 状态
     */

    private String status;


    /**
     * 组名
     */

    private String name;

    /**
     * 用户    多对一
     */

    private SyncUser user;
    /**
     * 密码
     */

    private String password;
    /**
     * 备注
     */

    private String remark;

    /**
     * 类型  1==普通 组 .2==临时组. 3== 嵌套组
     */

    private Integer type;

    /**
     * 加入密码  (临时组用)
     */

    private String inPutPassword;

    /**
     * 退出密码(临时组用)
     */

    private String outPutPassword;

    /**
     * 频道ID
     */

    private String channel;

    /**
     * 1== 企业建立  2== 二级管理建立
     */

    private String level;

    /**
     * 负责人手机
     */

    private String phone;


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

    //user的主键   非表里的USERID

    public SyncUser getUser() {
        return user;
    }

    public void setUser(SyncUser user) {
        this.user = user;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getInPutPassword() {
        return inPutPassword;
    }

    public void setInPutPassword(String inPutPassword) {
        this.inPutPassword = inPutPassword;
    }

    public String getOutPutPassword() {
        return outPutPassword;
    }

    public void setOutPutPassword(String outPutPassword) {
        this.outPutPassword = outPutPassword;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
