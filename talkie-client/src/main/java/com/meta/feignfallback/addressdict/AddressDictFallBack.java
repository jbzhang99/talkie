package com.meta.feignfallback.addressdict;

import com.meta.Result;
import com.meta.feignclient.addressDict.AddressDictClient;
import com.meta.model.addressDict.MAddressDict;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23下午1:52
 *
 * @version 1.0
 **/
@Component
public class AddressDictFallBack implements FallbackFactory<AddressDictClient> {
    @Override
    public AddressDictClient create(Throwable cause) {
        return new AddressDictClient(){
            @Override
            public Result<MAddressDict> search(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result<MAddressDict> get(Long id) {
                return null;
            }
        };
    }
}
