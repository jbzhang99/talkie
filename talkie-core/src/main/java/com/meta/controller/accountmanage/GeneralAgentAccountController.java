package com.meta.controller.accountmanage;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.datetime.DateTimeUtil;
import com.meta.error.errorMsgDict;
import com.meta.model.GeneralAgent.GeneralAgentEvent;
import com.meta.model.user.User;
import com.meta.regex.RegexUtil;
import com.meta.remark.remarkDict;
import com.meta.service.accountmanage.GeneralAgentAccountService;
import com.meta.service.generalagent.GeneralAgentEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Created by lhq on 2017/11/17.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "general_agent_account", description = "总代理账号管理信息接口")
public class GeneralAgentAccountController extends BaseControllerUtil {

    @Autowired
    private GeneralAgentAccountService generalAgentAccountService;

    @Autowired
    private GeneralAgentEventService generalAgentEventService;

    @RequestMapping(value = ServiceUrls.GeneralAgentAccount.GENERAL_AGENT_ACCOUNTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取总代(账号管理)列表", notes = "根据查询条件获总代(账号管理)列表在前端表格展示")
    public Result<User> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<User> result = generalAgentAccountService.searchExtendDistinct(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }

    @RequestMapping(value = ServiceUrls.GeneralAgentAccount.GENERAL_AGENT_ACCOUNTS, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    public Result modifyPassword(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) throws Exception {
        generalAgentAccountService.modifyPasswordById(id, password);
        return success("");
    }

    @RequestMapping(value = ServiceUrls.GeneralAgentAccount.GENERAL_AGENT_ACCOUNT_MODIFY_STATUS_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status) throws Exception {
        generalAgentAccountService.modifyStatusById(id, status);
        return success("");
    }

    @RequestMapping(value = ServiceUrls.GeneralAgentAccount.GENERAL_AGENT_ACCOUNT, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改总代(账号管理)信息", notes = "创建/修改总代(账号管理)信息")
    public Result<User> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid User user) throws Exception {

        User userParent = generalAgentAccountService.findOne(getUserId());
        if (RegexUtil.isNull(user.getId())) {
            Long countAccount = generalAgentAccountService.countByParentId(getUserId(), "9", "8");
            //账号管理，最多只能建20个，
            if (countAccount >= 20) {
                return error(errorMsgDict.ADD_ACCOUNT_TO_LIMIT);
            }
            User userTemp = generalAgentAccountService.findByAccount(user.getAccount());
            if (!RegexUtil.isNull(userTemp)) {
                return error("该账号已存在，请重新填写！");
            }
            user.setParentId(getUserId());
            user.setIsDel(1);

            /**
             *  操作记录
             */
            GeneralAgentEvent generalAgentEvent = new GeneralAgentEvent();
            generalAgentEvent.setByUserAccount(user.getAccount());
            generalAgentEvent.setType(3);
            generalAgentEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            generalAgentEvent.setCreateUser(userParent.getId());
            if (user.getLanguage().equals("zh")) {
                generalAgentEvent.setRemark(remarkDict.ADD + remarkDict.USER + user.getName());
            } else if (user.getLanguage().equals("en")) {
                generalAgentEvent.setRemark(remarkDict.ADD_ENGLISHI + "  " + remarkDict.USER_ENGLISH + "  " + user.getName());
            }
            generalAgentEvent.setIp(getIpAddr());
            generalAgentEvent.setUser(userParent);
            generalAgentEventService.save(generalAgentEvent);
            return success(generalAgentAccountService.save(user));

        } else {
            /**
             * 操作记录
             */

            GeneralAgentEvent generalAgentEvent = new GeneralAgentEvent();
            generalAgentEvent.setByUserAccount(user.getAccount());
            generalAgentEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            generalAgentEvent.setType(5);
            generalAgentEvent.setCreateUser(userParent.getId());
            if (user.getLanguage().equals("zh")) {
                generalAgentEvent.setRemark(remarkDict.MODIFY + remarkDict.USER + user.getName());
            } else if (user.getLanguage().equals("en")) {
                generalAgentEvent.setRemark(remarkDict.MODIFY_ENGLISH + "  " + remarkDict.USER_ENGLISH + "  " + user.getName());
            }
            generalAgentEvent.setIp(getIpAddr());
            generalAgentEvent.setUser(userParent);
            generalAgentEventService.save(generalAgentEvent);

            return success(generalAgentAccountService.save(user));
        }
    }

    @RequestMapping(value = ServiceUrls.GeneralAgentAccount.GENERAL_AGENT_ACCOUNTS, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除账号", notes = "根据ID删除账号")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) throws Exception {
        generalAgentAccountService.delete(id);
        return success(null);
    }

}
