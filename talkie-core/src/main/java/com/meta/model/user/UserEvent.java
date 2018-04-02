package com.meta.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meta.datetime.DateTimeUtil;
import com.meta.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by llin on 2017/9/28.
 */
@Entity
@Table(name = "talkie_t_user_event")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "用户基本操作信息", description = "用户基本操作信息")
public class UserEvent extends BaseEntity {

    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 用户事件类型, 1= 用户登录,,2= 用户登出,3= 新增用户, 4 =删除用户 , 5= 修改用户,6= 新增群组,  7 == 修改群组, 8== 新增好友 ,9== 删除好友
     * 10==修改状态
     */
    @ApiModelProperty(value = "用户事件类型")
    @NotNull(message = "用户事件类型不能为空")
    private Integer type;

    /**
     * 用户   多对一
     */
    @ApiModelProperty(value = "用户  多对一   ,,其中USERID  == USER的主键 ")
    private User user;
    /**
     * ip
     */
    @ApiModelProperty("ip")
    private String ip;

    /**
     * 被操作方
     */
    @ApiModelProperty(value = "被操作方account")
    private String byUserAccount;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String remark;

    /**
     * 父ID
     */
    @ApiModelProperty(value = "父ID")
    private Long parentId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, length = 32)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "type", length = 3,nullable = false)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "ip", length = 200)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Column(name = "remark", length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "by_user_account", length = 255)
    public String getByUserAccount() {
        return byUserAccount;
    }

    public void setByUserAccount(String byUserAccount) {
        this.byUserAccount = byUserAccount;
    }

    @Column(name = "parent_id", length = 32)
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }



    /**
     * 以下是构造函数
     */


    public UserEvent() {
    }

    /**
     * 1= 用户登录,,2= 用户登出,3= 新增用户, 4 =删除用户 , 5= 修改用户
     * @param operatorAct
     * @param targetAct
     * @param eventType
     * @return
     */
    public  UserEvent(User operatorAct ,User  targetAct,int eventType ,String ip){
        String event="";
        switch (eventType){
            case  1:
                event  = "登录";
                break;
            case  2:
                event  = "登出";
                break;
            case  3:
                event  = "新增";
                break;
            case  4:
                event  = "删除";
                break;
            case  5:
                event  = "修改";
                break;
        }
        this.byUserAccount = targetAct.getAccount();
        this.createDate = DateTimeUtil.simpleDateTimeFormat(new Date());
        this.createUser = operatorAct.getId();
        this.ip = ip ;
        this.parentId = targetAct.getParentId() ;
        this.type = eventType;
        this.user = operatorAct ;
        this.remark = "操作账号："+operatorAct.getAccount() +",操作类型："+ event+ (targetAct !=null ? ",目标账号:" +targetAct.getAccount():null) ;
    }


    /**
     *  6= 新增群组,  7 == 修改群组,
     * @param operatorAct 操作用户
     * @param targetGroup
     * @param eventType
     * @return
     */
    public  UserEvent(User operatorAct ,String  targetGroup,int eventType ,String ip){
        String event="";
        switch (eventType){
            case  6:
                event = "新增群组";
                break;
            case 7:
                event = "修改群组";
                break;
        }
        this.ip = ip ;
        this.type = eventType;
        this.user = operatorAct ;
        this.createDate = DateTimeUtil.simpleDateTimeFormat(new Date());
        this.createUser = operatorAct.getId();
        this.ip = ip ;
        this.type = eventType;
        this.user = operatorAct ;
        this.remark = "操作账号："+operatorAct.getAccount() +",操作类型："+ event + (targetGroup !=null ? ",目标群组:" +targetGroup:null) ;
    }

    /**
     * 8== 新增好友 ,9== 删除好友
     * @param sourceAct
     * @param targetAct
     * @param eventType
     * @return
     */
    public  UserEvent(User operatorAct,User sourceAct ,User  targetAct,int eventType,String ip){
        String event="";
        switch (eventType){
            case  8:
                event = "新增好友";
                break;
            case 7:
                event = "删除好友";
                break;
        }
        this.ip = ip ;
        this.type = eventType;
        this.user = operatorAct ;
        this.createDate = DateTimeUtil.simpleDateTimeFormat(new Date());
        this.createUser = operatorAct.getId();
        this.ip = ip ;
        this.type = eventType;
        this.user = operatorAct ;
        this.remark =  "操作账号："+operatorAct.getAccount() +",操作类型：" +(sourceAct !=null ? ",对象账号:" +sourceAct.getAccount():null)+ event +(targetAct !=null ? ", 目标账号:" +targetAct.getAccount():null) ;
    }

}
