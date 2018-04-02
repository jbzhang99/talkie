package com.meta.model.merchant;


import com.meta.model.MBaseEntity;
import com.meta.model.qmanage.MQUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by Administrator on 2017/9/12
 */


@ApiModel(value = "代理基本信息", description = "代理基本信息")
public class MMerchant extends MBaseEntity {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "剩余Q币")
    private  Double remainQ;

    /**
     代理商   1==总代 ,  代理==2, 3=子代理 , 4==企业  ,5== 代理管理 ,6==信息查询 , 7 ==普通用户
     *
     * @return
     */
    @ApiModelProperty("代理商")
    private String merchantLevel;

    @ApiModelProperty(value = "代理商中文名")
    private  String merchantLevelName;
    //用户没有代理商地址的选项

    /**
     * 代理商国家
     */

    @ApiModelProperty(value = "代理商国家")
    private String country;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    /**
     * 代理商国家ID
     */
    @ApiModelProperty(value = "代理商国家ID")
    private Integer countryId;
    /**
     * 代理商省份
     */
    @ApiModelProperty("代理商省份")
    private String province;
    /**
     * 代理商省份ID
     */
    @ApiModelProperty("代理商省份ID")
    private Integer provinceId;
    /**
     * 代理商城市
     */
    @ApiModelProperty("代理商城市")
    private String city;
    /**
     * 代理城市ID
     */
    @ApiModelProperty(value = "代理城市ID")
    private Integer cityId;
    /**
     * 代理商 区/县
     */

    @ApiModelProperty("代理商区/县")
    private String county;

    /**
     * 代理商区/县ID
     */
    @ApiModelProperty(value = "代理商区/县")
    private Integer countyId;
    /**
     * 代理商街道
     */
    @ApiModelProperty("代理商街道")
    private String street;

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
     * 子代理的下属企业数量
     */
    @ApiModelProperty(value = "子代理的下属企业数量")
    private  Long countCompany;

    @ApiModelProperty(value = "企业下终端数量")
    private  Long countTerminal;
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

    @ApiModelProperty(value = "邮箱")
    private String eamil;


    /**
     * /**
     * 用户功能(用,间隔,,1=单呼,,2= 定位,3=好友,,4=群组,5= 录音)
     *
     * @return
     */
    @ApiModelProperty(value = "用户功能(用,间隔,,1=单呼,,2= 定位,3=好友,,4=群组,5= 录音)")
    private String funcs;

    @ApiModelProperty(value = "功能中文说明")

    private  String funcsName;

    /**
     * 逻辑删除  (1= 未删除,,2= 已删除)
     *
     * @return
     */
    private Integer isDel;


    /**
     * 设备账号前缀(代理代号+子代量代号(没有则不填 )+用户账号)
     *
     * @return
     */
    @ApiModelProperty("设备账号前缀(代理代号+子代量代号(没有则不填 )+用户账号)")
    private String prefixAccount;

    /**
     * 用户的Q 币表  一对一
     *
     * @return
     */
    @ApiModelProperty(value = "用户的Q币表  一对一")
    private MQUser mqUser;

    @ApiModelProperty(value = "分配 时的Q币值 ")
    private  Integer value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRemainQ() {
        return remainQ;
    }

    public void setRemainQ(Double remainQ) {
        this.remainQ = remainQ;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public Long getCountCompany() {
        return countCompany;
    }

    public void setCountCompany(Long countCompany) {
        this.countCompany = countCompany;
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

    public String getFuncs() {
        return funcs;
    }

    public void setFuncs(String funcs) {
        this.funcs = funcs;
    }

    public String getFuncsName() {
        return funcsName;
    }

    public void setFuncsName(String funcsName) {
        this.funcsName = funcsName;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getPrefixAccount() {
        return prefixAccount;
    }

    public void setPrefixAccount(String prefixAccount) {
        this.prefixAccount = prefixAccount;
    }

    public MQUser getMqUser() {
        return mqUser;
    }

    public void setMqUser(MQUser mqUser) {
        this.mqUser = mqUser;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getCountTerminal() {
        return countTerminal;
    }

    public void setCountTerminal(Long countTerminal) {
        this.countTerminal = countTerminal;
    }

    public String getEamil() {
        return eamil;
    }

    public void setEamil(String eamil) {
        this.eamil = eamil;
    }
}
