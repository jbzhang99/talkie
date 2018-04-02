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
 * @author:lhq
 * @date:2017/12/21 9:07
 */
@Entity
@Table(name = "talkie_q_validate")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "Q币权限", description = "Q币权限")
public class QValidate extends BaseEntity {


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
