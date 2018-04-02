package com.meta.sync.model.quser;

import com.meta.sync.model.user.SyncUser;

/**
 * Created by llin on 2017/9/29.
 */

public class SyncQUser {

    private Long id;

    /**
     * 用户表 一对一
     */
    private SyncUser user;

    /**
     * 可用余额
     */

    private Double balance;

    /**
     * 已分配 的余额
     *
     * @return
     */

    private Double alreadyBalance;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SyncUser getUser() {
        return user;
    }

    public void setUser(SyncUser user) {
        this.user = user;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getAlreadyBalance() {
        return alreadyBalance;
    }

    public void setAlreadyBalance(Double alreadyBalance) {
        this.alreadyBalance = alreadyBalance;
    }



    /**
     * 创建日期
     */

    protected String createDate;

    /**
     * 创建人
     */

    protected Long createUser;

    /**
     * 修改日期
     */

    protected String modifyDate;

    /**
     * 修改人
     */

    protected Long modifyUser;


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


    /**
     * 当前登录ID
     */

    private Long currentUserId;

    public Long getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Long currentUserId) {
        this.currentUserId = currentUserId;
    }


}
