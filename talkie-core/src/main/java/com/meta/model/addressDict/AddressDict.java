package com.meta.model.addressDict;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.Valid;

/**
 * Created by lhq on 2017/10/12.
 * 中国城市对照表
 */
@Entity
@Table(name = "talkie_address_dict")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "中国城市对照表", description = "中国城市对照表")
public class AddressDict {


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


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, length = 32)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "level", length = 9)
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Column(name = "name", length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "pid", length = 32)
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Column(name = "post_code", length = 9)
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
