package com.meta.model.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meta.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by llin on 2017/9/29.
 */
@Entity
@Table(name = "talkie_t_menu")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "菜单基本信息", description = "菜单基本信息")
public class Menu extends BaseEntity {


    @ApiModelProperty(value = "父ID")
    private Long parentId;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    @Size(min = 2, message = "请输入长度最少为2位的名称")
    private String name;


    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;


    /**
     * URL(一级菜单时需输入不可重复的序号，二级菜单时需输入URL)
     */
    @ApiModelProperty(value = "URL")
    private String url;
    @Transient
    private List<Menu> children;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private String sorts;

    /**
     * 菜单是否隐藏  1== 否  ，2== 是
     */
    @ApiModelProperty(value = "菜单是否隐藏  1== 否  ，2== 是")
    @NotBlank(message = "隐藏不能为空")
    private String isHide;
    /**
     * @return
     */
    @ApiModelProperty(value = "菜单与权限   一对多")
    private List<MenuRole> menuRoleList;

    @ApiModelProperty(value = "备注")
    private String remark;


    @Column(name = "parent_id", length = 32, nullable = false)
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Column(name = "name", length = 50, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "url", length = 2000, nullable = false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "sorts", nullable = false)
    public String getSorts() {
        return sorts;
    }

    public void setSorts(String sorts) {
        this.sorts = sorts;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu", cascade = {CascadeType.ALL})
    @JsonIgnore
    public List<MenuRole> getMenuRoleList() {
        return menuRoleList;
    }

    public void setMenuRoleList(List<MenuRole> menuRoleList) {
        this.menuRoleList = menuRoleList;
    }

    @Column(name = "icon")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIsHide() {
        return isHide;
    }

    public void setIsHide(String isHide) {
        this.isHide = isHide;
    }

    @Transient
    public List<Menu> getChildren() {
        return children;
    }

    @Transient
    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    @Column(name = "remark", length = 100)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
