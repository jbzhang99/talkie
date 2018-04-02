package com.meta.model.menu;

import com.meta.model.MBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by llin on 2017/9/29.
 */
@ApiModel(value = "菜单基本信息", description = "菜单基本信息")
public class MMenu extends MBaseEntity {


    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "父ID")
    private Long parentId;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    @Size(min = 2, message = "请输入长度最少为2位的名称")
    private String name;


    /**
     * 菜单是否隐藏  1== 否  ，2== 是
     */
    @ApiModelProperty(value = "菜单是否隐藏  1== 否  ，2== 是")
    @NotBlank(message = "隐藏不能为空")
    private  String isHide;

    /**
     * URL
     */
    @ApiModelProperty(value = "URL")
    private String url;
    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private String sorts;


    /**
     * @return
     */
    @ApiModelProperty(value = "菜单与权限   一对多")
    private List<MMenuRole> mMenuRoleList;
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 子菜单
     *
     * @return
     */
    private     List<MMenu>  children;

    public List<MMenu> getChildren() {
        return children;
    }

    public void setChildren(List<MMenu> children) {
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSorts() {
        return sorts;
    }

    public void setSorts(String sorts) {
        this.sorts = sorts;
    }

    public List<MMenuRole> getmMenuRoleList() {
        return mMenuRoleList;
    }

    public void setmMenuRoleList(List<MMenuRole> mMenuRoleList) {
        this.mMenuRoleList = mMenuRoleList;
    }

    public String getIsHide() {
        return isHide;
    }

    public void setIsHide(String isHide) {
        this.isHide = isHide;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "MMenu{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", isHide='" + isHide + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", sorts='" + sorts + '\'' +
                ", mMenuRoleList=" + mMenuRoleList +
                ", remark='" + remark + '\'' +
                ", children=" + children +
                '}';
    }
}

