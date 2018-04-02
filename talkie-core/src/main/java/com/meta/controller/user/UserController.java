package com.meta.controller.user;

import com.meta.BaseControllerUtil;
import com.meta.MD5.MD5Util;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.datetime.DateTimeUtil;
import com.meta.exception.UserCreateException;
import com.meta.model.GeneralAgent.GeneralAgentEvent;
import com.meta.model.accountant.AccountantEvent;
import com.meta.model.enterprise.EnterpriseEvent;
import com.meta.model.merchant.MerchantEvent;
import com.meta.model.qmanage.QUser;
import com.meta.model.qmanage.QUserEvent;
import com.meta.model.terminal.Terminal;
import com.meta.model.user.User;
import com.meta.model.user.UserEvent;
import com.meta.regex.RegexUtil;
import com.meta.remark.remarkDict;
import com.meta.service.accountant.AccountantEventService;
import com.meta.service.enterprise.EnterpriseEventService;
import com.meta.service.enterprise.SecEnterPriseService;
import com.meta.service.enterprise.SecEnterPriseUserGroupService;
import com.meta.service.generalagent.GeneralAgentEventService;
import com.meta.service.merchant.MerchantEventService;
import com.meta.service.qmanage.QUserEventService;
import com.meta.service.qmanage.QUserService;
import com.meta.service.sim.SIMservice;
import com.meta.service.terminal.TerminalService;
import com.meta.service.user.UserEventService;
import com.meta.service.user.UserFriendService;
import com.meta.service.user.UserService;
import com.meta.sync.utils.SyncUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

