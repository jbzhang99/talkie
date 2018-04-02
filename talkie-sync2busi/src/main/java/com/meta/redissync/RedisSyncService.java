package com.meta.redissync;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface RedisSyncService {
    /**
     * 服务器在线数
     */
    Map<String, Long> getBusiServerOnlineNumber();

    List<String> getBusiSvrSyncQueueName();

    void putHashSet(String name, String key, String value);

    void delHashSet(String name, String key);

    String getHashSet(String name, String key);
}
