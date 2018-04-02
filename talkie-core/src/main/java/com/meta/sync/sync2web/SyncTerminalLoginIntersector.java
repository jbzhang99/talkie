package com.meta.sync.sync2web;

import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;

import com.cloud.talkie.data.sync.DataSyncIntersector;
import com.cloud.talkie.data.sync.vo.busi2web.SyncTerminalLogin;
import com.meta.datetime.DateTimeUtil;
import com.meta.model.user.User;
import com.meta.model.user.UserEvent;
import com.meta.redissync.user.RedisSyncUser;
import com.meta.service.user.UserEventService;
import com.meta.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Component
public class SyncTerminalLoginIntersector extends BaseComponent implements DataSyncIntersector<SyncTerminalLogin> {
    
    @Autowired
    UserService userService;
    @Autowired
    RedisSyncUser redisUser;
    @Autowired
    private UserEventService userEventService;

    @Override
    public String getSyncName() {
        return SyncTerminalLogin.class.getName();
    }

    @Override
    public boolean beforeHandle(SyncTerminalLogin req) {
        log.info("Sync", "SyncTerminalAddr:" + JSON.toJSONString(req));
        Long terminalId = req.getTerminalId();

        User user = userService.searchByTerminal_Id(terminalId);
        log.info("Sync", "terminal user:" + JSON.toJSONString(user));
        if (null != user) {
            userEventService.delByUserId(String.valueOf(user.getId()));
            UserEvent userevent = new UserEvent();
            userevent.setType(1);
            userevent.setUser(user);
            userevent.setParentId(user.getParentId());
            userevent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            userevent.setModifyDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            userevent.setIp(req.getTerminalAddr());
            userevent.setRemark("设备登录：" + user.getName());
            userEventService.save(userevent);
        }
        return false;
    }

    @Override
    public boolean afterHandle(SyncTerminalLogin req) {

        return false;
    }

}
