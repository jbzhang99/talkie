package com.meta.sync.sync2busi.usergroup;

import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;

import com.cloud.sync2busiaop.intersector.HandlerIntersector;
import com.cloud.sync2busiaop.vo.usergroup.UserGroupDeleteByUserIdAndGroupIdVO;
import com.meta.Result;
import com.meta.model.group.Group;
import com.meta.model.user.User;
import com.meta.model.user.UserGroup;
import com.meta.redissync.group.RedisSyncGroup;
import com.meta.redissync.user.RedisSyncUser;
import com.meta.service.group.GroupService;
import com.meta.service.terminal.TerminalService;
import com.meta.service.user.UserService;
import com.meta.sync.sync2busi.notify.Sync2Busi;
import com.meta.sync.syncconvert.group.SyncConvertGroup;
import com.meta.sync.syncconvert.user.SyncConvertUser;
import com.meta.sync.utils.SyncUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserGroupDeleteByUserIdAndGroupId extends BaseComponent implements HandlerIntersector<UserGroupDeleteByUserIdAndGroupIdVO> {
    
    @Autowired
    RedisSyncUser redisUser;
    @Autowired
    RedisSyncGroup redisGroup;
    @Autowired
    UserService userService;
    @Autowired
    GroupService groupService;
    @Autowired
    TerminalService terminalService;
    @Autowired
    Sync2Busi sync2Busi;
    @Autowired
    SyncConvertUser syncConvertUser;
    @Autowired
    SyncConvertGroup syncConvertGroup;
    @Autowired
    private SyncUtils syncUtils;

    private List<Long> listGroupIdBefore;
    private List<Long> listUserIdBefore;

    @Override
    public String getClassName() {
        return UserGroupDeleteByUserIdAndGroupIdVO.class.getName();
    }

    @Override
    public boolean beforeHandle(UserGroupDeleteByUserIdAndGroupIdVO vo) {
        // args:["35","1","2"]
        // args:["35","2,3","2"]
        // args:["7","1","1"]
        // args:["11,13","1","1"]
        log.info("SYNC", "usergroup deleteByUserIdAndGroupId, args:" + JSON.toJSONString(vo.getArgObj()));
        Object[] args = (Object[]) vo.getArgObj();
        String userIds = (String) args[0];
        String groupIds = (String) args[1];
        listGroupIdBefore = new ArrayList<>();
        listUserIdBefore = new ArrayList<>();
        for (String userId : userIds.split(",")) {
            listUserIdBefore.add(Long.valueOf(userId));
        }
        for (String groupId : groupIds.split(",")) {
            listGroupIdBefore.add(Long.valueOf(groupId));
        }
        return false;
    }

    @Override
    public boolean afterHandle(UserGroupDeleteByUserIdAndGroupIdVO vo) {
        // ret:{"currPage":1,"detailModelList":[],"pageSize":15,"successFlg":true,"totalCount":0,"totalPage":0}
        Object[] args = (Object[]) vo.getArgObj();
        log.info("SYNC", "usergroup deleteByUserIdAndGroupId, ret:" + JSON.toJSONString(vo.getRetObj()));
        Result result = (Result<UserGroup>) vo.getRetObj();
        if (true == result.isSuccessFlg()) {
            for (Long userId : listUserIdBefore) {
                User user = syncUtils.findUserWithTerminalByUserId(userId);
                log.info("SYNC", "userID:" + userId + " ret:" + JSON.toJSONString(user));
                if (null == user || null == user.getTerminal()) {
                    continue;
                }

                List<Group> listUserGroup = groupService.findAlreadyUser(userId);
                redisUser.putUserGroupListByTerminalId(user.getTerminal().getId(), syncConvertGroup.convertGroupList(listUserGroup));
            }

            for (Long groupId : listGroupIdBefore) {
                List<User> listGroupUser = userService.findAlreadyUserGroup(groupId);
                List<User> listGroupUserWithTerminal = new ArrayList<>();
                for (User user : listGroupUser) {
                    User dbUser = syncUtils.findUserWithTerminalByUserId(user.getId());
                    if (dbUser.getTerminal() == null) {
                        log.info("SYNC", "user from db has no terminal:" + JSON.toJSONString(dbUser));
                        continue;
                    }
                    listGroupUserWithTerminal.add(dbUser);
                }
                redisGroup.putGroupUserListById(groupId, syncConvertUser.convertUserList(listGroupUserWithTerminal));
            }

            sync2Busi.syncUserGroup(listUserIdBefore, listGroupIdBefore);
        }
        return false;
    }
}
