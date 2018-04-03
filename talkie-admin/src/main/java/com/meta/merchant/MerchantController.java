package com.meta.merchant;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.commonUtil.CommonUtils;
import com.meta.commonUtil.EnglishCommonUtils;
import com.meta.feignclient.merchant.MerchantClient;
import com.meta.feignclient.qmanage.QMerchantClient;
import com.meta.model.merchant.MMerchant;
import com.meta.model.qmanage.MQMerchant;
import com.meta.model.totalinfo.MMerchantTotalCountInfo;
import com.meta.model.totalinfo.MTotalCountInfo;
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
 * Created by lhq on 2017/11/14.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "merchant", description = "代理信息接口")
public class MerchantController extends BaseControllerUtil {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(MerchantController.class);


    @Autowired
    private MerchantClient merchantClient;
    @Autowired
    private QMerchantClient qMerchantClient;

    @RequestMapping(value = ServiceUrls.Merchant.MERCHANTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取代理列表", notes = "根据查询条件获代理列表在前端表格展示")
    public Result<MMerchant> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MMerchant> result = null;
        try {
            result = merchantClient.search(filters, "-createDate", size, page);
            if (result.getDetailModelList().size() > 0 && result.getDetailModelList() != null) {
                findDetail(result, getLanguage());

//                result.getDetailModelList().stream().map( o->{
//                 if("zh".endsWith(getLanguage())){
//                     o.setStatusName(CommonUtils.findByStatusName(o.getStatus()));
//                 } else if ("en".equals(getLanguage()) ){
//                     o.setStatusName(EnglishCommonUtils.findByStatusName(o.getStatus()));
//                 } else {
//                     return  error("目前不支持该语言!");
//                 }
//                    Result<MQMerchant> mqUser = null;
//                    mqUser = qMerchantClient.get(o.getId());
//                    o.setRemainQ(mqUser.getObj().getBalance());
//                    Result<MMerchant> mMerchantResult1 = merchantClient.searchNoPage("EQ_parentId=" + o.getId());
//                    if (!RegexUtil.isNull(mMerchantResult1.getDetailModelList())) {
////                        Long count = 0L;
////                        for (MMerchant mMerchant : mMerchantResult1.getDetailModelList()) {
////                            count += merchantClient.countParnetIdAndMerchantLevel(mMerchant.getId(), "7");
////                        }
////                        o.setCountTerminal(count);
//                        (mMerchantResult1.getDetailModelList().stream().map()
//                    }
//                    o.setCountCompany(merchantClient.countParnetIdAndMerchantLevel(o.getId(), "4"));
//                    o.setModifyDate(mqUser.getObj().getModifyDate());
//                    return  o;
//                });

            }
        } catch (Exception e) {
            logger.error("获取代理列表异常！");
            logger.error(e.getMessage(), e);
            return error("获取代理列表异常！");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.Merchant.MERCHANTS_NO_PAGE, method = RequestMethod.GET)
    @ApiOperation(value = "获取代理列表 [无分页]", notes = "根据查询条件获代理列表在前端表格展示")
    public Result<MMerchant> searchNoPage(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "language", value = "语言", defaultValue = "")
            @RequestParam(value = "language", required = false) String language) {
        Result<MMerchant> result = null;
        try {
            result = merchantClient.searchNoPage(filters);
            if (result.getDetailModelList().size() > 0 && result.getDetailModelList() != null) {
                findDetail(result, language);
            }
        } catch (Exception e) {
            logger.error("获取代理列表异常！");
            logger.error(e.getMessage(), e);
            return error("获取代理列表异常！");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.Merchant.MERCHANTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改代理信息", notes = "创建/修改代理信息")
    public Result<MMerchant> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MMerchant mMerchant) throws UnknownHostException {
        Result<MMerchant> result = null;
        try {
            result = merchantClient.create(mMerchant);
        } catch (Exception e) {
            logger.error("创建/修改代理信息失败！");
            logger.error(e.getMessage(), e);
            return error("创建/修改代理信息失败！");
        }
        return result;

    }

    @RequestMapping(value = ServiceUrls.Merchant.MERCHANTS, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除代理", notes = "根据ID删除代理")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "")
            @RequestParam(value = "merchantLevel", required = false) String merchantLevel,
            @ApiParam(name = "currentUserId", value = "currentUserId", defaultValue = "")
            @RequestParam(value = "currentUserId", required = false) Long currentUserId) {
        Result<MMerchant> result = null;
        try {
            result = merchantClient.delete(id, merchantLevel, currentUserId);
        } catch (Exception e) {
            logger.error("删除代理信息失败！");
            logger.error(e.getMessage(), e);
            return error("删除代理信息失败！");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    public Result modifyPasswordById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) throws Exception {
        Result<MMerchant> result = null;
        try {
            result = merchantClient.modifyPasswordById(id, password);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("修改密码失败！");
            return error("修改密码失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_FIND_BY_ACCOUNT_AND__PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据账号，用户等级，父ID查找详情", notes = "根据账号，用户等级，父ID查找详情")
    public Result<MMerchant> findByAccountAndParentId(
            @ApiParam(name = "account", value = "account", defaultValue = "")
            @RequestParam(value = "account", required = false) String account,
            @ApiParam(name = "language", value = "语言", defaultValue = "")
            @RequestParam(value = "language", required = false) String language,
            @ApiParam(name = "parentId", value = "ParentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId) throws Exception {
        Result<MMerchant> result = null;
        try {
            result = merchantClient.findByAccountAndParentId(account, parentId);
            if (!RegexUtil.isNull(result.getObj())) {
                findObj(result, language);
            }
        } catch (Exception e) {
            logger.error("查找失败！");
            logger.error(e.getMessage(), e);
            return error("修改密码失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_MODIFY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status) throws Exception {
        Result<MMerchant> result = null;
        try {
            result = merchantClient.modifyStatusById(id, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("变更状态失败！");
            return error("变更状态失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_SEARCH_ACCOUNT, method = RequestMethod.GET)
    @ApiOperation(value = "获取用户列表(账号管理用)", notes = "获取用户列表(账号管理用)")
    public Result<MMerchant> searchByAccount(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "language", value = "语言", defaultValue = "")
            @RequestParam(value = "language", required = false) String language,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) throws Exception {
        Result<MMerchant> result = null;
        try {
            result = merchantClient.searchByAccount(filters, "-createDate", size, page);
            if (result.getDetailModelList().size() > 0) {
                findDetail(result, language);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户")
    public Result<MMerchant> deleteById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) throws Exception {
        Result<MMerchant> result = null;
        try {
            result = merchantClient.deleteById(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("删除失败！");
            return error("删除失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_TOTAL_INFO, method = RequestMethod.GET)
    @ApiOperation(value = "获取总代的统计信息", notes = "获取总代的统计信息 ")
    public Result<MTotalCountInfo> getGeneralTotalInfo() throws Exception {
        Result<MTotalCountInfo> result = null;
        try {
            result = merchantClient.getGeneralTotalInfo(1L);
        } catch (Exception e) {
            logger.error("获取总统计信息失败！");
            logger.error(e.getMessage(), e);
            return error("获取总统计信息失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_SEC_TOTAL_INFO, method = RequestMethod.GET)
    @ApiOperation(value = "获取代理(子代理)的统计信息", notes = "获取代理(子代理)的统计信息 ")
    public Result<MMerchantTotalCountInfo> getGeneralTotalInfo(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "flag", value = "flag", defaultValue = "")
            @RequestParam(value = "flag", required = false) boolean flag) throws Exception {
        Result<MMerchantTotalCountInfo> result = null;
        try {
            result = merchantClient.getMerchantTotalInfo(id, flag);
        } catch (Exception e) {
            logger.error("获取总统计信息失败！");
            logger.error(e.getMessage(), e);
            return error("获取总统计信息失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.Merchant.MERCHANTS_BY_ID_AND_LANGUAGE, method = RequestMethod.GET)
    @ApiOperation(value = "根据ID获取代理", notes = "根据ID获取代理")
    public Result<MMerchant> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) throws Exception {
        Result<MMerchant> result = null;
        try {
            result = merchantClient.get(id);
            if (!RegexUtil.isNull(result.getObj().getStatus())) {
                findObj(result, getLanguage());
            }
        } catch (Exception e) {
            logger.error("删除失败！");
            logger.error(e.getMessage(), e);
            return error("删除失败！");
        }
        return result;
    }


    private Result<MMerchant> findDetail(Result<MMerchant> mMerchantResult, String language) {
        for (MMerchant temp : mMerchantResult.getDetailModelList()) {
            Result<MQMerchant> mqUser = null;
            mqUser = qMerchantClient.get(temp.getId());
            temp.setRemainQ(mqUser.getObj().getBalance());

            Result<MMerchant> mMerchantResult1 = merchantClient.searchNoPage("EQ_parentId=" + temp.getId());
            if (!RegexUtil.isNull(mMerchantResult1.getDetailModelList())) {
                Long count = 0L;
                for (MMerchant mMerchant : mMerchantResult1.getDetailModelList()) {
                    count += merchantClient.countParnetIdAndMerchantLevel(mMerchant.getId(), "7");
                }
                temp.setCountTerminal(count);
            }
            temp.setCountCompany(merchantClient.countParnetIdAndMerchantLevel(temp.getId(), "4"));
            temp.setModifyDate(mqUser.getObj().getModifyDate());
            if ("zh".equals(language)) {
                //中文
                if (!RegexUtil.isNull(temp.getMerchantLevel())) {
                    temp.setMerchantLevelName(CommonUtils.findMerchantLevel(temp.getMerchantLevel()));
                }
                temp.setStatusName(CommonUtils.findByStatusName(temp.getStatus()));
            } else if ("en".equals(language)) {
                temp.setStatusName(EnglishCommonUtils.findByStatusName(temp.getStatus()));
                if (!RegexUtil.isNull(temp.getMerchantLevel())) {
                    temp.setMerchantLevelName(EnglishCommonUtils.findMerchantLevel(temp.getMerchantLevel()));
                }

            }
        }
        return mMerchantResult;
    }

    private Result<MMerchant> findObj(Result<MMerchant> mMerchantResult, String language) {
        if ("zh".equals(language)) {
            //中文
            mMerchantResult.getObj().setStatusName(CommonUtils.findByStatusName(mMerchantResult.getObj().getStatus()));
        } else if ("en".equals(language)) {
            mMerchantResult.getObj().setStatusName(EnglishCommonUtils.findByStatusName(mMerchantResult.getObj().getStatus()));
        }
        return mMerchantResult;
    }

}