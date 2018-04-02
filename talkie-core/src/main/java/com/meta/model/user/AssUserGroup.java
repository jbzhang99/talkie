package com.meta.model.user;

import com.meta.model.group.Group;

import java.util.List;

/**
 * Created by lhq on 2017/11/27.

        群组，旁边专用
 */

public class AssUserGroup {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 群组
     *
     * @return
     */
    private List<Group> groupList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }
}
