package com.meta.model.role;

import com.meta.model.MBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by llin on 2017/9/28.
 */
@ApiModel(value = "权限模型", description = "权限模型")
public class MRole extends MBaseEntity {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    @Size(max = 50, min = 2, message = "长度在2-50之间")
    private String name;

    /**
     * 类型
     */
    @ApiModelProperty("类型")
    @Min(1)
    @NotNull(message = "类型不能为空")
    private Integer type;
    /**
     * 父ID
     */
    @ApiModelProperty(value = "父ID")
    private String parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
