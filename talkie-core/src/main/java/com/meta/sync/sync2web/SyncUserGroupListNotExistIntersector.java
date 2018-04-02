package com.meta.sync.sync2web;

import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;

import com.cloud.talkie.data.sync.DataSyncIntersector;
import com.cloud.talkie.data.sync.vo.busi2web.SyncUserGroupListNotExist;
import com.meta.model.group.Group;
import com.meta.model.user.User;
import com.meta.redissync.user.RedisSyncUser;
import com.meta.service.group.GroupService;
import com.meta.service.user.UserService;
import com.meta.sync.sync2busi.notify.Sync2Busi;
import com.meta.sync.syncconvert.group.SyncConvertGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class SyncUserGroupListNotExistIntersector extends BaseComponent implements DataSyncIntersector<SyncUserGroupListNotExist> {
    

    @Autowired
    UserService userService;
    @Autowired
    GroupService groupService;
    @Autowired
    RedisSyncUser redisUser;
    @Autowired
    Sync2Busi sync2Busi;
    @Autowired
    SyncConvertGroup syncConvertGroup;

    @Override
    public String getSyncName() {
        return SyncUserGroupListNotExist.class.getName();
    }

    @Override
    public boolean beforeHandle(SyncUserGroupListNotExist req) {
        log.info("SYNC", "SyncUserGroupListNotExist:" + JSON.toJSONString(req));
        if (req.getTerminalId() != null) {
            User user = userService.searchByTerminal_Id(req.getTerminalId());
            if (null != user) {
                List<Group> listUserGroup = groupService.findAlreadyUser(user.getId());
                log.info("SYNC", "user:" + JSON.toJSONString(user) + " listUserGroup:" + JSON.toJSONString(listUserGroup));
                if (null != listUserGroup) {
                    redisUser.putUserGroupListByTerminalId(req.getTerminalId(), syncConvertGroup.convertGroupList(listUserGroup));
                    List<Long> listUserId = new ArrayList<>();
                    List<Long> listGroupId = new ArrayList<>();
                    listUserId.add(user.getId());
                    for (Group group : listUserGroup) {
                        listGroupId.add(group.getId());
                    }
                    sync2Busi.syncUserGroup(listUserId, listGroupId);
                }
            }
        }
        return false;
    }

    @Override
    public boolean afterHandle(SyncUserGroupListNotExist req) {

        return false;
    }

}
