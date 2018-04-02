package com.meta.model.terminal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.meta.JsonViewConfig;
import com.meta.model.BaseEntity;
import com.meta.model.map.BaiDuMap;
import com.meta.model.map.MetaBaseStation;
import com.meta.model.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * Created by llin on 2017/9/28.
 */
@Entity
@Table(name = "talkie_t_terminal")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "终端基本信息", description = "终端基本信息")
public class Terminal extends BaseEntity {


    /**
     * *  设备终端表,, 每一个用户不一定要有一个, 对应用户的 终端ID,
     */
    /**
     * 逻辑删除  (1= 未删除,,2= 已删除)
     *
     * @return
     */
    private Integer isDel;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "用户")
    private User user;


    @ApiModelProperty(value = "定位表，，一对多")
    private List<BaiDuMap> baiDuMapList;


    /**
     * 基站定位  一对多
     * @return
     */

    @ApiModelProperty("基站定位，一对多")
    private  List<MetaBaseStation> metaBaseStationList ;

    @Column(name = "name", length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Column(name = "is_del", length = 3)
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "terminal", cascade = {CascadeType.ALL})
    @JsonIgnore
    public List<BaiDuMap> getBaiDuMapList() {
        return baiDuMapList;
    }

    public void setBaiDuMapList(List<BaiDuMap> baiDuMapList) {
        this.baiDuMapList = baiDuMapList;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "terminal", cascade = {CascadeType.ALL})
    @JsonIgnore
    public List<MetaBaseStation> getMetaBaseStationList() {
        return metaBaseStationList;
    }

    public void setMetaBaseStationList(List<MetaBaseStation> metaBaseStationList) {
        this.metaBaseStationList = metaBaseStationList;
    }
}
