package com.meta.merchant;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.commonUtil.CommonUtils;
import com.meta.commonUtil.EnglishCommonUtils;
import com.meta.feignclient.merchant.MerchantEventClient;
import com.meta.model.merchant.MMerchantEvent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by lhq on 2017/11/13.
 */

@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "merchant_event", description = "代理操作信息接口")
public class MerchantEventController extends BaseControllerUtil {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(MerchantEventController.class);

    @Autowired
    private MerchantEventClient merchantEventClient;

    @RequestMapping(value = ServiceUrls.MerchantEvent.MERCHANT_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取代理操作列表", notes = "根据查询条件获代理操作列表在前端表格展示")
    public Result<MMerchantEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MMerchantEvent> result = null;
        try {
            result = merchantEventClient.search(filters, "-createDate", size, page);
            if (result.getDetailModelList().size() > 0) {
                result.getDetailModelList().stream().forEach(a->{
                  if(a.getType() >0){
                      if ("zh".equals(getLanguage())) {
//                            //中文
                            a.setTypeName(CommonUtils.findByUserEventType(a.getType().toString()));
                        } else if ("en".equals(getLanguage())) {
                            a.setTypeName(EnglishCommonUtils.findByUserEventType(a.getType().toString()));
                        }

                  }
                });
            }
        } catch (Exception e) {
            logger.error("获取代理操作列表失败！");
            logger.error(e.getMessage(), e);
            return error("获取代理操作列表失败！");
        }
        return result;
    }


}
