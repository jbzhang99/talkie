//package com.meta.TotalInfo;
//
//import com.meta.BaseControllerUtil;
//import com.meta.Result;
//import com.meta.ServiceUrls;
//import com.meta.VersionNumbers;
//import com.meta.feignclient.totalInfo.MerchantTotalInfoClent;
//import com.meta.model.totalinfo.MMerchantTotalCountInfo;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
///**
// * @author:lhq
// * @date:2017/12/4 14:59
// */
//@RestController
//@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
//@Api(value = "merchant_total_info", description = "代理信息统计接口")
//public class MerchantTotalInfoController extends BaseControllerUtil {
//
//    @Autowired
//    private MerchantTotalInfoClent merchantTotalInfoClent;
//
//
//    @RequestMapping(value = ServiceUrls.MerchantTotalInfo.MERCHANT_TOTAL_INFOS, method = RequestMethod.GET)
//    @ApiOperation(value = "获取代理信息统计表", notes = "根据查询条件获代理用户信息统计在前端表格展示")
//    public Result<MMerchantTotalCountInfo> search(
//            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
//            @RequestParam(value = "filters", required = false) String filters,
//            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
//            @RequestParam(value = "sorts", required = false) String sorts,
//            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
//            @RequestParam(value = "size", required = false) int size,
//            @ApiParam(name = "page", value = "页码", defaultValue = "1")
//            @RequestParam(value = "page", required = false) int page) {
//        Result<MMerchantTotalCountInfo> result = null;
//        try {
//            result = merchantTotalInfoClent.search(filters, sorts, size, page);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return error("获取代理信息统计表失败！");
//        }
//        return result;
//    }
//
//    @RequestMapping(value = ServiceUrls.MerchantTotalInfo.MERCHANT_TOTAL_INFOS, method = RequestMethod.POST)
//    @ApiOperation(value = "创建/修改代理用户信息统计信息", notes = "创建/修改代理用户信息统计信息")
//    public Result<MMerchantTotalCountInfo> create(
//            @ApiParam(name = "json_model", value = "", defaultValue = "")
//            @RequestBody @Valid MMerchantTotalCountInfo mMerchantTotalCountInfo) {
//        Result<MMerchantTotalCountInfo> result = null;
//        try {
//            result = merchantTotalInfoClent.create(mMerchantTotalCountInfo);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return error("创建/修改代理用户信息统计信息失败！");
//        }
//        return result;
//    }
//}
