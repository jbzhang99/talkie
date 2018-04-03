package com.meta.enterprise;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.addressDict.AddressDictController;
import com.meta.commonUtil.CommonUtils;
import com.meta.commonUtil.EnglishCommonUtils;
import com.meta.feignclient.enterprise.EnterPriseAccountClient;
import com.meta.model.enterprise.MEnterpriseAccount;
import com.meta.model.generalagent.MGeneralAgentAccount;
import com.meta.model.merchant.MMerchantAccount;
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
@Api(value = "enter_prise_account", description = "企业账号管理信息接口")
public class EnterPriseAccountController extends BaseControllerUtil {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(EnterPriseAccountController.class);

    @Autowired
    private EnterPriseAccountClient enterPriseAccountClient;

    @RequestMapping(value = ServiceUrls.EnterPriseAccount.ENTER_PRISE_ACCOUNTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改账号信息", notes = "创建/修改企业信息")
    public Result<MEnterpriseAccount> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MEnterpriseAccount mEnterpriseAccount) throws Exception {
        Result<MEnterpriseAccount> result = null;
        try {
            result = enterPriseAccountClient.create(mEnterpriseAccount);
        } catch (Exception e) {
            logger.error("创建/修改账号失败！");
            logger.error(e.getMessage(), e);
            return error("创建/修改账号失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.EnterPriseAccount.ENTER_PRISE_ACCOUNTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取企业(账号管理)列表", notes = "根据查询条件获企业(账号管理)列表在前端表格展示")
    public Result<MEnterpriseAccount> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MEnterpriseAccount> result = null;
        try {
            result = enterPriseAccountClient.search(filters, "-createDate", size, page);
            if (result.getDetailModelList().size() > 0) {
                findDetail(result);
            }
        } catch (Exception e) {
            logger.error("获取企业(账号管理)列表失败");
            logger.error(e.getMessage(), e);
            return error("获取企业(账号管理)列表失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.EnterPriseAccount.ENTER_PRISE_ACCOUNT, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    public Result<MEnterpriseAccount> modifyPassword(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) throws Exception {
        Result<MEnterpriseAccount> result = null;
        try {
            result = enterPriseAccountClient.modifyPassword(id, password);
        } catch (Exception e) {
            logger.error("修改密码失败");
            logger.error(e.getMessage(), e);
            return error("修改密码失败");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.EnterPriseAccount.ENTER_PRISE_ACCOUNT_MODIFY_STATUS_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result<MEnterpriseAccount> modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status) throws Exception {
        Result<MEnterpriseAccount> result = null;
        try {
            result = enterPriseAccountClient.modifyStatusById(id, status);
        } catch (Exception e) {
            logger.error("变更状态失败");
            logger.error(e.getMessage(), e);
            return error("变更状态失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.EnterPriseAccount.ENTER_PRISE_ACCOUNTS, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除账号", notes = "根据ID删除账号")
    public Result<MEnterpriseAccount> delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) throws Exception {
        Result<MEnterpriseAccount> result = null;
        try {
            result = enterPriseAccountClient.delete(id);
        } catch (Exception e) {
            logger.error("删除账号失败");
            logger.error(e.getMessage(), e);
            return error("删除账号失败！");
        }
        return result;
    }

    private Result<MEnterpriseAccount> findDetail(Result<MEnterpriseAccount> result) {

        result.getDetailModelList().stream().forEach(a->{
            if ("zh".equals(getLanguage())) {
                //中文
                a.setStatusName(CommonUtils.findByStatusName(a.getStatus()));
                if (!RegexUtil.isNull(a.getMerchantLevel())) {
                    a.setMerchantLevelName(CommonUtils.findMerchantLevel(a.getMerchantLevel()));
                }
            } else if ("en".equals(getLanguage())) {
                a.setStatusName(EnglishCommonUtils.findByStatusName(a.getStatus()));
                if (!RegexUtil.isNull(a.getMerchantLevel())) {
                    a.setMerchantLevelName(EnglishCommonUtils.findMerchantLevel(a.getMerchantLevel()));
                }
            }
        });
        return result;
    }

}
