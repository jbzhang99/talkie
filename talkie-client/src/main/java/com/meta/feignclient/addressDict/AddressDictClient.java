package com.meta.feignclient.addressDict;

import com.meta.*;
import com.meta.feignfallback.addressdict.AddressDictFallBack;
import com.meta.model.addressDict.MAddressDict;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by lhq on 2017/10/12.
 */
@FeignClient(name = ServiceNames.TALKIE_CORE,fallbackFactory = AddressDictFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface AddressDictClient {

    @Cacheable(value = RedisValue.FIND_USER, key = "'search_maddress_dict_conditions_filters='+#p0+'_and_sorts='+#p1+'_and_size='+#p2+'_and_page='+#p3", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.AddressDict.ADDRESS_DICT, method = RequestMethod.GET)
    @ApiOperation(value = "获取省份城市表", notes = "根据查询条件获省份城市在前端表格展示")
     Result<MAddressDict> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);

    @Cacheable(value = RedisValue.FIND_USER, key = "'get_maddress_dict_conditions_id='+#p0+'", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.AddressDict.ADDRESS_DICTS, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取权限信息", notes = "根据id获取权限信息")
     Result<MAddressDict> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id);
}
