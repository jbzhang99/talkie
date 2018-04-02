package com.meta.accountant;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.commonUtil.CommonUtils;
import com.meta.commonUtil.EnglishCommonUtils;
import com.meta.feignclient.accountant.AccountantManageClient;
import com.meta.model.accountant.MAccountant;
import com.meta.regex.RegexUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * create by lhq
 * create date on  18-3-1下午4:12
 *
 * @version 1.0
 **/
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "accountant_manage", description = "会计信息接口")
public class AccountantManageController extends BaseControllerUtil {

    @Autowired
    private AccountantManageClient accountantManageClient;

    @RequestMapping(value = ServiceUrls.AccountantManage.ACCOUNTANT_MANAGES, method = RequestMethod.GET)
    @ApiOperation(value = "获取会计列表", notes = "根据查询条件获会计列表在前端表格展示")
    public Result<MAccountant> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,

            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MAccountant> result = null;
        try {
            result = accountantManageClient.search(filters, sorts, size, page);
            if (result.getDetailModelList().size() > 0) {
                if (result.getDetailModelList().size() > 0) {
                    findDetail(result, getLanguage());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("获取会计列表异常");
            return error("获取会计列表异常");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.AccountantManage.ACCOUNTANT_MANAGE, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改会计信息", notes = "创建/修改会计信息")
    public Result<MAccountant> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MAccountant mAccountant) {
        Result<MAccountant> result = null;
        try {
            result = accountantManageClient.create(mAccountant);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("创建/修改会计操作失败！");
            return error("创建/修改会计操作失败！");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.AccountantManage.ACCOUNTANT_MANAGE_BY_ID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户")
    public Result<MAccountant> deleteById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id) throws Exception {
        Result<MAccountant> result = null;
        try {
            result = accountantManageClient.deleteById(id);
        } catch (Exception e) {

            logger.error("删除账号失败");
            logger.error(e.getMessage(), e);
            return error("删除账号失败！");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.AccountantManage.ACCOUNTANT_MANAGES, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    public Result<MAccountant> modifyPassword(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) throws Exception {
        Result<MAccountant> result = null;
        try {
            result = accountantManageClient.modifyPassword(id, password);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("修改密码异常");
            return error("修改密码异常");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.AccountantManage.ACCOUNTANT_MANAGE_MODIFY_STATUS, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status) throws Exception {
        Result<MAccountant> result = null;
        try {
            result = accountantManageClient.modifyStatusById(id, status);
        } catch (Exception e) {
            logger.error("修改状态异常");
            logger.error(e.getMessage(), e);
            return error("修改状态异常");
        }
        return result;
    }

    private Result<MAccountant> findDetail(Result<MAccountant> mAccountantResult, String language) {
        for (MAccountant temp : mAccountantResult.getDetailModelList()) {
            if (language.equals("zh")) {
                //中文
                temp.setStatusName(CommonUtils.findByStatusName(temp.getStatus()));
                if (!RegexUtil.isNull(temp.getMerchantLevel())) {
                    temp.setMerchantLevelName(CommonUtils.findMerchantLevel(temp.getMerchantLevel()));
                }
            } else if ("en".equals(language)) {
                temp.setStatusName(EnglishCommonUtils.findByStatusName(temp.getStatus()));
                if (!RegexUtil.isNull(temp.getMerchantLevel())) {
                    temp.setMerchantLevelName(EnglishCommonUtils.findMerchantLevel(temp.getMerchantLevel()));
                }
            }
        }
        return mAccountantResult;
    }
}
