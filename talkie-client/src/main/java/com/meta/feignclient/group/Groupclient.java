package com.meta.feignclient.group;

import com.meta.*;
import com.meta.model.group.MGroup;
import com.meta.model.user.MAssUserGroup;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * Created by lhq on 2017/10/20.
 */
@FeignClient(name = ServiceNames.TALKIE_CORE)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface Groupclient {

    @Cacheable(value = RedisValue.FIND_GROUP, key = "'search_groups_conditions_filters='+#p0+'_and_sorts='+#p1+'_and_size='+#p2+'_and_page='+#p3", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.Group.GROUPS, method = RequestMethod.GET)
    @ApiOperation(value = "获取组表", notes = "根据查询条件获组在前端表格展示")
    Result<MGroup> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);

    @CacheEvict(value = RedisValue.FIND_GROUP, allEntries = true)
    @RequestMapping(value = ServiceUrls.Group.GROUPS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改组信息", notes = "创建/修改组信息")
    Result<MGroup> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MGroup mGroup);

    @RequestMapping(value = ServiceUrls.Group.GROUP_BY_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取组信息", notes = "根据id获取组信息")
    Result<MGroup> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id);

    @CacheEvict(value = RedisValue.FIND_GROUP, allEntries = true)
    @RequestMapping(value = ServiceUrls.Group.GROUPS, method = RequestMethod.DELETE)
    @ApiOperation(value = "删除组信息", notes = "根据id删除组信息")
    Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id,
            @ApiParam(name = "user_id", value = "user_id", defaultValue = "")
            @RequestParam(value = "user_id") Long user_id);

    @CacheEvict(value = RedisValue.FIND_GROUP, allEntries = true)
    @RequestMapping(value = ServiceUrls.Group.GROUP_MODIFY_BY_STATUS,method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态",notes = "根据ID变更状态")
    public  Result modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status") String status);

    @RequestMapping(value = ServiceUrls.Group.GROUP_FIND_WAIT_BY_USER, method = RequestMethod.GET)
    @ApiOperation(value = "查找未添加用户的组", notes = "查找未添加用户的组")
    public Result<MGroup> findWaitByUser(
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId") Long userId,
            @ApiParam(name = "currId", value = "currId", defaultValue = "")
            @RequestParam(value = "currId") Long currId) throws Exception ;

    //@Cacheable(value = RedisValue.FIND_USER_GROUP, key = "'search_group_find_already_user_conditions_userId='+#p0", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.Group.GROUP_FIND_ALREADY_USER,method = RequestMethod.GET)
    @ApiOperation(value = "查找已添加用户的组",notes = "查找已添加用户的组")
    public Result<MGroup> findAlreadyUser(
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId") Long userId);

    @RequestMapping(value = ServiceUrls.Group.GROUP_ASSIGNMENT, method = RequestMethod.GET)
    @ApiOperation(value = "获取组表(群组功能用)", notes = "根据查询条件获组(群组功能用)在前端表格展示")
    public Result<MAssUserGroup> searchAssignment(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id);

    @Cacheable(value = RedisValue.FIND_GROUP, key = "'search_groups_conditions_no_page_filters='+#p0", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.Group.GROUP_SEARCH_NOPAGE, method = RequestMethod.GET)
    @ApiOperation(value = "查找群组不分页", notes = "查找群组不分页")
    public Result<MGroup> searchNoPage(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters);
}
