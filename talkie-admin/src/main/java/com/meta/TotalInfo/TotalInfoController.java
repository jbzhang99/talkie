//package com.meta.TotalInfo;
//
//import com.meta.BaseControllerUtil;
//import com.meta.Result;
//import com.meta.ServiceUrls;
//import com.meta.VersionNumbers;
//import com.meta.feignclient.totalInfo.TotalInfoClient;
//import com.meta.model.totalinfo.MTotalCountInfo;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
///**
// * @author:lhq
// * @date:2017/12/4 14:44
// */
//@RestController
//@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
//@Api(value = "total_info", description = "总代信息统计接口")
//public class TotalInfoController extends BaseControllerUtil {
//
//    @Autowired
//    private TotalInfoClient totalInfoClient;
//    private final static Logger logger = LoggerFactory.getLogger(TotalInfoController.class);
//
//
//    @RequestMapping(value = ServiceUrls.TotalInfo.TOTAL_INFOS, method = RequestMethod.GET)
//    @ApiOperation(value = "获取用户信息统计表", notes = "根据查询条件获用户信息统计在前端表格展示")
//    public Result<MTotalCountInfo> search(
//            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
//            @RequestParam(value = "filters", required = false) String filters,
//            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
//            @RequestParam(value = "sorts", required = false) String sorts,
//            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
//            @RequestParam(value = "size", required = false) int size,
//            @ApiParam(name = "page", value = "页码", defaultValue = "1")
//            @RequestParam(value = "page", required = false) int page) {
//        Result<MTotalCountInfo> result = null;
//        try {
//            result = totalInfoClient.search(filters, sorts, size, page);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return error("获取信息统计表失败！");
//        }
//        return result;
//    }
//
//    @RequestMapping(value = ServiceUrls.TotalInfo.TOTAL_INFOS, method = RequestMethod.POST)
//    @ApiOperation(value = "创建/修改用户信息统计信息", notes = "创建/修改用户信息统计信息")
//    public Result<MTotalCountInfo> create(
//            @ApiParam(name = "json_model", value = "", defaultValue = "")
//            @RequestBody @Valid MTotalCountInfo mTotalCountInfo) {
//        Result<MTotalCountInfo> result = null;
//        try {
//            result = totalInfoClient.create(mTotalCountInfo);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return error("获取信息统计表失败！");
//        }
//        return result;
//    }
//
//}
