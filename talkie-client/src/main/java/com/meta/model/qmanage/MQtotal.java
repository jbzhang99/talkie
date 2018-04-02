package com.meta.model.qmanage;

/**
 * Created by lhq on 2017/10/27.
 *
 * 统计 Q币
 */

public class MQtotal {

    /**
     *  待分配余额
     */
    private Double balance;
    /**
     * 已分配余额
     */
    private Double alreadyAssign;


    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getAlreadyAssign() {
        return alreadyAssign;
    }

    public void setAlreadyAssign(Double alreadyAssign) {
        this.alreadyAssign = alreadyAssign;
    }
}
