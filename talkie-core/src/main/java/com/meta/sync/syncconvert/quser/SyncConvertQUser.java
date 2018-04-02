package com.meta.sync.syncconvert.quser;

import com.meta.model.qmanage.QUser;
import com.meta.sync.model.quser.SyncQUser;
import com.meta.sync.syncconvert.user.SyncConvertUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SyncConvertQUser {
    @Autowired
    SyncConvertUser syncConvertUser;

    public SyncQUser convertQUser(QUser qUser) {
        if(null == qUser) {
            return null;
        }
        SyncQUser syncQUser = new SyncQUser();
        syncQUser.setBalance(qUser.getBalance());
        if(qUser.getUser() != null) {
            syncQUser.setUser(syncConvertUser.convertUser(qUser.getUser()));
        }

        syncQUser.setModifyDate(qUser.getModifyDate());

        return syncQUser;
    }

}
