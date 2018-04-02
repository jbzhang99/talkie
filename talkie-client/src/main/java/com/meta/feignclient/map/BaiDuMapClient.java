package com.meta.feignclient.map;

import com.meta.Result;
import com.meta.ServiceNames;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.model.map.MBaiDuMap;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author:lhq
 * @date:2017/12/11 11:07
 */
@FeignClient(name = ServiceNames.TALKIE_CORE)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface BaiDuMapClient  {

    @RequestMapping(value = ServiceUrls.BaiDuMap.BAIDU_MAP, method = RequestMethod.GET)
    @ApiOperation(value = "根据账号获取用户的轨迹", notes = "根据账号获取用户的轨迹")
    public Result<MBaiDuMap> getById(
            @ApiParam(name = "files", value = "files", defaultValue = "")
            @RequestParam(value = "files") String files,
            @ApiParam(name = "startDate", value = "startDate", defaultValue = "")
            @RequestParam(value = "startDate",required = false) String startDate,
            @ApiParam(name = "endDate", value = "endDate", defaultValue = "")
            @RequestParam(value = "endDate",required = false) String endDate);

}
