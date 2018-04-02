package com.meta.feignclient.merchant;

import com.meta.*;
import com.meta.feignfallback.merchant.SecMerchantFallBack;
import com.meta.model.merchant.MMerchant;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * Created by lhq on 2017/11/20.
 */
@FeignClient(name = ServiceNames.TALKIE_CORE, fallbackFactory = SecMerchantFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface SecMerchantClient {


    @Cacheable(value = RedisValue.FIND_SEC_MERCHANT, key = "'search_sec_merchants_conditions_filters='+#p0+'_and_sorts='+#p1+'_and_size='+#p2+'_and_page='+#p3", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取子代理列表", notes = "根据查询条件获子代理列表在前端表格展示")
    Result<MMerchant> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);

    @CacheEvict(value = RedisValue.FIND_SEC_MERCHANT, allEntries = true)
    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改子代理信息", notes = "创建/修改子代理信息")
    Result<MMerchant> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MMerchant mMerchant);

    @CacheEvict(value = RedisValue.FIND_SEC_MERCHANT, allEntries = true)
    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANTS, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户")
    Result deleteById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "merchantLevel")
            @RequestParam(value = "merchantLevel") String merchantLevel,
            @ApiParam(name = "currentUserId", value = "currentUserId", defaultValue = "")
            @RequestParam(value = "currentUserId") Long currentUserId,
            @ApiParam(name = "account", value = "account", defaultValue = "")
            @RequestParam(value = "account") String account,
            @ApiParam(name = "name", value = "name", defaultValue = "")
            @RequestParam(value = "name") String name);

    @Cacheable(value = RedisValue.FIND_SEC_MERCHANT, key = "'search_sec_merchants_conditions_id='+#p0", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANT_BY_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据ID获取代理", notes = "根据ID获取代理")
    Result<MMerchant> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id);

    @CacheEvict(value = RedisValue.FIND_SEC_MERCHANT, allEntries = true)
    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANT, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    Result modifyPasswordById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password);

    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANT_FIND_BY_ACCOUNT_AND__PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据账号，用户等级，父ID查找详情", notes = "根据账号，用户等级，父ID查找详情")
    Result<MMerchant> findByAccountAndParentId(
            @ApiParam(name = "account", value = "account", defaultValue = "")
            @RequestParam(value = "account", required = false) String account,
            @ApiParam(name = "parentId", value = "ParentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId);

    @CacheEvict(value = RedisValue.FIND_SEC_MERCHANT, allEntries = true)
    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANT_MODIFY_STATUS_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    Result modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status);

    @CacheEvict(value = RedisValue.FIND_SEC_MERCHANT, allEntries = true)
    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANT, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除代理", notes = "根据ID删除代理")
    Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "")
            @RequestParam(value = "merchantLevel", required = false) String merchantLevel,
            @ApiParam(name = "currentUserId", value = "currentUserId", defaultValue = "")
            @RequestParam(value = "currentUserId", required = false) Long currentUserId);

    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANT_COUNT_COMPANY_BY_PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "统计子代理的下属企业数量", notes = "统计子代理的下属企业数量")
    Integer countCompayByParentId(
            @ApiParam(name = "parentId", value = "父ID", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "isDel", value = "是否删除", defaultValue = "")
            @RequestParam(value = "isDel", required = false) Integer isDel);

}
