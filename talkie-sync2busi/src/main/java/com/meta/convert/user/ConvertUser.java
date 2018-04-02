package com.meta.convert.user;

import com.meta.sync.model.user.SyncUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ConvertUser {

    public com.tcloud.talkie.module.user.pojo.User convertNew2Old(SyncUser syncUser) {
        if (null == syncUser) {
            return null;
        }
        com.tcloud.talkie.module.user.pojo.User oldUser = new com.tcloud.talkie.module.user.pojo.User();

        if (syncUser.getTerminalId() != null) {
            oldUser.setTerminal_id(syncUser.getTerminalId());
        }

        oldUser.setId(syncUser.getId());
        oldUser.setPhoneNum(syncUser.getTelPhone());
        oldUser.setMerchantId(syncUser.getParentId());
        oldUser.setDel(syncUser.getIsDel());
        oldUser.setVoicePrio(syncUser.getVoicePrio());
        oldUser.setStatus(Integer.valueOf(syncUser.getStatus()));
        oldUser.setRemark(syncUser.getRemark());
        oldUser.setPrefixAccount(syncUser.getPrefixAccount());
        oldUser.setPassword(syncUser.getPassword());
        oldUser.setName(syncUser.getName());
        oldUser.setFuncs(syncUser.getFuncs());
        oldUser.setAccount(syncUser.getAccount());

        return oldUser;
    }


    public List<com.tcloud.talkie.module.user.pojo.User> convertNew2OldList(List<SyncUser> listSyncUser) {
        if (null == listSyncUser) {
            return null;
        }
        List<com.tcloud.talkie.module.user.pojo.User> listOldUser = new ArrayList<>();
        for (SyncUser syncUser : listSyncUser) {
            com.tcloud.talkie.module.user.pojo.User oldUser = convertNew2Old(syncUser);
            listOldUser.add(oldUser);
        }
        return listOldUser;
    }

}
