package com.meta.model.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by lhq on 2017/11/23.
 */
@ApiModel(value = "群组添加用户用", description = "群组添加用户用")
public class MGroupUser {

    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 用户名称
     *
     * @return
     */
    @ApiModelProperty("用户名称")
    private String name;

    /**
     * 账户
     *
     * @return
     */
    @ApiModelProperty("账户")
    private String account;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
