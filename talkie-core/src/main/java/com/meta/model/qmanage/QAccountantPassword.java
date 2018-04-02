package com.meta.model.qmanage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meta.model.BaseEntity;
import com.meta.model.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * create by lhq
 * create date on  18-2-26上午9:34
 *总代（会计）给代理分配Q币时，需提供密码
 * @version 1.0
 **/
@Entity
@Table(name = "talkie_q_accountant_password")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "代理分配Q币时需提供密码", description = "代理分配Q币时需提供密码")
public class QAccountantPassword extends BaseEntity {
    public QAccountantPassword() {
    }

    public QAccountantPassword(User user) {
        this.user = user;
    }

    @ApiModelProperty(value = "用户  一对一 ")
    private User user;

    @ApiModelProperty(value = "密码")
    private String password;

    @Column(name = "password", length = 32, nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
