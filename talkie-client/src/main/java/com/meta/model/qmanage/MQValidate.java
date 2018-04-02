package com.meta.model.qmanage;

import com.meta.model.MBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author:lhq
 * @date:2017/12/21 9:07
 */
@ApiModel(value = "Q币权限", description = "Q币权限")
public class MQValidate extends MBaseEntity {


    @ApiModelProperty(value = "密码")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
