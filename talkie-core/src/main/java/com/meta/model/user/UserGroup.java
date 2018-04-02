package com.meta.model.user;

import com.meta.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * Created by llin on 2017/9/28.
 */
@Entity
@Table(name = "talkie_t_user_group")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "用户组模型", description = "用户组模型")
public class UserGroup extends BaseEntity {


    /**
     * userId
     */
    @ApiModelProperty("userId")
    private Long userId;

    /**
     * 临时用， userid
     */
    @Transient
    private String userIdTemp;
    @Transient
    public String getUserIdTemp() {
        return userIdTemp;
    }
    @Transient
    public void setUserIdTemp(String userIdTemp) {
        this.userIdTemp = userIdTemp;
    }

    /**
     * 组ID
     */
    @ApiModelProperty("组ID ==主键 ")
    private Long groupId;

    /**
     * 组ID  临时用
     */
    @Transient
    private String groupIdTemp;

    @Transient
    public String getGroupIdTemp() {
        return groupIdTemp;
    }

    @Transient
    public void setGroupIdTemp(String groupIdTemp) {
        this.groupIdTemp = groupIdTemp;
    }

    /**
     * 权限  ID
     */

    private Long roleId;




    /**
     * 类型   是群组加用户 == 1  ，，，用户加群组 ==2
     *
     * @return
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

    @Column(name = "user_id", length = 32)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "group_id", length = 32)
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Column(name = "role_id", length = 32)
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
