package com.meta.enterprise;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.datetime.DateTimeUtil;
import com.meta.feignclient.enterprise.SecEnterPriseUserGroupClient;
import com.meta.model.enterprise.MSecEnterPriseUserGroup;
import com.meta.regex.RegexUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * Created by lhq on 2017/11/23.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "sec_enter_priseuser_uroup", description = "二级管理与组接口")
public class SecEnterPriseUserGroupController extends BaseControllerUtil {

    //日志
    private  static  final Logger logger= LoggerFactory.getLogger(SecEnterPriseUserGroupController.class);

    @Autowired
    private SecEnterPriseUserGroupClient secEnterPriseUserGroupClient;

    @RequestMapping(value = ServiceUrls.SecEnterPriseUserGroup.SEC_ENTER_PRISE_USER_GROUPS, method = RequestMethod.GET)
    @ApiOperation(value = "获取二级管理与组事件列表", notes = "根据查询条件获二级管理与组列表在前端表格展示")
    public Result<MSecEnterPriseUserGroup> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MSecEnterPriseUserGroup> result= null;
        try {
            result=secEnterPriseUserGroupClient.search(filters, sorts, size, page);
        }catch (Exception e){
            logger.error("二级管理与组失败！");
            logger.error(e.getMessage(),e);
            return error("二级管理与组失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.SecEnterPriseUserGroup.SEC_ENTER_PRISE_USER_GROUPS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改二级管理与组信息", notes = "创建/修改二级管理与组信息")
    public Result<MSecEnterPriseUserGroup> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MSecEnterPriseUserGroup mSecEnterPriseUserGroup) {
        Result<MSecEnterPriseUserGroup> result= null;
        try {
            result=secEnterPriseUserGroupClient.create(mSecEnterPriseUserGroup);
        }catch (Exception e){
            logger.error("创建/修改二级管理与组失败！");
            logger.error(e.getMessage(),e);
            return error("创建/修改二级管理与组失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.SecEnterPriseUserGroup.SEC_ENTER_PRISE_USER_GROUPS, method = RequestMethod.DELETE)
    @ApiOperation(value = "删除用户组信息", notes = "根据id删除用户组信息")
    public Result<MSecEnterPriseUserGroup> delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) {
        Result<MSecEnterPriseUserGroup> result= null;
        try {
            result=secEnterPriseUserGroupClient.delete(id);
        }catch (Exception e){
            logger.error("删除失败！");
            logger.error(e.getMessage(),e);
            return error("删除失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.SecEnterPriseUserGroup.SEC_ENTER_PRISE_USER_GROUP_BATCH, method = RequestMethod.POST)
    @ApiOperation(value = "批量新增用户组信息", notes = "批量新增用户组信息")
    public Result<MSecEnterPriseUserGroup> batchCreat(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MSecEnterPriseUserGroup mSecEnterPriseUserGroup) throws Exception {
        Result<MSecEnterPriseUserGroup> result= null;
        try {
            result=secEnterPriseUserGroupClient.batchCreat(mSecEnterPriseUserGroup);
        }catch (Exception e){
            logger.error("新增失败！");
            logger.error(e.getMessage(),e);
            return error("新增失败！");
        }
        return result;
    }
    @RequestMapping(value = ServiceUrls.SecEnterPriseUserGroup.SEC_ENTER_PRISE_USER_GROUP_DEL_BATCH, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据userid and groupid 删除信息", notes = "根据userid and groupid 删除信息")
    public Result<MSecEnterPriseUserGroup> deleteByUserIdAndGroupId(
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId") String userId,
            @ApiParam(name = "groupId", value = "groupId", defaultValue = "")
            @RequestParam(value = "groupId") String groupId,
            @ApiParam(name = "type", value = "type", defaultValue = "")
            @RequestParam(value = "type") String type) throws Exception {
        Result<MSecEnterPriseUserGroup> result= null;
        try {
            result=secEnterPriseUserGroupClient.deleteByUserIdAndGroupId(userId, groupId, type);
        }catch (Exception e){
            logger.error("删除失败！");
            logger.error(e.getMessage(),e);
            return error("删除失败！");
        }
        return result;


    }

}
