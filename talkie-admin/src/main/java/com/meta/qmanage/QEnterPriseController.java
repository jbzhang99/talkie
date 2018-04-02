package com.meta.qmanage;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.feignclient.qmanage.QEnterpriseClient;
import com.meta.model.qmanage.MQEnterprise;
import com.meta.model.qmanage.MQtotal;
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
 * Created by lhq on 2017/11/15.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "q_enter_prise", description = "Q币企业接口")
public class QEnterPriseController extends BaseControllerUtil {

    //日志
    private  static  final Logger logger= LoggerFactory.getLogger(QEnterPriseController.class);

    @Autowired
    private QEnterpriseClient qEnterpriseClient;


    @RequestMapping(value = ServiceUrls.QEnterPrise.Q_ENTER_PRISES, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币企业表", notes = "根据查询条件获Q币企业在前端表格展示")
    public Result<MQEnterprise> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MQEnterprise> result = null;
        try {
            result = qEnterpriseClient.search(filters, "-createDate", size, page);
        } catch (Exception e) {
            logger.error("获取Q币企业失败！");
            logger.error(e.getMessage(),e);
            return error("获取Q币企业失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.QEnterPrise.Q_ENTER_PRISES, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改Q币企业信息", notes = "创建/修改Q币企业信息")
    public Result<MQEnterprise> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MQEnterprise mqEnterprise) {
        Result<MQEnterprise> result = null;
        try {
            result = qEnterpriseClient.create(mqEnterprise);
        } catch (Exception e) {
            logger.error("创建/修改Q币失败！");
            logger.error(e.getMessage(),e);
            return error("创建/修改Q币失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.QEnterPrise.Q_ENTER_PRISE_MODIFY_BALANCE_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID更改余额(回收/划分)", notes = "根据ID更改余额(回收/划分)")
    public Result<MQEnterprise> modifyBalanceById(
            @ApiParam(name = "sourceId", value = "来源ID", defaultValue = "")
            @RequestParam(value = "sourceId", required = false) Long sourceId,
            @ApiParam(name = "targetId", value = "目标ID", defaultValue = "")
            @RequestParam(value = "targetId", required = false) Long targetId,
            @ApiParam(name = "value", value = "值", defaultValue = "")
            @RequestParam(value = "value", required = false) Double value,
            @ApiParam(name = "type", value = "类型，划分还是收回", defaultValue = "")
            @RequestParam(value = "type", required = false) Integer type,
            @ApiParam(name = "currentId", value = "操作者ID", defaultValue = "")
            @RequestParam(value = "currentId", required = false) Long currentId) {
        Result<MQEnterprise> result = null;
        try {
            result = qEnterpriseClient.modifyBalanceById(sourceId, targetId, value, type, currentId);
        } catch (Exception e) {
            logger.error("回收/划分Q币失败！");
            logger.error(e.getMessage(),e);
            return error("回收/划分Q币失败！");
        }
        return result;

    }

    @RequestMapping(value = ServiceUrls.QEnterPrise.Q_ENTER_PRISE_TOTAL_Q, method = RequestMethod.GET)
    @ApiOperation(value = "查询可用的Q币 ，及已分配的Q币", notes = "查询可用的Q币 ，及已分配的Q币")
    public MQtotal findQBalance(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id) {
        MQtotal mQtotal=null;
        try {
         mQtotal = qEnterpriseClient.findQBalance(id);

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return mQtotal;
        }
        return mQtotal;
    }

    @RequestMapping(value = ServiceUrls.QEnterPrise.Q_ENTER_PRISE_BATCH_ADD_OR_DEL, method = RequestMethod.POST)
    @ApiOperation(value = "批量操作(增/减)Q币", notes = "批量操作(增/减)Q币")
    public Result<MQEnterprise> batchAddOrDel(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "value", value = "value", defaultValue = "")
            @RequestParam(value = "value", required = false) int value,
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId", required = false) Long userId){
        Result<MQEnterprise> result = null;
        try {
            result = qEnterpriseClient.batchAddOrDel(id, value, userId);
        } catch (Exception e) {
            logger.error("批量操作Q币失败！");
            logger.error(e.getMessage(),e);
            return error("批量操作Q币失败！");
        }
        return result;

    }

}
