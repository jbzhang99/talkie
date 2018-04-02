package com.meta.controller.terminal;

import com.fasterxml.jackson.annotation.JsonView;
import com.meta.*;
import com.meta.model.terminal.Terminal;
import com.meta.service.terminal.TerminalService;
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
@Api(value = "terminal", description = "设备终端接口")
public class TerminalController  extends BaseControllerUtil {

    private final static Logger logger = LoggerFactory.getLogger(TerminalController.class);

    @Autowired
    private TerminalService terminalService;

    @RequestMapping(value = ServiceUrls.Terminal.TERMINALS, method = RequestMethod.GET)
    @ApiOperation(value = "获取设备终端列表", notes = "根据查询条件获设备终端在前端表格展示")
    public Result<Terminal> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<Terminal> result = terminalService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }


    @RequestMapping(value = ServiceUrls.Terminal.TERMINALS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改设备终端信息", notes = "创建/修改设备终端信息")
    public Result<Terminal> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid Terminal terminal) {
        return success(terminalService.save(terminal));
    }
    @RequestMapping(value = ServiceUrls.Terminal.TERMINAL_BY_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取设备终端信息", notes = "根据id获取设备终端信息")
    public Result<Terminal> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id) {
        Terminal terminal = terminalService.findOne(id);
        return success(terminal);
    }


    @RequestMapping(value = ServiceUrls.Terminal.TERMINALS, method = RequestMethod.DELETE)
    @ApiOperation(value = "删除设备终端信息", notes = "根据id删除设备终端信息")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id) {
        terminalService.delete(id);
        return success(null);
    }



}
