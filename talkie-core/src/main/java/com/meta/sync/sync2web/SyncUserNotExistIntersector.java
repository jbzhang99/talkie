package com.meta.sync.sync2web;

import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;

import com.cloud.talkie.data.sync.DataSyncIntersector;
import com.cloud.talkie.data.sync.vo.busi2web.SyncUserNotExist;
import com.meta.datetime.DateTimeUtil;
import com.meta.model.qmanage.QUser;
import com.meta.model.user.User;
import com.meta.redissync.friend.RedisSyncFriend;
import com.meta.redissync.quser.RedisSyncQUser;
import com.meta.redissync.user.RedisSyncUser;
import com.meta.service.qmanage.QUserService;
import com.meta.service.user.UserFriendService;
import com.meta.service.user.UserService;
import com.meta.sync.sync2busi.notify.Sync2Busi;
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
public class SyncUserNotExistIntersector extends BaseComponent implements DataSyncIntersector<SyncUserNotExist> {
    

    @Autowired
    UserService userService;
    @Autowired
    QUserService qUserService;
    @Autowired
    UserFriendService userFriendService;
    @Autowired
    RedisSyncUser redisUser;
    @Autowired
    RedisSyncQUser redisQUser;
    @Autowired
    RedisSyncFriend redisFriend;
    @Autowired
    Sync2Busi sync2Busi;
    @Autowired
    SyncConvertUser syncConvertUser;
    @Autowired
    SyncConvertQUser syncConvertQUser;
    @Autowired
    private SyncUtils syncUtils;

    @Override
    public String getSyncName() {
        return SyncUserNotExist.class.getName();
    }

    @Override
    public boolean beforeHandle(SyncUserNotExist req) {
        log.info("SYNC", "SyncUserNotExist:" + JSON.toJSONString(req));
        User user = null;
        if (req.getTerminalId() != null) {
            user = userService.searchByTerminal_Id(req.getTerminalId());
        } else if (req.getUserId() != null) {
            user = syncUtils.findUserWithTerminalByUserId(req.getUserId());
        } else if (req.getAcount() != null) {
            user = userService.findByAccount(req.getAcount());
        }
        log.info("SYNC", "terminal user:" + JSON.toJSONString(user));

        if (null != user) {
            redisUser.putUserById(user.getId(), syncConvertUser.convertUser(user));
            redisUser.putUserByAccount(user.getAccount(), syncConvertUser.convertUser(user));
            if (user.getTerminal() != null) {
                redisUser.putUserByTerminalId(user.getTerminal().getId(), syncConvertUser.convertUser(user));
            }
            sync2Busi.syncUser(user, false, false);

            List<User> listFriend = userFriendService.getFriendUserList(user.getId());
            redisFriend.putFriendUserListByUserId(user.getId(), syncConvertUser.convertUserList(listFriend));
            if (null != user.getTerminal()) {
                redisFriend.putFriendUserListByTerminalId(user.getTerminal().getId(), syncConvertUser.convertUserList(listFriend));

                List<Long> listTerminalId = new ArrayList<>();
                listTerminalId.add(user.getTerminal().getId());
                sync2Busi.syncUserFriend(listTerminalId);
            }

            List<QUser> result = qUserService.search("EQ_user.id=" + user.getId());
            redisQUser.putQUserById(user.getId(), syncConvertQUser.convertQUser(result.get(0)));
            if (user.getTerminal() != null) {
                try {
                    redisQUser.putQUserByTerminalId(user.getTerminal().getId(), syncConvertQUser.convertQUser(result.get(0)));
                    redisQUser.putQDateByTerminalId(user.getTerminal().getId(), DateTimeUtil.simpleDateTimeParse(result.get(0).getModifyDate()));
                    sync2Busi.syncQUserModify(user.getTerminal().getId());
                } catch (ParseException e) {
                }
            }

        }

        return false;
    }

    @Override
    public boolean afterHandle(SyncUserNotExist req) {

        return false;
    }

}
