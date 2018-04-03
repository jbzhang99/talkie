package com.meta.qmanage;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.commonUtil.CommonUtils;
import com.meta.feignclient.qmanage.QEnterpriseEventClient;
import com.meta.model.qmanage.MQEnterpriseEvent;
import com.meta.model.qmanage.MQUserEvent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@Api(value = "q_enter_prise_event", description = "Q币企业操作记录接口")
public class QEnterPriseEventController extends BaseControllerUtil {
    //日志
    private static final Logger logger = LoggerFactory.getLogger(QEnterPriseEventController.class);


    @Autowired
    private QEnterpriseEventClient qEnterpriseEventClient;

    @RequestMapping(value = ServiceUrls.QEnterPriseEvent.Q_ENTER_PRISE_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币代理操作表", notes = "根据查询条件获Q币代理操作在前端表格展示")
    public Result<MQEnterpriseEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MQEnterpriseEvent> result = null;
        try {
            result = qEnterpriseEventClient.search(filters, "-createDate", size, page);
            if (result.getDetailModelList().size() > 0) {
                findDetail(result);
            }
        } catch (Exception e) {

            logger.error("获取Q币企业操作失败！");
            logger.error(e.getMessage(), e);
            return error("获取Q币企业操作失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.QEnterPriseEvent.Q_ENTER_PRISE_EVENT, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币企业操作表不分页", notes = "获取Q币企业操作表不分页")
    public Result<MQEnterpriseEvent> searchNoPage(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters) {
        Result<MQEnterpriseEvent> result = null;
        try {
            result = qEnterpriseEventClient.searchNoPage(filters);
            if (result.getDetailModelList().size() > 0) {
                findDetail(result);
            }
        } catch (Exception e) {

            logger.error("获取Q币企业操作失败！");
            logger.error(e.getMessage(), e);
            return error("获取Q币企业操作失败！");
        }
        return result;
    }



    private Result<MQEnterpriseEvent> findDetail(Result<MQEnterpriseEvent> result) {
        result.getDetailModelList().stream().forEach(a -> {
            if (a.getType() > 0) {
                a.setTypeName(CommonUtils.findByQUserEventType(a.getType().toString()));
            }
        });

        return result;
    }

}