/**
 * Created by llin on 2017/9/13.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "user", description = "会员信息接口")
@SuppressWarnings("all")
public class  UserController extends BaseControllerUtil {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private TerminalService terminalService;
    @Autowired
    private UserService userService;
    @Autowired
    private QUserService qUserService;
    @Autowired
    private UserEventService userEventService;
    @Autowired
    private UserFriendService userFriendService;
    @Autowired
    private QUserEventService qUserEventService;
    @Autowired
    private MerchantEventService merchantEventService;
    @Autowired
    private EnterpriseEventService enterpriseEventService;
    @Autowired
    private GeneralAgentEventService generalAgentEventService;
    @Autowired
    private SecEnterPriseUserGroupService secEnterPriseUserGroupService;
    @Autowired
    private AccountantEventService accountantEventService;
    @Autowired
    SyncUtils syncUtils;
    @Autowired
    private SIMservice siMservice;


    @Autowired
    private SecEnterPriseService secEnterPriseService;

    @RequestMapping(value = ServiceUrls.User.LOGIN_BY_ACCOUNT, method = RequestMethod.POST)
    @ApiOperation(value = "根据账号和密码登录", notes = "根据账号和密码登录")
    public Result<User> loginByAccount(
            @ApiParam(name = "account", value = "账户", defaultValue = "")
            @RequestParam(value = "account", required = false) String account,
            @ApiParam(name = "password", value = "密码", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) throws Exception {
        User user = userService.loginByAccount(account, password);
        if (RegexUtil.isNull(user)) {
            return error("账号或密码错误!");
        }
        if (user.getMerchantLevel().equals("7")) {  //普通 用户和总代
            UserEvent userEvent = new UserEvent();
            userEvent.setType(1);
            userEvent.setIp(getIpAddr());
            userEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            userEvent.setUser(user);
            userEvent.setRemark(user.getName() + remarkDict.LOGIN + remarkDict.STSTEN + remarkDict.SUCCESS);
            userEventService.save(userEvent);
        } else if (user.getMerchantLevel().equals("2") || user.getMerchantLevel().equals("3") || user.getMerchantLevel().equals("8") || user.getMerchantLevel().equals("9") || user.getMerchantLevel().equals("10") || user.getMerchantLevel().equals("11")) {  //代理或是子代理
            MerchantEvent merchantEvent = new MerchantEvent();
            merchantEvent.setType(1);
            merchantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            merchantEvent.setCreateUser(user.getId());
            merchantEvent.setIp(getIpAddr());
            merchantEvent.setRemark(user.getName() + remarkDict.LOGIN + remarkDict.STSTEN + remarkDict.SUCCESS);
            merchantEvent.setUser(user);
            merchantEventService.save(merchantEvent);
        } else if (user.getMerchantLevel().equals("4") || user.getMerchantLevel().equals("12") || user.getMerchantLevel().equals("13") || user.getMerchantLevel().equals("14") || user.getMerchantLevel().equals("15")) { //企业
            EnterpriseEvent enterpriseEvent = new EnterpriseEvent();
            enterpriseEvent.setType(1);
            enterpriseEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            enterpriseEvent.setCreateUser(user.getId());
            enterpriseEvent.setIp(getIpAddr());
            enterpriseEvent.setRemark(user.getName() + remarkDict.LOGIN + remarkDict.STSTEN + remarkDict.SUCCESS);
            enterpriseEvent.setUser(user);
            enterpriseEventService.save(enterpriseEvent);
        } else if (user.getMerchantLevel().equals("1") || user.getMerchantLevel().equals("5") || user.getMerchantLevel().equals("6")) {  //总代
            GeneralAgentEvent generalAgentEvent = new GeneralAgentEvent();
            generalAgentEvent.setType(1);
            generalAgentEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            generalAgentEvent.setCreateUser(user.getId());
            generalAgentEvent.setIp(getIpAddr());
            generalAgentEvent.setRemark(user.getName() + remarkDict.LOGIN + remarkDict.STSTEN + remarkDict.SUCCESS);
            generalAgentEvent.setUser(user);
            generalAgentEventService.save(generalAgentEvent);
        } else if (user.getMerchantLevel().equals("16")){
            AccountantEvent accountantEvent= new AccountantEvent();
            accountantEvent.setType(1);
            accountantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            accountantEvent.setCreateUser(user.getId());
            accountantEvent.setIp(getIpAddr());
            accountantEvent.setRemark(user.getName() + remarkDict.LOGIN + remarkDict.STSTEN + remarkDict.SUCCESS);
            accountantEvent.setUser(user);
            accountantEventService.save(accountantEvent);
        }else {
            return error("登录异常！");
        }
        return success(user);
    }

    @RequestMapping(value = ServiceUrls.User.USER_FIND_BY_ACCOUNT, method = RequestMethod.GET)
    @ApiOperation(value = "根据账号查找", notes = "根据账号查找")
    public Result<User> findByAccount(
            @ApiParam(name = "account", value = "账户", defaultValue = "")
            @RequestParam(value = "account", required = false) String account) throws Exception {
        User user = userService.findByAccount(account);
        return success(user);
    }

    @RequestMapping(value = ServiceUrls.User.USER_FIND_BY_ACCOUNT, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除账号", notes = "根据id删除账号")
    public Result delById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id) throws Exception {
        userService.delete(id);
        return success(null);
    }

    @RequestMapping(value = ServiceUrls.User.USERS_BATCH_SAVE, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改用户信息", notes = "创建/修改用户信息")
    @Transactional(rollbackFor = {UserCreateException.class})
    public Result<User> batchSave(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid User[] users) throws Exception {
        try {
            if (users.length > 0) {
            User userParent = userService.findOne(getUserId());
            Date date = new Date();
            for (User temp : users) {
                User userTemp = userService.findByAccount(temp.getAccount());
                if (!RegexUtil.isNull(userTemp)) {
                  throw  new UserCreateException("账号"+userTemp.getAccount()+"已存在，请重新填写！");
                }
//                if (temp.getIccId() == null){
//                    return error("请输入账号"+temp.getAccount()+"的iccID");
//                }else if( userService.findOneByFilter("EQ_iccId="+temp.getIccId()) != null){
//                    return error("iccId:"+temp.getIccId()+"已经被使用，请选择未被使用的！");
//                }else if(siMservice.findOneByFilter("EQ_iccId="+temp.getIccId()) ==null){
//                    return error("iccId:"+temp.getIccId()+"不存在，请使用正确的iccId！");
//                }
                temp.setParentId(getUserId());
                temp.setIsDel(1);
                temp.setStatus("2");
//                temp.setPassword(MD5Util.hashStr("123456"));
                temp.setMerchantLevel("7");




                /**
                 * Q币余额
                 */
                QUser qUser = new QUser();
                qUser.setBalance(0D);
                qUser.setAlreadyBalance(0D);
                qUser.setCreateDate(DateTimeUtil.simpleDateTimeFormat(date));
                qUser.setModifyDate(DateTimeUtil.simpleDateTimeFormat(date));
                qUser.setUser(temp);
                temp.setqUser(qUser);

                /**
                 * 设备终端 创建终端
                 */
                Terminal terminal = new Terminal();
                terminal.setCreateUser(temp.getCreateUser());
                terminal.setIsDel(1);
                terminal.setUser(temp);
                temp.setTerminal(terminal);
                temp.setPrefixAccount(temp.getName());

                /**
                 *  操作记录
                 */
                UserEvent userEvent = new UserEvent();
                userEvent.setByUserAccount(temp.getAccount());
                userEvent.setType(3);
                userEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(date));
                userEvent.setCreateUser(userParent.getId());
                userEvent.setIp(getIpAddr());
                userEvent.setRemark(remarkDict.ADD + remarkDict.USER + temp.getName());
                userEvent.setUser(userParent);
                userEventService.save(userEvent);
                userService.save(temp);
            }

        }


        }catch (UserCreateException e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return error(e.getMsg());
        }
        return success(null);

    }


    @RequestMapping(value = ServiceUrls.User.USERS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改用户信息", notes = "创建/修改用户信息")
    public Result<User> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid User user) throws Exception {
        User userParent = userService.findOne(getUserId());
        if (RegexUtil.isNull(user.getId())) {
            User userTemp = userService.findByAccount(user.getAccount());
            if (!RegexUtil.isNull(userTemp)) {
                return error("该账号已存在，请重新填写！");
            }
//            if(user.getMerchantLevel().equals("7")){//如果是创建终端
//                if (user.getIccId() == null){
//                    return error("请输入该用户的iccID");
//                }else if( userService.findOneByFilter("EQ_iccId="+user.getIccId()) != null){
//                    return error("该iccId已经被使用，请选择未被使用的！");
//                }else if(siMservice.findOneByFilter("EQ_iccId="+user.getIccId()) ==null){
//                    return error("该iccId不存在，请使用正确的iccId！");
//                }
//            }
            user.setParentId(getUserId());
            user.setIsDel(1);
            user.setStatus("2");
            /**
             * Q币余额
             */
            QUser qUser = new QUser();
            qUser.setBalance(0D);
            qUser.setAlreadyBalance(0D);
            qUser.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            qUser.setModifyDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            qUser.setUser(user);
            user.setqUser(qUser);

            /**
             * 设备终端 创建终端
             */
            Terminal terminal = new Terminal();
            terminal.setCreateUser(user.getCreateUser());
            terminal.setIsDel(1);
            terminal.setUser(user);
            user.setTerminal(terminal);
            user.setPrefixAccount(user.getName());
            String ip=InetAddress.getLocalHost().getHostAddress();
            /**
             *  操作记录
             */
            UserEvent userEvent = new UserEvent();
            userEvent.setByUserAccount(user.getAccount());
            userEvent.setType(3);
            userEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            userEvent.setCreateUser(userParent.getId());
            userEvent.setIp(ip);
            if(user.getLanguage().equals("zh")){
                userEvent.setRemark(remarkDict.ADD + remarkDict.USER + user.getName());
            }else if (user.getLanguage().equals("en")){
                userEvent.setRemark(remarkDict.ADD_ENGLISHI +"  "+ remarkDict.USER_ENGLISH + "  "+user.getName());
            }
            userEvent.setUser(userParent);
            //企业操作记录
            enterpriseEventService.UserEventLog(userParent,user,3);
            userEventService.save(userEvent);

            userService.save(user);
            return success(user);

        } else {
//            if(user.getMerchantLevel().equals("7")){//如果是创建终端
//                if (user.getIccId() == null){
//                    return error("请输入该用户的iccID");
//                }
//                //查询如果该用户的iccid还是原来的那个就不进行更新
//               User tempUser= userService.findOne(user.getId()); //用于比较的原本用户数据
//                if (tempUser != null? !user.getIccId().equals(tempUser.getIccId()) : true ){
//                    if( userService.findOneByFilter("EQ_iccId="+user.getIccId())!= null){
//                        return error("该iccId已经被使用，请选择未被使用的！");
//                    }else if(siMservice.findOneByFilter("EQ_iccId="+user.getIccId()) ==null){
//                        return error("该iccId不存在，请使用正确的iccId！");
//                    }
//                }
//            }
            /**
             * 操作记录
             */
            UserEvent userEvent = new UserEvent();
            userEvent.setByUserAccount(user.getAccount());
            userEvent.setType(5);
            userEvent.setIp(getIpAddr());
            userEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            userEvent.setCreateUser(userParent.getId());
            if(user.getLanguage().equals("zh")){
                userEvent.setRemark(remarkDict.MODIFY + remarkDict.USER + user.getName());
            }else if (user.getLanguage().equals("en")){
                userEvent.setRemark(remarkDict.MODIFY_ENGLISH +"  "+ remarkDict.USER_ENGLISH + "  "+user.getName());
            }
            userEvent.setUser(user);
            userEventService.save(userEvent);
            enterpriseEventService.UserEventLog(userParent,user,5);
            return success(userService.save(user));
        }
    }

    @RequestMapping(value = ServiceUrls.User.USERS, method = RequestMethod.GET)
    @ApiOperation(value = "获取用户列表", notes = "根据查询条件获用户列表在前端表格展示")
    public Result<User> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<User> result = userService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }


    @RequestMapping(value = ServiceUrls.User.USER_COUNT_COMPANY_BY_PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "统计子代理的下属企业数量", notes = "统计子代理的下属企业数量")
    public Integer countCompayByParentId(
            @ApiParam(name = "parentId", value = "父ID", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "isDel", value = "是否删除", defaultValue = "")
            @RequestParam(value = "isDel", required = false) Integer isDel,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "")
            @RequestParam(value = "merchantLevel", required = false) String merchantLevel) {
        Integer intTemp = userService.countCompayByParentId(parentId, isDel, merchantLevel);
        return intTemp;
    }

    @RequestMapping(value = ServiceUrls.User.USERS, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "")
            @RequestParam(value = "merchantLevel", required = false) String merchantLevel,
            @ApiParam(name = "currentUserId", value = "currentUserId", defaultValue = "")
            @RequestParam(value = "currentUserId", required = false) Long currentUserId) throws Exception {
        UserEvent userEvent = new UserEvent();
        Double moneyTotal = 0D;
        User user = userService.findOne(id); //查询 上传的用户信息
        User userParent = userService.findOne(currentUserId); //查询 上传信息的父信息

        if (merchantLevel.equals("7")) { //用户
            QUser qUser = qUserService.queryUserByUserID(id);
            moneyTotal += qUser.getBalance();
            //用户操作记录
            UserEvent userEvent1 = new UserEvent();
            userEvent1.setType(4);
            userEvent.setByUserAccount(user.getAccount());
            userEvent1.setIp(InetAddress.getLocalHost().getHostAddress());
            userEvent1.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            userEvent1.setRemark(remarkDict.PRIMARY_KEY + userParent.getId() + remarkDict.DEL + remarkDict.USER + remarkDict.PRIMARY_KEY + user.getId());
            userEvent1.setUser(userParent);
            userEventService.save(userEvent1);
            //Q币回到收企业
            QUser qUserParent = qUserService.queryUserByUserID(user.getParentId());
            qUserParent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            qUserParent.setModifyUser(userParent.getId());
            qUserParent.setBalance(qUserParent.getBalance() + moneyTotal);
            qUserService.save(qUserParent);
            //Q币回收记录
            QUserEvent qUserEvent = new QUserEvent();
            qUserEvent.setValue(moneyTotal);
            qUserEvent.setType(1);
            qUserEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            qUserEvent.setRemark(remarkDict.PRIMARY_KEY + id + remarkDict.RECOVER + moneyTotal + remarkDict.MONEY);
            qUserEvent.setUser(userParent);

            qUserEventService.save(qUserEvent);
            userFriendService.deleteByUserId(id);
            userFriendService.deleteByFriendId(id);
            secEnterPriseUserGroupService.delByUserId(id);
            terminalService.deleteByUser_Id(id);
            //企业操作记录
            enterpriseEventService.UserEventLog(userParent,user,4);
            userService.delete(id);
        }
        return success(null);
    }

    @RequestMapping(value = ServiceUrls.User.USER_MODIYY_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result modifyUserById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status) throws Exception {
        User userParent = userService.findOne(getUserId());
        UserEvent userEvent = new UserEvent();
        userEvent.setType(10);
        userEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
        userEvent.setUser(userParent);
        userEvent.setByUserAccount(getAccount());
        if ("1".equals(status)) {
            userEvent.setRemark(remarkDict.MODIFY + remarkDict.USER + remarkDict.STATUS + "为" + remarkDict.NORMAL);
        } else if ("2".equals(status)) {
            userEvent.setRemark(remarkDict.MODIFY + remarkDict.USER + remarkDict.STATUS + "为" + remarkDict.PAUSE);
        }
        userEvent.setIp(InetAddress.getLocalHost().getHostAddress());
        userEventService.save(userEvent);

        userService.modifyStatusById(id, status);
        return success("");
    }

    @RequestMapping(value = ServiceUrls.User.USER_ACCOUNT, method = RequestMethod.GET)
    @ApiOperation(value = "获取用户列表(账号管理用)", notes = "获取用户列表(账号管理用)")
    public Result<User> searchByAccount(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) throws Exception {
        Page<User> result = userService.searchExtendDistinct(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }

    @RequestMapping(value = ServiceUrls.User.USER_BY_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据ID获取用户", notes = "根据ID获取用户")
    public Result<User> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id) throws Exception {
        User user = userService.findOne(id);
        return success(user);
    }

    @RequestMapping(value = ServiceUrls.User.USER_MODIFY_PASSWORD_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    public Result modifyPassword(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) throws Exception {

        User operator = userService.findOne(getUserId());
        User targetUser =  userService.findOne(id);
        userEventService.save(new UserEvent(operator,targetUser,5,getIpAddr()));
        userService.modifyPassword(id, password);
        return success("");
    }

    @RequestMapping(value = ServiceUrls.User.USER_FIND_BY_ACCOUNT_AND_MERCHANT_LEVEL_AND_PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据账号，用户等级，父ID查找详情", notes = "根据账号，用户等级，父ID查找详情")
    public Result<User> findByAccountAndParentId(
            @ApiParam(name = "account", value = "account", defaultValue = "")
            @RequestParam(value = "account", required = false) String account,
            @ApiParam(name = "parentId", value = "ParentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId) throws Exception {
        User user = userService.findByAccountAndParentId(account, parentId);
        return success(user);
    }


    @RequestMapping(value = ServiceUrls.User.USER_WAIT_USER_GROUP, method = RequestMethod.GET)
    @ApiOperation(value = "群组用，查找待添加的用户", notes = "群组用，查找待添加的用户")
    public Result<User> findWaitUserGroup(
            @ApiParam(name = "parentId", value = "parentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "groupId", value = "groupId", defaultValue = "")
            @RequestParam(value = "groupId", required = false) Long groupId) throws Exception {
        List<User> users = userService.findWaitUserGroup(parentId, groupId);
        if (users.size() > 0) {
            for (User temp : users) {
                temp.setPassword("");
            }
        }
        return getResultList(users);
    }

    @RequestMapping(value = ServiceUrls.User.USER_FIND_ALREADY_USER_GROUP, method = RequestMethod.GET)
    @ApiOperation(value = "查找已添加 的用户列表(群组用)", notes = "查找已添加 的用户列表(群组用)")
    public Result<User> findAlreadyUserGroup(
            @ApiParam(name = "groupId", value = "groupId", defaultValue = "")
            @RequestParam(value = "groupId", required = false) Long groupId) throws Exception {
        List<User> users = userService.findAlreadyUserGroup(groupId);
        for (User temp : users) {
            temp.setPassword("");
        }

        return getResultList(users);
    }

    @RequestMapping(value = ServiceUrls.User.USER_FIND_CAN_ADD_FRIEND, method = RequestMethod.GET)
    @ApiOperation(value = "查找可以添加的好友 屏蔽自己", notes = "查找可以添加的好友 屏蔽自己")
    public Result<User> findCanAddFriend(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "parentId", value = "parentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId) throws Exception {
        List<User> users = userService.findCanAddFriend(id, parentId);
        if (users.size() > 0) {
            for (User temp : users) {
                temp.setPassword("");
            }
        }
        return getResultList(users);
    }


    @RequestMapping(value = ServiceUrls.User.USER_ALREADY_ADD_FRIEND, method = RequestMethod.GET)
    @ApiOperation(value = "好友列表--查找已经添加的好友列表", notes = "好友列表--查找已经添加的好友列表")
    public Result<User> findAlreadyAddFriend(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id) throws Exception {
        List<User> users = userService.findAlreadyAddFriend(id);
        if (users.size() > 0) {
            for (User temp : users) {
                temp.setPassword("");
            }
        }
        return getResultList(users);
    }

    @RequestMapping(value = ServiceUrls.User.USER_SEARCH_AND_GROUP, method = RequestMethod.GET)
    @ApiOperation(value = "查找好友列表，有含群组ID,含有二级管理ID", notes = "查找好友列表，有含群组ID，含有二级管理ID")
    public Result<User> searchUserAndGroup(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "currGroupList", value = "currGroupList", defaultValue = "")
            @RequestParam(value = "currGroupList", required = false) Long currGroupList,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) throws Exception {
        String strTemp = "";
        if (!RegexUtil.isNull(currGroupList)) {
            List<User> userList = userService.findAlreadyUserGroup(currGroupList);
            if (userList.size() > 0 && userList != null) {
                for (User temp : userList) {
                    strTemp += temp.getId() + ",";
                }
                filters = filters + ";IN_id=" + strTemp;
            }

        }
        Page<User> result = userService.searchExtendDistinct(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }



    @RequestMapping(value = ServiceUrls.User.USER_SEARCH_AND_GROUP_DATE, method = RequestMethod.GET)
    @ApiOperation(value = "查找好友列表，有含群组ID,有日期(Q币分配用)", notes = "查找好友列表，有含群组ID,有日期(Q币分配用)")
    public Result<User> searchUserAndGroupAndDate(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "currGroupList", value = "currGroupList", defaultValue = "")
            @RequestParam(value = "currGroupList", required = false) Long currGroupList,
            @ApiParam(name = "GT_createDate", value = "GT_createDate", defaultValue = "")
            @RequestParam(value = "GT_createDate", required = false) String GT_createDate,
            @ApiParam(name = "LT_createDate", value = "LT_createDate", defaultValue = "")
            @RequestParam(value = "LT_createDate", required = false) String LT_createDate,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) throws Exception {
        String strTemp = "";
        String strTemp1 = "";
        if (!RegexUtil.isNull(currGroupList) && !RegexUtil.isNull(GT_createDate) && !RegexUtil.isNull(LT_createDate)) {
            List<User> userList = userService.findAlreadyUserGroup(currGroupList);
            if (userList.size() > 0 && userList != null) {
                for (User temp : userList) {
                    strTemp += temp.getId() + ",";
                }
            }
            List<QUser> qUserPage = qUserService.search("GT_createDate=" + GT_createDate + ";LT_createDate=" + LT_createDate + ";IN_user.id=" + strTemp);
            if (qUserPage.size() > 0 && qUserPage != null) {
                for (QUser qTemp : qUserPage) {
                    strTemp1 += qTemp.getUser().getId() + ",";
                }
            }
            filters = filters + ";IN_id=" + strTemp1;
        }
        if (!RegexUtil.isNull(currGroupList) && RegexUtil.isNull(GT_createDate) && RegexUtil.isNull(LT_createDate)) {
            List<User> userList = userService.findAlreadyUserGroup(currGroupList);
            if (userList.size() > 0 && userList != null) {
                for (User temp : userList) {
                    strTemp += temp.getId() + ",";
                }
            }
            filters = filters + ";IN_id=" + strTemp;

        }
        if (RegexUtil.isNull(currGroupList) && !RegexUtil.isNull(GT_createDate) && !RegexUtil.isNull(LT_createDate)) {
            List<User> userList = qUserService.finddate( GT_createDate, LT_createDate);
            if (userList.size() > 0 && userList != null) {
                for (User temp : userList) {
                    strTemp += temp.getId() + ",";
                }
            }
            filters = filters + ";IN_id=" + strTemp;
        }
            Page<User> result = userService.search(filters, sorts, page, size);
            return getResultList(result.getContent(), result.getTotalElements(), page, size);

    }


    @RequestMapping(value = ServiceUrls.User.USER_ONLINE_STATUS, method = RequestMethod.GET)
    @ApiOperation(value = "查询在线状态", notes = "查询在线状态")
    public Boolean queryUserOnlineStatus(
            @ApiParam(name = "userId", value = "用户id", defaultValue = "")
            @RequestParam(value = "userId", required = false) Long userId) throws Exception {

        Boolean isOnline = syncUtils.getOnlineStatusByUserId(userId);

        return isOnline;
    }


    @RequestMapping(value = ServiceUrls.User.user_search_user_isOnline, method = RequestMethod.GET)
    @ApiOperation(value = "查找看用户是否在线", notes = "实际为查看全部终端")
    public Result<User> searchUserIsOnline(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "currGroupList", value = "currGroupList", defaultValue = "")
            @RequestParam(value = "currGroupList", required = false) Long currGroupList) throws Exception {
        String strTemp = "";
        if (!RegexUtil.isNull(currGroupList)) {
            List<User> userList = userService.findAlreadyUserGroup(currGroupList);
            if (userList.size() > 0 && userList != null) {
                for (User temp : userList) {
                    strTemp += temp.getId() + ",";
                }
                filters = filters + ";IN_id=" + strTemp;
            }
        }
        List<User> result = userService.searchExtendDistinct(filters);
        return getResultList(result);
    }

    @PostMapping(value = ServiceUrls.User.ICCIDUSERS_BATCH_SAVE)
    @ApiOperation(value = "iccID用户 批量创建 or 编辑" ,notes = "如果一条信息失败会触发事务回滚")
    @Transactional(rollbackFor = {UserCreateException.class})
    public  Result<User> iccIdUserBatchSave(@ApiParam(name = "json_model", value = "", defaultValue = "")
                                                @RequestBody @Valid User[] users)throws Exception {
        try {
            if (users.length > 0) {
                User userParent = userService.findOne(getUserId());
                Date date = new Date();
                for (User temp : users) {
                    User userTemp = userService.findByAccount(temp.getAccount());
                    if (!RegexUtil.isNull(userTemp)) {
                        throw  new UserCreateException("账号"+userTemp.getAccount()+"已存在，请重新填写！");
                    }
                    System.err.println(temp.getAccount());
//                if (temp.getIccId() == null){
//                    throw  new UserCreateException("请输入账号"+temp.getAccount()+"的iccID");
//                }else if( userService.findOneByFilter("EQ_iccId="+temp.getIccId()) != null){
//                    throw  new UserCreateException("iccId:"+temp.getIccId()+"已经被使用，请选择未被使用的！");
//                }else if(siMservice.findOneByFilter("EQ_iccId="+temp.getIccId()) ==null){
//                    throw  new UserCreateException("iccId:"+temp.getIccId()+"不存在，请使用正确的iccId！");
//                }

                    /**
                     * 开始账号终端绑定
                     * 网页绑定后就传递给后台redis，如果有机器上来，查询一下redis ，
                     * 就绑定，如果这期间换了机器登录会什么情况，要求机器开着，等网页绑定后才能关闭，由于卡坏或者其他因数，
                     */
                    temp.setPassword(MD5Util.hashStr("123456"));



                    temp.setAccountType(1);
                    temp.setParentId(getUserId());
                    temp.setIsDel(1);
                    temp.setStatus("2");
                    temp.setAccountStatus(1);//已经绑定
                    /**
                     * iccId用户密码生成策略
                     */
                    temp.setMerchantLevel("7");
                    /**
                     * Q币余额
                     */
                    QUser qUser = new QUser();
                    qUser.setBalance(0D);
                    qUser.setAlreadyBalance(0D);
                    qUser.setCreateDate(DateTimeUtil.simpleDateTimeFormat(date));
                    qUser.setModifyDate(DateTimeUtil.simpleDateTimeFormat(date));
                    qUser.setUser(temp);
                    temp.setqUser(qUser);

                    /**
                     * 设备终端 创建终端
                     */
                    Terminal terminal = new Terminal();
                    terminal.setCreateUser(temp.getCreateUser());
                    terminal.setIsDel(1);
                    terminal.setUser(temp);
                    temp.setTerminal(terminal);
                    temp.setPrefixAccount(temp.getName());

                    /**
                     *  操作记录
                     */
                    UserEvent userEvent = new UserEvent(userParent,temp,3,getIpAddr());
                    userEvent.setUser(userParent);
                    userEventService.save(userEvent);
                    userService.save(temp);
                }

            }


        }catch (UserCreateException e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return error(e.getMsg());
        }
        return success(null);
    }


    /**
     * iccid与终端  是否连接
     * 网页绑定后就传递给后台redis，如果有机器上来，查询一下redis ，就绑定，
     * 如果这期间换了机器登录会什么情况，要求机器开着，等网页绑定后才能关闭，由于卡坏或者其他因数，
     * @param iccId
     * @return
     */
    public boolean terminalIsConnection(String iccId){


        return true;
    }

    /**
     * 终端与账号 绑定状态转换
     */
    public boolean  terminalStatusSwitch(){

        return true;
    }
}
