package com.meta.sync.syncconvert.user;

import com.meta.model.user.User;
import com.meta.sync.model.user.SyncUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class SyncConvertUser {
    public SyncUser convertUser(User user) {
        if(null == user) {
            return null;
        }
        SyncUser syncUser = new SyncUser();

        if(user.getTerminal() != null) {
            syncUser.setTerminalId(user.getTerminal().getId());
        }

        syncUser.setId(user.getId());
        syncUser.setTelPhone(user.getTelPhone());
        syncUser.setParentId(user.getParentId());
        syncUser.setIsDel(user.getIsDel());
        if(null == user.getVoicePrio()) {
            syncUser.setVoicePrio("0");
        } else {
            syncUser.setVoicePrio(user.getVoicePrio());
        }
        syncUser.setStatus(user.getStatus());
        syncUser.setRemark(user.getRemark());
        syncUser.setPrefixAccount(user.getPrefixAccount());
        syncUser.setPassword(user.getPassword());
        syncUser.setName(user.getName());
        syncUser.setFuncs(user.getFuncs());
        syncUser.setAccount(user.getAccount());

        return syncUser;
    }

    public List<SyncUser> convertUserList(List<User> listUser) {
        List<SyncUser> listSyncUser = new ArrayList<>();
        for(User user : listUser) {
            SyncUser syncUser = convertUser(user);
            listSyncUser.add(syncUser);
        }
        return listSyncUser;
    }
}
