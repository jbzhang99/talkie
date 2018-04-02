package com.meta.feignclient.qmanage;

import com.meta.Result;
import com.meta.ServiceNames;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.feignfallback.qmanage.QValidateFallBack;
import com.meta.model.qmanage.MQValidate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author:lhq
 * @date:2017/12/21 9:41
 */
@FeignClient(name = ServiceNames.TALKIE_CORE,fallbackFactory = QValidateFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface QValidateClient {

    @RequestMapping(value = ServiceUrls.QValidate.Q_VALIDATE, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取密码", notes = "根据id获取密码")
    public Result<MQValidate> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id);


    @RequestMapping(value = ServiceUrls.QValidate.Q_VALIDATES, method = RequestMethod.POST)
    @ApiOperation(value = "根据id获取密码", notes = "根据id获取密码")
    public Result<MQValidate> modifyPasswordById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password);

}
