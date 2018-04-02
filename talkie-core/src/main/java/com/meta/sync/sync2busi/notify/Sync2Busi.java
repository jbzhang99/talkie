package com.meta.sync.sync2busi.notify;

import com.cloud.talkie.data.op.DataCacheOp;
import com.cloud.talkie.data.sync.vo.web2busi.*;
import com.meta.convert.user.ConvertUser;
import com.meta.model.group.Group;
import com.meta.model.user.User;
import com.meta.redissync.RedisSyncService;
import com.meta.sync.syncconvert.user.SyncConvertUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Sync2Busi {
    @Autowired
    ConvertUser convertUser;
    @Autowired
    SyncConvertUser syncConvertUser;
    @Autowired
    RedisSyncService redisHolderService;

    public void syncUser(User user, boolean bNameChanged, boolean bFuncChanged) {
        com.tcloud.talkie.module.user.pojo.User oldUser = convertUser.convertNew2Old(syncConvertUser.convertUser(user));
        DataCacheOp<String, Long> op = new DataCacheOp<String, Long>();
        SyncUser data = new SyncUser();
        data.setUser(oldUser);
        data.setNameChanged(bNameChanged);
        data.setFuncChanged(bFuncChanged);
        op.syncData(data, redisHolderService.getBusiSvrSyncQueueName());
    }

    public void syncUserDelete(User user,
                               List<Group> listGroupBeforeDel,
                               List<User> listFriendBeforeDel) {
        com.tcloud.talkie.module.user.pojo.User oldUser = convertUser.convertNew2Old(syncConvertUser.convertUser(user));
        DataCacheOp<String, Long> op = new DataCacheOp<String, Long>();
        SyncUser syncUser = new SyncUser();
        syncUser.setUser(oldUser);
        syncUser.setNameChanged(false);
        syncUser.setFuncChanged(false);
        op.syncData(syncUser, redisHolderService.getBusiSvrSyncQueueName());

        SyncGroupInfoAndList syncGroupInfoAndList = new SyncGroupInfoAndList();
        List<Long> listGroupId = new ArrayList<Long>();
        for (Group gp : listGroupBeforeDel) {
            listGroupId.add(gp.getId());
        }
        syncGroupInfoAndList.setListGroupId(listGroupId);
        List<Long> listUserId = new ArrayList<Long>();
        listUserId.add(user.getId());
        syncGroupInfoAndList.setListUserId(listUserId);
        op.syncData(syncGroupInfoAndList, redisHolderService.getBusiSvrSyncQueueName());

        SyncFriendList syncFriendList = new SyncFriendList();
        List<Long> listTerminalId = new ArrayList<Long>();
        if (user.getTerminal() != null && user.getTerminal().getId() != null) {
            listTerminalId.add(user.getTerminal().getId());
        }
        for (User usr : listFriendBeforeDel) {
            if (usr.getTerminal() != null && usr.getTerminal().getId() != null) {
                listTerminalId.add(usr.getTerminal().getId());
            }
        }
        syncFriendList.setListTerminalId(listTerminalId);
        op.syncData(syncFriendList, redisHolderService.getBusiSvrSyncQueueName());
    }

    public void syncGroupDelete(Long groupId, List<User> listGroupUserBeforeDel) {
        DataCacheOp<String, Long> op = new DataCacheOp<String, Long>();
        SyncGroupInfoAndList syncGroupInfoAndList = new SyncGroupInfoAndList();
        List<Long> listGroupId = new ArrayList<Long>();
        listGroupId.add(groupId);
        syncGroupInfoAndList.setListGroupId(listGroupId);
        List<Long> listUserId = new ArrayList<Long>();
        for (User usr : listGroupUserBeforeDel) {
            listUserId.add(usr.getId());
        }
        syncGroupInfoAndList.setListUserId(listUserId);
        op.syncData(syncGroupInfoAndList, redisHolderService.getBusiSvrSyncQueueName());
    }

    public void syncUserGroup(List<Long> listUserIdBefore, List<Long> listGroupIdBefore) {
        DataCacheOp<String, Long> op = new DataCacheOp<String, Long>();
        SyncGroupInfoAndList syncGroupInfoAndList = new SyncGroupInfoAndList();

        syncGroupInfoAndList.setListGroupId(listGroupIdBefore);
        syncGroupInfoAndList.setListUserId(listUserIdBefore);

        op.syncData(syncGroupInfoAndList, redisHolderService.getBusiSvrSyncQueueName());

    }

    public void syncUserFriend(List<Long> listTerminalId) {
        DataCacheOp<String, Long> op = new DataCacheOp<String, Long>();
        SyncFriendList data = new SyncFriendList();
        data.setListTerminalId(listTerminalId);
        op.syncData(data, redisHolderService.getBusiSvrSyncQueueName());

    }

    public void syncQUserModify(Long terminalId) {

        if (null != terminalId) {
            DataCacheOp<String, Long> op = new DataCacheOp<String, Long>();
            SyncTerminalMoney data = new SyncTerminalMoney();
            data.setTerminalId(terminalId);
            op.syncData(data, redisHolderService.getBusiSvrSyncQueueName());
        }
    }

    public void syncGroup(Long groupId) {

        if (null != groupId) {
            DataCacheOp<String, Long> op = new DataCacheOp<String, Long>();
            SyncGroupVO data = new SyncGroupVO();
            data.setGroupId(groupId);
            op.syncData(data, redisHolderService.getBusiSvrSyncQueueName());
        }
    }
}
