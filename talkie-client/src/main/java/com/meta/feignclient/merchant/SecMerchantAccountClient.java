package com.meta.feignclient.merchant;

import com.meta.*;
import com.meta.feignfallback.merchant.SecMerchantAccountFallBack;
import com.meta.model.merchant.MMerchantAccount;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * Created by lhq on 2017/11/20.
 */
@FeignClient(name = ServiceNames.TALKIE_CORE,fallbackFactory = SecMerchantAccountFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface SecMerchantAccountClient {

    @Cacheable(value = RedisValue.FIND_SEC_MERCHANT_ACCOUNT, key = "'search_sec_merchant_account_conditions_filters='+#p0+'_and_sorts='+#p1+'_and_size='+#p2+'_and_page='+#p3", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.SecMerchantAccount.SEC_MERCHANT_ACCOUNTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取子代理(账号管理)列表", notes = "根据查询条件获子代理(账号管理)列表在前端表格展示")
     Result<MMerchantAccount> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);

    @CacheEvict(value = RedisValue.FIND_SEC_MERCHANT_ACCOUNT,allEntries = true)
    @RequestMapping(value = ServiceUrls.SecMerchantAccount.SEC_MERCHANT_ACCOUNT, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
     Result modifyPassword(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password);


    @CacheEvict(value = RedisValue.FIND_SEC_MERCHANT_ACCOUNT,allEntries = true)
    @RequestMapping(value = ServiceUrls.SecMerchantAccount.SEC_MERCHANT_ACCOUNT_MODIFY_STATUS_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
     Result modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status);

    @CacheEvict(value = RedisValue.FIND_SEC_MERCHANT_ACCOUNT,allEntries = true)
    @RequestMapping(value = ServiceUrls.SecMerchantAccount.SEC_MERCHANT_ACCOUNTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改子代理(账号管理)信息", notes = "创建/修改子代理(账号管理)信息")
     Result<MMerchantAccount> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MMerchantAccount mMerchantAccount);

    @CacheEvict(value = RedisValue.FIND_SEC_MERCHANT_ACCOUNT,allEntries = true)
    @RequestMapping(value = ServiceUrls.SecMerchantAccount.SEC_MERCHANT_ACCOUNTS, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除账号", notes = "根据ID删除账号")
     Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id);

}
