package com.meta.feignclient.enterprise;

import com.meta.*;
import com.meta.feignfallback.enterprise.SecEnterPriseFallBack;
import com.meta.model.enterprise.MEnterprise;
import com.meta.model.group.MGroupUser;
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
 * Created by lhq on 2017/11/21.
 */
@FeignClient(name = ServiceNames.TALKIE_CORE,fallbackFactory = SecEnterPriseFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface SecEnterPriseClient  {

    @Cacheable(value = RedisValue.FIND_ENTER_PRISE, key = "'search_sec_enter_prise_conditions_filters='+#p0+'_and_sorts='+#p1+'_and_size='+#p2+'_and_page='+#p3", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISES, method = RequestMethod.GET)
    @ApiOperation(value = "获取二级企业列表", notes = "根据查询条件获二级企业列表在前端表格展示")
    public Result<MEnterprise> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);

    @CacheEvict(value = RedisValue.FIND_ENTER_PRISE,allEntries = true)
    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISES, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改二级企业信息", notes = "创建/修改二级企业信息")
    public Result<MEnterprise> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MEnterprise mEnterprise);

    @CacheEvict(value = RedisValue.FIND_ENTER_PRISE,allEntries = true)
    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISES,method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除用户",notes = "根据ID删除用户")
    public  Result deleteById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id);

    @CacheEvict(value = RedisValue.FIND_ENTER_PRISE,allEntries = true)
    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISE, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    public Result modifyPasswordById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password);

    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISE_FIND_BY_ACCOUNT_AND__PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据账号，用户等级，父ID查找详情", notes = "根据账号，用户等级，父ID查找详情")
    public Result<MEnterprise> findByAccountAndParentId(
            @ApiParam(name = "account", value = "account", defaultValue = "")
            @RequestParam(value = "account", required = false) String account,
            @ApiParam(name = "parentId", value = "ParentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId);

    @CacheEvict(value = RedisValue.FIND_ENTER_PRISE,allEntries = true)
    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISE_MODIFY_STATUS_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status);


    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISE_COUNT_COMPANY_BY_PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "统计子代理的下属企业数量", notes = "统计子代理的下属企业数量")
    public Integer countCompayByParentId(
            @ApiParam(name = "parentId", value = "父ID", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "isDel", value = "是否删除", defaultValue = "")
            @RequestParam(value = "isDel", required = false) Integer isDel);

    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISE_WAIT_USER_GROUP, method = RequestMethod.GET)
    @ApiOperation(value = "群组用，查找待添加的用户", notes = "群组用，查找待添加的用户")
    public Result<MGroupUser> findWaitUserGroup(
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId", required = false) Long userId,
            @ApiParam(name = "groupId", value = "groupId", defaultValue = "")
            @RequestParam(value = "groupId", required = false) Long groupId);

    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISE_FIND_ALREADY_USER_GROUP, method = RequestMethod.GET)
    @ApiOperation(value = "查找已添加 的用户列表(群组用)", notes = "查找已添加 的用户列表(群组用)")
    public Result<MGroupUser> findAlreadyUserGroup(
            @ApiParam(name = "groupId", value = "groupId", defaultValue = "")
            @RequestParam(value = "groupId", required = false) Long groupId);



    @Cacheable(value = RedisValue.FIND_ENTER_PRISE, key = "'search_sec_enter_prise_noPage_conditions_filters='+#p0", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISES_NO_PAGE, method = RequestMethod.GET)
    @ApiOperation(value = "获取二级企业列表不分页", notes = "根据查询条件获二级企业列表在前端表格展示")
    public Result<MEnterprise> searchNoPage(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters);

    @RequestMapping(value = ServiceUrls.SecEnterPrise.SEC_ENTER_PRISE_COUNT_PARENT_ID_AND_MENCHANT_LEVEL,method = RequestMethod.GET)
    @ApiOperation(value = "根据PARENTID和MENCHANTLEVEL统计数量",notes = "根据PARENTID和MENCHANTLEVEL统计数量")
    public  Long countByParentIdAndMerchantLevel(
            @ApiParam(name = "parentId", value = "父ID", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "")
            @RequestParam(value = "merchantLevel", required = false) String  merchantLevel);
}
