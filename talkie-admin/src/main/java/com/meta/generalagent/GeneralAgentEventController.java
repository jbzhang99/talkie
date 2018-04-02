package com.meta.generalagent;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.commonUtil.CommonUtils;
import com.meta.commonUtil.EnglishCommonUtils;
import com.meta.datetime.DateTimeUtil;
import com.meta.feignclient.generalagent.GeneralAgentEventClient;
import com.meta.model.generalagent.MGeneralAgentEvent;
import com.meta.model.user.MUserEvent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * Created by lhq on 2017/11/14.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "generalAgentEvent", description = "总代操作接口")
public class GeneralAgentEventController extends BaseControllerUtil {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(GeneralAgentEventController.class);
    @Autowired
    private GeneralAgentEventClient generalAgentEventClient;

    @RequestMapping(value = ServiceUrls.GeneralAgentEvent.GENERAL_AGENT_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取总代操作记录列表", notes = "根据查询条件获总代操作记录列表在前端表格展示")
    public Result<MGeneralAgentEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MGeneralAgentEvent> result = null;
        try {
            result = generalAgentEventClient.search(filters, "-createDate", size, page);
            if (result.getDetailModelList().size() > 0) {
                for (MGeneralAgentEvent temp : result.getDetailModelList()) {
                    if ("zh".equals(getLanguage())) {
                        //中文
                        temp.setTypeName(CommonUtils.findByUserEventType(temp.getType().toString()));
                    } else if ("en".equals(getLanguage())) {
                        temp.setTypeName(EnglishCommonUtils.findByUserEventType(temp.getType().toString()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取总代操作记录失败！");
            logger.error(e.getMessage(), e);
            return error("获取总代操作记录失败！");
        }
        return result;
    }

}
