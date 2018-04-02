package com.meta.controller.accountant;

import com.meta.BaseControllerUtil;
import com.meta.MD5.MD5Util;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.datetime.DateTimeUtil;
import com.meta.model.accountant.AccountantEvent;
import com.meta.model.qmanage.QAccountantPassword;
import com.meta.model.user.User;
import com.meta.regex.RegexUtil;
import com.meta.remark.remarkDict;
import com.meta.service.accountant.AccountantEventService;
import com.meta.service.accountant.AccountantManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.InetAddress;
import java.util.Date;

/**
 * create by lhq
 * create date on  18-3-1下午4:02
 *
 * @version 1.0
 **/
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "accountant_manage", description = "会计信息接口")
public class AccountantManageController extends BaseControllerUtil {
    @Autowired
    private AccountantManageService accountantManageService;
    @Autowired
    private AccountantEventService accountantEventService;


    @RequestMapping(value = ServiceUrls.AccountantManage.ACCOUNTANT_MANAGES, method = RequestMethod.GET)
    @ApiOperation(value = "获取会计列表", notes = "根据查询条件获会计列表在前端表格展示")
    public Result<User> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<User> result = accountantManageService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }


    @RequestMapping(value = ServiceUrls.AccountantManage.ACCOUNTANT_MANAGE, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改会计信息", notes = "创建/修改会计信息")
    public Result<User> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid User user) throws Exception {
        User userParent = accountantManageService.findOne(getUserId());
        if (RegexUtil.isNull(user.getId())) {
            User userTemp = accountantManageService.findByAccount(user.getAccount());
            if (!RegexUtil.isNull(userTemp)) {
                return error("该账号已存在，请重新填写！");
            }
            if (RegexUtil.isNull(user.getId())) {
                user.setParentId(getUserId());
                user.setIsDel(1);

                AccountantEvent accountantEvent = new AccountantEvent();
                accountantEvent.setByUserAccount(user.getAccount());
                accountantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                accountantEvent.setType(3);
                accountantEvent.setCreateUser(userParent.getId());
                accountantEvent.setIp(InetAddress.getLocalHost().getHostAddress());
                if (user.getLanguage().equals("zh")) {
                    accountantEvent.setRemark(remarkDict.ADD + remarkDict.USER + user.getName());
                } else if (user.getLanguage().equals("en")) {
                    accountantEvent.setRemark(remarkDict.ADD_ENGLISHI + "  " + remarkDict.USER_ENGLISH + "  " + user.getName());
                }
                accountantEvent.setUser(userParent);

                //设置支付密码
                QAccountantPassword qAccountantPassword = new QAccountantPassword();
                qAccountantPassword.setPassword(MD5Util.hashStr("123456"));
                qAccountantPassword.setUser(user);
                user.setqAccountantPassword(qAccountantPassword);

                accountantEventService.save(accountantEvent);
            }

        } else {
            AccountantEvent accountantEvent = new AccountantEvent();
            accountantEvent.setByUserAccount(user.getAccount());
            accountantEvent.setType(5);
            accountantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            accountantEvent.setIp(InetAddress.getLocalHost().getHostAddress());
            if (user.getLanguage().equals("zh")) {
                accountantEvent.setRemark(remarkDict.MODIFY + remarkDict.USER + user.getName());
            } else if (user.getLanguage().equals("en")) {
                accountantEvent.setRemark(remarkDict.MODIFY_ENGLISH + "  " + remarkDict.USER_ENGLISH + "  " + user.getName());
            }
            accountantEvent.setUser(userParent);
            accountantEventService.save(accountantEvent);

        }
        return success(accountantManageService.save(user));
    }


    @RequestMapping(value = ServiceUrls.AccountantManage.ACCOUNTANT_MANAGE_BY_ID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户")
    public Result deleteById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id) throws Exception {
        accountantManageService.delete(id);
        return success(null);
    }


    @RequestMapping(value = ServiceUrls.AccountantManage.ACCOUNTANT_MANAGES, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    public Result modifyPassword(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) throws Exception {
        accountantManageService.modifyPasswordById(id, password);
        return success("");
    }

    @RequestMapping(value = ServiceUrls.AccountantManage.ACCOUNTANT_MANAGE_MODIFY_STATUS, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status) throws Exception {
        accountantManageService.modifyStatusById(id, status);
        return success("");
    }
}
