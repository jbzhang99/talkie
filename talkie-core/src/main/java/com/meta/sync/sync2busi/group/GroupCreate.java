package com.meta.sync.sync2busi.group;

import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;

import com.cloud.sync2busiaop.intersector.HandlerIntersector;
import com.cloud.sync2busiaop.vo.group.GroupCreateVO;
import com.meta.Result;
import com.meta.model.group.Group;
import com.meta.model.user.User;
import com.meta.redissync.group.RedisSyncGroup;
import com.meta.redissync.user.RedisSyncUser;
import com.meta.service.group.GroupService;
import com.meta.service.user.UserService;
import com.meta.sync.sync2busi.notify.Sync2Busi;
import com.meta.sync.syncconvert.group.SyncConvertGroup;
import com.meta.sync.utils.SyncUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupCreate extends BaseComponent implements HandlerIntersector<GroupCreateVO> {
    
    @Autowired
    RedisSyncGroup redisGroup;
    @Autowired
    RedisSyncUser redisUser;
    @Autowired
    Sync2Busi sync2Busi;
    @Autowired
    SyncConvertGroup syncConvertGroup;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private SyncUtils syncUtils;

    @Override
    public String getClassName() {
        return GroupCreateVO.class.getName();
    }

    @Override
    public boolean beforeHandle(GroupCreateVO groupCreateVO) {
        // args:[{"currentUserId":4,"level":"1","name":"qunzu05","phone":"15687978677","status":"1","type":2}]
        log.info("SYNC", "group create, args:" + JSON.toJSONString(groupCreateVO.getArgObj()));
        try {

        } catch(Exception e) {
            log.info("SYNC", "group create, exception:" + e);
        } finally {
            
        }
        return false;
    }

    @Override
    public boolean afterHandle(GroupCreateVO groupCreateVO) {
        Object[] args = (Object[]) groupCreateVO.getArgObj();
        Group argGroup = (Group) args[0];
        log.info("SYNC", "group create, ret:" + JSON.toJSONString(groupCreateVO.getRetObj()));
        Result<Group> result = (Result<Group>) groupCreateVO.getRetObj();
        if (result.isSuccessFlg()) {
            Group groupRet = (Group) result.getObj();
            Group group = groupService.findOne(groupRet.getId());
            log.info("SYNC", "group create, db group:" + JSON.toJSONString(group));
            redisGroup.putGroupById(group.getId(), syncConvertGroup.convertGroup(group));
            sync2Busi.syncGroup(group.getId());

            List<Long> listUserId = new ArrayList<>();
            List<User> listGroupUser = userService.findAlreadyUserGroup(group.getId());
            if (CollectionUtils.isNotEmpty(listGroupUser)) {
                for (User user : listGroupUser) {
                    listUserId.add(user.getId());
                    User userWithTerminal = syncUtils.findUserWithTerminalByUserId(user.getId());
                    if (null != userWithTerminal.getTerminal()) {
                        List<Group> listUserGroup = groupService.findAlreadyUser(userWithTerminal.getId());
                        redisUser.putUserGroupListByTerminalId(userWithTerminal.getTerminal().getId(), syncConvertGroup.convertGroupList(listUserGroup));
                    }
                }
            }
            sync2Busi.syncUserGroup(listUserId, null);
        }
        return false;
    }
}
