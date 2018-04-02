package com.meta.redissync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cloud.commons.config.AppConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.cloud.module.base.component.BaseComponent;
import com.tcloud.talkie.RedisKeys;

@Component
public class RedisSyncServiceImpl extends BaseComponent implements RedisSyncService {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Map<String, Long> getBusiServerOnlineNumber() {

        Set<Object> keys = redisTemplate.boundHashOps(
                RedisKeys.SERVER_ONLINE).keys();
        if (CollectionUtils.isEmpty(keys)) {
            return null;
        }
        Map<String, Long> mapServerOnline = new HashMap<String, Long>();
        for (Object key : keys) {
            String ip = (String) key;
            String strNum = (String) redisTemplate.boundHashOps(
                    RedisKeys.SERVER_ONLINE).get(key);
            Long num = Long.valueOf(strNum);
            mapServerOnline.put(ip, num);
        }
        return mapServerOnline;
    }

    @Override
    public List<String> getBusiSvrSyncQueueName() {
        Map<String, Long> mapServerOnline = getBusiServerOnlineNumber();
        if (MapUtils.isEmpty(mapServerOnline)) {
            return null;
        }
        List<String> queueNames = new ArrayList<String>();
        String syncQueue = AppConfig.getItemValue("protocol.common.sync.queue.name", "SyncQueue");
        for (String svrName : mapServerOnline.keySet()) {
            queueNames.add(syncQueue + ":" + svrName);
        }
        return queueNames;
    }

    @Override
    public void putHashSet(String name, String key, String value) {
        redisTemplate.boundHashOps(name).put(key, value);
    }

    @Override
    public void delHashSet(String name, String key) {
        redisTemplate.boundHashOps(name).delete(key);
    }

    @Override
    public String getHashSet(String name, String key) {
        return (String) redisTemplate.boundHashOps(name).get(key);
    }


}
