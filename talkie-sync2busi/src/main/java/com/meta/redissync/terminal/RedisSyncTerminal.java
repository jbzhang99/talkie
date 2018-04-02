package com.meta.redissync.terminal;


import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;
import com.meta.redissync.RedisSyncService;
import com.tcloud.talkie.RedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisSyncTerminal extends BaseComponent {
    @Autowired
    RedisSyncService redisHolderService;

    /**
     * get by terminal id
     */

    public com.tcloud.talkie.module.terminal.vo.TerminalVO getTerminalByTerminalId(Long id) {
        String strTerminal = redisHolderService.getHashSet(RedisKeys.TERMINAL_ID_INFO, JSON.toJSONString(id));
        if(null == strTerminal) {
            return null;
        }
        com.tcloud.talkie.module.terminal.vo.TerminalVO terminal = JSON.parseObject(strTerminal, com.tcloud.talkie.module.terminal.vo.TerminalVO.class);
        return terminal;
    }

    /**
     * get online status by terminal id
     */

    public Boolean getOnlineByTerminalId(Long id) {
        String strTerminal = redisHolderService.getHashSet(RedisKeys.TERMINAL_ID_INFO, JSON.toJSONString(id));
        if(null == strTerminal) {
            return null;
        }
        com.tcloud.talkie.module.terminal.vo.TerminalVO terminal = JSON.parseObject(strTerminal, com.tcloud.talkie.module.terminal.vo.TerminalVO.class);
        return terminal.getOnline();
    }


}
