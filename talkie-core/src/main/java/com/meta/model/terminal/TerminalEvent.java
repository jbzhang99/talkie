package com.meta.model.terminal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meta.model.BaseEntity;
import com.meta.model.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by lhq on 2017/11/16.
 */
@Entity
@Table(name = "talkie_t_terminal_event")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "终端操作基本信息", description = "终端操作基本信息")
public class TerminalEvent extends BaseEntity {

    /**
     * 与终端一对一  和用户一对一
     */


    @ApiModelProperty(value = "用户")
    private User user;

    /**
     * ip
     */
    @ApiModelProperty("ip")
    private String ip;



    @Column(name = "ip",length = 100)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
