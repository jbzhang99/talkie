package com.meta.controller.user;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.datetime.DateTimeUtil;
import com.meta.model.user.UserGroup;
import com.meta.regex.RegexUtil;
import com.meta.service.user.UserGroupService;
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
 * Created by llin on 2017/9/28.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "userGroup", description = "用户组接口")
public class UserGroupController extends BaseControllerUtil {

    private final static Logger logger = LoggerFactory.getLogger(UserGroupController.class);
    @Autowired
    private UserGroupService userGroupService;


    @RequestMapping(value = ServiceUrls.UserGroup.USER_GROUPS, method = RequestMethod.GET)
    @ApiOperation(value = "获取用户组事件列表", notes = "根据查询条件获用户组列表在前端表格展示")
    public Result<UserGroup> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<UserGroup> result = userGroupService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }


    @RequestMapping(value = ServiceUrls.UserGroup.USER_GROUPS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改用户组信息", notes = "创建/修改用户组信息")
    public Result<UserGroup> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid UserGroup userGroup) {
        return success(userGroupService.save(userGroup));
    }


    @RequestMapping(value = ServiceUrls.UserGroup.USER_GROUPS, method = RequestMethod.DELETE)
    @ApiOperation(value = "删除用户组信息", notes = "根据id删除用户组信息")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) {
        userGroupService.delete(id);
        return success(null);
    }

    @RequestMapping(value = ServiceUrls.UserGroup.USER_GROUP_BATCH, method = RequestMethod.POST)
    @ApiOperation(value = "批量新增用户组信息", notes = "批量新增用户组信息")
    public Result<UserGroup> batchCreat(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid UserGroup userGroup) throws Exception {

        if (userGroup.getType().equals("1")) {
            String[] userTemp = userGroup.getUserIdTemp().split(",");

            for (int x = 0; x < userTemp.length; x++) {
                UserGroup userGroup1 = new UserGroup();
                userGroup1.setCreateUser(getUserId());
                userGroup1.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                userGroup1.setUserId(Long.parseLong(userTemp[x]));
                userGroup1.setGroupId(userGroup.getGroupId());
                userGroupService.save(userGroup1);
            }

        } else if (userGroup.getType().equals("2")) {
            String[] groupTemp = userGroup.getGroupIdTemp().split(",");

            for (int x = 0; x < groupTemp.length; x++) {
                UserGroup userGroup2 = new UserGroup();
                userGroup2.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                userGroup2.setCreateUser(getUserId());
                userGroup2.setUserId(userGroup.getUserId());
                userGroup2.setGroupId(Long.parseLong(groupTemp[x]));
                userGroupService.save(userGroup2);
            }
        }
        return success(null);
    }


    @RequestMapping(value = ServiceUrls.UserGroup.USER_GROUP_BATCH, method = RequestMethod.DELETE)
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
             boolean flag = userGroupService.deleteByUserIdAndGroupId(Long.parseLong(strTemp[x]), Long.parseLong(groupId));
             if (!flag) {
                 return error("删除失败！");
             }
         }
     } else if ( type.equals("2")){
         String[] groupTemp = groupId.split(",");
         for (int x = 0; x < groupTemp.length; x++) {
             boolean flag = userGroupService.deleteByUserIdAndGroupId(Long.parseLong(userId), Long.parseLong(groupTemp[x]));
             if (!flag) {
                 return error("删除失败！");
             }
         }

     }
        return success(null);
    }

}
