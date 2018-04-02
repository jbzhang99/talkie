package com.meta.feignclient.qmanage;

import com.meta.Result;
import com.meta.ServiceNames;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.feignfallback.qmanage.QUserEventFallBack;
import com.meta.model.qmanage.MQUserEvent;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by lhq on 2017/10/18.
 */
@FeignClient(name = ServiceNames.TALKIE_CORE,fallbackFactory = QUserEventFallBack.class )
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface QUserEventClient {

    @RequestMapping(value = ServiceUrls.QUserEvent.Q_USER_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币用户操作表", notes = "根据查询条件获Q币用户操作在前端表格展示")
    public Result<MQUserEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);

}
