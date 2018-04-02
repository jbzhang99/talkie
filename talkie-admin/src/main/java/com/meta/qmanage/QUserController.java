package com.meta.qmanage;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.feignclient.qmanage.QUserClient;
import com.meta.model.qmanage.MQUser;
import com.meta.model.qmanage.MQtotal;
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
 * Created by lhq on 2017/10/19.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "quser", description = "Q币用户接口")
public class QUserController extends BaseControllerUtil {
    //日志
    private  static  final Logger logger= LoggerFactory.getLogger(QUserController.class);

    @Autowired
    private QUserClient qUserClient;

    @RequestMapping(value = ServiceUrls.QUser.Q_USER_MODIFY_BALANCE_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID更改余额", notes = "根据ID更改余额")
    public Result<MQUser> modifyBalanceById(
            @ApiParam(name = "sourceId", value = "来源ID", defaultValue = "")
            @RequestParam(value = "sourceId", required = false) Long sourceId,
            @ApiParam(name = "targetId", value = "目标ID", defaultValue = "")
            @RequestParam(value = "targetId", required = false) Long targetId,
            @ApiParam(name = "value", value = "值", defaultValue = "")
            @RequestParam(value = "value", required = false) Double value,
            @ApiParam(name = "type", value = "类型，划分还是收回", defaultValue = "")
            @RequestParam(value = "type", required = false) Integer type) throws Exception {
        Result<MQUser> result = null;
        try {
            result = qUserClient.modifyBalanceById(sourceId, targetId, value, type);
        } catch (Exception e) {
            logger.error("操作失败");
            logger.error(e.getMessage(),e);
            return error("操作失败");
        }

        return result;
    }


    @RequestMapping(value = ServiceUrls.QUser.Q_USERS, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币用户表", notes = "根据查询条件获Q币用户在前端表格展示")
    public Result<MQUser> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MQUser> result = null;
        try {
            result = qUserClient.search(filters, "-createDate", size, page);
        } catch (Exception e) {
            logger.error("操作失败");
            logger.error(e.getMessage(),e);
            return error("操作失败");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.QUser.Q_USER_TOTAL_Q, method = RequestMethod.GET)
    @ApiOperation(value = "查询可用的Q币 ，及已分配的Q币", notes = "查询可用的Q币 ，及已分配的Q币")
    public  Result<MQtotal>  findQBalance(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id) {
        Result<MQtotal> result = new Result();
        try {
            MQtotal  mQtotal = qUserClient.findQBalance(id);
              result.setObj(mQtotal);
        }catch (Exception e){
            logger.error( ServiceUrls.QUser.Q_USER_TOTAL_Q+"查询可用的Q币 ，及已分配的Q币");
            logger.error(e.getMessage(),e);
        }

        return result;

    }

    @RequestMapping(value = ServiceUrls.QUser.Q_USER_BATCH_ADD_OR_DEL, method = RequestMethod.POST)
    @ApiOperation(value = "批量操作(增/减)Q币", notes = "批量操作(增/减)Q币")
    public Result<MQUser>  batchAddOrDel(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) String id,
            @ApiParam(name = "value", value = "value", defaultValue = "")
            @RequestParam(value = "value", required = false) Double value,
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId", required = false) Long userId) throws Exception {
        Result<MQUser> result = null;
        try {
            result = qUserClient.batchAddOrDel(id,value,userId);
        } catch (Exception e) {
            logger.error("操作失败");
            logger.error(e.getMessage(),e);
            return error("操作失败");
        }
        return result;

    }

}
