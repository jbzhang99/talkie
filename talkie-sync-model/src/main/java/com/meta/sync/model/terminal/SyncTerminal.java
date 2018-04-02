package com.meta.sync.model.terminal;

import com.meta.sync.model.user.SyncUser;

/**
 * Created by llin on 2017/9/28.
 */

public class SyncTerminal {

    private Long id;
    /**
     * *  设备终端表,, 每一个用户不一定要有一个, 对应用户的 终端ID,
     */
    /**
     * 逻辑删除  (1= 未删除,,2= 已删除)
     *
     * @return
     */
    private Integer isDel;


    private String name;

    private SyncUser user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public SyncUser getUser() {
        return user;
    }

    public void setUser(SyncUser user) {
        this.user = user;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
