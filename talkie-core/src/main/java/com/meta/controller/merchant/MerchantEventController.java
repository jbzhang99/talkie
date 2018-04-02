package com.meta.controller.merchant;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.datetime.DateTimeUtil;
import com.meta.model.merchant.MerchantEvent;
import com.meta.model.user.User;
import com.meta.model.user.UserEvent;
import com.meta.remark.remarkDict;
import com.meta.service.merchant.MerchantEventService;
import com.meta.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Created by lhq on 2017/11/10.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "merchant_event", description = "代理操作信息接口")
public class MerchantEventController extends BaseControllerUtil {

    @Autowired
    private MerchantEventService merchantEventService;
    @Autowired
    private UserService userService;


    @RequestMapping(value = ServiceUrls.MerchantEvent.MERCHANT_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取代理操作列表", notes = "根据查询条件获代理操作列表在前端表格展示")
    public Result<MerchantEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<MerchantEvent> result = merchantEventService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }

    @RequestMapping(value = ServiceUrls.MerchantEvent.MERCHANT_EVENTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改代理操作事件信息", notes = "创建/修改代理操作事件信息")
    public Result<MerchantEvent> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MerchantEvent merchantEvent) throws Exception {
        User user = userService.findOne(getUserId());
        merchantEvent.setUser(user);
        return success(merchantEventService.save(merchantEvent));
    }

}
