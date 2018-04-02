package com.meta.feignclient.terminal;

import com.meta.Result;
import com.meta.ServiceNames;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by xry on 2018/01/09.
 */
@FeignClient(name = ServiceNames.TALKIE_CORE)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface AuthClient {

    /**
     * auth device, and return server address info
     *
     * @param token
     * @return
     */
    @RequestMapping(value = ServiceUrls.Terminal.TERMINAL_AUTH, method = RequestMethod.GET)
    @ApiOperation(value = "设备鉴权", notes = "设备鉴权")
    Result<String> auth(
            @ApiParam(name = "token", value = "鉴权字串", defaultValue = "")
            @RequestParam(value = "token", required = true) String token);

}
