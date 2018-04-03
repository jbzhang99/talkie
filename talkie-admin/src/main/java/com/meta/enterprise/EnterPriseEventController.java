package com.meta.enterprise;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.commonUtil.CommonUtils;
import com.meta.commonUtil.EnglishCommonUtils;
import com.meta.feignclient.enterprise.EnterpriseEventClient;
import com.meta.model.enterprise.MEnterpriseEvent;
import com.meta.model.generalagent.MGeneralAgentEvent;
import com.meta.model.group.MGroup;
import com.meta.regex.RegexUtil;
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

/**
 * Created by lhq on 2017/11/21.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "enterprise_event", description = "企业操作信息接口")
public class EnterPriseEventController extends BaseControllerUtil {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(EnterPriseEventController.class);
    @Autowired
    private EnterpriseEventClient enterpriseEventClient;

    @RequestMapping(value = ServiceUrls.EnterpriseEvent.ENTERPRISE_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取企业操作列表", notes = "根据查询条件获企业操作列表在前端表格展示")
    public Result<MEnterpriseEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MEnterpriseEvent> result = null;
        try {
            result = enterpriseEventClient.search(filters, "-createDate", size, page);
            if (result.getDetailModelList().size() > 0) {
                findDetail(result);
            }
        } catch (Exception e) {
            logger.error("获取企业操作列表失败！ ");
            logger.error(e.getMessage(), e);
            return error("获取企业操作列表失败！");
        }
        return result;
    }

    private Result<MEnterpriseEvent> findDetail(Result<MEnterpriseEvent> result) {

        result.getDetailModelList().stream().forEach(a->{
            if ("zh".equals(getLanguage())) {
                if (!RegexUtil.isNull(a.getType())) {
                    a.setTypeName(CommonUtils.findByUserEventType(a.getType().toString()));
                }
            } else if ("en".equals(getLanguage())) {
                if (!RegexUtil.isNull(a.getType())) {
                    a.setTypeName(EnglishCommonUtils.findByUserEventType(a.getType().toString()));
                }
            }
        });

        return result;
    }

}
