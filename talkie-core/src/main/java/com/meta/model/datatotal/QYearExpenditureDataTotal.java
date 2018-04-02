package com.meta.model.datatotal;

/**
 * create by lhq
 * create date on  18-2-28上午11:26
 *  年度支出统计
 * @version 1.0
 **/
public class QYearExpenditureDataTotal {


    /**
     * name 年份(官方)
     */
    private  String name;

    /**
     * 支出
     */
    private  String value;

    private QYearEarningDataTotal qYearEarningDataTotal;


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

    public QYearEarningDataTotal getqYearEarningDataTotal() {
        return qYearEarningDataTotal;
    }

    public void setqYearEarningDataTotal(QYearEarningDataTotal qYearEarningDataTotal) {
        this.qYearEarningDataTotal = qYearEarningDataTotal;
    }
}
