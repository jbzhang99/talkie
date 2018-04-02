package com.meta.model.menu;

import com.meta.model.MBaseEntity;
import com.meta.model.role.MRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * Created by lhq on 2017/10/10.
 */
@ApiModel(value = "菜单与权限的基本信息", description = "菜单与权限基本信息")
public class MMenuRole extends MBaseEntity {


    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "菜单 多对一")
    private MMenu menu;
    @ApiModelProperty(value = "权限 多对一 ")
    private MRole mRole;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MMenu getMenu() {
        return menu;
    }

    public void setMenu(MMenu menu) {
        this.menu = menu;
    }

    public MRole getmRole() {
        return mRole;
    }

    public void setmRole(MRole mRole) {
        this.mRole = mRole;
    }
}
