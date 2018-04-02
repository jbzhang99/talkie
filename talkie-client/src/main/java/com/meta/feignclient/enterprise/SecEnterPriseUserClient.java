package com.meta.feignclient.enterprise;

import com.meta.Result;
import com.meta.ServiceNames;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.feignfallback.enterprise.SecEnterPriseUserFallBack;
import com.meta.model.enterprise.MSecEnterPriseUser;
import com.meta.model.user.MUser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * Created by lhq on 2017/11/22.
 */
@FeignClient(name = ServiceNames.TALKIE_CORE,fallbackFactory = SecEnterPriseUserFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface SecEnterPriseUserClient  {



    @RequestMapping(value = ServiceUrls.SecEnterPriseUser.SEC_ENTER_PRISE_USERS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改二级企业与用户信息", notes = "创建/修改二级企业与用户信息")
    public Result<MSecEnterPriseUser> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MSecEnterPriseUser mSecEnterPriseUser);

    @RequestMapping(value = ServiceUrls.SecEnterPriseUser.SEC_ENTER_PRISE_USERS,method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除用户",notes = "根据ID删除用户")
    public  Result deleteById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id);

    @RequestMapping(value = ServiceUrls.SecEnterPriseUser.SEC_ENTER_PRISE_USERS,method = RequestMethod.GET)
    @ApiOperation(value = "根据userId和PID查找二级企业",notes = "根据ID和PID查找二级企业")
    public  Result<MUser> findByUserIdAndParentId(
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId") Long userId,
            @ApiParam(name = "parentId", value = "parentId", defaultValue = "")
            @RequestParam(value = "parentId") Long parentId);

    @RequestMapping(value = ServiceUrls.SecEnterPriseUser.SEC_ENTER_PRISE_USER,method = RequestMethod.GET)
    @ApiOperation(value = "根据userId和PID查找二级企业",notes = "根据ID和PID查找二级企业")
    public  Result<MSecEnterPriseUser> findByUserId(
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId") Long userId);

}
