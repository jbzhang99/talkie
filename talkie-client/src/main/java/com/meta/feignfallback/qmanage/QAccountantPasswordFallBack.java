package com.meta.feignfallback.qmanage;

import com.meta.Result;
import com.meta.feignclient.qmanage.QAccountantPasswordClient;

import feign.hystrix.FallbackFactory;

/**
 * Created by y747718944 on 2018/2/26
 */
public class QAccountantPasswordFallBack implements FallbackFactory<QAccountantPasswordClient> {

    @Override
    public QAccountantPasswordClient create(Throwable cause) {
       return new QAccountantPasswordClient() {
           @Override
           public Result get(String password) {
               return null;
           }

           @Override
           public Result passWordIsNull() {
               return null;
           }

           @Override
           public Result save(String password) {
               return null;
           }
       };
    }
}
