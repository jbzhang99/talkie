package com.meta.accountant;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.commonUtil.CommonUtils;
import com.meta.commonUtil.EnglishCommonUtils;
import com.meta.feignclient.accountant.AccountantEventClient;
import com.meta.model.accountant.MAccountantEvent;
import com.meta.regex.RegexUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by lhq
 * create date on  18-3-2上午10:52
 *
 * @version 1.0
 **/
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "accountant_event", description = "会计操作信息接口")
public class AccountantEventController extends BaseControllerUtil {

    @Autowired
    private AccountantEventClient accountantEventClient;

    @RequestMapping(value = ServiceUrls.AccountantEvent.ACCOUNTANT_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取会计操作列表", notes = "根据查询条件获会计操作列表在前端表格展示")
    public Result<MAccountantEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MAccountantEvent> result = null;
        try {
            result = accountantEventClient.search(filters, sorts, size, page);
            if (result.getDetailModelList().size() > 0) {
                if (result.getDetailModelList().size() > 0) {
                    findDetail(result);
                }
            }
        } catch (Exception e) {
            logger.error("获取会计操作列表异常");
            logger.error(e.getMessage(), e);
            return error("获取会计操作列表异常");
        }
        return result;
    }

    private Result<MAccountantEvent> findDetail(Result<MAccountantEvent> result) {

        result.getDetailModelList().stream().forEach(a->{
            if ("zh".equals(getLanguage())) {
                if (!RegexUtil.isNull(a.getType())) {
                    a.setTypeName(CommonUtils.findByUserEventType(a.getType().toString()));
                }
            } else if ("en".equals(getLanguage())) {
                if (!RegexUtil.isNull(a.getType())) {
                    a.setTypeName(EnglishCommonUtils.findByUserEventType(a.getType().toString()));
                }

            }

        });
        return result;
    }
}
