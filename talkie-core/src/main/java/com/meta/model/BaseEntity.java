package com.meta.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Entity - 基类
 * Description: 公共基础的对象属性，每个对象均要继承BaseEntity
 */
@EntityListeners(EntityListener.class)
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    protected String createDate;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    protected Long createUser;

    /**
     * 修改日期
     */
    @ApiModelProperty(value = "修改日期")
    protected String modifyDate;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    protected Long modifyUser;

    /**
     * 语言
     * @return
     */
    @Transient
    private  String language;

    @Transient
    public String getLanguage() {
        return language==null?"zh":language;
    }
    @Transient
    public void setLanguage(String language) {
        this.language = language;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, length = 32)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "create_date")
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Column(name = "create_user", length = 32)
    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    @Column(name = "modify_date")
    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Column(name = "modify_user", length = 32)
    public Long getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Long modifyUser) {
        this.modifyUser = modifyUser;
    }
}
