package com.meta.controller.accountant;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.model.accountant.AccountantEvent;
import com.meta.service.accountant.AccountantEventService;
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
 * create by lhq
 * create date on  18-3-2上午10:39
 *
 * @version 1.0
 **/
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "accountant_event", description = "会计操作信息接口")
public class AccountantEventController  extends BaseControllerUtil {


    @Autowired
    private AccountantEventService accountantEventService;



    @RequestMapping(value = ServiceUrls.AccountantEvent.ACCOUNTANT_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取会计操作列表", notes = "根据查询条件获会计操作列表在前端表格展示")
    public Result<AccountantEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<AccountantEvent> result = accountantEventService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }
}
