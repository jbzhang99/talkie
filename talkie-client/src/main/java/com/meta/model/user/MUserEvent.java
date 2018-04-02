package com.meta.model.user;

import com.meta.model.MBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by llin on 2017/9/28.
 */
@ApiModel(value = "用户基本信息", description = "用户基本信息")
public class MUserEvent extends MBaseEntity {

    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 用户事件类型, 1= 用户登录,,2= 用户登出,3= 新增用户, 4 =删除用户 , 5= 修改用户,6==新增群组,7=修改群组,
     */
    @ApiModelProperty(value = "用户事件类型")
    private Integer type;

    @ApiModelProperty(value = "类型中文名")
    private  String typeName;
    /**
     * ip
     */
    @ApiModelProperty("ip")
    private String ip;
    /**
     * 父ID
     */
    @ApiModelProperty(value = "父ID")
    private Long parentId;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
