package com.meta.model.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meta.model.BaseEntity;
import com.meta.model.menu.MenuRole;
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
import java.util.List;

/**
 * Created by llin on 2017/9/28.
 */
@Entity
@Table(name = "talkie_t_role")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "权限模型", description = "权限模型")
public class Role extends BaseEntity {


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
    private Long parentId;

    /**
     * 菜单与权限
     *
     * @return
     */
    private List<MenuRole> menuRoleList;


    @Column(name = "name", length = 50,nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "parent_id", length = 32)
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Column(name = "type", length = 3)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade = {CascadeType.ALL})
    @JsonIgnore
    public List<MenuRole> getMenuRoleList() {
        return menuRoleList;
    }

    public void setMenuRoleList(List<MenuRole> menuRoleList) {
        this.menuRoleList = menuRoleList;
    }
}
