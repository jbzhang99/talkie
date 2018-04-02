package com.meta.controller.merchant;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.datetime.DateTimeUtil;
import com.meta.error.errorMsgDict;
import com.meta.model.GeneralAgent.GeneralAgentEvent;
import com.meta.model.merchant.MerchantEvent;
import com.meta.model.qmanage.QUser;
import com.meta.model.totolInfo.MerchantTotalCountInfo;
import com.meta.model.totolInfo.TotalCountInfo;
import com.meta.model.user.User;
import com.meta.model.user.UserEvent;
import com.meta.regex.RegexUtil;
import com.meta.remark.remarkDict;
import com.meta.service.generalagent.GeneralAgentEventService;
import com.meta.service.merchant.MerchantEventService;
import com.meta.service.merchant.MerchantService;
import com.meta.service.qmanage.QUserService;
import com.meta.service.user.UserFriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lhq on 2017/11/14.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "merchant", description = "代理信息接口")
public class MerchantController extends BaseControllerUtil {

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MerchantEventService merchantEventService;
    @Autowired
    private GeneralAgentEventService generalAgentEventService;
    @Autowired
    private QUserService qUserService;
    @Autowired
    private UserFriendService userFriendService;

    @RequestMapping(value = ServiceUrls.Merchant.MERCHANTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取代理列表", notes = "根据查询条件获代理列表在前端表格展示")
    public Result<User> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<User> result = merchantService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }


