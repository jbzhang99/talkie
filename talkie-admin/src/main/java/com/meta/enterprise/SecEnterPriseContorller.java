package com.meta.enterprise;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.commonUtil.CommonUtils;
import com.meta.commonUtil.EnglishCommonUtils;
import com.meta.feignclient.enterprise.SecEnterPriseClient;
import com.meta.feignclient.qmanage.QSecEnterPriseClient;
import com.meta.model.enterprise.MEnterprise;
import com.meta.model.enterprise.MEnterpriseEvent;
import com.meta.model.group.MGroupUser;
import com.meta.model.qmanage.MQEnterprise;
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
import java.util.List;

/**
 * Created by lhq on 2017/11/21.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "sec_enterprise", description = "二级企业信息接口")
public class SecEnterPriseContorller extends BaseControllerUtil {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(SecEnterPriseContorller.class);
    @Autowired
    private SecEnterPriseClient secEnterPriseClient;
    @Autowired
    private QSecEnterPriseClient qEnterpriseClient;

    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISES, method = RequestMethod.GET)
    @ApiOperation(value = "获取二级企业列表", notes = "根据查询条件获二级企业列表在前端表格展示")
    public Result<MEnterprise> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MEnterprise> result = null;
        try {
            result = secEnterPriseClient.search(filters, "-createDate", size, page);
            if (result.getDetailModelList().size() > 0) {
                findDetail(result, getLanguage());
            }

        } catch (Exception e) {
            logger.error("获取二级企业操作列表失败！");
            logger.error(e.getMessage(), e);
            return error("获取二级企业操作列表失败！");
        }
        return result;
    }


    private Result<MEnterprise> findDetail(Result<MEnterprise> mEnterpriseResult, String language) {
        for (MEnterprise temp : mEnterpriseResult.getDetailModelList()) {
            Result<MQEnterprise> mqUser = null;
            mqUser = qEnterpriseClient.findByUserId(temp.getId());
            temp.setRemainQ(mqUser.getObj().getBalance());
            temp.setModifyDate(mqUser.getObj().getModifyDate());
            temp.setCountCompany(secEnterPriseClient.countByParentIdAndMerchantLevel(temp.getId(), "7"));

            if ("zh".equals(language)) {
                if (!RegexUtil.isNull(temp.getStatus())) {
                    temp.setStatusName(CommonUtils.findByStatusName(temp.getStatus()));
                }
            } else if ("en".equals(language)) {
                if (!RegexUtil.isNull(temp.getStatus())) {
                    temp.setStatusName(EnglishCommonUtils.findByStatusName(temp.getStatus()));
                }
            }
        }
        return mEnterpriseResult;
    }


    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISES_NO_PAGE, method = RequestMethod.GET)
    @ApiOperation(value = "获取二级企业列表不分页", notes = "根据查询条件获二级企业列表在前端表格展示")
    public Result<MEnterprise> searchNoPage(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters) {
        Result<MEnterprise> result = null;
        try {
            result = secEnterPriseClient.searchNoPage(filters);

        } catch (Exception e) {
            logger.error("获取二级企业操作列表失败！");
            logger.error(e.getMessage(), e);
            return error("获取二级企业操作列表失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISES, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改二级企业信息", notes = "创建/修改二级企业信息")
    public Result<MEnterprise> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MEnterprise mEnterprise) throws Exception {
        Result<MEnterprise> result = null;
        try {
            result = secEnterPriseClient.create(mEnterprise);
        } catch (Exception e) {
            logger.error("创建/修改二级管理失败！");
            logger.error(e.getMessage(), e);
            return error("创建/修改二级管理失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISES, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户")
    public Result deleteById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) throws Exception {
        Result<MEnterprise> result = null;
        try {
            result = secEnterPriseClient.deleteById(id);
        } catch (Exception e) {
            logger.error("删除二级管理失败！");
            logger.error(e.getMessage(), e);
            return error("删除二级管理失败！");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISE, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    public Result modifyPasswordById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) throws Exception {
        Result<MEnterprise> result = null;
        try {
            result = secEnterPriseClient.modifyPasswordById(id, password);
        } catch (Exception e) {
            logger.error("修改密码失败！");
            logger.error(e.getMessage(), e);
            return error("修改密码失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISE_FIND_BY_ACCOUNT_AND__PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据账号，用户等级，父ID查找详情", notes = "根据账号，用户等级，父ID查找详情")
    public Result<MEnterprise> findByAccountAndParentId(
            @ApiParam(name = "account", value = "account", defaultValue = "")
            @RequestParam(value = "account", required = false) String account,
            @ApiParam(name = "parentId", value = "ParentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId) {
        Result<MEnterprise> result = null;
        try {
            result = secEnterPriseClient.findByAccountAndParentId(account, parentId);
        } catch (Exception e) {
            logger.error("查找详情失败！");
            logger.error(e.getMessage(), e);
            return error("查找详情失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISE_MODIFY_STATUS_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status) throws Exception {
        Result<MEnterprise> result = null;
        try {
            result = secEnterPriseClient.modifyStatusById(id, status);
        } catch (Exception e) {
            logger.error("变更状态失败！");
            logger.error(e.getMessage(), e);
            return error("变更状态失败！");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISE_WAIT_USER_GROUP, method = RequestMethod.GET)
    @ApiOperation(value = "群组用，查找待添加的用户", notes = "群组用，查找待添加的用户")
    public Result<MGroupUser> findWaitUserGroup(
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId", required = false) Long userId,
            @ApiParam(name = "groupId", value = "groupId", defaultValue = "")
            @RequestParam(value = "groupId", required = false) Long groupId) throws Exception {
        Result<MGroupUser> result = null;
        try {
            result = secEnterPriseClient.findWaitUserGroup(userId, groupId);
        } catch (Exception e) {
            logger.error("查找失败！");
            logger.error(e.getMessage(), e);
            return error("查找失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISE_FIND_ALREADY_USER_GROUP, method = RequestMethod.GET)
    @ApiOperation(value = "查找已添加 的用户列表(群组用)", notes = "查找已添加 的用户列表(群组用)")
    public Result<MGroupUser> findAlreadyUserGroup(
            @ApiParam(name = "groupId", value = "groupId", defaultValue = "")
            @RequestParam(value = "groupId", required = false) Long groupId) throws Exception {
        Result<MGroupUser> result = null;
        try {
            result = secEnterPriseClient.findAlreadyUserGroup(groupId);
        } catch (Exception e) {
            logger.error("查找失败！");
            logger.error(e.getMessage(), e);
            return error("查找失败！");
        }
        return result;
    }


}
