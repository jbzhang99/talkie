package com.meta.feignclient.enterprise;

import com.meta.*;
import com.meta.feignfallback.enterprise.EnterPriseFallBack;
import com.meta.model.enterprise.MEnterprise;
import com.meta.model.totalinfo.MEnterPriseTotalCountInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * Created by lhq on 2017/11/14.
 */
@FeignClient(name = ServiceNames.TALKIE_CORE,fallbackFactory = EnterPriseFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface EnterpriseClient {

    @CacheEvict(value = RedisValue.FIND_ENTER_PRISE,allEntries = true)
    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
     Result modifyPassword(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password);

    @CacheEvict(value = RedisValue.FIND_ENTER_PRISE,allEntries = true)
    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISES, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改企业信息", notes = "创建/修改企业信息")
     Result<MEnterprise> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MEnterprise mEnterprise);

    @Cacheable(value = RedisValue.FIND_ENTER_PRISE, key = "'search_enter_prise_conditions_filters='+#p0+'_and_sorts='+#p1+'_and_size='+#p2+'_and_page='+#p3", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISES, method = RequestMethod.GET)
    @ApiOperation(value = "获取企业列表", notes = "根据查询条件获企业列表在前端表格展示")
     Result<MEnterprise> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);


    @Cacheable(value = RedisValue.FIND_ENTER_PRISE, key = "'search_enter_prise_conditions_id='+#p0", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE_BY_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据ID获取代理", notes = "根据ID获取代理")
     Result<MEnterprise> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id);

    @CacheEvict(value = RedisValue.FIND_ENTER_PRISE,allEntries = true)
    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISES, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除企业", notes = "根据ID删除企业")
     Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "")
            @RequestParam(value = "merchantLevel", required = false) String merchantLevel,
            @ApiParam(name = "currentUserId", value = "currentUserId", defaultValue = "")
            @RequestParam(value = "currentUserId", required = false) Long currentUserId);


    @CacheEvict(value = RedisValue.FIND_ENTER_PRISE,allEntries = true)
    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE_MODIFY_STATUS_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
     Result modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status);

    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISES_FIND_BY_ACCOUNT_AND__PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据账号，用户等级，父ID查找详情", notes = "根据账号，用户等级，父ID查找详情")
     Result<MEnterprise> findByAccountAndParentId(
            @ApiParam(name = "account", value = "account", defaultValue = "")
            @RequestParam(value = "account", required = false) String account,
            @ApiParam(name = "parentId", value = "ParentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId);

    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE_COUNT_COMPANY_BY_PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "统计子代理的下属企业数量", notes = "统计子代理的下属企业数量")
     Integer countCompayByParentId(
            @ApiParam(name = "parentId", value = "父ID", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "isDel", value = "是否删除", defaultValue = "")
            @RequestParam(value = "isDel", required = false) Integer isDel);

    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE_TOTAL_INFO, method = RequestMethod.GET)
    @ApiOperation(value = "获取企业(子企业)的统计信息", notes = "获取企业(子企业)的统计信息 ")
     Result<MEnterPriseTotalCountInfo> getEnterPriseTotalInfo(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "flag", value = "flag", defaultValue = "")
            @RequestParam(value = "flag", required = false) boolean flag);


    @Cacheable(value = RedisValue.FIND_ENTER_PRISE, key = "'search_enter_prise_not_page_conditions_filters='+#p0", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE_NO_PAGE, method = RequestMethod.GET)
    @ApiOperation(value = "获取企业列表不分页", notes = "获取企业列表不分页")
     Result<MEnterprise> searchNoPage(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters);

    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE_BY_TERMINAL, method = RequestMethod.GET)
    @ApiOperation(value = "获取企业下的用户，终端专用", notes = "获取企业下的用户，终端专用")
     Result<MEnterprise> searchByTerminal(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "parentId", value = "父ID", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "GT_createDate", value = "GT_createDate", defaultValue = "")
            @RequestParam(value = "GT_createDate", required = false) String GT_createDate,
            @ApiParam(name = "LT_createDate", value = "LT_createDate", defaultValue = "")
            @RequestParam(value = "LT_createDate", required = false) String LT_createDate,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);


    @RequestMapping(value = ServiceUrls.Enterprise.ENTERPRISE_COUNT_PARENT_ID_AND_MERCHANT_LEVEL,method = RequestMethod.GET)
    @ApiOperation(value = "根据父ID和等级获取统计数量",notes = "根据父ID和等级获取统计数量")
      Long countByParentIdAndMerchantLevel(
            @ApiParam(name = "parentId", value = "parentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "")
            @RequestParam(value = "merchantLevel", required = false) String merchantLevel);


}
