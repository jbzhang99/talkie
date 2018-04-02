package com.meta.feignclient.qmanage;

import com.meta.*;
import com.meta.feignfallback.qmanage.QMerchantFallBack;
import com.meta.model.qmanage.MQMerchant;
import com.meta.model.qmanage.MQtotal;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * Created by lhq on 2017/11/15.
 */
@FeignClient(name = ServiceNames.TALKIE_CORE, fallbackFactory = QMerchantFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface QMerchantClient {

    @Cacheable(value = RedisValue.FIND_MERCHANT, key = "'search_q_merchant_conditions_filters='+#p0+'_and_sorts='+#p1+'_and_size='+#p2+'_and_page='+#p3", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.QMerchant.Q_MERCHANTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币代理表", notes = "根据查询条件获Q币代理在前端表格展示")
    Result<MQMerchant> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);

    @CacheEvict(value = RedisValue.FIND_MERCHANT, allEntries = true)
    @RequestMapping(value = ServiceUrls.QMerchant.Q_MERCHANTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改Q币代理信息", notes = "创建/修改Q币代理信息")
    Result<MQMerchant> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MQMerchant mqMerchant);

    /**
     * 充值需要清除用户代理企业的状态
     */
    @CacheEvict(value = {RedisValue.FIND_MERCHANT, RedisValue.FIND_Q_USER, RedisValue.FIND_ENTER_PRISE}, allEntries = true)
    @RequestMapping(value = ServiceUrls.QMerchant.Q_MERCHANT_MODIFY_BALANCE_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID更改余额(划分/回收)", notes = "根据ID更改余额(回收/划分)")
    Result modifyBalanceById(
            @ApiParam(name = "sourceId", value = "来源ID", defaultValue = "")
            @RequestParam(value = "sourceId", required = false) Long sourceId,
            @ApiParam(name = "targetId", value = "目标ID", defaultValue = "")
            @RequestParam(value = "targetId", required = false) Long targetId,
            @ApiParam(name = "value", value = "值", defaultValue = "")
            @RequestParam(value = "value", required = false) Double value,
            @ApiParam(name = "type", value = "类型，划分还是收回", defaultValue = "")
            @RequestParam(value = "type", required = false) Integer type,
            @ApiParam(name = "language", value = "语言", defaultValue = "")
            @RequestParam(value = "language", required = false) String language);

    @CacheEvict(value = RedisValue.FIND_MERCHANT, allEntries = true)
    @RequestMapping(value = ServiceUrls.QMerchant.Q_MERCHANT_BATCH_ADD_OR_DEL, method = RequestMethod.POST)
    @ApiOperation(value = "批量操作(增/减)Q币", notes = "批量操作(增/减)Q币")
    Result batchAddOrDel(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) String id,
            @ApiParam(name = "value", value = "value", defaultValue = "")
            @RequestParam(value = "value", required = false) Double value,
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId", required = false) Long userId,
            @ApiParam(name = "language", value = "语言", defaultValue = "")
            @RequestParam(value = "language", required = false) String language);

    @Cacheable(value = RedisValue.FIND_MERCHANT, key = "'search_q_merchant_find_q_balance_conditions_id='+#p0+'", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.QMerchant.Q_MERCHANT_TOTAL_Q, method = RequestMethod.GET)
    @ApiOperation(value = "查询可用的Q币 ，及已分配的Q币", notes = "查询可用的Q币 ，及已分配的Q币")
    MQtotal findQBalance(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id);


    @Cacheable(value = RedisValue.FIND_MERCHANT, key = "'search_q_merchant_get_conditions_id='+#p0", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.QMerchant.Q_MERCHANT_BY_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据idQ币代理信息", notes = "根据idQ币代理信息")
    Result<MQMerchant> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id", required = false) Long id);

}



