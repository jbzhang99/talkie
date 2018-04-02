package com.meta.model.boardcast;

import com.meta.model.MBaseEntity;
import com.meta.model.user.MUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * created by administrator on 2017/12/20
 */
@ApiModel(value = "总代特殊播报信息", description = "总代特殊播报信息")
public class MBoardCast extends MBaseEntity {


    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "备注")

    private String remark;


    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    @Size(max = 30, message = "内容长度在30个字以内")
    @NotBlank(message = "内容不能为空")
    private String value;

    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "状态中文名")
    private String statusName;

    @ApiModelProperty(value = "代理ID 临时用")
    private Long proxyId;

    @ApiModelProperty(value = "用户  多对一")
    private MUser user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public Long getProxyId() {
        return proxyId;
    }

    public void setProxyId(Long proxyId) {
        this.proxyId = proxyId;
    }

    public MUser getUser() {
        return user;
    }

    public void setUser(MUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "MBoardCast{" +
                "id=" + id +
                ", remark='" + remark + '\'' +
                ", value='" + value + '\'' +
                ", status='" + status + '\'' +
                ", statusName='" + statusName + '\'' +
                ", proxyId=" + proxyId +
                ", user=" + user +
                '}';
    }
}
