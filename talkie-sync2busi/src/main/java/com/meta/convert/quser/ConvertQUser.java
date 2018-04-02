package com.meta.convert.quser;

import com.meta.datetime.DateTimeUtil;
import com.meta.sync.model.quser.SyncQUser;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class ConvertQUser {

    public com.tcloud.talkie.module.q.pojo.QUser convertNew2Old(SyncQUser syncQUser) {
        if (syncQUser == null) {
            return null;
        }
        com.tcloud.talkie.module.q.pojo.QUser oldQUser = new com.tcloud.talkie.module.q.pojo.QUser();

        oldQUser.setBalance(syncQUser.getBalance().longValue());
        if (syncQUser.getUser() != null) {
            oldQUser.setUserId(syncQUser.getUser().getId());
        }

        try {
            oldQUser.setModify_time(DateTimeUtil.simpleDateTimeParse(syncQUser.getModifyDate()));
        } catch (ParseException e) {

        }

        return oldQUser;
    }

}
