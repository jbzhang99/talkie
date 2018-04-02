package com.meta.terminal;


import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.feignclient.terminal.AuthClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xry on 2018/01/09.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "auth", description = "设备终端接入鉴权")
public class AuthController extends BaseControllerUtil {
    @Autowired
    private AuthClient authClient;

    /**
     * auth device, and return server address info
     *
     * @param token
     * @return
     */
    @RequestMapping(value = ServiceUrls.Terminal.TERMINAL_AUTH, method = RequestMethod.GET)
    @ApiOperation(value = "设备鉴权", notes = "设备鉴权")
    public Result<String> auth(
            @ApiParam(name = "token", value = "鉴权字串", defaultValue = "")
            @RequestParam(value = "token", required = true) String token) {
        return authClient.auth(token);
    }
}
