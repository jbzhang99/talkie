package com.meta.redissync.quser;


import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;
import com.meta.convert.quser.ConvertQUser;
import com.meta.redissync.RedisSyncService;
import com.meta.sync.model.quser.SyncQUser;
import com.tcloud.talkie.RedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RedisSyncQUser extends BaseComponent {
    @Autowired
    RedisSyncService redisHolderService;
    @Autowired
    ConvertQUser convertQUser;

    /**
     * get/put/del by user id
     */
    public void putQUserById(Long id, SyncQUser syncQUser) {
        com.tcloud.talkie.module.q.pojo.QUser oldQUser = convertQUser.convertNew2Old(syncQUser);
        redisHolderService.putHashSet(RedisKeys.USER_ID_QUSER, JSON.toJSONString(id), JSON.toJSONString(oldQUser));
    }

    public com.tcloud.talkie.module.q.pojo.QUser getQUserById(Long id) {
        String strUser = redisHolderService.getHashSet(RedisKeys.USER_ID_QUSER, JSON.toJSONString(id));
        if(null == strUser) {
            return null;
        }
        com.tcloud.talkie.module.q.pojo.QUser quser = JSON.parseObject(strUser, com.tcloud.talkie.module.q.pojo.QUser.class);
        return quser;
    }

    public void delQUserById(Long id) {
        redisHolderService.delHashSet(RedisKeys.USER_ID_QUSER, JSON.toJSONString(id));
    }


    /**
     * get/put/del by terminal id
     */
    public void putQUserByTerminalId(Long id, SyncQUser syncQUser) {
        com.tcloud.talkie.module.q.pojo.QUser oldQUser = convertQUser.convertNew2Old(syncQUser);
        redisHolderService.putHashSet(RedisKeys.USER_ID_QUSER, JSON.toJSONString(id), JSON.toJSONString(oldQUser));
    }

    public com.tcloud.talkie.module.q.pojo.QUser getQUserByTerminalId(Long id) {
        String strUser = redisHolderService.getHashSet(RedisKeys.USER_ID_QUSER, JSON.toJSONString(id));
        if(null == strUser) {
            return null;
        }
        com.tcloud.talkie.module.q.pojo.QUser quser = JSON.parseObject(strUser, com.tcloud.talkie.module.q.pojo.QUser.class);
        return quser;
    }

    public void delQUserByTerminalId(Long id) {
        redisHolderService.delHashSet(RedisKeys.USER_ID_QUSER, JSON.toJSONString(id));
    }


    /**
     * get/put/del end-date by terminal id
     */
    public void putQDateByTerminalId(Long id, Date date) {
        redisHolderService.putHashSet(RedisKeys.TERMINAL_ID_QDATE, JSON.toJSONString(id), JSON.toJSONString(date));
    }

    public Date getQDateByTerminalId(Long id) {
        String strValue = redisHolderService.getHashSet(RedisKeys.TERMINAL_ID_QDATE, JSON.toJSONString(id));
        if(null == strValue) {
            return null;
        }
        Date date = JSON.parseObject(strValue, Date.class);
        return date;
    }

    public void delQDateByTerminalId(Long id) {
        redisHolderService.delHashSet(RedisKeys.TERMINAL_ID_QDATE, JSON.toJSONString(id));
    }


}
