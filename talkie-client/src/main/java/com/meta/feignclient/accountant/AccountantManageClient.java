package com.meta.feignclient.accountant;

import com.meta.*;
import com.meta.feignfallback.accountant.AccountantManageFallBack;
import com.meta.model.accountant.MAccountant;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * create by lhq
 * create date on  18-3-1下午4:09
 *
 * @version 1.0
 **/
@FeignClient(name = ServiceNames.TALKIE_CORE, fallbackFactory = AccountantManageFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface AccountantManageClient {

    @Cacheable(value = RedisValue.FIND_ACCOUNTANT, key = "'search_accountant_conditions_filters='+#p0+'_and_sorts='+#p1+'_and_size='+#p2+'_and_page='+#p3", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.AccountantManage.ACCOUNTANT_MANAGES, method = RequestMethod.GET)
    @ApiOperation(value = "获取会计列表", notes = "根据查询条件获会计列表在前端表格展示")
    Result<MAccountant> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);

    @CacheEvict(value = RedisValue.FIND_ACCOUNTANT, allEntries = true)
    @RequestMapping(value = ServiceUrls.AccountantManage.ACCOUNTANT_MANAGES, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    Result modifyPassword(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password);

    @CacheEvict(value = RedisValue.FIND_ACCOUNTANT, allEntries = true)
    @RequestMapping(value = ServiceUrls.AccountantManage.ACCOUNTANT_MANAGE_MODIFY_STATUS, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    Result modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status);

    @CacheEvict(value = RedisValue.FIND_ACCOUNTANT, allEntries = true)
    @RequestMapping(value = ServiceUrls.AccountantManage.ACCOUNTANT_MANAGE_BY_ID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户")
    Result deleteById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id);

    @CacheEvict(value = RedisValue.FIND_ACCOUNTANT, allEntries = true)
    @RequestMapping(value = ServiceUrls.AccountantManage.ACCOUNTANT_MANAGE, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改会计信息", notes = "创建/修改会计信息")
    Result<MAccountant> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MAccountant user);

}
