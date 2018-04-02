package com.meta.sync.sync2web;

import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;

import com.cloud.talkie.data.sync.DataSyncIntersector;
import com.cloud.talkie.data.sync.vo.busi2web.SyncFriendListNotExist;
import com.meta.model.user.User;
import com.meta.redissync.friend.RedisSyncFriend;
import com.meta.service.user.UserFriendService;
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
public class SyncFriendListNotExistIntersector extends BaseComponent implements DataSyncIntersector<SyncFriendListNotExist> {
    

    @Autowired
    UserService userService;
    @Autowired
    UserFriendService userFriendService;
    @Autowired
    RedisSyncFriend redisFriend;
    @Autowired
    Sync2Busi sync2Busi;
    @Autowired
    SyncConvertUser syncConvertUser;

    @Override
    public String getSyncName() {
        return SyncFriendListNotExist.class.getName();
    }

    @Override
    public boolean beforeHandle(SyncFriendListNotExist req) {
        log.info("SYNC", "SyncFriendListNotExist:" + JSON.toJSONString(req));

        if (req.getTerminalId() != null) {
            User user = userService.searchByTerminal_Id(req.getTerminalId());
            if (null != user) {
                List<User> listFriend = userFriendService.getFriendUserList(user.getId());
                redisFriend.putFriendUserListByUserId(user.getId(), syncConvertUser.convertUserList(listFriend));
                redisFriend.putFriendUserListByTerminalId(req.getTerminalId(), syncConvertUser.convertUserList(listFriend));

                List<Long> listTerminalId = new ArrayList<>();
                listTerminalId.add(req.getTerminalId());
                sync2Busi.syncUserFriend(listTerminalId);
            }

        }
        return false;
    }

    @Override
    public boolean afterHandle(SyncFriendListNotExist req) {

        return false;
    }

}
