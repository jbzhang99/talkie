package com.meta.service.enterprise;

import com.meta.dao.enterprise.EnterpriseEventRepository;
import com.meta.model.enterprise.EnterpriseEvent;
import com.meta.model.user.User;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by lhq on 2017/11/10.
 * 企业 的操作记录表
 */
@Service
@Transactional
public class EnterpriseEventService  extends BaseServiceImpl<EnterpriseEvent, Long> implements BaseService<EnterpriseEvent, Long> {

    @Autowired
    public void setBaseDao(EnterpriseEventRepository enterpriseEventRepository) {
        super.setBaseDao(enterpriseEventRepository);
    }

    @Autowired
    private EnterpriseEventRepository enterpriseEventRepository;


    /**
     * 企业操作用户 终端和账号
     */
    public void  UserEventLog(User nowUser,User user,int type) throws UnknownHostException {
    
        EnterpriseEvent bean=new EnterpriseEvent();
        bean.setType(type);
        bean.setIp(InetAddress.getLocalHost().getHostAddress());
        bean.setByUserAccount(user.getAccount());
        bean.setRemark(UserEventRemark(type,user.getLanguage(),nowUser.getAccount(),user.getAccount(),null,null,null));
        bean.setUser(nowUser);
        super.save(bean);

    }

    /**
     * 6= 新增群组,  7 == 修改群组 10 删除
     * @param nowUser
     * @param type
     * @param GroupName
     * @param newGroupName
     * @throws UnknownHostException
     */
    public void  saveGroupEventLog(User nowUser,int type,String GroupName,String newGroupName) throws UnknownHostException {
        EnterpriseEvent bean=new EnterpriseEvent();
        bean.setType(type);
        bean.setIp(InetAddress.getLocalHost().getHostAddress());
        bean.setByUserAccount(nowUser.getAccount());
        bean.setRemark(UserEventRemark(type,nowUser.getLanguage(),nowUser.getAccount(),null,GroupName,newGroupName,null));
        bean.setUser(nowUser);
        super.save(bean);

    }

    /**
     * 8== 新增好友 ,9== 删除好友
     * @param nowUser
     * @param type
     *
     * @throws UnknownHostException
     */
    public void  saveFriendEventLog(User nowUser,User user,int type,String friendAccout) throws UnknownHostException {
        EnterpriseEvent bean=new EnterpriseEvent();
        bean.setType(type);
        bean.setIp(InetAddress.getLocalHost().getHostAddress());
        bean.setByUserAccount(nowUser.getAccount());
        bean.setRemark(UserEventRemark(type,nowUser.getLanguage(),nowUser.getAccount(),user.getAccount(),null,null,friendAccout));
        bean.setUser(nowUser);
        super.save(bean);

    }










    /**
     * 用户事件类型, 1= 用户登录,,2= 用户登出,3= 新增用户, 4 =删除用户 , 5= 修改用户,6= 新增群组,  7 == 修改群组, 8== 新增好友 ,9== 删除好友
     */
    public String UserEventRemark(int type,String language, String currAccout, String accout,String GroupName,String newGroupName,String friendAccout){
       
        switch (type){
            case 1: //1= 用户登录
                return  language.equals("zh")?
                        "用户退出，账号："+currAccout
                        :"Sign Out ，Accout："+currAccout;

            case 2://2= 用户登出
                return  language.equals("zh")?
                        "用户退出，账号："+currAccout
                        :"Sign Out ，Accout："+currAccout;

            case 3://3= 新增用户,
                return  language.equals("zh")?
                        "账号："+currAccout+" 新增终端，账号："+accout
                        : "Accout："+currAccout+" Add Terminal，Accout："+accout;

            case 4://4 =删除用户
                return  language.equals("zh")?
                        "账号："+currAccout+" 删除终端，账号："+accout
                        :"Accout："+currAccout+" Delete Terminal，Accout："+accout;

            case 5://5= 修改用户
                return  language.equals("zh")?
                        "账号："+currAccout+" 编辑终端，账号："+accout
                        :"Accout："+currAccout+" Update Terminal，Accout："+accout;

            case 6://6= 新增群组
                return  language.equals("zh")?
                        "账号:"+currAccout+" 新增群组 群组名称："+GroupName
                        :"Accout:"+currAccout+" Add Group，Group Name："+GroupName;

            case 7:// 7 == 修改群组,new
                return  language.equals("zh")?
                       "账号:"+currAccout+" 修改群组 群组名称："+GroupName+" 改为 "+newGroupName
                        :"Accout:"+currAccout+" Update Group，Group Name："+GroupName+" Change to "+newGroupName;

            case 8://8== 新增好友
                return  language.equals("zh")?
                        "账号："+currAccout+" 新增好友，好友账号："+accout +" 添加 "+friendAccout
                        : "Accout："+currAccout+" Add Friend，FriendAccout："+accout+" 添加 "+friendAccout;
            case 9://9== 删除好友
                return  language.equals("zh")?
                        "账号："+currAccout+" 删除好友，好友账号："+accout+" 删除 "+friendAccout
                        : "Accout："+currAccout+" Delete Friend，FriendAccout："+accout+" Delete to "+friendAccout;
            case 10:// 10 == 删除群组,
                return  language.equals("zh")?
                        "账号:"+currAccout+" 删除群组 群组名称："+GroupName
                        :"Accout:"+currAccout+" Delete Group，Group Name："+GroupName;
            case 11:// 11 == 暂停用户
                return  language.equals("zh")?
                        "账号:"+currAccout+" 暂停用户，账号："+accout
                        :"Suspend User ，Accout："+accout;
            case 12:// 12 == 启用用户
                return  language.equals("zh")?
                        "账号:"+currAccout+" 启用用户，账号："+accout
                        :"Accout:"+currAccout+" Enabled User ，Accout："+accout;
            case 13://3= 新增账号
                return  language.equals("zh")?
                        "账号："+currAccout+" 新增账号，账号："+accout
                        :"Accout："+currAccout+"Add Accout，Accout："+accout;
            case 14://4 =删除账号
                return  language.equals("zh")?
                        "账号："+currAccout+" 删除账号，账号："+accout
                        :"Accout："+currAccout+"Delete Accout，Accout："+accout;
            case 15://5= 修改账号
                return  language.equals("zh")?
                        "账号："+currAccout+" 编辑账号，账号："+accout
                        :"Accout："+currAccout+"Update Accout，Accout："+accout;

        }

        return null;
    }




    //登录备注
    public String getLoginEventRemark(String language,String accout){
        return  language.equals("zh")?
                "用户登录，账号："+accout
                :"User Login，Accout："+accout;
    }
    //登出备注
    public String getSignOutEventRemark(String language,String accout){
        return  language.equals("zh")?
                "用户退出，账号："+accout
                :"Sign Out ，Accout："+accout;
    }
    //新增用户
    public String getAddUserRemark(String language,String accout){
        return  language.equals("zh")?
                "新增用户，账号："+accout
                :"Add User，Accout："+accout;
    }

    //删除用户
    public String getDeleteUserRemark(String language,String accout){
        return  language.equals("zh")?
                "删除用户，账号："+accout
                :"Delete User，Accout："+accout;
    }



}
