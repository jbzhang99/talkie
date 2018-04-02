package com.meta.controller.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.meta.*;
import com.meta.datetime.DateTimeUtil;
import com.meta.model.user.User;
import com.meta.model.user.UserEvent;
import com.meta.model.user.UserFriend;
import com.meta.remark.remarkDict;
import com.meta.service.enterprise.EnterpriseEventService;
import com.meta.service.user.UserEventService;
import com.meta.service.user.UserFriendService;
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
import java.util.Date;

/**
 * Created by llin on 2017/9/28.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "userFriend", description = "用户好友接口")
public class UserFriendController extends BaseControllerUtil {

    private final static Logger logger = LoggerFactory.getLogger(UserFriendController.class);

    @Autowired
    private UserFriendService userFriendService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserEventService userEventService;
    @Autowired
    private  EnterpriseEventService enterpriseEventService;

    @RequestMapping(value = ServiceUrls.UserFriend.USER_FRIENDS, method = RequestMethod.GET)
    @ApiOperation(value = "获取用户好友事件列表", notes = "根据查询条件获用户好友列表在前端表格展示")
    public Result<UserFriend> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<UserFriend> result = userFriendService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }

    @RequestMapping(value = ServiceUrls.UserFriend.USER_FRIENDS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改用户好友信息", notes = "创建/修改用户好友信息")
    public Result<UserFriend> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid UserFriend userFriend) {
        return success(userFriendService.save(userFriend));
    }


    @RequestMapping(value = ServiceUrls.UserFriend.USER_FRIEND_BATCH_ADD_OR_DEL, method = RequestMethod.POST)
    @ApiOperation(value = "批量新增好友", notes = "批量新增好友")
    public Result<UserFriend> batchAddOrDelFriend(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid UserFriend userFriend) throws Exception {

        User user = userService.findOne(getUserId());
        User self = userService.findOne(userFriend.getSelfUserId());

        if (userFriend.getType().equals("1")) { // 新增
            String[] strArray = userFriend.getFriendUserIDTemp().split(",");
            for (int x = 0; x < strArray.length; x++) {
                Long friendUserId=Long.parseLong(strArray[x]);
                Long selfUserId=userFriend.getSelfUserId();
                UserFriend selfUser = new UserFriend();//自己添加对方好友bean
                selfUser.setUser(user);
                selfUser.setSelfUserId(selfUserId);
                selfUser.setFriendUserId(friendUserId);
                selfUser.setCreateUser(user.getId());
                selfUser.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));

                UserFriend friendUser= new UserFriend();;//对方添加自己为好友bean
                friendUser.setUser(user);
                //添加对方的好友 相反的
                friendUser.setFriendUserId(selfUserId);
                friendUser.setSelfUserId(friendUserId);
                friendUser.setCreateUser(user.getId());
                friendUser.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));

                User friend = userService.findOne(friendUserId);

                try {
                    //操作记录
                    UserEvent userEvent = new UserEvent();
                    userEvent.setUser(user);
                    userEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                    userEvent.setType(8);
                    userEvent.setIp(InetAddress.getLocalHost().getHostAddress());
                    userEvent.setRemark(self.getName() + remarkDict.ADD + remarkDict.FRIEND + friend.getName());
                    userEventService.save(userEvent);
                    userFriendService.save(selfUser);//自己添加对方好友
                    userFriendService.save(friendUser);//对方添加自己为好友
                    //企业添加好友
                    enterpriseEventService.saveFriendEventLog(user,self,8,friend.getAccount());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        } else if ("2".equals(userFriend.getType())) {
            String[] strArray = userFriend.getFriendUserIDTemp().split(",");
            for (int x = 0; x < strArray.length; x++) {
                userFriendService.deleteByUserIdAndFriendId(userFriend.getSelfUserId(), Long.parseLong(strArray[x]));
                User friend = userService.findOne(Long.parseLong(strArray[x]));
                try {
                    //操作记录
                    UserEvent userEvent = new UserEvent();
                    userEvent.setUser(user);
                    userEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                    userEvent.setType(9);
                    userEvent.setIp(InetAddress.getLocalHost().getHostAddress());
                    userEvent.setRemark(self.getName() + remarkDict.DEL + remarkDict.FRIEND + friend.getName());
                    userEventService.save(userEvent);
                    //企业添加好友
                    enterpriseEventService.saveFriendEventLog(user,self,9,friend.getAccount());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

            }
        } else {
            return error("非法操作！");
        }
        return success(null);

    }
}
