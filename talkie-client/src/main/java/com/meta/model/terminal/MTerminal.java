package com.meta.model.terminal;

import com.meta.model.MBaseEntity;
import com.meta.model.user.MUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * Created by llin on 2017/9/28.
 */
@ApiModel(value = "终端基本信息",description = "终端基本信息")
public class MTerminal extends MBaseEntity {


    /**
     * *  设备终端表,, 每一个用户不一定要有一个, 对应用户的 终端ID,
     */


    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty("用户表   一对一")
    private MUser mUser;

    @ApiModelProperty(value = "名称")
    private  String name;


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

    public MUser getmUser() {
        return mUser;
    }

    public void setmUser(MUser mUser) {
        this.mUser = mUser;
    }

}
