package com.meta.controller.enterprise;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.datetime.DateTimeUtil;
import com.meta.model.enterprise.SecEnterPriseUserGroup;
import com.meta.regex.RegexUtil;
import com.meta.service.enterprise.SecEnterPriseUserGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * Created by lhq on 2017/11/23.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "sec_enter_priseuser_uroup", description = "二级管理与组接口")
public class SecEnterPriseUserGroupController extends BaseControllerUtil {

    @Autowired
    private SecEnterPriseUserGroupService secEnterPriseUserGroupService ;


    @RequestMapping(value = ServiceUrls.SecEnterPriseUserGroup.SEC_ENTER_PRISE_USER_GROUPS, method = RequestMethod.GET)
    @ApiOperation(value = "获取二级管理与组事件列表", notes = "根据查询条件获二级管理与组列表在前端表格展示")
    public Result<SecEnterPriseUserGroup> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<SecEnterPriseUserGroup> result = secEnterPriseUserGroupService.searchExtendDistinct(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }



    @RequestMapping(value = ServiceUrls.SecEnterPriseUserGroup.SEC_ENTER_PRISE_USER_GROUPS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改二级管理与组信息", notes = "创建/修改二级管理与组信息")
    public Result<SecEnterPriseUserGroup> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid SecEnterPriseUserGroup secEnterPriseUserGroup) {
        return success(secEnterPriseUserGroupService.save(secEnterPriseUserGroup));
    }


    @RequestMapping(value = ServiceUrls.SecEnterPriseUserGroup.SEC_ENTER_PRISE_USER_GROUPS, method = RequestMethod.DELETE)
    @ApiOperation(value = "删除用户组信息", notes = "根据id删除用户组信息")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) {
        secEnterPriseUserGroupService.delete(id);
        return success(null);
    }

    @RequestMapping(value = ServiceUrls.SecEnterPriseUserGroup.SEC_ENTER_PRISE_USER_GROUP_BATCH, method = RequestMethod.POST)
    @ApiOperation(value = "批量新增用户组信息", notes = "批量新增用户组信息")
    public Result<SecEnterPriseUserGroup> batchCreat(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid SecEnterPriseUserGroup secEnterPriseUserGroup) throws Exception {

        if (secEnterPriseUserGroup.getType().equals("1")) {
            String[] userTemp = secEnterPriseUserGroup.getUserIdTemp().split(",");

            for (int x = 0; x < userTemp.length; x++) {
                SecEnterPriseUserGroup userGroup1 = new SecEnterPriseUserGroup();
                userGroup1.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                userGroup1.setCreateUser(secEnterPriseUserGroup.getCurrentUserId());
                userGroup1.setUserId(Long.parseLong(userTemp[x]));
                userGroup1.setGroupId(secEnterPriseUserGroup.getGroupId());
                userGroup1.setEventType(secEnterPriseUserGroup.getEventType());
                if(secEnterPriseUserGroup.getEventType()==1 ||secEnterPriseUserGroup.getEventType()==2){//如果是指派或旁听事件需要先清理用户之前的数据
                    secEnterPriseUserGroupService.deleteByUserIdAndType(userGroup1.getUserId(),secEnterPriseUserGroup.getEventType());
                }
                secEnterPriseUserGroupService.save(userGroup1);
            }

        } else if (secEnterPriseUserGroup.getType().equals("2")) {
            String[] groupTemp = secEnterPriseUserGroup.getGroupIdTemp().split(",");

            for (int x = 0; x < groupTemp.length; x++) {
                SecEnterPriseUserGroup userGroup2 = new SecEnterPriseUserGroup();
                userGroup2.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                userGroup2.setUserId(secEnterPriseUserGroup.getUserId());
                userGroup2.setCreateUser(secEnterPriseUserGroup.getCurrentUserId());
                userGroup2.setGroupId(Long.parseLong(groupTemp[x]));
                secEnterPriseUserGroupService.save(userGroup2);
            }

        }
        System.err.println("11");
        return success("1");
    }

    @RequestMapping(value = ServiceUrls.SecEnterPriseUserGroup.SEC_ENTER_PRISE_USER_GROUP_DEL_BATCH, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据userid and groupid 删除信息", notes = "根据userid and groupid 删除信息")
    public Result deleteByUserIdAndGroupId(
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId") String userId,
            @ApiParam(name = "groupId", value = "groupId", defaultValue = "")
            @RequestParam(value = "groupId") String groupId,
            @ApiParam(name = "type", value = "type", defaultValue = "")
            @RequestParam(value = "type") String type) throws Exception {
        //通过群组方来删除  type == 1
        if(type.equals("1")){
            String[] strTemp = userId.split(",");
            for (int x = 0; x < strTemp.length; x++) {
                boolean flag = secEnterPriseUserGroupService.deleteByUserIdAndGroupId(Long.parseLong(strTemp[x]), Long.parseLong(groupId));
                if (!flag) {
                    return error("删除失败！");
                }
            }
        } else if ( type.equals("2")){
            String[] groupTemp = groupId.split(",");
            for (int x = 0; x < groupTemp.length; x++) {
                boolean flag = secEnterPriseUserGroupService.deleteByUserIdAndGroupId(Long.parseLong(userId), Long.parseLong(groupTemp[x]));
                if (!flag) {
                    return error("删除失败！");
                }
            }

        }
        return success("");
    }







}
