package com.meta.sync.syncconvert.group;

import com.meta.model.group.Group;
import com.meta.sync.model.group.SyncGroup;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class SyncConvertGroup {
    public SyncGroup convertGroup(Group group) {
        if(null == group) {
            return null;
        }
        SyncGroup syncGroup = new SyncGroup();

        syncGroup.setName(group.getName());
        syncGroup.setParentId(group.getParentId());
        syncGroup.setId(group.getId());
        syncGroup.setType(group.getType());
        syncGroup.setStatus(group.getStatus());

        return syncGroup;
    }

    public List<SyncGroup> convertGroupList(List<Group> listGroup) {
        List<SyncGroup> listSyncGroup = new ArrayList<>();
        for(Group group : listGroup) {
            SyncGroup syncGroup = convertGroup(group);
            listSyncGroup.add(syncGroup);
        }
        return listSyncGroup;
    }
}
