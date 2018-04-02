package com.meta.redissync.friend;


import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;
import com.meta.convert.user.ConvertUser;
import com.meta.redissync.RedisSyncService;
import com.meta.sync.model.user.SyncUser;
import com.tcloud.talkie.RedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisSyncFriend extends BaseComponent {
    @Autowired
    RedisSyncService redisHolderService;
    @Autowired
    ConvertUser convertUser;

    /**
     * get/put/del friend user list by user id
     */
    public void putFriendUserListByUserId(Long id, List<SyncUser> listSyncUser) {
        List<com.tcloud.talkie.module.user.pojo.User> listOldUser = convertUser.convertNew2OldList(listSyncUser);
        redisHolderService.putHashSet(RedisKeys.USER_ID_FRIENDLIST, JSON.toJSONString(id), JSON.toJSONString(listOldUser));
    }

    public List<com.tcloud.talkie.module.user.pojo.User> getFriendUserListByUserId(Long id) {
        String strValue = redisHolderService.getHashSet(RedisKeys.USER_ID_FRIENDLIST, JSON.toJSONString(id));
        if(null == strValue) {
            return null;
        }
        List<com.tcloud.talkie.module.user.pojo.User> listUser = JSON.parseArray(strValue, com.tcloud.talkie.module.user.pojo.User.class);
        return listUser;
    }

    public void delFriendUserListByUserId(Long id) {
        redisHolderService.delHashSet(RedisKeys.USER_ID_FRIENDLIST, JSON.toJSONString(id));
    }

    /**
     * get/put/del friend user list by terminal id
     */
    public void putFriendUserListByTerminalId(Long id, List<SyncUser> listSyncUser) {
        List<com.tcloud.talkie.module.user.pojo.User> listOldUser = convertUser.convertNew2OldList(listSyncUser);
        redisHolderService.putHashSet(RedisKeys.TERMINAL_ID_FRIENDLIST, JSON.toJSONString(id), JSON.toJSONString(listOldUser));
    }

    public List<com.tcloud.talkie.module.user.pojo.User> getFriendUserListByTerminalId(Long id) {
        String strValue = redisHolderService.getHashSet(RedisKeys.TERMINAL_ID_FRIENDLIST, JSON.toJSONString(id));
        if(null == strValue) {
            return null;
        }
        List<com.tcloud.talkie.module.user.pojo.User> listUser = JSON.parseArray(strValue, com.tcloud.talkie.module.user.pojo.User.class);
        return listUser;
    }

    public void delFriendUserListByTerminalId(Long id) {
        redisHolderService.delHashSet(RedisKeys.TERMINAL_ID_FRIENDLIST, JSON.toJSONString(id));
    }


}
