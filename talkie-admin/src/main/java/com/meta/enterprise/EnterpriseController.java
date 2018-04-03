package com.meta.enterprise;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.commonUtil.CommonUtils;
import com.meta.commonUtil.EnglishCommonUtils;
import com.meta.error.errorMsgDict;
import com.meta.feignclient.enterprise.EnterpriseClient;
import com.meta.feignclient.qmanage.QEnterpriseClient;
import com.meta.feignclient.user.UserClient;
import com.meta.model.enterprise.MEnterprise;
import com.meta.model.qmanage.MQEnterprise;
import com.meta.model.totalinfo.MEnterPriseTotalCountInfo;
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
import java.util.stream.Collectors;

/**
 * Created by lhq on 2017/11/13.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "enterprise", description = "企业信息接口")
public class EnterpriseController extends BaseControllerUtil {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(EnterpriseController.class);

    @Autowired
    private UserClient userClient;
    @Autowired
    private EnterpriseClient enterpriseClient;
    @Autowired
    private QEnterpriseClient qEnterpriseClient;

    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISES, method = RequestMethod.GET)
    @ApiOperation(value = "获取企业操作列表", notes = "根据查询条件获企业操作列表在前端表格展示")
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
            result = enterpriseClient.search(filters, "-createDate", size, page);
            if (result.getDetailModelList().size() > 0) {
                findDetail(result);
            }

        } catch (Exception e) {
            logger.error("获取企业操作列表失败！");
            logger.error(e.getMessage(), e);
            return error("获取企业操作列表失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE_BY_TERMINAL, method = RequestMethod.GET)
    @ApiOperation(value = "获取企业下的用户，终端专用", notes = "获取企业下的用户，终端专用")
    public Result<MEnterprise> searchByTerminal(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "parentId", value = "父ID", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "GT_createDate", value = "GT_createDate", defaultValue = "")
            @RequestParam(value = "GT_createDate", required = false) String GT_createDate,
            @ApiParam(name = "LT_createDate", value = "LT_createDate", defaultValue = "")
            @RequestParam(value = "LT_createDate", required = false) String LT_createDate,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MEnterprise> result = null;
        try {
            result = enterpriseClient.searchByTerminal(filters, "-createDate", size, parentId, GT_createDate, LT_createDate, page);
            if (result.getDetailModelList().size() > 0) {
                findDetail(result);
            }

        } catch (Exception e) {
            logger.error("获取企业操作列表失败");
            logger.error(e.getMessage(), e);
            return error("获取企业操作列表失败！");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE_NO_PAGE, method = RequestMethod.GET)
    @ApiOperation(value = "获取企业列表不分页", notes = "获取企业列表不分页")
    public Result<MEnterprise> searchNoPage(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters) throws Exception {
        Result<MEnterprise> result = null;
        try {
            result = enterpriseClient.searchNoPage(filters);
        } catch (Exception e) {
            logger.error("获取企业操作列表失败！");
            logger.error(e.getMessage(), e);
            return error("获取企业操作列表失败！");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISES, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改企业信息", notes = "创建/修改企业信息")
    public Result<MEnterprise> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MEnterprise mEnterprise) {
        Result<MEnterprise> result = null;
        try {
            result = enterpriseClient.create(mEnterprise);
        } catch (Exception e) {
            logger.error("创建/修改企业操作失败！");
            logger.error(e.getMessage(), e);
            return error("创建/修改企业操作失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE_BY_ID_AND_LANGUAGE, method = RequestMethod.GET)
    @ApiOperation(value = "根据ID获取企业", notes = "根据ID获取企业")
    public Result<MEnterprise> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) throws Exception {
        Result<MEnterprise> result = null;
        try {
            result = enterpriseClient.get(id);
            if (!RegexUtil.isNull(result.getObj().getStatus())) {
                findObj(result);
            }

        } catch (Exception e) {
            logger.error("获取企业失败！");
            logger.error(e.getMessage(), e);
            return error("获取企业失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISES, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除企业", notes = "根据ID删除企业")
    public Result<MEnterprise> delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "")
            @RequestParam(value = "merchantLevel", required = false) String merchantLevel,
            @ApiParam(name = "currentUserId", value = "currentUserId", defaultValue = "")
            @RequestParam(value = "currentUserId", required = false) Long currentUserId) {
        Result<MEnterprise> result = null;
        try {
            /**
             * 删除企业
             * 需要判断
             * 1、改企业是否还有群组和用户
             */

            result = enterpriseClient.delete(id, merchantLevel, currentUserId);


        } catch (Exception e) {
            logger.error(errorMsgDict.DEL_COMPANY);
            logger.error(e.getMessage(), e);
            return error(errorMsgDict.DEL_COMPANY);
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    public Result<MEnterprise> modifyPassword(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) throws Exception {
        Result<MEnterprise> result = null;
        try {
            result = enterpriseClient.modifyPassword(id, password);
        } catch (Exception e) {
            logger.error("修改密码失败！  ");
            logger.error(e.getMessage(), e);
            return error("修改密码失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISES_FIND_BY_ACCOUNT_AND__PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据账号，用户等级，父ID查找详情", notes = "根据账号，用户等级，父ID查找详情")
    public Result<MEnterprise> findByAccountAndParentId(
            @ApiParam(name = "account", value = "account", defaultValue = "")
            @RequestParam(value = "account", required = false) String account,
            @ApiParam(name = "parentId", value = "ParentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId) throws Exception {
        Result<MEnterprise> result = null;
        try {
            result = enterpriseClient.findByAccountAndParentId(account, parentId);
        } catch (Exception e) {
            logger.error("修改密码失败！");
            logger.error(e.getMessage(), e);
            return error("修改密码失败！");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE_MODIFY_STATUS_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result<MEnterprise> modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status) throws Exception {
        Result<MEnterprise> result = null;
        try {
            result = enterpriseClient.modifyStatusById(id, status);
        } catch (Exception e) {
            logger.error("变更状态失败！  ");
            logger.error(e.getMessage(), e);
            return error("变更状态失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE_TOTAL_INFO, method = RequestMethod.GET)
    @ApiOperation(value = "获取企业(子企业)的统计信息", notes = "获取企业(子企业)的统计信息 ")
    public Result<MEnterPriseTotalCountInfo> getEnterPriseTotalInfo(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "flag", value = "flag", defaultValue = "")
            @RequestParam(value = "flag", required = false) boolean flag) throws Exception {
        Result<MEnterPriseTotalCountInfo> result = null;
        try {
            result = enterpriseClient.getEnterPriseTotalInfo(id, flag);
        } catch (Exception e) {
            logger.error("统计失败！");
            logger.error(e.getMessage(), e);
            return error("统计失败！");
        }
        return result;
    }

    private Result<MEnterprise> findDetail(Result<MEnterprise> result) {

        result.getDetailModelList().stream().forEach(a -> {

            if ("zh".equals(getLanguage())) {
                a.setIsOnLine(CommonUtils.isOnLine(userClient.queryUserOnlineStatus(a.getId())));
                if (!RegexUtil.isNull(a.getMerchantLevel())) {
                    a.setMerchantLevelName(CommonUtils.findMerchantLevel(a.getMerchantLevel()));
                }
                a.setStatusName(CommonUtils.findByStatusName(a.getStatus()));
            } else if ("en".equals(getLanguage())) {
                a.setStatusName(EnglishCommonUtils.findByStatusName(a.getStatus()));
                if (!RegexUtil.isNull(a.getMerchantLevel())) {
                    a.setMerchantLevelName(EnglishCommonUtils.findMerchantLevel(a.getMerchantLevel()));
                }
                a.setIsOnLine(EnglishCommonUtils.isOnLine(userClient.queryUserOnlineStatus(a.getId())));
            }

            Result<MQEnterprise> mqUser = null;
            mqUser = qEnterpriseClient.findByUserId(a.getId());

            if (!RegexUtil.isNull(mqUser.getObj())) {
                a.setRemainQ(mqUser.getObj().getBalance() != null ? mqUser.getObj().getBalance() : 0D);
                a.setModifyDate(mqUser.getObj().getModifyDate() );
            } else {
                a.setRemainQ(0D);
            }
            a.setCountCompany(enterpriseClient.countByParentIdAndMerchantLevel(a.getId(), "7"));

        });
        return result;
    }

    private Result<MEnterprise> findObj(Result<MEnterprise> mMerchantResult) {
        if ("zh".equals(getLanguage())) {
            //中文
            mMerchantResult.getObj().setStatusName(CommonUtils.findByStatusName(mMerchantResult.getObj().getStatus()));
        } else if ("en".equals(getLanguage())) {
            mMerchantResult.getObj().setStatusName(EnglishCommonUtils.findByStatusName(mMerchantResult.getObj().getStatus()));
        }
        return mMerchantResult;
    }
}
