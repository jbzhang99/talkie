package com.meta.sync.sync2busi.user;

import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;

import com.cloud.sync2busiaop.intersector.HandlerIntersector;
import com.cloud.sync2busiaop.vo.user.UserCreateVO;
import com.meta.Result;
import com.meta.model.group.Group;
import com.meta.model.user.User;
import com.meta.redissync.friend.RedisSyncFriend;
import com.meta.redissync.group.RedisSyncGroup;
import com.meta.redissync.user.RedisSyncUser;
import com.meta.service.group.GroupService;
import com.meta.service.terminal.TerminalService;
import com.meta.service.user.UserFriendService;
import com.meta.service.user.UserService;
import com.meta.sync.sync2busi.notify.Sync2Busi;
import com.meta.sync.syncconvert.user.SyncConvertUser;
import com.meta.sync.utils.SyncUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UserCreate extends BaseComponent implements HandlerIntersector<UserCreateVO> {
    
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
    private UserFriendService userFriendService;
    @Autowired
    private TerminalService terminalService;
    @Autowired
    private SyncUtils syncUtils;
    private User userBeforeModify;

    @Override
    public String getClassName() {
        return UserCreateVO.class.getName();
    }

    @Override
    public boolean beforeHandle(UserCreateVO userCreateVO) {
        Object[] args = (Object[]) userCreateVO.getArgObj();
        User user = (User) args[0];
        user = userService.findByAccount(user.getAccount());
        userBeforeModify = new User();
        try {
            BeanUtils.copyProperties(userBeforeModify, user);
        } catch (Exception e) {
            userBeforeModify = null;
        }

        return false;
    }

    @Override
    public boolean afterHandle(UserCreateVO userCreateVO) {
        Result<User> result = (Result<User>) userCreateVO.getRetObj();
        Object[] args = (Object[]) userCreateVO.getArgObj();
        User user = (User) args[0];
        User userWithTerminal = syncUtils.findUserWithTerminalByUserId(user.getId());
        log.info("SYNC", "userWithTerminal:" + JSON.toJSONString(userWithTerminal));
        redisUser.putUserById(user.getId(), syncConvertUser.convertUser(userWithTerminal));

        if (null != userWithTerminal.getAccount()) {
            redisUser.putUserByAccount(userWithTerminal.getAccount(), syncConvertUser.convertUser(userWithTerminal));
        }

        if (null != userWithTerminal.getTerminal()) {
            redisUser.putUserByTerminalId(userWithTerminal.getTerminal().getId(), syncConvertUser.convertUser(userWithTerminal));
        }

        if (null == userBeforeModify) {
            sync2Busi.syncUser(userWithTerminal, false, false);
        } else {
            boolean bNameChanged = false;
            boolean bFuncChanged = false;
            if (0 != userBeforeModify.getName().compareTo(userWithTerminal.getName())) {
                bNameChanged = true;
            }

            String[] funcsBefore = null;
            String[] funcsNow = null;
            if (userBeforeModify.getFuncs() != null) {
                funcsBefore = userBeforeModify.getFuncs().split(",");
                Arrays.sort(funcsBefore);
            }
            if (user.getFuncs() != null) {
                funcsNow = user.getFuncs().split(",");
                Arrays.sort(funcsNow);
            }

            if (funcsBefore == null && funcsNow != null ||
                    funcsBefore != null && funcsNow == null) {
                bFuncChanged = true;
            } else {
                if (funcsBefore.length != funcsNow.length) {
                    bFuncChanged = true;
                } else {
                    if (!Arrays.equals(funcsBefore, funcsNow)) {
                        bFuncChanged = true;
                    }
                }

            }

            sync2Busi.syncUser(userWithTerminal, bNameChanged, bFuncChanged);

            if (bNameChanged) {
                updateToGroupUserList(userWithTerminal.getId());
                updateToFriendUserList(userWithTerminal.getId());
            }
        }

        return false;
    }


    public void updateToGroupUserList(Long userId) {
        List<Group> listUserGroup = groupService.findAlreadyUser(userId);
        if (CollectionUtils.isEmpty(listUserGroup)) {
            return;
        }

        List<Long> listGroupId = new ArrayList<Long>();
        // 所在群组，更新列表同步用户名
        for (Group group : listUserGroup) {
            listGroupId.add(group.getId());
            List<User> listGroupUser = userService.findAlreadyUserGroup(group.getId());
            List<User> listGroupUserWithTerminal = new ArrayList<>();
            for (User user : listGroupUser) {
                User userWithTerminal = userService.findOne(user.getId());
                listGroupUserWithTerminal.add(userWithTerminal);
            }
            redisGroup.putGroupUserListById(group.getId(), syncConvertUser.convertUserList(listGroupUserWithTerminal));
        }

        sync2Busi.syncUserGroup(null, listGroupId);
    }

    public void updateToFriendUserList(Long userId) {
        List<Long> listTerminalId = new ArrayList<>();
        List<User> listFriendUser = userFriendService.getFriendUserList(userId);
        if (CollectionUtils.isEmpty(listFriendUser)) {
            return;
        }
        // 更新好友的好友列表
        for (User fr : listFriendUser) {
            List<User> listHisFriend = userFriendService.getFriendUserList(fr.getId());
            redisFriend.putFriendUserListByUserId(fr.getId(), syncConvertUser.convertUserList(listHisFriend));
            if (fr.getTerminal() != null) {
                redisFriend.putFriendUserListByTerminalId(fr.getTerminal().getId(), syncConvertUser.convertUserList(listHisFriend));
                listTerminalId.add(fr.getTerminal().getId());
            }
        }
        sync2Busi.syncUserFriend(listTerminalId);
    }

}
