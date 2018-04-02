package com.meta.model.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meta.model.BaseEntity;
import com.meta.model.role.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by lhq on 2017/10/10.
 */
@Entity
@Table(name = "talkie_t_menu_role")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "菜单与权限的基本信息", description = "菜单与权限基本信息")
public class MenuRole extends BaseEntity {


    @ApiModelProperty(value = "菜单 多对一")
    private Menu menu;
    @ApiModelProperty(value = "权限 多对一 ")
    private Role role;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @JsonIgnore
    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @JsonIgnore
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
