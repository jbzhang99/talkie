package com.meta.model.FileData;

import com.meta.model.MBaseEntity;
import com.meta.model.user.MUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotBlank;

/**
 * ybh  录音数据bean
 */
@ApiModel(value = "录音基本信息",description = "录音基本信息")
public class MAudio extends MBaseEntity {

    @ApiModelProperty("主键")
    private  Long Id;

    @ApiModelProperty("录音用户账号")
    @NotBlank(message = "上传用户账号不能为空")
    private String account;

    @ApiModelProperty(value = "用户  多对一")
    private MUser user;

    @ApiModelProperty("时长")
    private String duration;
    @ApiModelProperty("录音用户名")
    private String name;

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

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }


    public MUser getUser() {
        return user;
    }

    public void setUser(MUser user) {
        this.user = user;
    }
}
