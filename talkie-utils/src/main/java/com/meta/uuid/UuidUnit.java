package com.meta.uuid;


import java.util.UUID;

/**
 * Created by linhq on 2017/1/13 0013.
 * uuid生成工具类
 */
public class UuidUnit {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }


}
