//package com.meta.feignfallback.merchant;
//
//import com.meta.Result;
//import com.meta.feignclient.merchant.MerchantEventClient;
//import com.meta.model.merchant.MMerchantEvent;
//import feign.hystrix.FallbackFactory;
//import org.springframework.stereotype.Component;
//
///**
// * create by lhq
// * create date on  18-2-23上午10:14
// *
// * @version 1.0
// **/
//@Component
//public class MerchantEventFallBack  implements FallbackFactory<MerchantEventClient> {
//
//
//    @Override
//    public MerchantEventClient create(Throwable cause) {
//        return new MerchantEventClient(){
//            @Override
//            public Result<MMerchantEvent> search(String filters, String sorts, int size, int page) {
//                return null;
//            }
//        };
//    }
//}