    @RequestMapping(value = ServiceUrls.Merchant.MERCHANTS_NO_PAGE, method = RequestMethod.GET)
    @ApiOperation(value = "获取代理列表", notes = "根据查询条件获代理列表在前端表格展示")
    public Result<User> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters) {
        List<User> result = merchantService.search(filters);
        return getResultList(result);
    }

    @RequestMapping(value = ServiceUrls.Merchant.MERCHANTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改代理信息", notes = "创建/修改代理信息")
    public Result<User> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid User user) throws Exception {
        User userParent = merchantService.findOne(getUserId());
        if (RegexUtil.isNull(user.getId())) {
            User userTemp = merchantService.findByAccount(user.getAccount());
            if (!RegexUtil.isNull(userTemp)) {
                return error("该账号已存在，请重新填写！");
            }
            user.setParentId(getUserId());
            user.setIsDel(1);
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
             * 操作记录
             */
            //代理操作子代理
            if (userParent.getMerchantLevel().equals("3") || userParent.getMerchantLevel().equals("2")) {
                MerchantEvent merchantEvent = new MerchantEvent();
                merchantEvent.setByUserAccount(user.getAccount());
                merchantEvent.setType(3);
                merchantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                merchantEvent.setCreateUser(userParent.getId());
                merchantEvent.setIp(getIpAddr());
                if (user.getLanguage().equals("zh")) {
                    merchantEvent.setRemark(remarkDict.ADD + remarkDict.USER + user.getName());
                } else if (user.getLanguage().equals("en")) {
                    merchantEvent.setRemark(remarkDict.ADD_ENGLISHI + "  " + remarkDict.USER_ENGLISH + "  " + user.getName());
                }
                merchantEvent.setUser(userParent);
                merchantEventService.save(merchantEvent);
            }
            //总代级 操作代理
            if (userParent.getMerchantLevel().equals("1")) {
                GeneralAgentEvent generalAgentEvent = new GeneralAgentEvent();
                generalAgentEvent.setByUserAccount(user.getAccount());
                generalAgentEvent.setType(3);
                generalAgentEvent.setIp(getIpAddr());
                generalAgentEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                if (user.getLanguage().equals("zh")) {
                    generalAgentEvent.setRemark(remarkDict.ADD + remarkDict.USER + user.getName());
                } else if (user.getLanguage().equals("en")) {
                    generalAgentEvent.setRemark(remarkDict.ADD_ENGLISHI + "  " + remarkDict.USER_ENGLISH + "  " + user.getName());
                }
                generalAgentEvent.setUser(userParent);
                generalAgentEventService.save(generalAgentEvent);
            }

            return success(merchantService.save(user));
        } else {
            /**
             * 操作记录
             */
            if (userParent.getMerchantLevel().equals("3") || userParent.getMerchantLevel().equals("2")) {
                MerchantEvent merchantEvent = new MerchantEvent();
                merchantEvent.setByUserAccount(user.getAccount());
                merchantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                merchantEvent.setType(5);
                merchantEvent.setCreateUser(userParent.getId());
                merchantEvent.setIp(getIpAddr());
                if (user.getLanguage().equals("zh")) {
                    merchantEvent.setRemark(remarkDict.MODIFY + remarkDict.USER + user.getName());
                } else if (user.getLanguage().equals("en")) {
                    merchantEvent.setRemark(remarkDict.MODIFY_ENGLISH + "  " + remarkDict.USER_ENGLISH + "  " + user.getName());
                }
                merchantEvent.setUser(userParent);
                merchantEventService.save(merchantEvent);
            }
            if (userParent.getMerchantLevel().equals("1")) {
                GeneralAgentEvent generalAgentEvent = new GeneralAgentEvent();
                generalAgentEvent.setByUserAccount(user.getAccount());
                generalAgentEvent.setType(5);
                generalAgentEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                generalAgentEvent.setIp(getIpAddr());
                if (user.getLanguage().equals("zh")) {
                    generalAgentEvent.setRemark(remarkDict.MODIFY + remarkDict.USER + user.getName());
                } else if (user.getLanguage().equals("en")) {
                    generalAgentEvent.setRemark(remarkDict.MODIFY_ENGLISH + "  " + remarkDict.USER_ENGLISH + "  " + user.getName());
                }
                generalAgentEvent.setUser(userParent);
                generalAgentEventService.save(generalAgentEvent);
            }
            return success(merchantService.save(user));
        }
    }

    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户")
    public Result deleteById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) throws Exception {
        merchantService.delete(id);
        return success(null);

    }

    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_BY_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据ID获取代理", notes = "根据ID获取代理")
    public Result<User> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id) throws Exception {
        User user = merchantService.findOne(id);
        return success(user);
    }

    @RequestMapping(value = ServiceUrls.Merchant.MERCHANTS, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除代理", notes = "根据ID删除代理")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "")
            @RequestParam(value = "merchantLevel", required = false) String merchantLevel,
            @ApiParam(name = "currentUserId", value = "currentUserId", defaultValue = "")
            @RequestParam(value = "currentUserId", required = false) Long currentUserId) throws Exception {
        UserEvent userEvent = new UserEvent();
        Double moneyTotal = 0D;
        User user = merchantService.findOne(id); //查询 上传的用户信息
        User userParent = merchantService.findOne(currentUserId); //查询 上传信息的操作者信息

        if (user.getMerchantLevel().equals("2")) {//    等级为2 ==代理
            //查询子代理(企业)
            int merchantCount = merchantService.countCompayByParentId(id, 1);
            if (merchantCount > 0) {
                return error(errorMsgDict.DEL_MERCHANTS_EXIST_COMPANY);
            }

            //   List<User> userListMerchant = merchantService.findByParentId(id);
//            if (userListMerchant.size() > 0) {
//
//                for (User temp : userListMerchant) {
//                    //如果是子代理
//                    if (temp.getMerchantLevel().equals("3")) {
//                        //查子代理的余额
//                        List<QUser> qUserList = qUserService.search("EQ_user.id=" + temp.getId());
//                        moneyTotal += qUserList.get(0).getBalance();
//                        //查出子代理下面的企业
//                        List<User> userListUserE = merchantService.findByParentId(temp.getId());
//                        if (userListUserE.size() > 0) {
//                            for (User uUsers : userListUserE) {
//                                List<QUser> qUserList2 = qUserService.search("EQ_user.id=" + uUsers.getId());
//                                moneyTotal += qUserList2.get(0).getBalance();
//                                //查询企业下面的用户
//                                List<User> userListUser = merchantService.findByParentId(uUsers.getId());
//                                if (userListUser.size() > 0) {
//                                    for (User aa : userListUser) {
//                                        List<QUser> qUserList3 = qUserService.search("EQ_user.id=" + aa.getId());
//                                        moneyTotal += qUserList3.get(0).getBalance();
//                                        //操作记录
//                                        GeneralAgentEvent generalAgentEvent = new GeneralAgentEvent();
//                                        generalAgentEvent.setType(4);
//                                        generalAgentEvent.setByUserAccount(aa.getAccount());
//                                        generalAgentEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
//                                        generalAgentEvent.setIp(InetAddress.getLocalHost().getHostAddress());
//                                        generalAgentEvent.setRemark(remarkDict.DEL + remarkDict.USER + aa.getName());
//                                        generalAgentEvent.setUser(userParent);
//                                        generalAgentEventService.save(generalAgentEvent);
//                                        //删除用户
//                                        userFriendService.deleteByUserId(aa.getId());
//                                        userFriendService.deleteByFriendId(aa.getId());
//                                        merchantService.delete(aa.getId());
//                                    }
//                                }
//                                //删除企业
//                                GeneralAgentEvent generalAgentEvent = new GeneralAgentEvent();
//                                generalAgentEvent.setType(4);
//                                generalAgentEvent.setByUserAccount(uUsers.getAccount());
//                                generalAgentEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
//                                generalAgentEvent.setIp(InetAddress.getLocalHost().getHostAddress());
//                                generalAgentEvent.setRemark(remarkDict.DEL + remarkDict.USER + uUsers.getName());
//                                generalAgentEvent.setUser(userParent);
//                                generalAgentEventService.save(generalAgentEvent);
//                                merchantService.delete(uUsers.getId());
//                            }
//                            //删除 子代理
//                            //操作记录
//                            GeneralAgentEvent generalAgentEvent = new GeneralAgentEvent();
//                            generalAgentEvent.setType(4);
//                            generalAgentEvent.setIp(InetAddress.getLocalHost().getHostAddress());
//                            generalAgentEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
//                            generalAgentEvent.setRemark(remarkDict.DEL + remarkDict.USER + temp.getName());
//                            generalAgentEvent.setUser(userParent);
//                            generalAgentEventService.save(generalAgentEvent);
//                            merchantService.delete(temp.getId());
//                            //////////////////
//                        }
//                    } else if (temp.getMerchantLevel().equals("4")) {
//                        //查出企业的Q币
//                        List<QUser> qUserList = qUserService.search("EQ_user.id=" + temp.getId());
//                        moneyTotal += qUserList.get(0).getBalance();
//                        //查企业下面的用户
//                        List<User> userListUserE = merchantService.findByParentId(temp.getId());
//                        if (userListUserE.size() > 0) {
//                            for (User aa : userListUserE) {
//                                List<QUser> qUserList3 = qUserService.search("EQ_user.id=" + aa.getId());
//                                moneyTotal += qUserList3.get(0).getBalance();
//                                //操作记录
//                                GeneralAgentEvent generalAgentEvent = new GeneralAgentEvent();
//                                generalAgentEvent.setByUserAccount(aa.getAccount());
//                                generalAgentEvent.setType(4);
//                                generalAgentEvent.setIp(InetAddress.getLocalHost().getHostAddress());
//                                generalAgentEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
//                                generalAgentEvent.setRemark(remarkDict.DEL + remarkDict.USER + aa.getName());
//                                generalAgentEvent.setUser(userParent);
//                                generalAgentEventService.save(generalAgentEvent);
//                                //删除用户
//                                userFriendService.deleteByUserId(aa.getId());
//                                userFriendService.deleteByFriendId(aa.getId());
//                                merchantService.delete(aa.getId());
//                            }
//                        }
//                        //
//                        //删除企业
//                        //操作记录
//                        GeneralAgentEvent generalAgentEvent = new GeneralAgentEvent();
//                        generalAgentEvent.setType(4);
//                        generalAgentEvent.setByUserAccount(temp.getAccount());
//                        generalAgentEvent.setIp(InetAddress.getLocalHost().getHostAddress());
//                        generalAgentEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
//                        generalAgentEvent.setRemark(remarkDict.DEL + remarkDict.USER + temp.getName());
//                        generalAgentEvent.setUser(userParent);
//                        generalAgentEventService.save(generalAgentEvent);
//                        merchantService.delete(temp.getId());
//                    }
//                }

            //   }
            //  加钱啦
            QUser qUser = qUserService.queryUserByUserID(user.getParentId());//查询父级的QB信息
            if (qUser != null) {
                qUser.setBalance(qUser.getBalance() + moneyTotal);//回收QB
                qUser.setAlreadyBalance(qUser.getAlreadyBalance() - moneyTotal);//减少已分配数量
                qUserService.save(qUser);
            }

            //删除代理
            //操作记录
            GeneralAgentEvent generalAgentEvent = new GeneralAgentEvent();
            generalAgentEvent.setType(4);
            generalAgentEvent.setByUserAccount(user.getAccount());
            generalAgentEvent.setIp(getIpAddr());
            generalAgentEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            generalAgentEvent.setRemark(remarkDict.DEL + remarkDict.USER + user.getName());
            generalAgentEvent.setUser(userParent);
            generalAgentEventService.save(generalAgentEvent);
            merchantService.delete(id);
        }
        if (user.getMerchantLevel().equals("3")) { //子代理

            List<User> userListMerchant = merchantService.findByParentId(id);
            if (userListMerchant.size() > 0) {
                for (User temp : userListMerchant) {
                    //查出企业的企业的余额相加
                    QUser qUser = qUserService.findOne(temp.getId());
                    moneyTotal += qUser.getBalance();
                    //查出该企业下面的用户
                    List<User> userListUser = merchantService.findByParentId(temp.getId());
                    if (userListUser.size() > 0) {
                        for (User userTemp : userListUser) {
                            QUser qtemp = qUserService.findOne(userTemp.getId());
                            moneyTotal += qtemp.getBalance();
                            //用户操作记录
                            GeneralAgentEvent generalAgentEvent = new GeneralAgentEvent();
                            generalAgentEvent.setType(4);
                            generalAgentEvent.setByUserAccount(userTemp.getAccount());
                            generalAgentEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                            generalAgentEvent.setIp(InetAddress.getLocalHost().getHostAddress());
                            generalAgentEvent.setRemark(remarkDict.DEL + remarkDict.USER + userTemp.getName());
                            generalAgentEvent.setUser(userParent);
                            generalAgentEventService.save(generalAgentEvent);


                            userFriendService.deleteByUserId(userTemp.getId());
                            userFriendService.deleteByFriendId(userTemp.getId());
                            merchantService.delete(userTemp.getId());
                        }
                    }
                    GeneralAgentEvent generalAgentEvent = new GeneralAgentEvent();
                    generalAgentEvent.setType(4);
                    generalAgentEvent.setByUserAccount(temp.getAccount());
                    generalAgentEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                    generalAgentEvent.setIp(getIpAddr());
                    generalAgentEvent.setRemark(remarkDict.DEL + remarkDict.USER + temp.getName());
                    generalAgentEvent.setUser(userParent);
                    generalAgentEventService.save(generalAgentEvent);
                    merchantService.delete(temp.getId());

                }
            }

            //回收到 总代理
            QUser qUser = qUserService.findOne(user.getParentId());
            qUser.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            qUser.setModifyUser(userParent.getId());
            qUser.setBalance(qUser.getBalance() + moneyTotal);
            qUserService.save(qUser);
            //用户操作记录
            GeneralAgentEvent generalAgentEvent = new GeneralAgentEvent();
            generalAgentEvent.setType(4);
            generalAgentEvent.setByUserAccount(user.getAccount());
            generalAgentEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            generalAgentEvent.setIp(getIpAddr());
            generalAgentEvent.setRemark(remarkDict.DEL + remarkDict.USER + user.getName());
            generalAgentEvent.setUser(userParent);
            generalAgentEventService.save(generalAgentEvent);
            //删除自己
            merchantService.delete(id);
        }
        return success(null);
    }


    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    public Result modifyPasswordById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) throws Exception {
        merchantService.modifyPasswordById(id, password);
        return success("");
    }

    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_FIND_BY_ACCOUNT_AND__PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据账号，用户等级，父ID查找详情", notes = "根据账号，用户等级，父ID查找详情")
    public Result<User> findByAccountAndParentId(
            @ApiParam(name = "account", value = "account", defaultValue = "")
            @RequestParam(value = "account", required = false) String account,
            @ApiParam(name = "parentId", value = "ParentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId) throws Exception {
        User user = merchantService.findByAccountAndParentId(account, parentId);
        return success(user);
    }

    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_MODIFY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status) throws Exception {


        User userParent = merchantService.findOne(getUserId());
        GeneralAgentEvent generalAgentEvent = new GeneralAgentEvent();
        generalAgentEvent.setByUserAccount(getAccount());
        generalAgentEvent.setType(10);
        generalAgentEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
        generalAgentEvent.setIp(getIpAddr());
        generalAgentEvent.setUser(userParent);
        if ("1".equals(status)) {
            generalAgentEvent.setRemark(remarkDict.MODIFY + remarkDict.USER + remarkDict.STATUS + "为" + remarkDict.NORMAL);
        } else if ("2".equals(status)) {
            generalAgentEvent.setRemark(remarkDict.MODIFY + remarkDict.USER + remarkDict.STATUS + "为" + remarkDict.PAUSE);
        }

        generalAgentEventService.save(generalAgentEvent);


        merchantService.modifyStatusById(id, status);
        return success("");
    }

    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_COUNT_COMPANY_BY_PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "统计子代理的下属企业数量", notes = "统计子代理的下属企业数量")
    public Result<Map<String, Integer>> countCompayByParentId(
            @ApiParam(name = "parentId", value = "父ID", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "isDel", value = "是否删除", defaultValue = "")
            @RequestParam(value = "isDel", required = false) Integer isDel) {
        Result<Map<String, Integer>> result = new Result<Map<String, Integer>>();
        result.setObj(merchantService.getcountCompayAndCountTerminalByproxyId(parentId, isDel));
        return result;
    }

    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_SEARCH_ACCOUNT, method = RequestMethod.GET)
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
        Page<User> result = merchantService.searchExtendDistinct(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }


    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_TOTAL_INFO, method = RequestMethod.GET)
    @ApiOperation(value = "获取总代的统计信息", notes = "获取总代的统计信息(账号管理用)")
    public Result<TotalCountInfo> getGeneralTotalInfo(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id) throws Exception {
        TotalCountInfo totalCountInfo = merchantService.getTotalCountInfo(id);
        return success(totalCountInfo);
    }

    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_SEC_TOTAL_INFO, method = RequestMethod.GET)
    @ApiOperation(value = "获取代理(子代理)的统计信息", notes = "获取代理(子代理)的统计信息 ")
    public Result<MerchantTotalCountInfo> getMerchantTotalInfo(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "flag", value = "flag", defaultValue = "")
            @RequestParam(value = "flag", required = false) boolean flag) throws Exception {
        MerchantTotalCountInfo merchantTotalCountInfo = merchantService.getMerchantTotalCountInfo(id, flag);
        return success(merchantTotalCountInfo);
    }


    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_COUNT_PARNET_ID_AND_MERCHANT_LEVEL, method = RequestMethod.GET)
    @ApiOperation(value = "根据父ID和等级来统计", notes = "根据父ID和等级来统计")
    public Long countParnetIdAndMerchantLevel(
            @ApiParam(name = "parentId", value = "parentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "")
            @RequestParam(value = "merchantLevel", required = false) String merchantLevel) throws Exception {
        Long log = merchantService.countParentIdAndMerchantLevel(parentId, merchantLevel);

        return log;
    }


}
