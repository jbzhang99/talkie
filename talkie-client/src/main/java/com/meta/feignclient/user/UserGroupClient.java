package com.meta.feignclient.user;

import com.meta.*;
import com.meta.model.user.MUserGroup;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * Created by lhq on 2017/10/23.
 *
 */
@FeignClient(name = ServiceNames.TALKIE_CORE)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface UserGroupClient {

    @RequestMapping(value = ServiceUrls.UserGroup.USER_GROUPS, method = RequestMethod.GET)
    @ApiOperation(value = "获取用户组事件列表", notes = "根据查询条件获用户组列表在前端表格展示")
    Result<MUserGroup> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);

    @CacheEvict(value = RedisValue.FIND_USER_GROUP, allEntries = true)
    @RequestMapping(value = ServiceUrls.UserGroup.USER_GROUPS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改用户组信息", notes = "创建/修改用户组信息")
    Result<MUserGroup> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MUserGroup mUserGroup);


    @CacheEvict(value ={ RedisValue.FIND_USER_GROUP, RedisValue.FIND_Q_USER}, allEntries = true)
    @RequestMapping(value = ServiceUrls.UserGroup.USER_GROUPS, method = RequestMethod.DELETE)
    @ApiOperation(value = "删除用户组信息", notes = "根据id删除用户组信息")
    Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id);

    /**
     * 修改群组信息会涉及到 群组用户查询缓存需要同时清除
     * @param mUserGroup
     * @return
     */
    @CacheEvict(value ={ RedisValue.FIND_USER_GROUP, RedisValue.FIND_Q_USER}, allEntries = true)
    @RequestMapping(value = ServiceUrls.UserGroup.USER_GROUP_BATCH, method = RequestMethod.POST)
    @ApiOperation(value = "批量新增用户组信息", notes = "批量新增用户组信息")
    Result<MUserGroup> batchCreat(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MUserGroup mUserGroup);

    @CacheEvict(value ={ RedisValue.FIND_USER_GROUP, RedisValue.FIND_Q_USER}, allEntries = true)
    @RequestMapping(value = ServiceUrls.UserGroup.USER_GROUP_BATCH, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据userid and groupid 删除信息", notes = "根据userid and groupid 删除信息")
    public Result deleteByUserIdAndGroupId(
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId") String userId,
            @ApiParam(name = "groupId", value = "groupId", defaultValue = "")
            @RequestParam(value = "groupId") String groupId,
            @ApiParam(name = "type", value = "type", defaultValue = "")
            @RequestParam(value = "type") String type);


}



