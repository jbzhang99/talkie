package com.meta.map;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.feignclient.map.BaiDuMapClient;
import com.meta.model.map.MBaiDuMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:lhq
 * @date:2017/12/11 11:09
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "baidu_map", description = "定位信息，基本百度地图接口")
public class BaiDuMapController extends BaseControllerUtil {
    //日志
    private  static  final Logger logger= LoggerFactory.getLogger(BaiDuMapController.class);
    @Autowired
    private BaiDuMapClient baiDuMapClient;


    @RequestMapping(value = ServiceUrls.BaiDuMap.BAIDU_MAP, method = RequestMethod.GET)
    @ApiOperation(value = "根据账号获取用户的轨迹", notes = "根据账号获取用户的轨迹")
    public Result<MBaiDuMap> getById(
            @ApiParam(name = "files", value = "files", defaultValue = "")
            @RequestParam(value = "files") String files,
            @ApiParam(name = "startDate", value = "startDate", defaultValue = "")
            @RequestParam(value = "startDate",required = false) String startDate,
            @ApiParam(name = "endDate", value = "endDate", defaultValue = "")
            @RequestParam(value = "endDate",required = false) String endDate) {
        Result<MBaiDuMap> result=null;
        try {
            result = baiDuMapClient.getById(files, startDate, endDate);
        }catch (Exception e){
            logger.error("获取信息失败！");
            logger.error(e.getMessage(),e);
            return error("获取信息失败！");
        }
        return result;


    }

    }
