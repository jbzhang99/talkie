package com.meta.sync.model.user;

public class SyncUser {

    private Long id;


    /**
     * 公司名称
     */

    private String companyName;

    /**
     * 代理商   1==总代 ,  代理==2, 3=子代理 , 4==企业  ,5== 总代代理管理 ,6==总代信息查询 , 7 ==普通用户 ,8==（代理）信息查询 ,9==(代理)企业管理，
     * 10==子代理信息查询 ，，11== 子代理企业管理  ,,12=企业的信息查询  ，，13==企业的用户管理 ,, 14== 二级企业
     * 15 == 二级企业的信息查询
     *
     * @return
     */

    private String merchantLevel;


    //用户没有代理商的选项

    /**
     * 代理商国家
     */

    private String country;

    /**
     * 代理商国家ID
     */

    private Integer countryId;
    /**
     * 代理商省份
     */

    private String province;
    /**
     * 代理商省份ID
     */

    private Integer provinceId;
    /**
     * 代理商城市
     */

    private String city;
    /**
     * 代理城市ID
     */

    private Integer cityId;
    /**
     * 代理商 区/县
     */


    private String county;

    /**
     * 代理商区/县ID
     */

    private Integer countyId;
    /**
     * 代理商街道
     */

    private String street;

    /**
     * 用户名称
     *
     * @return
     */

    private String name;

    /**
     * 账户
     *
     * @return
     */

    private String account;

    /**
     * 密码
     *
     * @return
     */

    private String password;

    /**
     * 父ID
     */

    private Long parentId;
    /**
     * 手机
     *
     * @return
     */

    private String phone;


    private String telPhone;

    /**
     * 身份证
     */

    private String identityCard;

    /**
     * 状态   (1= 正常,,2=欠费 , 3=暂停)
     *
     * @return
     */


    private String status;

    /**
     * 备注
     *
     * @return
     */

    private String remark;

    /**
     * /**
     * 用户功能(用,间隔,,1=单呼,,2= 定位,3=好友,,5=群组,4= 录音)
     *
     * @return
     */

    private String funcs;

    /**
     * 逻辑删除  (1= 未删除,,2= 已删除)
     *
     * @return
     */

    private Integer isDel;

    /**
     * 设备终端  一对一
     */

    private Long terminalId;


    /**
     * 设备账号前缀(代理代号+子代量代号(没有则不填 )+用户账号)
     *
     * @return
     */

    private String prefixAccount;


    private String voicePrio;// 设备对讲优先级　　0 低，　1 中，　2 高， 3 特高


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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getMerchantLevel() {
        return merchantLevel;
    }

    public void setMerchantLevel(String merchantLevel) {
        this.merchantLevel = merchantLevel;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Long getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Long terminalId) {
        this.terminalId = terminalId;
    }

    public String getVoicePrio() {
        return voicePrio;
    }

    public void setVoicePrio(String voicePrio) {
        this.voicePrio = voicePrio;
    }


}
