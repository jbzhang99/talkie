package com.meta.controller.enterprise;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.datetime.DateTimeUtil;
import com.meta.model.enterprise.EnterpriseEvent;
import com.meta.model.group.Group;
import com.meta.model.qmanage.QUser;
import com.meta.model.user.User;
import com.meta.regex.RegexUtil;
import com.meta.remark.remarkDict;
import com.meta.service.enterprise.EnterpriseEventService;
import com.meta.service.enterprise.SecEnterPriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;

/**
 * Created by lhq on 2017/11/21.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "sec_enterprise", description = "二级企业信息接口")
public class SecEnterPriseController extends BaseControllerUtil {

    @Autowired
    private SecEnterPriseService secEnterPriseService;
    @Autowired
    private EnterpriseEventService enterpriseEventService;

    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISES, method = RequestMethod.GET)
    @ApiOperation(value = "获取二级企业列表", notes = "根据查询条件获二级企业列表在前端表格展示")
    public Result<User> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<User> result = secEnterPriseService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }

    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISES_NO_PAGE, method = RequestMethod.GET)
    @ApiOperation(value = "获取二级企业列表不分页", notes = "根据查询条件获二级企业列表在前端表格展示")
    public Result<User> searchNoPage(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters) {
        List<User> result = secEnterPriseService.search(filters);
        return getResultList(result);
    }

    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISES, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改二级企业信息", notes = "创建/修改二级企业信息")
    public Result<User> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid User user) throws Exception {
        User userParent = secEnterPriseService.findOne(getUserId());
        if (RegexUtil.isNull(user.getId())) {
            User userTemp = secEnterPriseService.findByAccount(user.getAccount());
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
             *  操作记录
             */
            EnterpriseEvent enterpriseEvent = new EnterpriseEvent();
            enterpriseEvent.setType(3);
            enterpriseEvent.setByUserAccount(user.getAccount());
            enterpriseEvent.setCreateUser(userParent.getId());
            enterpriseEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            if (user.getLanguage().equals("zh")) {
                enterpriseEvent.setRemark(remarkDict.ADD + remarkDict.USER + user.getName());
            } else if (user.getLanguage().equals("en")) {
                enterpriseEvent.setRemark(remarkDict.ADD_ENGLISHI + "  " + remarkDict.USER_ENGLISH + "  " + user.getName());
            }
            enterpriseEvent.setIp(getIpAddr());
            enterpriseEvent.setUser(userParent);
            enterpriseEventService.save(enterpriseEvent);
            return success(secEnterPriseService.save(user));

        } else {
            /**
             * 操作记录
             */
            //企业操作
            if (userParent.getMerchantLevel().equals("4")) {
                EnterpriseEvent enterpriseEvent = new EnterpriseEvent();
                enterpriseEvent.setByUserAccount(user.getAccount());
                enterpriseEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                enterpriseEvent.setCreateUser(userParent.getId());
                enterpriseEvent.setType(5);
                if (user.getLanguage().equals("zh")) {
                    enterpriseEvent.setRemark(remarkDict.MODIFY + remarkDict.USER + user.getName());
                } else if (user.getLanguage().equals("en")) {
                    enterpriseEvent.setRemark(remarkDict.MODIFY_ENGLISH + "  " + remarkDict.USER_ENGLISH + "  " + user.getName());
                }
                enterpriseEvent.setIp(getIpAddr());
                enterpriseEvent.setUser(userParent);
                enterpriseEventService.save(enterpriseEvent);
            }
            if (userParent.getMerchantLevel().equals("14")) {  //自己操作
                EnterpriseEvent enterpriseEvent = new EnterpriseEvent();
                enterpriseEvent.setType(5);
                enterpriseEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                enterpriseEvent.setByUserAccount(user.getAccount());
                if (user.getLanguage().equals("zh")) {
                    enterpriseEvent.setRemark(remarkDict.ADD + remarkDict.USER + user.getName());
                } else if (user.getLanguage().equals("en")) {
                    enterpriseEvent.setRemark(remarkDict.ADD_ENGLISHI + "  " + remarkDict.USER_ENGLISH + "  " + user.getName());
                }
                enterpriseEvent.setCreateUser(userParent.getId());
                enterpriseEvent.setIp(getIpAddr());
                enterpriseEvent.setUser(userParent);
                enterpriseEventService.save(enterpriseEvent);
            }
            return success(secEnterPriseService.save(user));
        }
    }

    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISES, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户")
    public Result deleteById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) throws Exception {
        secEnterPriseService.delete(id);
        return success(null);

    }

    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISE, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    public Result modifyPasswordById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) throws Exception {
        secEnterPriseService.modifyPasswordById(id, password);
        return success("");
    }

    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISE_FIND_BY_ACCOUNT_AND__PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据账号，用户等级，父ID查找详情", notes = "根据账号，用户等级，父ID查找详情")
    public Result<User> findByAccountAndParentId(
            @ApiParam(name = "account", value = "account", defaultValue = "")
            @RequestParam(value = "account", required = false) String account,
            @ApiParam(name = "parentId", value = "ParentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId) throws Exception {
        User user = secEnterPriseService.findByAccountAndParentId(account, parentId);
        return success(user);
    }

    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISE_MODIFY_STATUS_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status) throws Exception {
        secEnterPriseService.modifyStatusById(id, status);
        return success("");
    }

    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISE_COUNT_COMPANY_BY_PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "统计子代理的下属企业数量", notes = "统计子代理的下属企业数量")
    public Integer countCompayByParentId(
            @ApiParam(name = "parentId", value = "父ID", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "isDel", value = "是否删除", defaultValue = "")
            @RequestParam(value = "isDel", required = false) Integer isDel) {
        Integer intTemp = secEnterPriseService.countCompayByParentId(parentId, isDel);
        return intTemp;
    }

    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISE_COUNT_PARENT_ID_AND_MENCHANT_LEVEL,method = RequestMethod.GET)
    @ApiOperation(value = "根据PARENTID和MENCHANTLEVEL统计数量",notes = "根据PARENTID和MENCHANTLEVEL统计数量")
    public  Long countByParentIdAndMerchantLevel(
            @ApiParam(name = "parentId", value = "父ID", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "")
            @RequestParam(value = "merchantLevel", required = false) String  merchantLevel) throws Exception{
       Long log=secEnterPriseService.countByParentIdAndMerchantLevel(parentId, merchantLevel);
       return log;
    }


    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISE_WAIT_USER_GROUP, method = RequestMethod.GET)
    @ApiOperation(value = "群组用，查找待添加的用户", notes = "群组用，查找待添加的用户")
    public Result<User> findWaitUserGroup(
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId", required = false) Long userId,
            @ApiParam(name = "groupId", value = "groupId", defaultValue = "")
            @RequestParam(value = "groupId", required = false) Long groupId) throws Exception {
        List<User> users = secEnterPriseService.findWaitUserGroup(userId, groupId);
        return getResultList(users);
    }

    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISE_FIND_ALREADY_USER_GROUP, method = RequestMethod.GET)
    @ApiOperation(value = "查找已添加 的用户列表(群组用)", notes = "查找已添加 的用户列表(群组用)")
    public Result<User> findAlreadyUserGroup(
            @ApiParam(name = "groupId", value = "groupId", defaultValue = "")
            @RequestParam(value = "groupId", required = false) Long groupId) throws Exception {
        List<User> users = secEnterPriseService.findAlreadyUserGroup(groupId);
        return getResultList(users);
    }


}
