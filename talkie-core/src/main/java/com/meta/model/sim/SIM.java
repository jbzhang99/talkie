package com.meta.model.sim;

import com.meta.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by y747718944 on 2018/2/
 * Sim 卡信息 维护表
 *
 */
@Entity
@Table(name = "talkie_t_SIM" )
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "SIM卡信息", description = "SIM卡信息")
public class SIM extends BaseEntity {

    public SIM() {
    }

    public SIM(String iccId) {
        this.iccId = iccId;
    }

    @ApiModelProperty("SIM 的ICCID 是唯一的 跟用户是一对一的关系")
    private String iccId;

//    @NotBlank(message = "ICCID不能为空")
    @Column(name = "icc_id",unique =true ,nullable = false ,   length = 100)
    public String getIccId() {
        return iccId;
    }

    public void setIccId(String iccId) {
        this.iccId = iccId;
    }




}
