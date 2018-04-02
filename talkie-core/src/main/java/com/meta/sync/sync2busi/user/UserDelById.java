package com.meta.sync.sync2busi.user;

import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;

import com.cloud.sync2busiaop.intersector.HandlerIntersector;
import com.cloud.sync2busiaop.vo.user.UserDelByIdVO;
import com.meta.model.group.Group;
import com.meta.model.terminal.Terminal;
import com.meta.model.user.User;
import com.meta.redissync.friend.RedisSyncFriend;
import com.meta.redissync.group.RedisSyncGroup;
import com.meta.redissync.user.RedisSyncUser;
import com.meta.service.group.GroupService;
import com.meta.service.terminal.TerminalService;
import com.meta.service.user.UserService;
import com.meta.sync.sync2busi.notify.Sync2Busi;
import com.meta.sync.syncconvert.user.SyncConvertUser;
import com.meta.sync.utils.SyncUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDelById extends BaseComponent implements HandlerIntersector<UserDelByIdVO> {
    
    @Autowired
    RedisSyncUser redisUser;
    @Autowired
    RedisSyncGroup redisGroup;
    @Autowired
    RedisSyncFriend redisFriend;
    @Autowired
    Sync2Busi sync2Busi;
    @Autowired
    SyncConvertUser syncConvertUser;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private TerminalService terminalService;
    @Autowired
    private SyncUtils syncUtils;
    private User userBeforeDel;
    private Terminal terminalBeforeDel;
    private List<Group> listGroupBeforeDel;
    private List<User> listFriendBeforeDel;

    @Override
    public String getClassName() {
        return UserDelByIdVO.class.getName();
    }

    @Override
    public boolean beforeHandle(UserDelByIdVO userDelByIdVO) {
        Object[] args = (Object[]) userDelByIdVO.getArgObj();
        Long id = (Long) args[0];
        userBeforeDel = syncUtils.findUserWithTerminalByUserId(id);
        log.info("SYNC", "user del by id, db user:" + JSON.toJSONString(userBeforeDel));
        if (null != userBeforeDel) {
            terminalBeforeDel = terminalService.searchByUser_Account(userBeforeDel.getAccount());
            listGroupBeforeDel = groupService.findAlreadyUser(userBeforeDel.getId());
            listFriendBeforeDel = userService.findAlreadyAddFriend(userBeforeDel.getId());
        }
        return false;
    }

    @Override
    public boolean afterHandle(UserDelByIdVO userDelByIdVO) {
        Object[] args = (Object[]) userDelByIdVO.getArgObj();
        log.info("SYNC", "user del by id, args:" + JSON.toJSONString(args));
        Long id = (Long) args[0];
        User user = syncUtils.findUserWithTerminalByUserId(id);
        log.info("SYNC", "user del by id, db user:" + JSON.toJSONString(user));

        if (null == user) {
            // del success
            if (null != userBeforeDel) {
                redisUser.delUserByAccount(userBeforeDel.getAccount());
                redisUser.delUserById(userBeforeDel.getId());
            }
            if (null != terminalBeforeDel) {
                redisUser.delUserByTerminalId(terminalBeforeDel.getId());
                redisUser.delUserGroupListByTerminalId(terminalBeforeDel.getId());
            }
        }

        if (null != listGroupBeforeDel) {
            for (Group gp : listGroupBeforeDel) {
                List<User> listGroupUser = userService.findAlreadyUserGroup(gp.getId());
                redisGroup.putGroupUserListById(gp.getId(), syncConvertUser.convertUserList(listGroupUser));
            }
        }

        if (null != listFriendBeforeDel) {
            // update friend list
            for (User usr : listFriendBeforeDel) {
                List<User> listFriendUser = userService.findAlreadyAddFriend(usr.getId());
                redisFriend.putFriendUserListByUserId(usr.getId(), syncConvertUser.convertUserList(listFriendUser));
                if (null != usr.getTerminal() && null != usr.getTerminal().getId()) {
                    redisFriend.putFriendUserListByTerminalId(usr.getTerminal().getId(), syncConvertUser.convertUserList(listFriendUser));
                }
            }
        }

        // notify to busi server
        sync2Busi.syncUserDelete(userBeforeDel,
                listGroupBeforeDel,
                listFriendBeforeDel);

        return false;
    }
}
