package com.meta.qmanage;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.commonUtil.CommonUtils;
import com.meta.commonUtil.EnglishCommonUtils;
import com.meta.feignclient.qmanage.QMerchantEventClient;
import com.meta.model.qmanage.MQGeneralAgentEvent;
import com.meta.model.qmanage.MQMerchantEvent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lhq on 2017/11/15.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "q_merchant_event", description = "Q币代理操作记录接口")
public class QMerchantEventController extends BaseControllerUtil {
    //日志
    private  static  final Logger logger= LoggerFactory.getLogger(QMerchantEventController.class);

    @Autowired
    private QMerchantEventClient qMerchantEventClient;

    @RequestMapping(value = ServiceUrls.QMerchantEvent.Q_MERCHANT_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币代理操作表", notes = "根据查询条件获Q币代理操作在前端表格展示")
    public Result<MQMerchantEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "language", value = "语言", defaultValue = "")
            @RequestParam(value = "language", required = false) String language,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MQMerchantEvent> result = null;
        try {
            result = qMerchantEventClient.search(filters, "-createDate", size, page);
            if (result.getDetailModelList().size() > 0) {
                findDetail(result, language);
            }
        } catch (Exception e) {
            logger.error("获取Q币代理操作失败！");
            logger.error(e.getMessage(),e);
            return error("获取Q币代理操作失败！");
        }
        return result;
    }

    private Result<MQMerchantEvent> findDetail(Result<MQMerchantEvent> mqMerchantEventResult, String language) {
        for (MQMerchantEvent temp : mqMerchantEventResult.getDetailModelList()) {
            if ("zh".equals(language)) {
                temp.setTypeName(CommonUtils.findByQUserEventType(temp.getType().toString()));
            } else if ("en".equals(language)) {
                temp.setTypeName(EnglishCommonUtils.findByQUserEventType(temp.getType().toString()));

            }
        }
        return mqMerchantEventResult;
    }

    @RequestMapping(value = ServiceUrls.QMerchantEvent.Q_MERCHANT_EVENT, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币代理不分页操作表", notes = "根据查询条件获Q币代理操作不分页在前端表格展示")
    public Result<MQMerchantEvent> searchNoPage(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters) {
        Result<MQMerchantEvent> result = null;
        try {
            result = qMerchantEventClient.searchNoPage(filters);
            if (result.getDetailModelList().size() > 0) {
                for (MQMerchantEvent temp : result.getDetailModelList()) {
                    temp.setTypeName(CommonUtils.findByQUserEventType(temp.getType().toString()));
                }
            }
        } catch (Exception e) {
            logger.error("获取Q币代理操作失败！");
            logger.error(e.getMessage(),e);
            return error("获取Q币代理操作失败！");
        }
        return result;
    }


}
