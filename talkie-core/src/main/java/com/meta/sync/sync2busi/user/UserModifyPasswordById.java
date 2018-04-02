package com.meta.sync.sync2busi.user;

import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;

import com.cloud.sync2busiaop.intersector.HandlerIntersector;
import com.cloud.sync2busiaop.vo.user.UserModifyPasswordVO;
import com.meta.model.user.User;
import com.meta.redissync.user.RedisSyncUser;
import com.meta.sync.sync2busi.notify.Sync2Busi;
import com.meta.sync.syncconvert.user.SyncConvertUser;
import com.meta.sync.utils.SyncUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserModifyPasswordById extends BaseComponent implements HandlerIntersector<UserModifyPasswordVO> {
    
    @Autowired
    RedisSyncUser redisUser;
    @Autowired
    Sync2Busi sync2Busi;
    @Autowired
    SyncConvertUser syncConvertUser;
    @Autowired
    private SyncUtils syncUtils;

    @Override
    public String getClassName() {
        return UserModifyPasswordVO.class.getName();
    }

    @Override
    public boolean beforeHandle(UserModifyPasswordVO vo) {
        Object[] args = (Object[]) vo.getArgObj();
        log.info("SYNC", "user modify password by id, args:" + JSON.toJSONString(args));

        return false;
    }

    @Override
    public boolean afterHandle(UserModifyPasswordVO vo) {
        Object[] args = (Object[]) vo.getArgObj();
        log.info("SYNC", "user modify password by id, args:" + JSON.toJSONString(args));
        Long userId = (Long) args[0];
        User user = syncUtils.findUserWithTerminalByUserId(userId);
        log.info("SYNC", "user modify password by id, db user:" + JSON.toJSONString(user));
        if (user != null) {
            redisUser.putUserById(userId, syncConvertUser.convertUser(user));
            redisUser.putUserByAccount(user.getAccount(), syncConvertUser.convertUser(user));
            if (user.getTerminal() != null) {
                redisUser.putUserByTerminalId(user.getTerminal().getId(), syncConvertUser.convertUser(user));
            }

            sync2Busi.syncUser(user, false, false);
        }

        return false;
    }
}
