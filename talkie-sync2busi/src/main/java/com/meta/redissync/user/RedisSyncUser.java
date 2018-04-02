package com.meta.redissync.user;


import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;
import com.meta.convert.group.ConvertGroup;
import com.meta.convert.user.ConvertUser;
import com.meta.redissync.RedisSyncService;
import com.meta.sync.model.group.SyncGroup;
import com.meta.sync.model.user.SyncUser;
import com.tcloud.talkie.RedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisSyncUser extends BaseComponent {
    @Autowired
    RedisSyncService redisHolderService;
    @Autowired
    ConvertUser convertUser;
    @Autowired
    ConvertGroup convertGroup;

    /**
     * get/put/del by user id
     */
    public void putUserById(Long id, SyncUser syncUser) {
        com.tcloud.talkie.module.user.pojo.User oldUser = convertUser.convertNew2Old(syncUser);
        redisHolderService.putHashSet(RedisKeys.USER_ID_INFO, JSON.toJSONString(id), JSON.toJSONString(oldUser));
    }

    public com.tcloud.talkie.module.user.pojo.User getUserById(Long id) {
        String strUser = redisHolderService.getHashSet(RedisKeys.USER_ID_INFO, JSON.toJSONString(id));
        if(null == strUser) {
            return null;
        }
        com.tcloud.talkie.module.user.pojo.User user = JSON.parseObject(strUser, com.tcloud.talkie.module.user.pojo.User.class);
        return user;
    }

    public void delUserById(Long id) {
        redisHolderService.delHashSet(RedisKeys.USER_ID_INFO, JSON.toJSONString(id));
    }

    /**
     * get/put/del by user account
     */
    public void putUserByAccount(String account, SyncUser syncUser) {
        com.tcloud.talkie.module.user.pojo.User oldUser = convertUser.convertNew2Old(syncUser);
        redisHolderService.putHashSet(RedisKeys.USER_ACCOUNT_INFO, account, JSON.toJSONString(oldUser));
    }

    public com.tcloud.talkie.module.user.pojo.User getUserByAccount(String account) {
        String strUser = redisHolderService.getHashSet(RedisKeys.USER_ACCOUNT_INFO, account);
        if(null == strUser) {
            return null;
        }
        com.tcloud.talkie.module.user.pojo.User user = JSON.parseObject(strUser, com.tcloud.talkie.module.user.pojo.User.class);
        return user;
    }

    public void delUserByAccount(String account) {
        redisHolderService.delHashSet(RedisKeys.USER_ACCOUNT_INFO, account);
    }

    /**
     * get/put/del by user terminal id
     */
    public void putUserByTerminalId(Long id, SyncUser syncUser) {
        com.tcloud.talkie.module.user.pojo.User oldUser = convertUser.convertNew2Old(syncUser);
        redisHolderService.putHashSet(RedisKeys.TERMINAL_USER_INFO, JSON.toJSONString(id), JSON.toJSONString(oldUser));
    }

    public com.tcloud.talkie.module.user.pojo.User getUserByTerminalId(Long id) {
        String strUser = redisHolderService.getHashSet(RedisKeys.TERMINAL_USER_INFO, JSON.toJSONString(id));
        if(null == strUser) {
            return null;
        }
        com.tcloud.talkie.module.user.pojo.User user = JSON.parseObject(strUser, com.tcloud.talkie.module.user.pojo.User.class);
        return user;
    }

    public void delUserByTerminalId(Long id) {
        redisHolderService.delHashSet(RedisKeys.TERMINAL_USER_INFO, JSON.toJSONString(id));
    }

    /**
     * get/put/del user group list by terminal id
     */
    public void putUserGroupListByTerminalId(Long id, List<SyncGroup> listSyncGroup) {
        List<com.tcloud.talkie.module.group.pojo.Group> listOldGroup = convertGroup.convertNew2OldList(listSyncGroup);
        redisHolderService.putHashSet(RedisKeys.TERMINAL_ID_GROUPINFOLIST, JSON.toJSONString(id), JSON.toJSONString(listOldGroup));
    }

    public List<com.tcloud.talkie.module.group.pojo.Group> getUserGroupListByTerminalId(Long id) {
        String strValue = redisHolderService.getHashSet(RedisKeys.TERMINAL_ID_GROUPINFOLIST, JSON.toJSONString(id));
        if(null == strValue) {
            return null;
        }
        List<com.tcloud.talkie.module.group.pojo.Group> list = JSON.parseArray(strValue, com.tcloud.talkie.module.group.pojo.Group.class);
        return list;
    }

    public void delUserGroupListByTerminalId(Long id) {
        redisHolderService.delHashSet(RedisKeys.TERMINAL_ID_GROUPINFOLIST, JSON.toJSONString(id));
    }

}
