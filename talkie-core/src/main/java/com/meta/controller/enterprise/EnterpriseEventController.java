package com.meta.controller.enterprise;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.model.enterprise.EnterpriseEvent;
import com.meta.model.merchant.MerchantEvent;
import com.meta.service.enterprise.EnterpriseEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by lhq on 2017/11/10.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "enterprise_event", description = "企业操作信息接口")
public class EnterpriseEventController extends BaseControllerUtil {

    @Autowired
    private EnterpriseEventService enterpriseEventService;

    @RequestMapping(value = ServiceUrls.EnterpriseEvent.ENTERPRISE_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取企业操作列表", notes = "根据查询条件获企业操作列表在前端表格展示")
    public Result<EnterpriseEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<EnterpriseEvent> result = enterpriseEventService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }


    @RequestMapping(value = ServiceUrls.EnterpriseEvent.ENTERPRISE_EVENTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改企业操作事件信息", notes = "创建/修改企业操作事件信息")
    public Result<EnterpriseEvent> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid EnterpriseEvent enterpriseEvent) {
        return success(enterpriseEventService.save(enterpriseEvent ));
    }
}
