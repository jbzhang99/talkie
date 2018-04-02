package com.meta.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meta.model.BaseEntity;
import com.meta.model.FileData.Audio;
import com.meta.model.boardCast.GeneralAgentBoardCast;
import com.meta.model.enterprise.EnterpriseEvent;
import com.meta.model.enterprise.SecEnterPriseUser;
import com.meta.model.group.Group;
import com.meta.model.merchant.MerchantEvent;
import com.meta.model.qmanage.*;
import com.meta.model.terminal.Terminal;
import com.meta.model.terminal.TerminalEvent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Administrator on 2017/9/12
 */


@Entity
@Table(name = "talkie_t_user", uniqueConstraints = {@UniqueConstraint(name = "Talkie_user_id", columnNames = {"id"})})
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "用户基本信息", description = "用户基本信息")
public class User extends BaseEntity {

    @ApiModelProperty(value = "主键")
    private Long id;


    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    /**
     * 代理商   1==总代 ,  代理==2, 3=子代理 , 4==企业  ,5== 总代代理管理 ,6==总代信息查询 , 7 ==普通用户 ,8==（代理）信息查询 ,9==(代理)企业管理，
     * 10==子代理信息查询 ，，11== 子代理企业管理  ,,12=企业的信息查询  ，，13==企业的用户管理 ,, 14== 二级企业
     * 15 == 二级企业的信息查询  16=会计
     *
     * @return
     */

    @ApiModelProperty("代理商")
    @NotBlank(message = "权限不能为空")
    private String merchantLevel;


    //用户没有代理商的选项

    /**
     * 代理商国家
     */

    @ApiModelProperty(value = "代理商国家")
    private String country;

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
     * 账户
     *
     * @return
     */
    @ApiModelProperty("账户")
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
     * 父ID
     */
    @ApiModelProperty(value = "父ID")
    private Long parentId;
    /**
     * 手机
     *
     * @return
     */
    @ApiModelProperty("手机")
    private String phone;

    @ApiModelProperty(value = "固话")
    private String telPhone;

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
     * 用户功能(用,间隔,,1=单呼,,2= 定位,3=好友,,5=群组,4= 录音)
     *
     * @return
     */
    @ApiModelProperty("用户功能(用,间隔,,1=单呼,,2= 定位,3=好友,,5=群组,4= 录音)")
    private String funcs;

    /**
     * 逻辑删除  (1= 未删除,,2= 已删除)
     *
     * @return
     */
    @ApiModelProperty("逻辑删除  (1= 未删除,,2= 已删除)")
    private Integer isDel;

    /**
     * 设备终端  一对一
     */
    @ApiModelProperty("设备终端 一对一")
    private Terminal terminal;

    /**
     * 终端操作  一对一
     */
    @ApiModelProperty(value = "终端操作  一对一")
    private TerminalEvent terminalEvent;
    /**
     * 设备账号前缀(代理代号+子代量代号(没有则不填 )+用户账号)
     *
     * @return
     */
    @ApiModelProperty("设备账号前缀(代理代号+子代量代号(没有则不填 )+用户账号)")
    private String prefixAccount;

    @ApiModelProperty("终端账号分为两种一直是账号用户，另外一种是ICCID用户")
    private int accountType;
    @ApiModelProperty("ICCID用户独有属性，用于判断是否已经绑定终端")
    private int accountStatus; //0 还未绑定 1 已经绑定
    /**
     * e用户操作事件  一对多
     *
     * @return
     */
    @ApiModelProperty(value = "用户操作事件  一对多")
    private List<UserEvent> userEventListt;


    /**
     * 企业二级管理 与用户关第  一对多
     */
    private List<SecEnterPriseUser> secEnterPriseUserList;
    /**
     * 总代Q币操作记录  一对多
     */
    @ApiModelProperty(value = "总代Q币操作记录  一对多")
    private List<QGeneralAgentEvent> qGeneralAgentEventList;

