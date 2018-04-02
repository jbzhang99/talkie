package com.meta.model.qmanage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.meta.JsonViewConfig;
import com.meta.model.BaseEntity;
import com.meta.model.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by llin on 2017/9/29.
 */
@Entity
@Table(name = "talkie_q_user")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "Q币用户基本信息", description = "Q币用户基本信息")
public class QUser   {


    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 用户表 一对一
     */
    @ApiModelProperty(value = "用户表  一对一")
    private User user;

    /**
     * 可用余额
     */
    @ApiModelProperty(value = "可用余额")
    private Double balance;

    /**
     * 已分配 的余额
     *
     * @return
     */
    @ApiModelProperty(value = "已分配 的余额")
    private Double alreadyBalance;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, length = 32)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Column(name = "balance")
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }


    @Column(name = "already_balance")
    public Double getAlreadyBalance() {
        return alreadyBalance;
    }

    public void setAlreadyBalance(Double alreadyBalance) {
        this.alreadyBalance = alreadyBalance;
    }



    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    protected String createDate;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    protected Long createUser;

    /**
     * 修改日期
     */
    @ApiModelProperty(value = "修改日期")
    protected String modifyDate;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    protected Long modifyUser;



    @Column(name = "create_date")
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Column(name = "create_user", length = 32)
    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    @Column(name = "modify_date")
    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Column(name = "modify_user", length = 32)
    public Long getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Long modifyUser) {
        this.modifyUser = modifyUser;
    }


    /**
     * 当前登录ID
     */
    @Column(name = "current_user_id",length = 32)
    private Long currentUserId;

    public Long getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Long currentUserId) {
        this.currentUserId = currentUserId;
    }


//    @Override
//    public String toString() {
//        return "QUser{" +
//                "id=" + id +
//                ", user=" + user +
//                ", balance=" + balance +
//                ", alreadyBalance=" + alreadyBalance +
//                ", currentUserId=" + currentUserId +
//                '}';
//    }
}
