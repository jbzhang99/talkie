package com.meta.feignclient.merchant;

import com.meta.*;
import com.meta.feignfallback.merchant.MerchantFallBack;
import com.meta.model.merchant.MMerchant;
import com.meta.model.totalinfo.MMerchantTotalCountInfo;
import com.meta.model.totalinfo.MTotalCountInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by lhq on 2017/11/14.
 */
@FeignClient(name = ServiceNames.TALKIE_CORE, fallbackFactory = MerchantFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface MerchantClient {

    @Cacheable(value = RedisValue.FIND_MERCHANT, key = "'search_merchants_conditions_filters='+#p0+'_and_sorts='+#p1+'_and_size='+#p2+'_and_page='+#p3", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.Merchant.MERCHANTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取代理列表", notes = "根据查询条件获代理列表在前端表格展示")
    Result<MMerchant> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);


    @Cacheable(value = RedisValue.FIND_MERCHANT, key = "'search_merchants_no_page_conditions_filters='+#p0", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.Merchant.MERCHANTS_NO_PAGE, method = RequestMethod.GET)
    @ApiOperation(value = "获取代理列表 [无分页]", notes = "根据查询条件获代理列表在前端表格展示")
    Result<MMerchant> searchNoPage(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters);

    @CacheEvict(value = RedisValue.FIND_MERCHANT, allEntries = true)
    @RequestMapping(value = ServiceUrls.Merchant.MERCHANTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改代理信息", notes = "创建/修改代理信息")
    Result<MMerchant> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MMerchant mMerchant);

    @CacheEvict(value = RedisValue.FIND_MERCHANT, allEntries = true)
    @RequestMapping(value = ServiceUrls.Merchant.MERCHANTS, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除代理", notes = "根据ID删除代理")
    Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "")
            @RequestParam(value = "merchantLevel", required = false) String merchantLevel,
            @ApiParam(name = "currentUserId", value = "currentUserId", defaultValue = "")
            @RequestParam(value = "currentUserId", required = false) Long currentUserId);

    @CacheEvict(value = RedisValue.FIND_MERCHANT, allEntries = true)
    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    Result modifyPasswordById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password);


    @Cacheable(value = RedisValue.FIND_MERCHANT, key = "'merchant_find_by_account_and_parentid_conditions_account='+#p0+'_and_ParentId='+#p1", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_FIND_BY_ACCOUNT_AND__PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据账号，用户等级，父ID查找详情", notes = "根据账号，用户等级，父ID查找详情")
    Result<MMerchant> findByAccountAndParentId(
            @ApiParam(name = "account", value = "account", defaultValue = "")
            @RequestParam(value = "account", required = false) String account,
            @ApiParam(name = "parentId", value = "ParentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId);

    @CacheEvict(value = RedisValue.FIND_MERCHANT, allEntries = true)
    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_MODIFY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    Result modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status);

    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_COUNT_COMPANY_BY_PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "统计子代理的下属企业数量", notes = "统计子代理的下属企业数量")
    Result<Map<String, Integer>> countCompayByParentId(
            @ApiParam(name = "parentId", value = "父ID", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "isDel", value = "是否删除", defaultValue = "")
            @RequestParam(value = "isDel", required = false) Integer isDel);

    @Cacheable(value = RedisValue.FIND_MERCHANT, key = "'search_merchants_by_account_conditions_filters='+#p0+'_and_sorts='+#p1+'_and_size='+#p2+'_and_page='+#p3", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_SEARCH_ACCOUNT, method = RequestMethod.GET)
    @ApiOperation(value = "获取用户列表(账号管理用)", notes = "获取用户列表(账号管理用)")
    Result<MMerchant> searchByAccount(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);

    @CacheEvict(value = RedisValue.FIND_MERCHANT, allEntries = true)
    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户")
    Result deleteById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id);

    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_TOTAL_INFO, method = RequestMethod.GET)
    @ApiOperation(value = "获取总代的统计信息", notes = "获取总代的统计信息(账号管理用)")
    Result<MTotalCountInfo> getGeneralTotalInfo(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id);


    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_SEC_TOTAL_INFO, method = RequestMethod.GET)
    @ApiOperation(value = "获取代理(子代理)的统计信息", notes = "获取代理(子代理)的统计信息 ")
    Result<MMerchantTotalCountInfo> getMerchantTotalInfo(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "flag", value = "flag", defaultValue = "")
            @RequestParam(value = "flag", required = false) boolean flag);


    @Cacheable(value = RedisValue.FIND_MERCHANT, key = "'search_merchants_get_id_conditions_id='+#p0", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_BY_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据ID获取代理", notes = "根据ID获取代理")
    Result<MMerchant> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id);

    @CacheEvict(value = RedisValue.FIND_MERCHANT, allEntries = true)
    @RequestMapping(value = ServiceUrls.Merchant.MERCHANT_COUNT_PARNET_ID_AND_MERCHANT_LEVEL, method = RequestMethod.GET)
    @ApiOperation(value = "根据父ID和等级来统计", notes = "根据父ID和等级来统计")
    Long countParnetIdAndMerchantLevel(
            @ApiParam(name = "parentId", value = "parentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "")
            @RequestParam(value = "merchantLevel", required = false) String merchantLevel);
}
