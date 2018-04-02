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
 * Created by llin on 2017/9/28.
 */
@Entity
@Table(name = "talkie_t_sec_enterprise_user_friend")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "二级管理用户好友模型", description = "二级管理用户好友模型")
public class SecEnterPriseUserFriend extends BaseEntity {


    @ApiModelProperty("自己 的USERID")
    private Long selfUserId;
    /**
     * 用户表   多对一
     */
    @ApiModelProperty(value = "用户表  多对一")
    private User user;

    @ApiModelProperty("好友的USERiD")
    private Long friendUserId;


    /**
     * 好友USERID 临时用
     */
    @Transient
    private String friendUserIDTemp;

    @Transient
    public String getFriendUserIDTemp() {
        return friendUserIDTemp;
    }
    @Transient
    public void setFriendUserIDTemp(String friendUserIDTemp) {
        this.friendUserIDTemp = friendUserIDTemp;
    }

    /**
     * 组ID
     */
    @ApiModelProperty("组ID")
    @Transient
    private String groupId;



    @Column(name = "friend_user_id", length = 32,nullable = false)
    public Long getFriendUserId() {
        return friendUserId;
    }

    public void setFriendUserId(Long friendUserId) {
        this.friendUserId = friendUserId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    @Column(name = "self_user_id", length = 32)
    public Long getSelfUserId() {
        return selfUserId;
    }

    public void setSelfUserId(Long selfUserId) {
        this.selfUserId = selfUserId;
    }



    /**
     * 类型  1== 新增 ， 2== 删除
     */

    @Transient
    private String type;

    @Transient
    public String getType() {
        return type;
    }

    @Transient
    public void setType(String type) {
        this.type = type;
    }
}
