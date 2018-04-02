package com.meta.controller.accountmanage;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.datetime.DateTimeUtil;
import com.meta.error.errorMsgDict;
import com.meta.model.merchant.MerchantEvent;
import com.meta.model.user.User;
import com.meta.regex.RegexUtil;
import com.meta.remark.remarkDict;
import com.meta.service.accountmanage.SecMerchantAccountService;
import com.meta.service.merchant.MerchantEventService;
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
 * Created by lhq on 2017/11/20.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "sec_merchant_account", description = "子代理账号管理信息接口")
public class SecMerchantAccountController extends BaseControllerUtil {

    @Autowired
    private SecMerchantAccountService secMerchantAccountService;

    @Autowired
    private MerchantEventService merchantEventService;


    @RequestMapping(value = ServiceUrls.SecMerchantAccount.SEC_MERCHANT_ACCOUNTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取子代理(账号管理)列表", notes = "根据查询条件获子代理(账号管理)列表在前端表格展示")
    public Result<User> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<User> result = secMerchantAccountService.searchExtendDistinct(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }

    @RequestMapping(value = ServiceUrls.SecMerchantAccount.SEC_MERCHANT_ACCOUNT, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    public Result modifyPassword(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) throws Exception {
        secMerchantAccountService.modifyPasswordById(id, password);
        return success("");
    }

    @RequestMapping(value = ServiceUrls.SecMerchantAccount.SEC_MERCHANT_ACCOUNT_MODIFY_STATUS_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status) throws Exception {
        secMerchantAccountService.modifyStatusById(id, status);
        return success("");
    }

    @RequestMapping(value = ServiceUrls.SecMerchantAccount.SEC_MERCHANT_ACCOUNTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改子代理(账号管理)信息", notes = "创建/修改子代理(账号管理)信息")
    public Result<User> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid User user) throws Exception {

        User userParent = secMerchantAccountService.findOne(getUserId());
        if (RegexUtil.isNull(user.getId())) {//如果userID是空的就是新建
            Long countAccount = secMerchantAccountService.countByParentId(getUserId(), "9", "8");
            //（代理啊子代理企业 ）  的账号管理，最多只能建20个，

            if (countAccount >= 20) {
                return error(errorMsgDict.ADD_MERCHANTS_ACCOUNT_TO_LIMIT);
            }
            User userTemp = secMerchantAccountService.findByAccount(user.getAccount());
            if (!RegexUtil.isNull(userTemp)) {
                return error("该账号已存在，请重新填写！");
            }
            user.setParentId(getUserId());
            user.setIsDel(1);
            /**
             *  操作记录
             */

            MerchantEvent merchantEvent = new MerchantEvent();
            merchantEvent.setByUserAccount(user.getAccount());
            merchantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            merchantEvent.setType(3);
            merchantEvent.setCreateUser(userParent.getId());
            merchantEvent.setIp(getIpAddr());
            if (user.getLanguage().equals("zh")) {
                merchantEvent.setRemark(remarkDict.MODIFY + remarkDict.USER + user.getName());
            } else if (user.getLanguage().equals("en")) {
                merchantEvent.setRemark(remarkDict.MODIFY_ENGLISH + "  " + remarkDict.USER_ENGLISH + "  " + user.getName());
            }
            merchantEvent.setUser(userParent);
            merchantEventService.save(merchantEvent);
            return success(secMerchantAccountService.save(user));

        } else {
            /**
             * 操作记录
             */

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

            return success(secMerchantAccountService.save(user));
        }
    }

    @RequestMapping(value = ServiceUrls.SecMerchantAccount.SEC_MERCHANT_ACCOUNTS, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除账号", notes = "根据ID删除账号")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) throws Exception {
        secMerchantAccountService.delete(id);
        return success(null);
    }


}
