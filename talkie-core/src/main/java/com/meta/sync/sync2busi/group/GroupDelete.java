package com.meta.sync.sync2busi.group;

import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;

import com.cloud.sync2busiaop.intersector.HandlerIntersector;
import com.cloud.sync2busiaop.vo.group.GroupDeleteVO;
import com.meta.Result;
import com.meta.model.group.Group;
import com.meta.model.user.User;
import com.meta.redissync.group.RedisSyncGroup;
import com.meta.redissync.user.RedisSyncUser;
import com.meta.service.group.GroupService;
import com.meta.service.user.UserService;
import com.meta.sync.sync2busi.notify.Sync2Busi;
import com.meta.sync.syncconvert.group.SyncConvertGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupDelete extends BaseComponent implements HandlerIntersector<GroupDeleteVO> {
    
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
    private List<User> listGroupUserBeforeDel;

    @Override
    public String getClassName() {
        return GroupDeleteVO.class.getName();
    }

    @Override
    public boolean beforeHandle(GroupDeleteVO groupDeleteVO) {
        // args:[5]
        log.info("SYNC", "group delete, args:" + JSON.toJSONString(groupDeleteVO.getArgObj()));
        Object[] args = (Object[]) groupDeleteVO.getArgObj();
        Long id = (Long) args[0];
        listGroupUserBeforeDel = userService.findAlreadyUserGroup(id);
        return false;
    }

    @Override
    public boolean afterHandle(GroupDeleteVO groupDeleteVO) {
        // ret:{"currPage":1,"detailModelList":[],"pageSize":15,"successFlg":true,"totalCount":0,"totalPage":0}
        Object[] args = (Object[]) groupDeleteVO.getArgObj();
        Long groupId = (Long) args[0];
        log.info("SYNC", "group delete, ret:" + JSON.toJSONString(groupDeleteVO.getRetObj()));
        Result result = (Result) groupDeleteVO.getRetObj();
        if (true == result.isSuccessFlg() && null != listGroupUserBeforeDel) {
            redisGroup.delGroupUserListById(groupId);
            redisGroup.delGroupById(groupId);
            for (User usr : listGroupUserBeforeDel) {
                if (usr.getTerminal() == null) {
                    continue;
                }
                List<Group> listGroup = groupService.findAlreadyUser(usr.getId());
                redisUser.putUserGroupListByTerminalId(usr.getTerminal().getId(), syncConvertGroup.convertGroupList(listGroup));
            }

            // notify to busi server
            sync2Busi.syncGroupDelete(groupId, listGroupUserBeforeDel);
        }
        return false;
    }
}
