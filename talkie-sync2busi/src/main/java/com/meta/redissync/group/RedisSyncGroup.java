package com.meta.redissync.group;


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
public class RedisSyncGroup extends BaseComponent {
    @Autowired
    RedisSyncService redisHolderService;
    @Autowired
    ConvertGroup convertGroup;
    @Autowired
    ConvertUser convertUser;

    /**
     * get/put/del group by id
     */
    public void putGroupById(Long id, SyncGroup syncGroup) {
        com.tcloud.talkie.module.group.pojo.Group oldGroup = convertGroup.convertNew2Old(syncGroup);
        redisHolderService.putHashSet(RedisKeys.GROUP_ID_INFO, JSON.toJSONString(id), JSON.toJSONString(oldGroup));
    }

    public com.tcloud.talkie.module.group.pojo.Group getGroupById(Long id) {
        String strValue = redisHolderService.getHashSet(RedisKeys.GROUP_ID_INFO, JSON.toJSONString(id));
        if(null == strValue) {
            return null;
        }
        com.tcloud.talkie.module.group.pojo.Group group = JSON.parseObject(strValue, com.tcloud.talkie.module.group.pojo.Group.class);
        return group;
    }

    public void delGroupById(Long id) {
        redisHolderService.delHashSet(RedisKeys.GROUP_ID_INFO, JSON.toJSONString(id));
    }

    /**
     * get/put/del group user list by group id
     */
    public void putGroupUserListById(Long id, List<SyncUser> listSyncUser) {
        List<com.tcloud.talkie.module.user.pojo.User> listOldUser = convertUser.convertNew2OldList(listSyncUser);
        redisHolderService.putHashSet(RedisKeys.GROUP_ID_USERINFOLIST, JSON.toJSONString(id), JSON.toJSONString(listOldUser));
    }

    public List<com.tcloud.talkie.module.user.pojo.User> getGroupUserListById(Long id) {
        String strValue = redisHolderService.getHashSet(RedisKeys.GROUP_ID_USERINFOLIST, JSON.toJSONString(id));
        if(null == strValue) {
            return null;
        }
        List<com.tcloud.talkie.module.user.pojo.User> listUser = JSON.parseArray(strValue, com.tcloud.talkie.module.user.pojo.User.class);
        return listUser;
    }

    public void delGroupUserListById(Long id) {
        redisHolderService.delHashSet(RedisKeys.GROUP_ID_USERINFOLIST, JSON.toJSONString(id));
    }


}
