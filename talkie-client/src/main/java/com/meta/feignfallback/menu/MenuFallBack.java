package com.meta.feignfallback.menu;

import com.meta.Result;
import com.meta.feignclient.menu.MeunClient;
import com.meta.model.menu.MMenu;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23上午11:04
 *
 * @version 1.0
 **/
@Component
public class MenuFallBack  implements FallbackFactory<MeunClient> {
    @Override
    public MeunClient create(Throwable cause) {
        return new MeunClient(){
            @Override
            public Result<MMenu> findByRole(Long id, String roleId) {
                return null;
            }

            @Override
            public Result<MMenu> getMenuTree() {
                return null;
            }

            @Override
            public Result<MMenu> create(MMenu menu) {
                return null;
            }

            @Override
            public Result<MMenu> getById(Long id) {
                return null;
            }

            @Override
            public Result delete(Long id) {
                return null;
            }
        };
    }
}
