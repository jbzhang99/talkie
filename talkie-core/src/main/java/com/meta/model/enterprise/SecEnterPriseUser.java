package com.meta.model.enterprise;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meta.model.BaseEntity;
import com.meta.model.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by lhq on 2017/11/22.
 */
@Entity
@Table(name = "talkie_t_sec_enterprise_user")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "二级管理与用户基本信息", description = "二级管理与用户信息")
public class SecEnterPriseUser extends BaseEntity {


    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long byUserId;


    /**
     * 二级管理的ID;
     */
    @ApiModelProperty(value = "二级管理的ID;")
    private Long secEnterPriseId;

    /**
     *     一对多
     * @return
     */
    private User user;


    @Column(name = "by_user_id",unique = true,length = 32,nullable = false)
    public Long getByUserId() {
        return byUserId;
    }

    public void setByUserId(Long byUserId) {
        this.byUserId = byUserId;
    }

    @Column(name = "sec_enterprise_id",length = 32,nullable = false)
    public Long getSecEnterPriseId() {
        return secEnterPriseId;
    }

    public void setSecEnterPriseId(Long secEnterPriseId) {
        this.secEnterPriseId = secEnterPriseId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
