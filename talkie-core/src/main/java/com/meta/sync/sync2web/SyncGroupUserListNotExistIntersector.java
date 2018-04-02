package com.meta.sync.sync2web;

import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;

import com.cloud.talkie.data.sync.DataSyncIntersector;
import com.cloud.talkie.data.sync.vo.busi2web.SyncGroupUserListNotExist;
import com.meta.model.user.User;
import com.meta.redissync.group.RedisSyncGroup;
import com.meta.service.user.UserService;
import com.meta.sync.sync2busi.notify.Sync2Busi;
import com.meta.sync.syncconvert.user.SyncConvertUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class SyncGroupUserListNotExistIntersector extends BaseComponent implements DataSyncIntersector<SyncGroupUserListNotExist> {
    

    @Autowired
    UserService userService;
    @Autowired
    RedisSyncGroup redisGroup;
    @Autowired
    Sync2Busi sync2Busi;
    @Autowired
    SyncConvertUser syncConvertUser;

    @Override
    public String getSyncName() {
        return SyncGroupUserListNotExist.class.getName();
    }

    @Override
    public boolean beforeHandle(SyncGroupUserListNotExist req) {
        log.info("SYNC", "SyncGroupUserListNotExist:" + JSON.toJSONString(req));

        if (req.getGroupId() != null) {
            List<User> listGroupUser = userService.findAlreadyUserGroup(req.getGroupId());
            if (null != listGroupUser) {
                redisGroup.putGroupUserListById(req.getGroupId(), syncConvertUser.convertUserList(listGroupUser));

                List<Long> listUserId = new ArrayList<>();
                for (User user : listGroupUser) {
                    listUserId.add(user.getId());
                }
                List<Long> listGroupId = new ArrayList<>();
                listGroupId.add(req.getGroupId());

                sync2Busi.syncUserGroup(listUserId, listGroupId);

            }
        }
        return false;
    }

    @Override
    public boolean afterHandle(SyncGroupUserListNotExist req) {

        return false;
    }

}
