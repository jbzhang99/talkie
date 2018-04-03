package com.meta.generalagent;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.commonUtil.CommonUtils;
import com.meta.commonUtil.EnglishCommonUtils;
import com.meta.feignclient.generalagent.GeneralAgentAccountClient;
import com.meta.model.generalagent.MGeneralAgentAccount;
import com.meta.model.merchant.MMerchant;
import com.meta.model.qmanage.MQMerchant;
import com.meta.regex.RegexUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.UnknownHostException;

/**
 * Created by lhq on 2017/11/17.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "general_agent_account", description = "总代理账号管理信息接口")
public class GeneralAgentAccountController extends BaseControllerUtil {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(GeneralAgentAccountController.class);
    @Autowired
    private GeneralAgentAccountClient generalAgentAccountClient;

    @RequestMapping(value = ServiceUrls.GeneralAgentAccount.GENERAL_AGENT_ACCOUNTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取总代(账号管理)列表", notes = "根据查询条件获总代(账号管理)列表在前端表格展示")
    public Result<MGeneralAgentAccount> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MGeneralAgentAccount> result = null;
        try {
            result = generalAgentAccountClient.search(filters, "-createDate", size, page);
            if (result.getDetailModelList().size() > 0) {
                findDetail(result);
            }
        } catch (Exception e) {
            logger.error("获取总代(账号管理)列表失败！");
            logger.error(e.getMessage(), e);
            return error("获取总代(账号管理)列表失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.GeneralAgentAccount.GENERAL_AGENT_ACCOUNTS, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    public Result<MGeneralAgentAccount> modifyPassword(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) throws Exception {
        Result<MGeneralAgentAccount> result = null;
        try {
            result = generalAgentAccountClient.modifyPassword(id, password);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("修改密码失败！ ");
            return error("修改密码失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.GeneralAgentAccount.GENERAL_AGENT_ACCOUNT_MODIFY_STATUS_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result<MGeneralAgentAccount> modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status) throws Exception {
        Result<MGeneralAgentAccount> result = null;
        try {
            result = generalAgentAccountClient.modifyStatusById(id, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("变更状态失败！  ");
            return error("变更状态失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.GeneralAgentAccount.GENERAL_AGENT_ACCOUNT, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改总代(账号管理)信息", notes = "创建/修改总代(账号管理)信息")
    public Result<MGeneralAgentAccount> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MGeneralAgentAccount mGeneralAgentAccount) throws Exception {
        Result<MGeneralAgentAccount> result = null;
        try {
            result = generalAgentAccountClient.create(mGeneralAgentAccount);
        } catch (Exception e) {
            logger.error("变更状态失败！");
            logger.error(e.getMessage(), e);
            return error("变更状态失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.GeneralAgentAccount.GENERAL_AGENT_ACCOUNTS, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除账号", notes = "根据ID删除账号")
    public Result<MGeneralAgentAccount> delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) throws Exception {
        Result<MGeneralAgentAccount> result = null;
        try {
            result = generalAgentAccountClient.delete(id);
        } catch (Exception e) {
            logger.error("删除账号失败！   ");
            logger.error(e.getMessage(), e);
            return error("删除账号失败！");
        }
        return result;
    }

    private Result<MGeneralAgentAccount> findDetail(Result<MGeneralAgentAccount> result) {
       result.getDetailModelList().stream().forEach(a->{
           if ("zh".equals(getLanguage())) {
               if (!RegexUtil.isNull(a.getMerchantLevel())) {
                   a.setMerchantLevelName(CommonUtils.findMerchantLevel(a.getMerchantLevel()));
               }
               a.setStatusName(CommonUtils.findByStatusName(a.getStatus()));
           } else if ("en".equals(getLanguage())) {
               if (!RegexUtil.isNull(a.getMerchantLevel())) {
                   a.setMerchantLevelName(EnglishCommonUtils.findMerchantLevel(a.getMerchantLevel()));
               }
               a.setStatusName(EnglishCommonUtils.findByStatusName(a.getStatus()));
           }


       });
        return result;
    }

}
