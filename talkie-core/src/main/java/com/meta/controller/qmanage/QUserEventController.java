package com.meta.controller.qmanage;

import com.fasterxml.jackson.annotation.JsonView;
import com.meta.*;
import com.meta.model.qmanage.QUserEvent;
import com.meta.model.user.User;
import com.meta.service.qmanage.QUserEventService;
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
 * Created by lhq on 2017/9/30.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "quser_event", description = "Q币用户操作事件接口")
public class QUserEventController  extends BaseControllerUtil {

    private final static Logger logger = LoggerFactory.getLogger(QUserEventController.class);

    @Autowired
    private QUserEventService qUserEventService;

    @RequestMapping(value = ServiceUrls.QUserEvent.Q_USER_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币用户操作表", notes = "根据查询条件获Q币用户操作在前端表格展示")
    public Result<QUserEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<QUserEvent> result = qUserEventService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }

    @RequestMapping(value = ServiceUrls.QUserEvent.Q_USER_EVENTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改Q币用户操作信息", notes = "创建/修改Q币用户操作信息")
    public Result<QUserEvent> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid QUserEvent qUserEvent) {
        return success(qUserEventService.save(qUserEvent));
    }

    @RequestMapping(value = ServiceUrls.QUserEvent.Q_USER_EVENT, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取Q币用户操作信息", notes = "根据id获取Q币用户操作信息")
    public Result<QUserEvent> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) {
        QUserEvent qUserEvent = qUserEventService.findOne(id);
        return success(qUserEvent);
    }


    @RequestMapping(value = ServiceUrls.QUserEvent.Q_USER_EVENTS, method = RequestMethod.DELETE)
    @ApiOperation(value = "删除Q币用户操作信息", notes = "根据id删除Q币用户操作信息")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id) {
        qUserEventService.delete(id);
        return success(null);
    }
}