    /**
     * 代理Q币操作记录  一对多
     */
    @ApiModelProperty(value = "代理Q币操作记录， 一对多")
    private List<QMerchantEvent> qMerchantEventList;


    /**
     * 企业 Q币操作记录  一对多
     */
    @ApiModelProperty(value = "企业 Q币操作记录  一对多")
    private List<QEnterpriseEvent> qEnterpriseEventList;
    /**
     * 企业 操作事件  一对多
     */
    @ApiModelProperty(value = "企业 操作事件，，一对多")
    private List<EnterpriseEvent> enterpriseEventList;

    /**
     * 代理操作事件  一对多
     */
    @ApiModelProperty(value = "代理操作事件，，一对多")
    private List<MerchantEvent> merchantEventList;
    /**
     * 用户好友  一对多
     *
     * @return
     */
    private List<UserFriend> userFriendList;


    /**
     * 用户的Q 币表  一对一
     *
     * @return
     */
    @ApiModelProperty(value = "用户的Q币表  一对一")
    private QUser qUser;


    @ApiModelProperty(value = "录音数据表 一对多")
    private List<Audio> audioList;


    /**
     * 代理分配Q币时的密码
     */
    @ApiModelProperty(value = "代理分配Q币时的密码")
   private  QAccountantPassword qAccountantPassword;

    /**
     * 用户的操作Q币事件表  一对多
     *
     * @return
     */
    @ApiModelProperty("用户的操作Q币事件表  一对多")
    private List<QUserEvent> qUserEventsList;

    @ApiModelProperty("设备对讲优先级　　0 低，　1 中，　2 高， 3 特高")
    private String voicePrio;// 设备对讲优先级　　0 低，　1 中，　2 高， 3 特高


    /**
     * bobao
     */
    private  List<GeneralAgentBoardCast> generalAgentBoardCastListl;

    @ApiModelProperty("用户iccId")
    @Column(name ="icc_id" ,length = 100)
    private  String iccId;


    /**
     * 用户与组  一对多
     *
     * @return
     */

