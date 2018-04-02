package com.meta.feignclient.qmanage;

import com.meta.*;
import com.meta.feignfallback.qmanage.QAccountantPasswordFallBack;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by y747718944 on 2018/2/26
 */
@FeignClient(name = ServiceNames.TALKIE_CORE, fallbackFactory = QAccountantPasswordFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface QAccountantPasswordClient {

    @Cacheable(value = RedisValue.FIND_Q_ACCOUNTANT, key = "'search_q_accountant_conditions_password='+#p0", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.QAccountandPassword.Q_ACCOUNTANT_PASSWORD, method = RequestMethod.GET)
    @ApiOperation(value = "查询支付是否正确", notes = "验证密码是否正确")
    Result get(
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(name = "password", value = "password", required = false) String password);

    @GetMapping(ServiceUrls.QAccountandPassword.Q_ACCOUNTANT_PASSWORD_ISNULL)
    @ApiOperation(value = "验证是否存在用户密码")
    Result passWordIsNull();


    @CacheEvict(value = RedisValue.FIND_Q_ACCOUNTANT, allEntries = true)
    @PostMapping(ServiceUrls.QAccountandPassword.Q_ACCOUNTANT_PASSWORD)
    @ApiOperation(value = "新增or更新支付密码", notes = "根据用户user_id创建密码")
    Result save(
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(name = "password", value = "password", required = false)
                    String password);

}
