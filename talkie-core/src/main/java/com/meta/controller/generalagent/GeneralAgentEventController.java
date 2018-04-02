package com.meta.controller.generalagent;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.datetime.DateTimeUtil;
import com.meta.model.GeneralAgent.GeneralAgentEvent;
import com.meta.model.user.User;
import com.meta.service.generalagent.GeneralAgentEventService;
import com.meta.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * Created by lhq on 2017/11/14.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "generalAgentEvent", description = "总代操作接口")
public class GeneralAgentEventController extends BaseControllerUtil {

    @Autowired
    private GeneralAgentEventService generalAgentEventService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = ServiceUrls.GeneralAgentEvent.GENERAL_AGENT_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取总代操作记录列表", notes = "根据查询条件获总代操作记录列表在前端表格展示")
    public Result<GeneralAgentEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<GeneralAgentEvent> result = generalAgentEventService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }

    @RequestMapping(value = ServiceUrls.GeneralAgentEvent.GENERAL_AGENT_EVENTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改总代操作记录信息", notes = "创建/修改总代操作记录信息")
    public Result<GeneralAgentEvent> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid GeneralAgentEvent generalAgentEvent) throws Exception {
        User user = userService.findOne(getUserId());
        generalAgentEvent.setUser(user);
        return success(generalAgentEventService.save(generalAgentEvent));
    }

}
