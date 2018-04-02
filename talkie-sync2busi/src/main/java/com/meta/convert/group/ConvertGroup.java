package com.meta.convert.group;

import com.meta.sync.model.group.SyncGroup;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConvertGroup {

    public com.tcloud.talkie.module.group.pojo.Group convertNew2Old(SyncGroup syncGroup) {
        if (null == syncGroup) {
            return null;
        }
        com.tcloud.talkie.module.group.pojo.Group oldGroup = new com.tcloud.talkie.module.group.pojo.Group();

        oldGroup.setName(syncGroup.getName());
        oldGroup.setParentId(syncGroup.getParentId());
        oldGroup.setId(syncGroup.getId());
        oldGroup.setType(syncGroup.getType());
        // 1--正常  3--暂停
        oldGroup.setActive(Integer.valueOf(syncGroup.getStatus()));
        return oldGroup;
    }

    public List<com.tcloud.talkie.module.group.pojo.Group> convertNew2OldList(List<SyncGroup> listSyncGroup) {
        if (null == listSyncGroup) {
            return null;
        }
        List<com.tcloud.talkie.module.group.pojo.Group> listOldGroup = new ArrayList<>();
        for (SyncGroup syncGroup : listSyncGroup) {
            com.tcloud.talkie.module.group.pojo.Group oldGroup = convertNew2Old(syncGroup);
            listOldGroup.add(oldGroup);
        }
        return listOldGroup;
    }
}
