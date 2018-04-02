package com.meta.controller.qmanage;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.model.qmanage.QUser;
import com.meta.service.qmanage.QSecEnterPriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by lhq on 2017/11/21.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "q_sec_enter_prise", description = "Q币二级管理接口")
public class QSecEnterPriseController extends BaseControllerUtil {

    @Autowired
    private QSecEnterPriseService qSecEnterPriseService;


    @RequestMapping(value = ServiceUrls.QSecEnterPrise.Q_SEC_ENTER_PRISES, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改Q币二级企业信息", notes = "创建/修改Q币二级企业信息")
    public Result<QUser> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid QUser qUser) {
        return success(qSecEnterPriseService.save(qUser));
    }

    @RequestMapping(value = ServiceUrls.QSecEnterPrise.Q_SEC_ENTER_PRISE, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取Q币二级企业信息", notes = "根据id获取Q币二级企业信息")
    public Result<QUser> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id) {
        QUser qUser = qSecEnterPriseService.findOne(id);
        return success(qUser);
    }

    @RequestMapping(value = ServiceUrls.QSecEnterPrise.Q_SEC_ENTER_PRISE_BY_ID, method = RequestMethod.DELETE)
    @ApiOperation(value = "删除Q币用户信息", notes = "根据id删除Q币用户信息")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id) {
        qSecEnterPriseService.delete(id);
        return success(null);
    }

    @RequestMapping(value = ServiceUrls.QSecEnterPrise.Q_SEC_ENTER_PRISES, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币二级企业表", notes = "根据查询条件获Q币二级企业在前端表格展示")
    public Result<QUser> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<QUser> result = qSecEnterPriseService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }

    @RequestMapping(value = ServiceUrls.QSecEnterPrise.Q_SEC_ENTER_PRISE_FIND_USER_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据idQ币信息", notes = "根据idQ币信息")
    public Result<QUser> findByUserId(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id) {
        QUser qUser = qSecEnterPriseService.findByUserId(id);
        return success(qUser);
    }
}
