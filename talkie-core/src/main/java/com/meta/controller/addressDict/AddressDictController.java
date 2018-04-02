package com.meta.controller.addressDict;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.model.addressDict.AddressDict;
import com.meta.model.role.Role;
import com.meta.service.addressDict.AddressDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lhq on 2017/10/12.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "addressDict", description = "中国省份城市接口")
public class AddressDictController extends BaseControllerUtil {

    private final static Logger logger = LoggerFactory.getLogger(AddressDictController.class);

     @Autowired
    private AddressDictService addressDictService;

    @RequestMapping(value = ServiceUrls.AddressDict.ADDRESS_DICT, method = RequestMethod.GET)
    @ApiOperation(value = "获取省份城市表", notes = "根据查询条件获省份城市在前端表格展示")
    public Result<AddressDict> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<AddressDict> result = addressDictService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }



    @RequestMapping(value = ServiceUrls.AddressDict.ADDRESS_DICTS, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取省份城市信息", notes = "根据id获取省份城市信息")
    public Result<AddressDict> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) {
        AddressDict addressDict = addressDictService.findOne(id);
        return success(addressDict);
    }

}
