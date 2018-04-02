package com.meta.controller.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.meta.*;
import com.meta.datetime.DateTimeUtil;
import com.meta.model.user.User;
import com.meta.model.user.UserEvent;
import com.meta.regex.RegexUtil;
import com.meta.remark.remarkDict;
import com.meta.service.user.UserEventService;
import com.meta.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Created by llin on 2017/9/28.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "userEvent", description = "用户操作事件接口")
public class UserEventController extends BaseControllerUtil {

    private final static Logger logger = LoggerFactory.getLogger(UserEventController.class);

    @Autowired
    private UserEventService userEventService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = ServiceUrls.UserEvent.USER_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取用户操作事件列表", notes = "根据查询条件获用户操作列表在前端表格展示")
    public Result<UserEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<UserEvent> result = userEventService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }

    @RequestMapping(value = ServiceUrls.UserEvent.USER_EVENTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改用户操作事件信息", notes = "创建/修改用户操作事件信息")
    public Result<UserEvent> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid UserEvent userEvent) throws  Exception {
    if(userEvent.getType() == 2){
        try {
            userEvent.setIp(InetAddress.getLocalHost().getHostAddress());
            userEvent.setRemark(remarkDict.OUTLOGIN+remarkDict.STSTEN+remarkDict.SUCCESS);
            User user = userService.findOne(getUserId());
            userEvent.setUser(user);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
        return success(userEventService.save(userEvent));
    }


}
