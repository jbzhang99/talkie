package com.meta.user;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.commonUtil.CommonUtils;
import com.meta.datetime.DateTimeUtil;
import com.meta.feignclient.user.UserEventClient;
import com.meta.model.user.MUserEvent;
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
 * Created by lhq on 2017/10/18.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "userEvent", description = "用户操作事件接口")
public class UserEventController extends BaseControllerUtil {

    private final static Logger logger = LoggerFactory.getLogger(UserEventController.class);
    @Autowired
    private UserEventClient userEventClient;


    @RequestMapping(value = ServiceUrls.UserEvent.USER_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取用户操作事件列表", notes = "根据查询条件获用户操作列表在前端表格展示")
    public Result<MUserEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MUserEvent> result = null;
        try {
            result = userEventClient.search(filters, "-createDate", size, page);

            if (result.getDetailModelList().size() > 0) {
                for (MUserEvent temp : result.getDetailModelList()) {
                    if (temp.getType() > 0) {
                        temp.setTypeName(CommonUtils.findByUserEventType(temp.getType().toString()));
                    }
                }

            }

        } catch (Exception e) {
            logger.error("获取用户操作事件异常");
            logger.error(e.getMessage(),e);
            return error("获取用户操作事件异常");
        }
        return result;
    }

}
