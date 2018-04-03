package com.meta.qmanage;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.feignclient.qmanage.QSecEnterPriseEventClient;
import com.meta.model.qmanage.MQSecEnterPriseEvent;
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
 * Created by lhq on 2017/11/21.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "q_sec_enter_prise_event", description = "Q币二级管理操作记录接口")
public class QSecEnterPriseEventController extends BaseControllerUtil {

    //日志
    private  static  final Logger logger= LoggerFactory.getLogger(QSecEnterPriseEventController.class);

    @Autowired
    private QSecEnterPriseEventClient qSecEnterPriseEventClient;

    @RequestMapping(value = ServiceUrls.QSecEnterPriseEvent.Q_SEC_ENTER_PRISE_EVENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币二级管理操作表", notes = "根据查询条件获Q币二级管理操作在前端表格展示")
    public Result<MQSecEnterPriseEvent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MQSecEnterPriseEvent> result = null;
        try {
            result = qSecEnterPriseEventClient.search(filters, sorts, size, page);
        } catch (Exception e) {
            logger.error("获取Q币二级管理操作失败！");
            logger.error(e.getMessage(),e);
            return error("获取Q币二级管理操作失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.QSecEnterPriseEvent.Q_SEC_ENTER_PRISE_EVENTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改Q币二级管理操作记录信息", notes = "创建/修改Q币二级管理操作记录信息")
    public Result<MQSecEnterPriseEvent> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MQSecEnterPriseEvent mqSecEnterPriseEvent) {
        Result<MQSecEnterPriseEvent> result = null;
        try {
            result = qSecEnterPriseEventClient.create(mqSecEnterPriseEvent);
        } catch (Exception e) {
            logger.error("创建/修改Q币二级管理操作记录失败！");
            logger.error(e.getMessage(),e);
            return error("创建/修改Q币二级管理操作记录失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.QSecEnterPriseEvent.Q_SEC_ENTER_PRISE_EVENT, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取Q币二级管理操作信息", notes = "根据id获取Q币二级管理操作信息")
    public Result<MQSecEnterPriseEvent> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) {
        Result<MQSecEnterPriseEvent> result = null;
        try {
            result = qSecEnterPriseEventClient.get(id);
        } catch (Exception e) {
            logger.error("获取Q币二级管理操作信息失败！");
            logger.error(e.getMessage(),e);
            return error("获取Q币二级管理操作信息失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.QSecEnterPriseEvent.Q_SEC_ENTER_PRISE_EVENTS, method = RequestMethod.DELETE)
    @ApiOperation(value = "删除Q币二级管理操作信息", notes = "根据id删除Q币二级管理操作信息")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id) {
        Result<MQSecEnterPriseEvent> result = null;
        try {
            result = qSecEnterPriseEventClient.delete(id);
        } catch (Exception e) {
            logger.error("删除失败！");
            logger.error(e.getMessage(),e);
            return error("删除失败 ！");
        }
        return result;
    }



}
