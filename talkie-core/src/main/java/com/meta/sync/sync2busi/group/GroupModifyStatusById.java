package com.meta.sync.sync2busi.group;

import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;

import com.cloud.sync2busiaop.intersector.HandlerIntersector;
import com.cloud.sync2busiaop.vo.group.GroupModifyByIdVO;
import com.meta.Result;
import com.meta.model.group.Group;
import com.meta.model.user.User;
import com.meta.redissync.group.RedisSyncGroup;
import com.meta.redissync.user.RedisSyncUser;
import com.meta.service.group.GroupService;
import com.meta.service.user.UserService;
import com.meta.sync.sync2busi.notify.Sync2Busi;
import com.meta.sync.syncconvert.group.SyncConvertGroup;
import com.meta.sync.utils.SyncUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupModifyStatusById extends BaseComponent implements HandlerIntersector<GroupModifyByIdVO> {
    
    @Autowired
    RedisSyncGroup redisGroup;
    @Autowired
    RedisSyncUser redisUser;
    @Autowired
    Sync2Busi sync2Busi;
    @Autowired
    SyncConvertGroup syncConvertGroup;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private SyncUtils syncUtils;

    @Override
    public String getClassName() {
        return GroupModifyByIdVO.class.getName();
    }

    @Override
    public boolean beforeHandle(GroupModifyByIdVO vo) {
        // args:[{"currentUserId":4,"level":"1","name":"qunzu05","phone":"15687978677","status":"1","type":2}]
        log.info("SYNC", "group modify status by id, args:" + JSON.toJSONString(vo.getArgObj()));
        try {

        } catch(Exception e) {
            log.info("SYNC", "group modify status by id, exception:" + e);
        } finally {
            
        }
        return false;
    }

    @Override
    public boolean afterHandle(GroupModifyByIdVO vo) {
        Object[] args = (Object[]) vo.getArgObj();
        Long groupId = (Long) args[0];
        log.info("SYNC", "group modify status by id, ret:" + JSON.toJSONString(vo.getRetObj()));
        Result result = (Result) vo.getRetObj();
        if (result.isSuccessFlg()) {
            Group group = groupService.findOne(groupId);
            log.info("SYNC", "group modify status, db group:" + JSON.toJSONString(group));
            redisGroup.putGroupById(group.getId(), syncConvertGroup.convertGroup(group));

            sync2Busi.syncGroup(group.getId());
        }
        return false;
    }
}
