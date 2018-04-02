package com.meta.sync.sync2web;

import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;

import com.cloud.talkie.data.sync.DataSyncIntersector;
import com.cloud.talkie.data.sync.vo.busi2web.SyncGroupNotExist;
import com.cloud.talkie.data.sync.vo.busi2web.SyncUserNotExist;
import com.meta.datetime.DateTimeUtil;
import com.meta.model.group.Group;
import com.meta.model.qmanage.QUser;
import com.meta.model.user.User;
import com.meta.redissync.friend.RedisSyncFriend;
import com.meta.redissync.group.RedisSyncGroup;
import com.meta.redissync.quser.RedisSyncQUser;
import com.meta.redissync.user.RedisSyncUser;
import com.meta.service.group.GroupService;
import com.meta.service.qmanage.QUserService;
import com.meta.service.user.UserFriendService;
import com.meta.service.user.UserService;
import com.meta.sync.sync2busi.notify.Sync2Busi;
import com.meta.sync.syncconvert.group.SyncConvertGroup;
import com.meta.sync.syncconvert.quser.SyncConvertQUser;
import com.meta.sync.syncconvert.user.SyncConvertUser;
import com.meta.sync.utils.SyncUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class SyncGroupNotExistIntersector extends BaseComponent implements DataSyncIntersector<SyncGroupNotExist> {
    
    @Autowired
    GroupService groupService;
    @Autowired
    RedisSyncGroup redisGroup;
    @Autowired
    Sync2Busi sync2Busi;
    @Autowired
    SyncConvertGroup syncConvertGroup;
    @Autowired
    private SyncUtils syncUtils;

    @Override
    public String getSyncName() {
        return SyncGroupNotExist.class.getName();
    }

    @Override
    public boolean beforeHandle(SyncGroupNotExist req) {
        log.info("SYNC", "SyncGroupNotExist:" + JSON.toJSONString(req));
        Long groupId = req.getGroupId();
        Group group = null;
        if(null != groupId) {
            group = groupService.findOne(groupId);
        }
        log.info("SYNC", "group:" + JSON.toJSONString(group));

        if (null != group) {
            redisGroup.putGroupById(group.getId(), syncConvertGroup.convertGroup(group));
            sync2Busi.syncGroup(group.getId());

        }

        return false;
    }

    @Override
    public boolean afterHandle(SyncGroupNotExist req) {

        return false;
    }

}
