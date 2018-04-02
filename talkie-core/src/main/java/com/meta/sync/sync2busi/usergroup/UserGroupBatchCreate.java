package com.meta.sync.sync2busi.usergroup;

import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;

import com.cloud.sync2busiaop.intersector.HandlerIntersector;
import com.cloud.sync2busiaop.vo.usergroup.UserGroupBatchCreateVO;
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
public class UserGroupBatchCreate extends BaseComponent implements HandlerIntersector<UserGroupBatchCreateVO> {
    
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
        return UserGroupBatchCreateVO.class.getName();
    }

    @Override
    public boolean beforeHandle(UserGroupBatchCreateVO userGroupBatchCreateVO) {
        // args:[{"currentUserId":4,"groupIdTemp":"1","type":"2","userId":35}]
        // args:[{"currentUserId":4,"groupIdTemp":"1,2,3","type":"2","userId":35}]
        // args:[{"currentUserId":4,"groupId":1,"type":"1","userIdTemp":"11,13"}]
        Object[] args = (Object[]) userGroupBatchCreateVO.getArgObj();
        log.info("SYNC", "usergroup batch create, args:" + JSON.toJSONString(args));
        UserGroup argUserGroup = (UserGroup) args[0];
        listGroupIdBefore = new ArrayList<>();
        listUserIdBefore = new ArrayList<>();
        if (argUserGroup.getType().equals("1")) {
            listGroupIdBefore.add(argUserGroup.getGroupId());
            String userIdTemp = argUserGroup.getUserIdTemp();
            for (String strUserId : userIdTemp.split(",")) {
                listUserIdBefore.add(Long.valueOf(strUserId));
            }
        } else if (argUserGroup.getType().equals("2")) {
            String groupIdTemp = argUserGroup.getGroupIdTemp();
            for (String strGroupId : groupIdTemp.split(",")) {
                Long groupId = Long.valueOf(strGroupId);
                listGroupIdBefore.add(groupId);
            }
            listUserIdBefore.add(argUserGroup.getUserId());
        }
        return false;
    }

    @Override
    public boolean afterHandle(UserGroupBatchCreateVO userGroupBatchCreateVO) {
        // ret:{"currPage":1,"detailModelList":[],"pageSize":15,"successFlg":true,"totalCount":0,"totalPage":0}
        log.info("SYNC", "usergroup batch create, ret:" + JSON.toJSONString(userGroupBatchCreateVO.getRetObj()));
        Result<UserGroup> result = (Result<UserGroup>) userGroupBatchCreateVO.getRetObj();
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
