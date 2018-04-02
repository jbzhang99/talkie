package com.meta.model.datatotal;

/**
 * create by lhq
 * create date on  18-2-28上午11:26
 *  年度支出统计
 * @version 1.0
 **/
public class MQYearExpenditureDataTotal {


    /**
     * name 年份(官方)
     */
    private  String name;

    /**
     * 支出
     */
    private  String value;

    private MQYearEarningDataTotal qYearEarningDataTotal;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MQYearEarningDataTotal getqYearEarningDataTotal() {
        return qYearEarningDataTotal;
    }

    public void setqYearEarningDataTotal(MQYearEarningDataTotal qYearEarningDataTotal) {
        this.qYearEarningDataTotal = qYearEarningDataTotal;
    }
}

