package com.meta.model.map;

/**
 * create by lhq
 * create date on  18-1-30下午4:50
 *  聚合数据 电信基站deom
 * @version 1.0
 **/
public class resultDemo {

    private String resultcode;
    private String reason;
    private result result;
    private String error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public com.meta.model.map.result getResult() {
        return result;
    }

    public void setResult(com.meta.model.map.result result) {
        this.result = result;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }
}