    @ApiModelProperty(value = "用户与组  一对多")
    private List<Group> groupList;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, length = 32)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(name = "name", length = 32, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "account", length = 255, nullable = false, unique = true)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    @Column(name = "password", length = 32, nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "phone",  length = 11)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "status", length = 9, nullable = false)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "remark", length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @Column(name = "funcs", length = 32)
    public String getFuncs() {
        return funcs;
    }

    public void setFuncs(String funcs) {
        this.funcs = funcs;
    }

    @Column(name = "is_del", length = 9, nullable = false)
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    @Column(name = "prefix_account", length = 200)
    public String getPrefixAccount() {
        return prefixAccount;
    }

    public void setPrefixAccount(String prefixAccount) {
        this.prefixAccount = prefixAccount;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonIgnore
    public List<UserEvent> getUserEventListt() {
        return userEventListt;
    }

    public void setUserEventListt(List<UserEvent> userEventListt) {
        this.userEventListt = userEventListt;
    }

    @Column(name = "merchant_level")
    public String getMerchantLevel() {
        return merchantLevel;
    }

    public void setMerchantLevel(String merchantLevel) {
        this.merchantLevel = merchantLevel;
    }

    @Column(name = "country", length = 100)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "province", length = 100)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "city", length = 100)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "county", length = 100)
    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Column(name = "street", length = 200)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Column(name = "identity_card", length = 18)
    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonIgnore
    public List<UserFriend> getUserFriendList() {
        return userFriendList;
    }

    public void setUserFriendList(List<UserFriend> userFriendList) {
        this.userFriendList = userFriendList;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonIgnore
    public QUser getqUser() {
        return qUser;
    }

    public void setqUser(QUser qUser) {
        this.qUser = qUser;
    }

    @Column(name = "country_id", length = 50)
    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    @Column(name = "province_id", length = 50)
    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    @Column(name = "city_id", length = 50)
    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Column(name = "county_id", length = 50)
    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonIgnore
    public List<QUserEvent> getqUserEventsList() {
        return qUserEventsList;
    }

    public void setqUserEventsList(List<QUserEvent> qUserEventsList) {
        this.qUserEventsList = qUserEventsList;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonIgnore
    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }


    @Column(name = "company_name", length = 200)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name = "parent_id", length = 32)
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Column(name = "tel_phone", length = 12)
    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }




    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonIgnore
    public List<EnterpriseEvent> getEnterpriseEventList() {
        return enterpriseEventList;
    }

    public void setEnterpriseEventList(List<EnterpriseEvent> enterpriseEventList) {
        this.enterpriseEventList = enterpriseEventList;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonIgnore
    public List<MerchantEvent> getMerchantEventList() {
        return merchantEventList;
    }

    public void setMerchantEventList(List<MerchantEvent> merchantEventList) {
        this.merchantEventList = merchantEventList;
    }


    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonIgnore
    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonIgnore
    public List<QGeneralAgentEvent> getqGeneralAgentEventList() {
        return qGeneralAgentEventList;
    }

    public void setqGeneralAgentEventList(List<QGeneralAgentEvent> qGeneralAgentEventList) {
        this.qGeneralAgentEventList = qGeneralAgentEventList;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonIgnore
    public List<QMerchantEvent> getqMerchantEventList() {
        return qMerchantEventList;
    }

    public void setqMerchantEventList(List<QMerchantEvent> qMerchantEventList) {
        this.qMerchantEventList = qMerchantEventList;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonIgnore
    public List<QEnterpriseEvent> getqEnterpriseEventList() {
        return qEnterpriseEventList;
    }

    public void setqEnterpriseEventList(List<QEnterpriseEvent> qEnterpriseEventList) {
        this.qEnterpriseEventList = qEnterpriseEventList;
    }


    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonIgnore
    public TerminalEvent getTerminalEvent() {
        return terminalEvent;
    }

    public void setTerminalEvent(TerminalEvent terminalEvent) {
        this.terminalEvent = terminalEvent;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonIgnore
    public List<SecEnterPriseUser> getSecEnterPriseUserList() {
        return secEnterPriseUserList;
    }

    public void setSecEnterPriseUserList(List<SecEnterPriseUser> secEnterPriseUserList) {
        this.secEnterPriseUserList = secEnterPriseUserList;
    }

    @Column(name = "level",length = 20 )
    public String getVoicePrio() {
        return voicePrio;
    }

    public void setVoicePrio(String voicePrio) {
        this.voicePrio = voicePrio;
    }


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonIgnore
    public List<Audio> getAudioList() {
        return audioList;
    }

    public void setAudioList(List<Audio> audioList) {
        this.audioList = audioList;
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonIgnore
    public List<GeneralAgentBoardCast> getGeneralAgentBoardCastListl() {
        return generalAgentBoardCastListl;
    }

    public void setGeneralAgentBoardCastListl(List<GeneralAgentBoardCast> generalAgentBoardCastListl) {
        this.generalAgentBoardCastListl = generalAgentBoardCastListl;
    }

    public String getEamil() {
        return eamil;
    }

    public void setEamil(String eamil) {
        this.eamil = eamil;
    }

    public String getIccId() {
        return iccId;
    }

    public void setIccId(String iccId) {
        this.iccId = iccId;
    }


    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonIgnore
    public QAccountantPassword getqAccountantPassword() {
        return qAccountantPassword;
    }

    public void setqAccountantPassword(QAccountantPassword qAccountantPassword) {
        this.qAccountantPassword = qAccountantPassword;
    }


    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public int getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(int accountStatus) {
        this.accountStatus = accountStatus;
    }

    /**
     * 以下为临时使用字段
     */
    @ApiModelProperty(value = "iccId 区间偏移量")
    @Transient
    private int Offset;

    public int getOffset() {
        return Offset;
    }

    public void setOffset(int offset) {
        Offset = offset;
    }
}
