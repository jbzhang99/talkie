package com.meta.controller.group;

import com.fasterxml.jackson.annotation.JsonView;
import com.meta.*;
import com.meta.datetime.DateTimeUtil;
import com.meta.model.group.Group;
import com.meta.model.user.AssUserGroup;
import com.meta.model.user.User;
import com.meta.model.user.UserEvent;
import com.meta.regex.RegexUtil;
import com.meta.remark.remarkDict;
import com.meta.service.enterprise.EnterpriseEventService;
import com.meta.service.group.GroupService;
import com.meta.service.user.UserEventService;
import com.meta.service.user.UserGroupService;
import com.meta.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lhq on 2017/9/30.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "group", description = "组接口")
public class GroupController extends BaseControllerUtil {

    private final static Logger logger = LoggerFactory.getLogger(GroupController.class);
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private UserEventService userEventService;

    @Autowired
    private EnterpriseEventService enterpriseEventService;

    @RequestMapping(value = ServiceUrls.Group.GROUPS, method = RequestMethod.GET)
    @ApiOperation(value = "获取组表", notes = "根据查询条件获组在前端表格展示")
    public Result<Group> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<Group> result = groupService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }

    @RequestMapping(value = ServiceUrls.Group.GROUP_ASSIGNMENT, method = RequestMethod.GET)
    @ApiOperation(value = "获取组表(群组功能用)", notes = "根据查询条件获组(群组功能用)在前端表格展示")
    public Result<AssUserGroup> searchAssignment(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id) throws Exception {
        List<AssUserGroup> list = new ArrayList<AssUserGroup>();

        List<User> userList = userService.assignGroup(id);
        if (userList.size() > 0) {
            for (int x = 0; x < userList.size(); x++) {
                AssUserGroup assUserGroup = new AssUserGroup();
                assUserGroup.setId(userList.get(x).getId());
                assUserGroup.setName(userList.get(x).getName());
                list.add(assUserGroup);
                List<Group> groupPage = groupService.search("EQ_user.id=" + userList.get(x).getId());
                if (groupPage.size() > 0) {
                    assUserGroup.setGroupList(groupPage);
                }
            }
        }
        return getResultList(list);
    }


    @RequestMapping(value = ServiceUrls.Group.GROUPS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改组信息", notes = "创建/修改组信息")
    public Result<Group> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid Group group) throws Exception {
        User user = userService.findOne(getUserId());
        if (RegexUtil.isNull(group.getId())) {
            group.setUser(user);
            if (group.getLevel().equals("2")) {
                group.setParentId(user.getParentId());
            } else {
                group.setParentId(0L);
            }

            try {
                //新增操作记录
                UserEvent userEvent = new UserEvent();
                userEvent.setType(6);
                userEvent.setCreateUser(user.getId());
                userEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                userEvent.setIp(getIpAddr());
                if(user.getLanguage().equals("zh")){
                    userEvent.setRemark(remarkDict.ADD + remarkDict.USER + user.getName());
                }else if (user.getLanguage().equals("en")){
                    userEvent.setRemark(remarkDict.ADD_ENGLISHI +"  "+ remarkDict.USER_ENGLISH +"  "+ user.getName());
                }
                userEvent.setUser(user);
                enterpriseEventService.saveGroupEventLog(user,6,group.getName(),null);
                userEventService.save(userEvent);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

        } else {
            group.setUser(user);
            try {
                //新增操作记录
                UserEvent userEvent = new UserEvent();
                userEvent.setType(7);
                userEvent.setIp(getIpAddr());
                userEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                if(user.getLanguage().equals("zh")){
                    userEvent.setRemark(remarkDict.MODIFY + remarkDict.USER + user.getName());
                }else if (user.getLanguage().equals("en")){
                    userEvent.setRemark(remarkDict.MODIFY_ENGLISH +"  "+ remarkDict.USER_ENGLISH +"  "+ user.getName());
                }
                userEvent.setModifyUser(user.getId());
                userEvent.setUser(user);

                enterpriseEventService.saveGroupEventLog(user,7,group.getName(), groupService.findOne(group.getId()).getName());
                userEventService.save(userEvent);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        return success(groupService.save(group));
    }

    @RequestMapping(value = ServiceUrls.Group.GROUP_BY_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取组信息", notes = "根据id获取组信息")
    public Result<Group> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id) {
        Group group = groupService.findOne(id);
        return success(group);
    }


    @RequestMapping(value = ServiceUrls.Group.GROUPS, method = RequestMethod.DELETE)
    @ApiOperation(value = "删除组信息", notes = "根据id删除组信息")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id,
            @ApiParam(name = "user_id", value = "user_id", defaultValue = "")
            @RequestParam(value = "user_id") Long user_id
            )throws UnknownHostException{
       Group bean=groupService.findOne(id);
        User user=userService.findOne(user_id);
        enterpriseEventService.saveGroupEventLog(user,10,bean.getName(), null);
        userGroupService.deleteByGroupId(id);
        groupService.delete(id);
        return success(null);
    }


    @RequestMapping(value = ServiceUrls.Group.GROUP_MODIFY_BY_STATUS, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status") String status) throws Exception {
        boolean flag = groupService.modifyStatusById(id, status);
        if (flag) {
            return success("");
        } else {
            return error("变更失败！");
        }
    }

    @RequestMapping(value = ServiceUrls.Group.GROUP_FIND_WAIT_BY_USER, method = RequestMethod.GET)
    @ApiOperation(value = "查找未添加用户的组", notes = "查找未添加用户的组")
    public Result<Group> findWaitByUser(
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId") Long userId,
            @ApiParam(name = "currId", value = "currId", defaultValue = "")
            @RequestParam(value = "currId") Long currId) throws Exception {
        List<Group> list = groupService.findWaitByUser(userId,currId);
        return getResultList(list);
    }

    @RequestMapping(value = ServiceUrls.Group.GROUP_FIND_ALREADY_USER, method = RequestMethod.GET)
    @ApiOperation(value = "查找已添加用户的组", notes = "查找已添加用户的组")
    public Result<Group> findAlreadyUser(
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId") Long userId) throws Exception {
        List<Group> list = groupService.findAlreadyUser(userId);
        return getResultList(list);
    }


    @RequestMapping(value = ServiceUrls.Group.GROUP_SEARCH_NOPAGE, method = RequestMethod.GET)
    @ApiOperation(value = "查找群组不分页", notes = "查找群组不分页")
    public Result<Group> searchNoPage(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters) throws Exception {
        List<Group> groupList = groupService.search(filters);
        return getResultList(groupList);
    }
}
