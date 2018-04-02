package com.meta.model.datatotal;

import com.meta.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * create by lhq
 * create date on  18-2-26下午1:49
 *
 * @version 1.0
 **/
@Entity
@Table(name = "talkie_q_data_total")
@DynamicInsert
@DynamicUpdate
@ApiModel(value = "Q币消费表", description = "Q币消费表")
public class QGeneralagentDataTotal extends BaseEntity {

    /**
     * 年份
     */
    @ApiModelProperty(value = "年份")
    private Integer year;

    /**
     * 年份充值额
     */
    @ApiModelProperty(value = "年份充值额")
    private Double yearEarning;


    /**
     * 年份支出额
     */
    @ApiModelProperty(value = "年份支出额")
    private Double yearExpenditure;




    /**
     * 一月份支出额
     */
    @ApiModelProperty(value = "一月份支出额")
    private Double janExpenditure;



    /**
     * 二月份支出额
     */
    @ApiModelProperty(value = "二月份支出额")
    private Double febExpenditure;




    /**
     * 三月份支出额
     */
    @ApiModelProperty(value = "三月份支出额")
    private Double marExpenditure;


    /**
     * 四月份支出额
     */
    @ApiModelProperty(value = "四月份支出额")
    private Double aprExpenditure;



    /**
     * 五月份支出额
     */
    @ApiModelProperty(value = "五月份支出额")
    private Double mayExpenditure;



    /**
     * 六月份支出额
     */
    @ApiModelProperty(value = "六月份支出额")
    private Double junExpenditure;


    /**
     * 七月份支出额
     */
    @ApiModelProperty(value = "七月份支出额")
    private Double julExpenditure;


    /**
     * 八月份支出额
     */
    @ApiModelProperty(value = "八月份支出额")
    private Double augExpenditure;



    /**
     * 九月份支出额
     */
    @ApiModelProperty(value = "九月份支出额")
    private Double septExpenditure;


    /**
     * 十月份支出额
     */
    @ApiModelProperty(value = "十月份支出额")
    private Double octExpenditure;


    /**
     * 十一月份支出额
     */
    @ApiModelProperty(value = "十一月份支出额")
    private Double novExpenditure;



    /**
     * 十二月份支出额
     */
    @ApiModelProperty(value = "十二月份支出额")
    private Double decExpenditure;

    @Embedded
    private  QGeneralagentEarning qGeneralagentEarning;
    public QGeneralagentEarning getqGeneralagentEarning() {
        return qGeneralagentEarning;
    }
    public void setqGeneralagentEarning(QGeneralagentEarning qGeneralagentEarning) {
        this.qGeneralagentEarning = qGeneralagentEarning;
    }


    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Column(name = "year_earning", length = 50)
    public Double getYearEarning() {
        return yearEarning;
    }

    public void setYearEarning(Double yearEarning) {
        this.yearEarning = yearEarning;
    }

    @Column(name = "year_expenditure", length = 50)
    public Double getYearExpenditure() {
        return yearExpenditure;
    }

    public void setYearExpenditure(Double yearExpenditure) {
        this.yearExpenditure = yearExpenditure;
    }


    @Column(name = "jan_expenditure", length = 50)
    public Double getJanExpenditure() {
        return janExpenditure;
    }

    public void setJanExpenditure(Double janExpenditure) {
        this.janExpenditure = janExpenditure;
    }

    @Column(name = "feb_expenditure", length = 50)
    public Double getFebExpenditure() {
        return febExpenditure;
    }

    public void setFebExpenditure(Double febExpenditure) {
        this.febExpenditure = febExpenditure;
    }


    public Double getMarExpenditure() {
        return marExpenditure;
    }

    @Column(name = "mar_expenditure", length = 50)
    public void setMarExpenditure(Double marExpenditure) {
        this.marExpenditure = marExpenditure;
    }


    public Double getAprExpenditure() {
        return aprExpenditure;
    }

    @Column(name = "apr_expenditure", length = 50)
    public void setAprExpenditure(Double aprExpenditure) {
        this.aprExpenditure = aprExpenditure;
    }


    @Column(name = "may_expenditure", length = 50)
    public Double getMayExpenditure() {
        return mayExpenditure;
    }

    public void setMayExpenditure(Double mayExpenditure) {
        this.mayExpenditure = mayExpenditure;
    }


    @Column(name = "jun_expenditure", length = 50)
    public Double getJunExpenditure() {
        return junExpenditure;
    }

    public void setJunExpenditure(Double junExpenditure) {
        this.junExpenditure = junExpenditure;
    }

    @Column(name = "jul_expenditure", length = 50)
    public Double getJulExpenditure() {
        return julExpenditure;
    }

    public void setJulExpenditure(Double julExpenditure) {
        this.julExpenditure = julExpenditure;
    }


    @Column(name = "aug_expenditure", length = 50)
    public Double getAugExpenditure() {
        return augExpenditure;
    }

    public void setAugExpenditure(Double augExpenditure) {
        this.augExpenditure = augExpenditure;
    }


    @Column(name = "sept_expenditure", length = 50)
    public Double getSeptExpenditure() {
        return septExpenditure;
    }

    public void setSeptExpenditure(Double septExpenditure) {
        this.septExpenditure = septExpenditure;
    }

    @Column(name = "oct_expenditure", length = 50)
    public Double getOctExpenditure() {
        return octExpenditure;
    }

    public void setOctExpenditure(Double octExpenditure) {
        this.octExpenditure = octExpenditure;
    }


    @Column(name = "nov_expenditure", length = 50)
    public Double getNovExpenditure() {
        return novExpenditure;
    }

    public void setNovExpenditure(Double novExpenditure) {
        this.novExpenditure = novExpenditure;
    }


    @Column(name = "dec_expenditure", length = 50)
    public Double getDecExpenditure() {
        return decExpenditure;
    }

    public void setDecExpenditure(Double decExpenditure) {
        this.decExpenditure = decExpenditure;
    }


}
