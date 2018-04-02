package com.meta.exception;

/**
 * Created by y747718944 on 2018/1/25
 * 用户创建异常
 *  这是一个运行异常
 *  为了能触发事务
 */

public class UserCreateException  extends RuntimeException{
    private String msg;

    public UserCreateException(){

        super();
    }

    public UserCreateException(String msg){
        super(msg);
        this.msg=msg;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
