package com.meta.sim;

import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.feignclient.sim.SIMCilent;
import com.meta.model.MSIM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by y747718944 on 2018/2/28
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "SIM")
public class SIMController {

    @Autowired
    private SIMCilent simCilent;

    @GetMapping(ServiceUrls.SIM.SIM)
    @ApiOperation(value = "查询",notes = "")
    public Result<MSIM> Page (
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page){
        return simCilent.Page(filters,sorts,size,page);
    }

    @PostMapping(ServiceUrls.SIM.SIM)
    @ApiOperation(value = "新增",notes = "")
    public Result create(@ApiParam(name = "ICCID", value = "iccId集合", defaultValue = "")
                         @RequestParam(value = "ICCID", required = true) String[] ICCID) throws Exception{
        return simCilent.create(ICCID);
    }

    @DeleteMapping(ServiceUrls.SIM.SIM)
    @ApiOperation(value = "删除",notes = "")
    public Result Delete(@ApiParam(name = "id", value = "id", defaultValue = "")
                         @RequestParam(value = "id", required = true) Long id){

        return simCilent.Delete(id);
    }

}
