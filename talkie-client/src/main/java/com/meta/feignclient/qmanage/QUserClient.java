package com.meta.feignclient.qmanage;

import com.meta.*;
import com.meta.feignfallback.qmanage.QUserFallBack;
import com.meta.model.qmanage.MQUser;
import com.meta.model.qmanage.MQtotal;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by lhq on 2017/10/12.
 */
@FeignClient(name = ServiceNames.TALKIE_CORE,fallbackFactory = QUserFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface QUserClient {

    @RequestMapping(value = ServiceUrls.QUser.Q_USERS, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币用户表", notes = "根据查询条件获Q币用户在前端表格展示")
     Result<MQUser> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);

    @CacheEvict(value = RedisValue.FIND_ENTER_PRISE, allEntries = true)
    @RequestMapping(value = ServiceUrls.QUser.Q_USER_MODIFY_BALANCE_BY_ID,method = RequestMethod.POST)
    @ApiOperation(value = "根据ID更改余额",notes = "根据ID更改余额")
     Result modifyBalanceById(
            @ApiParam(name = "sourceId", value = "来源ID", defaultValue = "")
            @RequestParam(value = "sourceId", required = false) Long sourceId,
            @ApiParam(name = "targetId", value = "目标ID", defaultValue = "")
            @RequestParam(value = "targetId", required = false) Long targetId,
            @ApiParam(name = "value", value = "值", defaultValue = "")
            @RequestParam(value = "value", required = false) Double value,
            @ApiParam(name = "type", value = "类型，划分还是收回", defaultValue = "")
            @RequestParam(value = "type", required = false) Integer type);

    @RequestMapping(value = ServiceUrls.QUser.Q_USER_BY_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取Q币用户信息", notes = "根据id获取Q币用户信息")
     Result<MQUser> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id);

    @RequestMapping(value = ServiceUrls.QUser.Q_USER_TOTAL_Q, method = RequestMethod.GET)
    @ApiOperation(value = "查询可用的Q币 ，及已分配的Q币", notes = "查询可用的Q币 ，及已分配的Q币")
     MQtotal findQBalance(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id);

    @CacheEvict(value = {RedisValue.FIND_ENTER_PRISE,RedisValue.FIND_Q_USER}, allEntries = true)
    @RequestMapping(value = ServiceUrls.QUser.Q_USER_BATCH_ADD_OR_DEL,method = RequestMethod.POST)
    @ApiOperation(value = "批量操作(增/减)Q币",notes = "批量操作(增/减)Q币")
      Result batchAddOrDel(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) String id,
            @ApiParam(name = "value", value = "value", defaultValue = "")
            @RequestParam(value = "value", required = false) Double value,
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId", required = false) Long userId) ;


    @RequestMapping(value = ServiceUrls.QUser.Q_USER_QUERY_USER_BY_USER_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据userid查询", notes = "根据userid查询")
     Result<MQUser> findByUserId(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id);
}
