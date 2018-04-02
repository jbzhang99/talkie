package com.meta.model.addressDict;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lhq on 2017/10/12.
 * 中国城市对照表
 */
@ApiModel(value = "中国城市对照表", description = "中国城市对照表")
public class MAddressDict {


    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "等级")
    private String level;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "父ID")
    private String pid;

    @ApiModelProperty(value = "邮编")
    private String postCode;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
