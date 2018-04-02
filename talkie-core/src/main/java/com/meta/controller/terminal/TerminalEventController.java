package com.meta.controller.terminal;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.model.terminal.Terminal;
import com.meta.model.terminal.TerminalEvent;
import com.meta.service.terminal.TerminalEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lhq on 2017/11/16.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "terminal_event", description = "设备终端操作接口")
public class TerminalEventController extends BaseControllerUtil {

    @Autowired
    private TerminalEventService terminalEventService;

    @RequestMapping(value = ServiceUrls.TerminalEvent.TERMINAL_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取设备终端列表", notes = "根据查询条件获设备终端在前端表格展示")
    public Result<TerminalEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<TerminalEvent> result = terminalEventService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }

    @RequestMapping(value = ServiceUrls.TerminalEvent.TERMINAL_EVENT, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取设备终端操作信息", notes = "根据id获取设备终端操作信息")
    public Result<TerminalEvent> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) {
        TerminalEvent terminalEvent = terminalEventService.findOne(id);
        return success(terminalEvent);
    }
}
