package com.meta.model.user;

import com.meta.model.MBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Transient;

/**
 * Created by llin on 2017/9/28.
 */
@ApiModel(value = "用户好友模型", description = "用户好友模型")
public class MUserFriend extends MBaseEntity {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty("自己 的USERID")
    private Long selfUserId;
    /**
     * 用户表   多对一
     */
    @ApiModelProperty(value = "用户表  多对一")
    private MUser mUser;

    @ApiModelProperty("好友的USERiD")
    private Long friendUserId;

    /**
     * 好友USERID 临时用
     */
    private String friendUserIDTemp;

    public String getFriendUserIDTemp() {
        return friendUserIDTemp;
    }

    public void setFriendUserIDTemp(String friendUserIDTemp) {
        this.friendUserIDTemp = friendUserIDTemp;
    }

    @ApiModelProperty(value = "类型  1== 新增 ，，2 == 删除")
    private  String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MUser getmUser() {
        return mUser;
    }

    public void setmUser(MUser mUser) {
        this.mUser = mUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSelfUserId() {
        return selfUserId;
    }

    public void setSelfUserId(Long selfUserId) {
        this.selfUserId = selfUserId;
    }

    public Long getFriendUserId() {
        return friendUserId;
    }

    public void setFriendUserId(Long friendUserId) {
        this.friendUserId = friendUserId;
    }
}
