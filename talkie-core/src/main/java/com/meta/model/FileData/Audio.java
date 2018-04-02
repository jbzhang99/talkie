package com.meta.model.FileData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meta.model.BaseEntity;
import com.meta.model.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 *  录音 beans
 */
@Entity
@Table(name="talkie_t_audio",uniqueConstraints = {@UniqueConstraint(name = "Talkie_audio_id",columnNames = {"id"})})
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "录音数据",description = "录音数据")
public class Audio  extends BaseEntity{

    @ApiModelProperty("主键")
    private  Long Id;

    /**
     * 录音上传时间 为创建时间
     */

    @ApiModelProperty("录音用户账号")
    @NotBlank(message = "上传用户账号不能为空")
    private String account;



    @ApiModelProperty("录音用户ID  一对多")
    private User user;

    @ApiModelProperty("时长")
    private String duration;

    /**
     * 上传后的文件路径
     * 按日期建文件夹
     */
    @ApiModelProperty("文件路径")
    @NotBlank(message = "文件路径不能为空")
    private  String fileUrl;


    /**
     * 分组id
     */
    @ApiModelProperty("分组id")
    private Long groupId;
    @Column(name="account",length = 32)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Column(name = "duration",length = 32)
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    @Column(name = "file_url",length = 300)
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "group_id",length = 32)
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

}
