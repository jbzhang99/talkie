package com.meta.model.boardCast;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meta.model.BaseEntity;
import com.meta.model.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


/**
 * Created by  on 2017/12/14.
 */
@Entity
@Table(name = "talkie_t_special_board_cast")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "总代操作特殊播报", description = "总代操作特殊播报")
public class GeneralAgentBoardCast extends BaseEntity {


    /**
     * 播报开关 1==启用， 2==停用
     */
    @ApiModelProperty(value = "播报开关  1== 启用 , 2== 停用")
    private String status;


    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String value;



    @ApiModelProperty(value = "代理ID 临时用")
    @Transient
    private Long proxyId;

    @ApiModelProperty(value = "user json 临时")
    @Transient
    private String  userJson;

    @ApiModelProperty(value = "用户  多对一")
    private User user;

    @Column(name="status",length = 3)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "remark", length = 100)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Column(name="value",length = 30,nullable = false)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Transient
    public Long getProxyId() {
        return proxyId;
    }
    @Transient
    public void setProxyId(Long proxyId) {
        this.proxyId = proxyId;
    }



    @Transient
    public String getUserJson() {
        return userJson;
    }
    @Transient
    public void setUserJson(String userJson) {
        this.userJson = userJson;
    }
}
