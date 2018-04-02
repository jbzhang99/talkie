package com.meta.model.enterprise;


import com.meta.model.MBaseEntity;
import com.meta.model.group.MGroup;
import com.meta.model.qmanage.MQUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Administrator on 2017/9/12
 */


@ApiModel(value = "企业(账号管理)基本信息", description = "企业(账号管理)基本信息")
public class MEnterpriseAccount extends MBaseEntity {

    @ApiModelProperty(value = "主键")
    private Long id;



    /**
     代理商   1==总代 ,  代理==2, 3=子代理 , 4==企业  ,5== 代理管理 ,6==信息查询 , 7 ==普通用户
     *
     * @return
     */
    @ApiModelProperty("代理商")
    private String merchantLevel;

    @ApiModelProperty(value = "代理商中文名")
    private  String merchantLevelName;

    /**
     * 用户名称
     *
     * @return
     */
    @ApiModelProperty("用户名称")
    private String name;
    /**
     * 父ID
     */
    @ApiModelProperty(value = "父ID")
    private  Long parentId;
    @ApiModelProperty(value = "固话")
    private  String telPhone;

    /**
     * 账户
     *
     * @return
     */
    @ApiModelProperty("账户")
    @Size(min = 2, max = 16, message = "账户长度在2-16之间")
    @NotBlank(message = "账户不能为空")
    private String account;

    /**
     * 密码
     *
     * @return
     */
    @ApiModelProperty("密码")
    @Size(min = 6, max = 32, message = "密码长度在6-16之间")
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 手机
     *
     * @return
     */
    @ApiModelProperty("手机")
    private String phone;

    /**
     * 身份证
     */
    @ApiModelProperty(value = "身份证")
    private String identityCard;

    /**
     * 状态   (1= 正常,,2=欠费 , 3=暂停)
     *
     * @return
     */


    @ApiModelProperty("状态   (1= 正常,,2=欠费 , 3=暂停)")
    private String status;
    @ApiModelProperty(value = "状态中文名")
    private  String statusName;

    /**
     * 备注
     *
     * @return
     */
    @ApiModelProperty(value = "备注")
    private String remark;


    /**
     * 逻辑删除  (1= 未删除,,2= 已删除)
     *
     * @return
     */
    private Integer isDel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerchantLevel() {
        return merchantLevel;
    }

    public void setMerchantLevel(String merchantLevel) {
        this.merchantLevel = merchantLevel;
    }

    public String getMerchantLevelName() {
        return merchantLevelName;
    }

    public void setMerchantLevelName(String merchantLevelName) {
        this.merchantLevelName = merchantLevelName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
