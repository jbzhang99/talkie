package com.meta.controller.qmanage;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.model.qmanage.QEnterpriseEvent;
import com.meta.service.qmanage.QEnterpriseEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by lhq on 2017/11/14.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "q_enter_prise_event", description = "Q币企业操作记录接口")
public class QEnterpriseEventController extends BaseControllerUtil {

    @Autowired
    private QEnterpriseEventService QEnterpriseEventService;

    @RequestMapping(value = ServiceUrls.QEnterPriseEvent.Q_ENTER_PRISE_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币企业操作表", notes = "根据查询条件获Q币企业操作在前端表格展示")
    public Result<QEnterpriseEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<QEnterpriseEvent> result = QEnterpriseEventService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }

    @RequestMapping(value = ServiceUrls.QEnterPriseEvent.Q_ENTER_PRISE_EVENT, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币企业操作表不分页", notes = "获取Q币企业操作表不分页")
    public Result<QEnterpriseEvent> searchNoPage(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters) {
        List<QEnterpriseEvent> result = QEnterpriseEventService.search(filters);
        return getResultList(result);
    }


    @RequestMapping(value = ServiceUrls.QEnterPriseEvent.Q_ENTER_PRISE_EVENTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改Q币代理操作信息", notes = "创建/修改Q币代理操作信息")
    public Result<QEnterpriseEvent> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid QEnterpriseEvent qEnterpriseEvent) {
        return success(QEnterpriseEventService.save(qEnterpriseEvent));
    }

}
