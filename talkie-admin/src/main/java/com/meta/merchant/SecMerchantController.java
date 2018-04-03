package com.meta.merchant;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.commonUtil.CommonUtils;
import com.meta.commonUtil.EnglishCommonUtils;
import com.meta.error.errorMsgDict;
import com.meta.feignclient.merchant.SecMerchantClient;
import com.meta.feignclient.qmanage.QMerchantClient;
import com.meta.model.merchant.MMerchant;
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
 * Created by lhq on 2017/11/20.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "sec_merchant", description = "子代理信息接口")
public class SecMerchantController extends BaseControllerUtil {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(SecMerchantController.class);

    @Autowired
    private SecMerchantClient secMerchantClient;
    @Autowired
    private QMerchantClient qMerchantClient;

    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取子代理列表", notes = "根据查询条件获子代理列表在前端表格展示")
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
            result = secMerchantClient.search(filters, sorts, size, page);
            if (result.getDetailModelList().size() > 0) {
                findDetail(result);
            }

        } catch (Exception e) {
            logger.error("获取子代理列表失败！");
            logger.error(e.getMessage(), e);
            return error("获取子代理列表失败！");
        }
        return result;
    }

    private Result<MMerchant> findDetail(Result<MMerchant> result) {

        result.getDetailModelList().stream().forEach(o -> {
            Result<MQMerchant> mqUser = null;
            mqUser = qMerchantClient.get(o.getId());
            if (!RegexUtil.isNull(mqUser.getObj())) {
                o.setRemainQ(mqUser.getObj().getBalance());
                o.setModifyDate(mqUser.getObj().getModifyDate());
            }else{
                o.setRemainQ(0D);
            }
            if ("zh".equals(getLanguage())) {
                //中文
                o.setStatusName(CommonUtils.findByStatusName(o.getStatus()));
                if (!RegexUtil.isNull(o.getMerchantLevel())) {
                    o.setMerchantLevelName(CommonUtils.findMerchantLevel(o.getMerchantLevel()));
                }


            } else if ("en".equals(getLanguage())) {
                if (!RegexUtil.isNull(o.getMerchantLevel())) {
                    o.setMerchantLevelName(EnglishCommonUtils.findMerchantLevel(o.getMerchantLevel()));
                }
                o.setStatusName(EnglishCommonUtils.findByStatusName(o.getStatus()));

            }
        });

        return result;
    }

    private Result<MMerchant> findObj(Result<MMerchant> mMerchantResult) {
        if ("zh".equals(getLanguage())) {
            //中文
            mMerchantResult.getObj().setStatusName(CommonUtils.findByStatusName(mMerchantResult.getObj().getStatus()));
        } else if ("en".equals(getLanguage())) {
            mMerchantResult.getObj().setStatusName(EnglishCommonUtils.findByStatusName(mMerchantResult.getObj().getStatus()));
        }
        return mMerchantResult;
    }

    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改子代理信息", notes = "创建/修改子代理信息")
    public Result<MMerchant> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MMerchant mMerchant) throws Exception {
        Result<MMerchant> result = null;
        try {
            result = secMerchantClient.create(mMerchant);
        } catch (Exception e) {
            logger.error("创建/修改子代理失败！");
            logger.error(e.getMessage(), e);
            return error("创建/修改子代理失败！");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANTS, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除子代理", notes = "根据ID删除子代理")
    public Result<MMerchant> deleteById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "merchantLevel")
            @RequestParam(value = "merchantLevel") String merchantLevel,
            @ApiParam(name = "currentUserId", value = "currentUserId", defaultValue = "")
            @RequestParam(value = "currentUserId") Long currentUserId,
            @ApiParam(name = "account", value = "account", defaultValue = "")
            @RequestParam(value = "account") String account,
            @ApiParam(name = "name", value = "name", defaultValue = "")
            @RequestParam(value = "name") String name) throws Exception {

        Result<MMerchant> result = null;
        try {
            result = secMerchantClient.deleteById(id, merchantLevel, currentUserId, account, name);
        } catch (Exception e) {
            logger.error(errorMsgDict.DEL_SEC_MERCHANTS);
            logger.error(e.getMessage(), e);
            return error(errorMsgDict.DEL_SEC_MERCHANTS);
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANT, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    public Result<MMerchant> modifyPasswordById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) {
        Result<MMerchant> result = null;
        try {
            result = secMerchantClient.modifyPasswordById(id, password);
        } catch (Exception e) {
            logger.error("修改密码失败！ ");
            logger.error(e.getMessage(), e);
            return error("修改密码失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANT_MODIFY_STATUS_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status) throws Exception {

        Result<MMerchant> result = null;
        try {
            result = secMerchantClient.modifyStatusById(id, status);
        } catch (Exception e) {
            logger.error("变更状态失败！ ");
            logger.error(e.getMessage(), e);
            return error("变更状态失败！");
        }
        return result;

    }

    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANT, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除代理", notes = "根据ID删除代理")
    public Result<MMerchant> delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "")
            @RequestParam(value = "merchantLevel", required = false) String merchantLevel,
            @ApiParam(name = "currentUserId", value = "currentUserId", defaultValue = "")
            @RequestParam(value = "currentUserId", required = false) Long currentUserId) throws Exception {
        Result<MMerchant> result = null;
        try {
            result = secMerchantClient.delete(id, merchantLevel, currentUserId);
        } catch (Exception e) {
            logger.error("删除失败！");
            logger.error(e.getMessage(), e);
            return error("删除失败！");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANTS_BY_ID_AND_LANGUAGE, method = RequestMethod.GET)
    @ApiOperation(value = "根据ID获取代理", notes = "根据ID获取代理")
    public Result<MMerchant> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) throws Exception {
        Result<MMerchant> result = null;
        try {
            result = secMerchantClient.get(id);
            if (!RegexUtil.isNull(result.getObj().getStatus())) {
                findObj(result);
            }
        } catch (Exception e) {
            logger.error("获取子代理失败！");
            logger.error(e.getMessage(), e);
            return error("获取子代理失败！");
        }
        return result;
    }


}
