package com.meta.sync.sync2busi.quser;

import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;

import com.cloud.sync2busiaop.intersector.HandlerIntersector;
import com.cloud.sync2busiaop.vo.quser.QUserBatchAddOrDelVO;
import com.meta.datetime.DateTimeUtil;
import com.meta.model.qmanage.QUser;
import com.meta.model.user.User;
import com.meta.redissync.quser.RedisSyncQUser;
import com.meta.service.qmanage.QUserService;
import com.meta.service.user.UserService;
import com.meta.sync.sync2busi.notify.Sync2Busi;
import com.meta.sync.syncconvert.quser.SyncConvertQUser;
import com.meta.sync.utils.SyncUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;

@Component
public class QUserBatchAddOrDel extends BaseComponent implements HandlerIntersector<QUserBatchAddOrDelVO> {
    
    @Autowired
    RedisSyncQUser redisQUser;
    @Autowired
    QUserService qUserService;
    @Autowired
    UserService userService;
    @Autowired
    Sync2Busi sync2Busi;
    @Autowired
    SyncConvertQUser syncConvertQUser;
    @Autowired
    private SyncUtils syncUtils;

    @Override
    public String getClassName() {
        return QUserBatchAddOrDelVO.class.getName();
    }

    @Override
    public boolean beforeHandle(QUserBatchAddOrDelVO vo) {
        // args:["35,33",1,4]
        // args:[{"currentUserId":4,"friendUserIDTemp":"8,11,13,9","selfUserId":35,"type":"2"}]
        log.info("SYNC", "quser batch add or del, args:" + JSON.toJSONString(vo.getArgObj()));
        return false;
    }

    @Override
    public boolean afterHandle(QUserBatchAddOrDelVO vo) {
        log.info("SYNC", "quser batch add or del, ret:" + JSON.toJSONString(vo.getRetObj()));
        Object[] args = (Object[]) vo.getArgObj();
        String userIds = (String) args[0];
        for (String userId : userIds.split(",")) {
            User user = syncUtils.findUserWithTerminalByUserId(Long.parseLong(userId));
            List<QUser> result = qUserService.search("EQ_user.id=" + userId);
            redisQUser.putQUserById(Long.parseLong(userId), syncConvertQUser.convertQUser(result.get(0)));
            if (user != null && user.getTerminal() != null) {
                try {
                    redisQUser.putQUserByTerminalId(user.getTerminal().getId(), syncConvertQUser.convertQUser(result.get(0)));
                    redisQUser.putQDateByTerminalId(user.getTerminal().getId(), DateTimeUtil.simpleDateTimeParse(result.get(0).getModifyDate()));
                    sync2Busi.syncQUserModify(user.getTerminal().getId());
                } catch (ParseException e) {
                }
            }
        }

        return false;
    }
}
