package com.meta.controller.sim;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.model.sim.SIM;
import com.meta.service.sim.SIMservice;
import com.mysql.jdbc.SQLError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.POST;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by y747718944 on 2018/2/28
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "sim", description = "SIM卡管理接口")
public class SIMController  extends BaseControllerUtil {


    @Autowired
    private SIMservice siMservice;


    @GetMapping(ServiceUrls.SIM.SIM)
    @ApiOperation(value = "查询",notes = "")
    public Result<SIM> Page (
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page){
        Page<SIM> result = siMservice.searchExtendDistinct(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }

    @PostMapping(ServiceUrls.SIM.SIM)
    @ApiOperation(value = "新增",notes = "")
    public Result create(@ApiParam(name = "ICCID", value = "iccId集合", defaultValue = "")
                             @RequestParam(value = "ICCID", required = true) String[] ICCID) throws Exception{


        Map<String,Object> filtersMap = new HashMap<>();
        filtersMap.put("IN_iccId",ICCID);
       List<SIM> sims = siMservice.findList(filtersMap);
        if (sims.size()<1 ){
            List<SIM> simList = Arrays.asList(ICCID)
                    .stream()
                    .map( e -> new SIM(e))
                    .collect(Collectors.toList());
         List<SIM>  resule = siMservice.save(simList);
            return success(resule);
        }else {
          String msg="以下iccId已经存在 -->  ";
            for (SIM temp: sims){
                msg+=temp.getIccId()+",";
            }
            return error(msg);
        }



    }

    @DeleteMapping(ServiceUrls.SIM.SIM)
    @ApiOperation(value = "删除",notes = "")
    public Result Delete(@ApiParam(name = "id", value = "id", defaultValue = "")
                             @RequestParam(value = "id", required = true) Long id){
        siMservice.delete(Arrays.asList(id));
        return success("删除成功");
    }



}
