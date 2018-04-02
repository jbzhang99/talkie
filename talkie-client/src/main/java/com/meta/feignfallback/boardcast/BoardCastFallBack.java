package com.meta.feignfallback.boardcast;

import com.meta.Result;
import com.meta.feignclient.boardCast.BoardCastClient;
import com.meta.model.boardcast.MBoardCast;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-23下午1:51
 *
 * @version 1.0
 **/
@Component
public class BoardCastFallBack  implements FallbackFactory<BoardCastClient>{
    @Override
    public BoardCastClient create(Throwable cause) {
        return new BoardCastClient(){
            @Override
            public Result<MBoardCast> search(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result modifyStateById(Long id, String status) {
                return null;
            }

            @Override
            public Result delete(Long id) {
                return null;
            }

            @Override
            public Result create(MBoardCast mBoardCast) {
                return null;
            }
        };
    }
}
