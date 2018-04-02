package com.meta.model.user;

import com.meta.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by llin on 2017/9/29.
 */
@Entity
@Table(name = "talkie_t_user_type")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "用户(含 代理商)权限模型", description = "用户含 (代理商)权限模型")
public class UserType extends BaseEntity {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    @Size(max = 50, min = 2, message = "长度在2-50之间")
    private String name;

    /**
     * 父ID
     */
    @ApiModelProperty(value = "父ID")
    private String parentId;


    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    @NotNull(message = "类型不能为空")
    @Min(1)
    private Integer type;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, length = 32)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "parent_id", length = 32)
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Column(name = "type", length = 3)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
