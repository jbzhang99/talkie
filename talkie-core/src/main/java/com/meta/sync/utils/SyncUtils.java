package com.meta.sync.utils;

import com.meta.model.terminal.Terminal;
import com.meta.model.user.User;
import com.meta.redissync.terminal.RedisSyncTerminal;
import com.meta.service.terminal.TerminalService;
import com.meta.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SyncUtils {
    @Autowired
    private UserService userService;
    @Autowired
    private TerminalService terminalService;
    @Autowired
    RedisSyncTerminal redisSyncTerminal;

    public User findUserWithTerminalByUserId(Long id) {
        User usr = userService.findOne(id);
        if(null != usr) {
            Terminal terminal = terminalService.searchByUser_Account(usr.getAccount());
            if(null != terminal) {
                usr.setTerminal(terminal);
            }
        }
        return usr;
    }


    /*
    *  查询在线状态
    * */

    public Boolean getOnlineStatusByUserId(Long id) {
        User usr = findUserWithTerminalByUserId(id);
        if(null == usr || null == usr.getTerminal()) {
            return false;
        }
        Boolean isOnline = redisSyncTerminal.getOnlineByTerminalId(usr.getTerminal().getId());
        if(null == isOnline) {
            return false;
        }
        return isOnline;
    }
}
