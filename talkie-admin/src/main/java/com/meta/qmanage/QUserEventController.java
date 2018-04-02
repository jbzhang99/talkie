package com.meta.qmanage;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.commonUtil.CommonUtils;
import com.meta.feignclient.qmanage.QUserEventClient;
import com.meta.model.qmanage.MQUserEvent;
import com.meta.model.user.MUser;
import com.meta.regex.RegexUtil;
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
 * Created by lhq on 2017/10/18.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "quser_event", description = "Q币用户操作事件接口")
public class QUserEventController extends BaseControllerUtil {
    //日志
    private  static  final Logger logger= LoggerFactory.getLogger(QUserEventController.class);

    @Autowired
    private QUserEventClient qUserEventClient;


    @RequestMapping(value = ServiceUrls.QUserEvent.Q_USER_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币用户操作表", notes = "根据查询条件获Q币用户操作在前端表格展示")
    public Result<MQUserEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MQUserEvent> result = null;
        try {
            result = qUserEventClient.search(filters, "-createDate", size, page);
           if(result.getDetailModelList().size() >0){
               for(MQUserEvent temp: result.getDetailModelList()){
                 temp.setTypeName(CommonUtils.findByQUserEventType(temp.getType().toString()));
               }
            }
        } catch (Exception e) {
            logger.error("获取Q币用户操作异常！");
            logger.error(e.getMessage(),e);
            return error("获取Q币用户操作异常！");
        }
        return result;
    }

}
