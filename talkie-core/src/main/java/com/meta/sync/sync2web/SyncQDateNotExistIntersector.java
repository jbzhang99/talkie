package com.meta.sync.sync2web;

import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;

import com.cloud.talkie.data.sync.DataSyncIntersector;
import com.cloud.talkie.data.sync.vo.busi2web.SyncQDateNotExist;
import com.meta.datetime.DateTimeUtil;
import com.meta.model.qmanage.QUser;
import com.meta.model.user.User;
import com.meta.redissync.quser.RedisSyncQUser;
import com.meta.service.qmanage.QUserService;
import com.meta.service.user.UserService;
import com.meta.sync.sync2busi.notify.Sync2Busi;
import com.meta.sync.syncconvert.quser.SyncConvertQUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

@Transactional
@Component
public class SyncQDateNotExistIntersector extends BaseComponent implements DataSyncIntersector<SyncQDateNotExist> {
    

    @Autowired
    UserService userService;
    @Autowired
    QUserService qUserService;
    @Autowired
    RedisSyncQUser redisQUser;
    @Autowired
    Sync2Busi sync2Busi;
    @Autowired
    SyncConvertQUser syncConvertQUser;

    @Override
    public String getSyncName() {
        return SyncQDateNotExist.class.getName();
    }

    @Override
    public boolean beforeHandle(SyncQDateNotExist req) {
        log.info("SYNC", "SyncQDateNotExist:" + JSON.toJSONString(req));
        if (req.getTerminalId() != null) {
            User user = userService.searchByTerminal_Id(req.getTerminalId());
            if (null != user) {
                List<QUser> result = qUserService.search("EQ_user.id=" + user.getId());
                redisQUser.putQUserById(user.getId(), syncConvertQUser.convertQUser(result.get(0)));
                if (user.getTerminal() != null) {
                    try {
                        redisQUser.putQUserByTerminalId(user.getTerminal().getId(), syncConvertQUser.convertQUser(result.get(0)));
                        redisQUser.putQDateByTerminalId(user.getTerminal().getId(), DateTimeUtil.simpleDateTimeParse(result.get(0).getModifyDate()));
                        sync2Busi.syncQUserModify(user.getTerminal().getId());
                    } catch (ParseException e) {
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean afterHandle(SyncQDateNotExist req) {

        return false;
    }

}
