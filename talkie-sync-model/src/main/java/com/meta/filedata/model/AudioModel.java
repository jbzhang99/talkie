package com.meta.filedata.model;

/**
 *  录音 beans
 */

public class AudioModel {


    /**
     * 录音上传时间 为创建时间
     */

    private String account;

    private String duration;

    /**
     * 上传后的文件路径
     * 按日期建文件夹
     */
    private  String fileUrl;


    /**
     * 分组id
     */
    private Long groupId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

}
