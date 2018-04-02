package com.meta.sync.sync2busi.userfriend;

import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;

import com.cloud.sync2busiaop.intersector.HandlerIntersector;
import com.cloud.sync2busiaop.vo.userfriend.UserFriendBatchAddOrDelVO;
import com.meta.model.user.User;
import com.meta.model.user.UserFriend;
import com.meta.redissync.friend.RedisSyncFriend;
import com.meta.service.user.UserFriendService;
import com.meta.service.user.UserService;
import com.meta.sync.sync2busi.notify.Sync2Busi;
import com.meta.sync.syncconvert.user.SyncConvertUser;
import com.meta.sync.utils.SyncUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserFriendBatchAddOrDel extends BaseComponent implements HandlerIntersector<UserFriendBatchAddOrDelVO> {
    
    @Autowired
    RedisSyncFriend redisFriend;
    @Autowired
    Sync2Busi sync2Busi;
    @Autowired
    SyncConvertUser syncConvertUser;
    @Autowired
    private UserFriendService userFriendService;
    @Autowired
    private UserService userService;
    @Autowired
    private SyncUtils syncUtils;

    @Override
    public String getClassName() {
        return UserFriendBatchAddOrDelVO.class.getName();
    }

    @Override
    public boolean beforeHandle(UserFriendBatchAddOrDelVO userFriendBatchAddOrDelVO) {
        // args:[{"currentUserId":4,"friendUserIDTemp":"15,14,13","selfUserId":35,"type":"1"}]
        // args:[{"currentUserId":4,"friendUserIDTemp":"8,11,13,9","selfUserId":35,"type":"2"}]
        log.info("SYNC", "userfriend batch add or del, args:" + JSON.toJSONString(userFriendBatchAddOrDelVO.getArgObj()));

        return false;
    }

    @Override
    public boolean afterHandle(UserFriendBatchAddOrDelVO userFriendBatchAddOrDelVO) {
        Object[] args = (Object[]) userFriendBatchAddOrDelVO.getArgObj();
        UserFriend argUserFriend = (UserFriend) args[0];
        log.info("SYNC", "userfriend batch add or del, ret:" + JSON.toJSONString(userFriendBatchAddOrDelVO.getRetObj()));
        List<Long> listUserIdAffected = new ArrayList<>();
        List<Long> listTerminalId = new ArrayList<>();
        listUserIdAffected.add(Long.valueOf(argUserFriend.getSelfUserId()));
        for (String friendId : argUserFriend.getFriendUserIDTemp().split(",")) {
            listUserIdAffected.add(Long.valueOf(friendId));
        }

        for (Long userId : listUserIdAffected) {
            List<User> listUser = userFriendService.getFriendUserList(userId);
            redisFriend.putFriendUserListByUserId(userId, syncConvertUser.convertUserList(listUser));
            User user = syncUtils.findUserWithTerminalByUserId(userId);
            if (user.getTerminal() != null) {
                redisFriend.putFriendUserListByTerminalId(user.getTerminal().getId(), syncConvertUser.convertUserList(listUser));
                listTerminalId.add(user.getTerminal().getId());
            }
        }

        sync2Busi.syncUserFriend(listTerminalId);

        return false;
    }
}
