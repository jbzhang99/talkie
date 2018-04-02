package com.meta.controller.qmanage;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.model.qmanage.QGeneralAgentEvent;
import com.meta.model.qmanage.QSecEnterPriseEvent;
import com.meta.model.qmanage.QUser;
import com.meta.model.qmanage.QUserEvent;
import com.meta.service.qmanage.QSecEnterPriseEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by lhq on 2017/11/21.
 */

@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "q_sec_enter_prise_event", description = "Q币二级管理操作记录接口")
public class QSecEnterPriseEventController extends BaseControllerUtil {

    @Autowired
    private QSecEnterPriseEventService qSecEnterPriseEventService;

    @RequestMapping(value = ServiceUrls.QSecEnterPriseEvent.Q_SEC_ENTER_PRISE_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币二级管理操作表", notes = "根据查询条件获Q币二级管理操作在前端表格展示")
    public Result<QSecEnterPriseEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<QSecEnterPriseEvent> result = qSecEnterPriseEventService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }

    @RequestMapping(value = ServiceUrls.QSecEnterPriseEvent.Q_SEC_ENTER_PRISE_EVENTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改Q币二级管理操作记录信息", notes = "创建/修改Q币二级管理操作记录信息")
    public Result<QSecEnterPriseEvent> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid QSecEnterPriseEvent qSecEnterPriseEvent) {
        return success(qSecEnterPriseEventService.save(qSecEnterPriseEvent));
    }


    @RequestMapping(value = ServiceUrls.QSecEnterPriseEvent.Q_SEC_ENTER_PRISE_EVENT, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取Q币二级管理操作信息", notes = "根据id获取Q币二级管理操作信息")
    public Result<QSecEnterPriseEvent> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) {
        QSecEnterPriseEvent qSecEnterPriseEvent = qSecEnterPriseEventService.findOne(id);
        return success(qSecEnterPriseEvent);
    }

    @RequestMapping(value = ServiceUrls.QSecEnterPriseEvent.Q_SEC_ENTER_PRISE_EVENTS, method = RequestMethod.DELETE)
    @ApiOperation(value = "删除Q币二级管理操作信息", notes = "根据id删除Q币二级管理操作信息")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id) {
        qSecEnterPriseEventService.delete(id);
        return success(null);
    }

}
