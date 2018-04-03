package com.meta.merchant;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.commonUtil.CommonUtils;
import com.meta.commonUtil.EnglishCommonUtils;
import com.meta.feignclient.merchant.MerchantAccountClient;
import com.meta.model.enterprise.MEnterpriseAccount;
import com.meta.model.merchant.MMerchant;
import com.meta.model.merchant.MMerchantAccount;
import com.meta.model.qmanage.MQMerchant;
import com.meta.regex.RegexUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by lhq on 2017/11/17.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "merchant_account", description = "代理账号管理信息接口")
public class MerchantAccountController extends BaseControllerUtil {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(MerchantAccountController.class);
    @Autowired
    private MerchantAccountClient merchantAccountClient;

    @RequestMapping(value = ServiceUrls.MerchantAccount.MERCHANT_ACCOUNTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取代理(账号管理)列表", notes = "根据查询条件获代理(账号管理)列表在前端表格展示")
    public Result<MMerchantAccount> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MMerchantAccount> result = null;
        try {
            result = merchantAccountClient.search(filters, "-createDate", size, page);
            if (result.getDetailModelList().size() > 0) {
                findDetail(result);
            }
        } catch (Exception e) {
            logger.error("获取代理(账号管理)列表失败！");
            logger.error(e.getMessage(), e);
            return error("获取代理(账号管理)列表失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.MerchantAccount.MERCHANT_ACCOUNT, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    public Result<MMerchantAccount> modifyPassword(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) throws Exception {
        Result<MMerchantAccount> result = null;
        try {
            result = merchantAccountClient.modifyPassword(id, password);
        } catch (Exception e) {
            logger.error("修改密码失败！ ");
            logger.error(e.getMessage(), e);
            return error("修改密码失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.MerchantAccount.MERCHANT_ACCOUNT_MODIFY_STATUS_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result<MMerchantAccount> modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status) throws Exception {
        Result<MMerchantAccount> result = null;
        try {
            result = merchantAccountClient.modifyStatusById(id, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("变更状态失败！ ");
            return error("变更状态失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.MerchantAccount.MERCHANT_ACCOUNTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改代理(账号管理)信息", notes = "创建/修改代理(账号管理)信息")
    public Result<MMerchantAccount> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MMerchantAccount mMerchantAccount) throws Exception {
        Result<MMerchantAccount> result = null;
        try {
            result = merchantAccountClient.create(mMerchantAccount);
        } catch (Exception e) {
            logger.error("创建/修改代理(账号管理)失败！");
            logger.error(e.getMessage(), e);
            return error("创建/修改代理(账号管理)失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.MerchantAccount.MERCHANT_ACCOUNTS, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除账号", notes = "根据ID删除账号")
    public Result<MMerchantAccount> delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) throws Exception {
        Result<MMerchantAccount> result = null;
        try {
            result = merchantAccountClient.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("删除账号失败！");
            return error("删除账号失败！");
        }
        return result;
    }

    private Result<MMerchantAccount> findDetail(Result<MMerchantAccount> result) {
        result.getDetailModelList().stream().forEach(o -> {
            if ("zh".equals(getLanguage())) {
                //中文
                o.setStatusName(CommonUtils.findByStatusName(o.getStatus()));
                if (!RegexUtil.isNull(o.getMerchantLevel())) {
                    o.setMerchantLevelName(CommonUtils.findMerchantLevel(o.getMerchantLevel()));
                }
            } else if ("en".equals(getLanguage())) {
                o.setStatusName(EnglishCommonUtils.findByStatusName(o.getStatus()));
                if (!RegexUtil.isNull(o.getMerchantLevel())) {
                    o.setMerchantLevelName(EnglishCommonUtils.findMerchantLevel(o.getMerchantLevel()));
                }
            }
        });
        return result;
    }

}
