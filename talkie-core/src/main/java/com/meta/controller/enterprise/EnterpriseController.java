package com.meta.controller.enterprise;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.datetime.DateTimeUtil;
import com.meta.error.errorMsgDict;
import com.meta.model.enterprise.EnterpriseEvent;
import com.meta.model.merchant.MerchantEvent;
import com.meta.model.qmanage.QUser;
import com.meta.model.qmanage.QUserEvent;
import com.meta.model.totolInfo.EnterPriseTotalCountInfo;
import com.meta.model.user.User;
import com.meta.model.user.UserEvent;
import com.meta.regex.RegexUtil;
import com.meta.remark.remarkDict;
import com.meta.service.enterprise.EnterpriseEventService;
import com.meta.service.enterprise.EnterpriseService;
import com.meta.service.group.GroupService;
import com.meta.service.merchant.MerchantEventService;
import com.meta.service.qmanage.QUserEventService;
import com.meta.service.qmanage.QUserService;
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
import java.util.stream.Collectors;

/**
 * Created by lhq on 2017/11/14.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "enterprise", description = "企业信息接口")
public class EnterpriseController extends BaseControllerUtil {

    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private MerchantEventService merchantEventService;
    @Autowired
    private EnterpriseEventService enterpriseEventService;
    @Autowired
    private QUserService qUserService;
    @Autowired
    private QUserEventService qUserEventService;
    @Autowired
    private GroupService groupService;

    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISES, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改企业信息", notes = "创建/修改企业信息")
    public Result<User> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid User user) throws Exception {
        User userParent = enterpriseService.findOne(getUserId());
        if (RegexUtil.isNull(user.getId())) {
            User userTemp = enterpriseService.findByAccount(user.getAccount());
            if (!RegexUtil.isNull(userTemp)) {
                return error("该账号已存在，请重新填写！");
            }
            if (userParent.getMerchantLevel().equals("9")) {//判断是否是二级代理
                user.setParentId(userParent.getParentId());
            } else {
                user.setParentId(getUserId());
            }
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
             *  操作记录
             */
            //代理操作的
            if (userParent.getMerchantLevel().equals("2") || userParent.getMerchantLevel().equals("3") || userParent.getMerchantLevel().equals("9")) {
                MerchantEvent merchantEvent = new MerchantEvent();
                merchantEvent.setType(3);
                merchantEvent.setByUserAccount(user.getAccount());
                merchantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                merchantEvent.setCreateUser(userParent.getId());
                if (user.getLanguage().equals("zh")) {
                    merchantEvent.setRemark(remarkDict.ADD + remarkDict.USER + user.getName());
                } else if (user.getLanguage().equals("en")) {
                    merchantEvent.setRemark(remarkDict.ADD_ENGLISHI + "  " + remarkDict.USER_ENGLISH + "  " + user.getName());
                }
                merchantEvent.setIp(getIpAddr());
                merchantEvent.setUser(userParent);
                merchantEventService.save(merchantEvent);
            }
            return success(enterpriseService.save(user));

        } else {
            /**
             * 操作记录
             */
            if (userParent.getMerchantLevel().equals("2") || userParent.getMerchantLevel().equals("3") || userParent.getMerchantLevel().equals("9")) {
                MerchantEvent merchantEvent = new MerchantEvent();
                merchantEvent.setByUserAccount(user.getAccount());
                merchantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                merchantEvent.setCreateUser(userParent.getId());
                merchantEvent.setType(5);
                if (user.getLanguage().equals("zh")) {
                    merchantEvent.setRemark(remarkDict.MODIFY + remarkDict.USER + user.getName());
                } else if (user.getLanguage().equals("en")) {
                    merchantEvent.setRemark(remarkDict.MODIFY_ENGLISH + "  " + remarkDict.USER_ENGLISH + "  " + user.getName());
                }
                merchantEvent.setIp(getIpAddr());
                merchantEvent.setUser(userParent);
                merchantEventService.save(merchantEvent);
            }
            if (userParent.getMerchantLevel().equals("4")) {  //企业 级操作
                EnterpriseEvent enterpriseEvent = new EnterpriseEvent();
                enterpriseEvent.setType(5);
                enterpriseEvent.setByUserAccount(user.getAccount());
                enterpriseEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                if (user.getLanguage().equals("zh")) {
                    enterpriseEvent.setRemark(remarkDict.MODIFY + remarkDict.USER + user.getName());
                } else if (user.getLanguage().equals("en")) {
                    enterpriseEvent.setRemark(remarkDict.MODIFY_ENGLISH + "  " + remarkDict.USER_ENGLISH + "  " + user.getName());
                }
                enterpriseEvent.setCreateUser(userParent.getId());
                enterpriseEvent.setIp(getIpAddr());
                enterpriseEvent.setUser(userParent);
                enterpriseEventService.save(enterpriseEvent);
            }
            return success(enterpriseService.save(user));
        }
    }

    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISES, method = RequestMethod.GET)
    @ApiOperation(value = "获取企业列表", notes = "根据查询条件获企业列表在前端表格展示")
    public Result<User> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<User> result = enterpriseService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }

    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE_BY_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据ID获取企业", notes = "根据ID获取企业")
    public Result<User> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id) throws Exception {
        User user = enterpriseService.findOne(id);
        return success(user);
    }


    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISES, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除企业", notes = "根据ID删除企业")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "")
            @RequestParam(value = "merchantLevel", required = false) String merchantLevel,
            @ApiParam(name = "currentUserId", value = "currentUserId", defaultValue = "")
            @RequestParam(value = "currentUserId", required = false) Long currentUserId) throws Exception {

        UserEvent userEvent = new UserEvent();
        Double moneyTotal = 0D;
        User user = enterpriseService.findOne(id); //查询 上传的用户信息
        User userParent = enterpriseService.findOne(currentUserId); //查询 上传信息的操作者信息
        //企业
        if (merchantLevel.equals("4")) {
            Long groupNum = groupService.countByUser_Id(id);//根据USERID查找群组总数
            if (groupNum > 0) {
                return error(errorMsgDict.DEL_COMPANY_EXIST_CHILD);
            }
            Long childNum = enterpriseService.countByParentId(id);//根据父ID查找孩子总数
            if (childNum > 0) {
                return error(errorMsgDict.DEL_COMPANY_EXIST_CHILD);
            }
            /**
             *  查询 企业下面的用户
             *  以下代码实现自动回收 ，与当前需求不符合，先做注释保留
             *
             *  List<User> userList = enterpriseService.findByParentId(id);//查询该用户作为父级有多少代理
             if (userList.size() > 0) {
             for (User temp : userList) {
             List<QUser> qUserList = qUserService.search("EQ_user.id=" + temp.getId());
             moneyTotal += qUserList.get(0).getBalance();
             //用户操作记录
             MerchantEvent merchantEvent = new MerchantEvent();
             merchantEvent.setType(4);
             merchantEvent.setByUserAccount(temp.getAccount());
             merchantEvent.setIp(InetAddress.getLocalHost().getHostAddress());
             merchantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
             merchantEvent.setRemark(remarkDict.DEL + remarkDict.USER + temp.getName() + remarkDict.PRIMARY_KEY + temp.getId());
             merchantEvent.setUser(userParent);
             merchantEventService.save(merchantEvent);
             //Q币回收记录
             QUserEvent qUserEvent = new QUserEvent();
             qUserEvent.setValue(qUserList.get(0).getBalance());
             qUserEvent.setType(1);
             qUserEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
             qUserEvent.setRemark(remarkDict.RECOVER + qUserList.get(0).getBalance() + remarkDict.MONEY);
             qUserEvent.setUser(userParent);
             qUserEventService.save(qUserEvent);
             userFriendService.deleteByUserId(temp.getId());
             userFriendService.deleteByFriendId(temp.getId());
             enterpriseService.delete(temp.getId());
             }
             }
             */

            //回收QB到父级
            List<QUser> qUserList = qUserService.search("EQ_user.id=" + id);
            if (qUserList != null && qUserList.size() > 0) {
                moneyTotal += qUserList.get(0).getBalance();
                QUser qUser = qUserService.queryUserByUserID(user.getParentId());//查询父级的QB信息
                if (qUser != null) {
                    qUser.setBalance(qUser.getBalance() + moneyTotal);//回收QB
                    qUser.setAlreadyBalance(qUser.getAlreadyBalance() - moneyTotal);//减少已分配数量
                    qUserService.save(qUser);
                }
            }

            //Q币回收记录
            QUserEvent qUserEvent = new QUserEvent();
            qUserEvent.setValue(qUserList.get(0).getBalance());
            qUserEvent.setType(1);
            qUserEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            qUserEvent.setRemark(remarkDict.RECOVER + qUserList.get(0).getBalance() + remarkDict.MONEY);
            qUserEvent.setUser(userParent);
            qUserEventService.save(qUserEvent);
            /**
             * 代理操作记录
             */
            MerchantEvent merchantEvent = new MerchantEvent();
            merchantEvent.setType(4);
            merchantEvent.setByUserAccount(user.getAccount());
            merchantEvent.setIp(getIpAddr());
            merchantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            merchantEvent.setRemark(remarkDict.DEL + remarkDict.USER + user.getName());
            merchantEvent.setUser(userParent);
            merchantEventService.save(merchantEvent);
            enterpriseService.delete(id);//最后删除该企业
        }
        return success(null);
    }


    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    public Result modifyPassword(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) throws Exception {
        enterpriseService.modifyPassword(id, password);
        return success("");
    }


    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISES_FIND_BY_ACCOUNT_AND__PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据账号，用户等级，父ID查找详情", notes = "根据账号，用户等级，父ID查找详情")
    public Result<User> findByAccountAndParentId(
            @ApiParam(name = "account", value = "account", defaultValue = "")
            @RequestParam(value = "account", required = false) String account,
            @ApiParam(name = "parentId", value = "ParentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId) throws Exception {
        User user = enterpriseService.findByAccountAndParentId(account, parentId);
        return success(user);
    }


    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE_MODIFY_STATUS_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status) throws Exception {


        User userParent = enterpriseService.findOne(getUserId());
        MerchantEvent merchantEvent = new MerchantEvent();
        merchantEvent.setByUserAccount(getAccount());
        merchantEvent.setType(10);
        merchantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
        merchantEvent.setIp(getIpAddr());
        merchantEvent.setUser(userParent);
        if ("1".equals(status)) {
            merchantEvent.setRemark(remarkDict.MODIFY + remarkDict.USER + remarkDict.STATUS + "为" + remarkDict.NORMAL);
        } else if ("2".equals(status)) {
            merchantEvent.setRemark(remarkDict.MODIFY + remarkDict.USER + remarkDict.STATUS + "为" + remarkDict.PAUSE);
        }

        merchantEventService.save(merchantEvent);


        enterpriseService.modifyStatusById(id, status);
        return success("");
    }

    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE_COUNT_COMPANY_BY_PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "统计子代理的下属企业数量", notes = "统计子代理的下属企业数量")
    public Integer countCompayByParentId(
            @ApiParam(name = "parentId", value = "父ID", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "isDel", value = "是否删除", defaultValue = "")
            @RequestParam(value = "isDel", required = false) Integer isDel) {
        Integer intTemp = enterpriseService.countCompayByParentId(parentId, isDel);
        return intTemp;
    }

    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE_TOTAL_INFO, method = RequestMethod.GET)
    @ApiOperation(value = "获取企业(子企业)的统计信息", notes = "获取企业(子企业)的统计信息 ")
    public Result<EnterPriseTotalCountInfo> getEnterPriseTotalInfo(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "flag", value = "flag", defaultValue = "")
            @RequestParam(value = "flag", required = false) boolean flag) throws Exception {
        EnterPriseTotalCountInfo enterPriseTotalCountInfo = enterpriseService.getEnterPriseTotalInfo(id, flag);
        return success(enterPriseTotalCountInfo);
    }


    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE_NO_PAGE, method = RequestMethod.GET)
    @ApiOperation(value = "获取企业列表不分页", notes = "获取企业列表不分页")
    public Result<User> searchNoPage(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters) throws Exception {
        List<User> userList = enterpriseService.search(filters);
        return getResultList(userList);
    }

    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE_COUNT_PARENT_ID_AND_MERCHANT_LEVEL, method = RequestMethod.GET)
    @ApiOperation(value = "根据父ID和等级获取统计数量", notes = "根据父ID和等级获取统计数量")
    public Long countByParentIdAndMerchantLevel(
            @ApiParam(name = "parentId", value = "parentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "")
            @RequestParam(value = "merchantLevel", required = false) String merchantLevel) throws Exception {
        Long log = enterpriseService.countByParentIdAndMerchantLevel(parentId, merchantLevel);
        return log;
    }


    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE_BY_TERMINAL, method = RequestMethod.GET)
    @ApiOperation(value = "获取企业下的用户，终端专用", notes = "获取企业下的用户，终端专用")
    public Result<User> searchByTerminal(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "language", value = "语言", defaultValue = "")
            @RequestParam(value = "language", required = false) String language,
            @ApiParam(name = "parentId", value = "父ID", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "GT_createDate", value = "GT_createDate", defaultValue = "")
            @RequestParam(value = "GT_createDate", required = false) String GT_createDate,
            @ApiParam(name = "LT_createDate", value = "LT_createDate", defaultValue = "")
            @RequestParam(value = "LT_createDate", required = false) String LT_createDate,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        try {
            if (RegexUtil.isNull(parentId)) {
                Page<User> result = enterpriseService.search(filters, sorts, page, size);
                return getResultList(result.getContent(), result.getTotalElements(), page, size);
            } else if (!RegexUtil.isNull(LT_createDate) && !RegexUtil.isNull(GT_createDate) && !RegexUtil.isNull(parentId)) {
                List<User> result1 = enterpriseService.search("EQ_parentId=" + parentId + ";EQ_isDel=1;EQ_merchantLevel=7");
                if (result1.size() > 0) {
                    String str = result1.stream().map(x -> x.getqUser().getId().toString()).collect(Collectors.joining(","));
                    List<QUser> qUserResult = qUserService.search("GT_createDate=" + GT_createDate + ";LT_createDate=" + LT_createDate + ";IN_id=" + str);
                    if (qUserResult.size() > 0) {
                        String str2 = qUserResult.stream().map(x -> x.getUser().getId().toString()).collect(Collectors.joining(","));
                        filters = filters + ";IN_id=" + str2;
                    }
                    Page<User> result = enterpriseService.search(filters, "-createDate", size, page);
                    return getResultList(result.getContent(), result.getTotalElements(), page, size);
                }
            } else {
                Page<User> result = enterpriseService.search("EQ_parentId=" + parentId + ";EQ_isDel=1;EQ_merchantLevel=7;" + filters, "-createDate", page, size);
                return getResultList(result.getContent(), result.getTotalElements(), page, size);
            }

        } catch (Exception e) {
            logger.error("获取企业操作列表失败");
            logger.error(e.getMessage(), e);
            return error("获取企业操作列表失败！");
        }
        return null;
    }


}
