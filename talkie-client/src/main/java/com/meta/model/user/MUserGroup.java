package com.meta.model.user;

import com.meta.model.MBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Transient;


/**
 * Created by llin on 2017/9/28.
 */
@ApiModel(value = "用户组模型", description = "用户组模型")
public class MUserGroup extends MBaseEntity {

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
    private String groupIdTemp;
    private String userIdTemp;


    /**
     * 组ID
     */
    @ApiModelProperty("组ID ==主键 ")
    private Long groupId;

    /**
     * 权限  ID
     */

    private Long roleId;


    /**
     * 类型   是群组加用户 == 1  ，，，用户加群组 ==2
     *
     * @return
     */
    private String type;




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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getGroupIdTemp() {
        return groupIdTemp;
    }

    public void setGroupIdTemp(String groupIdTemp) {
        this.groupIdTemp = groupIdTemp;
    }

    public String getUserIdTemp() {
        return userIdTemp;
    }

    public void setUserIdTemp(String userIdTemp) {
        this.userIdTemp = userIdTemp;
    }
}
