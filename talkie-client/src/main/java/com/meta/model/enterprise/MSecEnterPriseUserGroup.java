package com.meta.model.enterprise;

import com.meta.model.MBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by llin on 2017/9/28.
 */
@ApiModel(value = "二级管理用户组模型", description = "二级管理用户组模型")
public class MSecEnterPriseUserGroup extends MBaseEntity {

    @ApiModelProperty(value = "主键")
    /**
     * 主键
     */
    private Long id;

    /**
     * userId
     */
    @ApiModelProperty("userId")
    private Long userId;

    /**
     * 临时用， userid
     */
    private String userIdTemp;


    /**
     * 组ID
     */
    @ApiModelProperty("组ID ==主键 ")
    private Long groupId;

    /**
     * 组ID  临时用
     */
    private String groupIdTemp;


    /**
     * 类型   是群组加用户 == 1  ，，，用户加群组 ==2
     *
     * @return
     */
    private String type;

    /**
     * 事件类型  1、 指派事件  2、旁听事件
     */

    private int eventType;




    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getUserIdTemp() {
        return userIdTemp;
    }

    public void setUserIdTemp(String userIdTemp) {
        this.userIdTemp = userIdTemp;
    }

    public String getGroupIdTemp() {
        return groupIdTemp;
    }

    public void setGroupIdTemp(String groupIdTemp) {
        this.groupIdTemp = groupIdTemp;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }


}
