package com.meta.sync.sync2busi.user;

import com.alibaba.fastjson.JSON;
import com.cloud.module.base.component.BaseComponent;

import com.cloud.sync2busiaop.intersector.HandlerIntersector;
import com.cloud.sync2busiaop.vo.user.UserBatchSaveVO;
import com.meta.redissync.user.RedisSyncUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserBatchSave extends BaseComponent implements HandlerIntersector<UserBatchSaveVO> {
    
    @Autowired
    RedisSyncUser redisUser;


    @Override
    public String getClassName() {
        return UserBatchSaveVO.class.getName();
    }

    @Override
    public boolean beforeHandle(UserBatchSaveVO userBatchSaveVO) {
        return false;
    }

    @Override
    public boolean afterHandle(UserBatchSaveVO userBatchSaveVO) {
        Object[] args = (Object[]) userBatchSaveVO.getArgObj();
        log.info("SYNC", "user batch save, args:" + JSON.toJSONString(args));
        return false;
    }
}
