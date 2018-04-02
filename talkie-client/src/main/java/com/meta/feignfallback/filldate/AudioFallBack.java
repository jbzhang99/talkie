package com.meta.feignfallback.filldate;

import com.meta.Result;
import com.meta.feignclient.fileData.Audioclient;
import com.meta.model.FileData.MAudio;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * create by lhq
 * create date on  18-2-23上午11:28
 *
 * @version 1.0
 **/
@Component
public class AudioFallBack implements FallbackFactory<Audioclient> {
    @Override
    public Audioclient create(Throwable cause) {
        return new Audioclient(){
            @Override
            public Result upload(MultipartFile multipartFile, String json_model) {
                return null;
            }

            @Override
            public Result<MAudio> search(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result delete(String audios) {
                return null;
            }

            @Override
            public Result down(String url, HttpServletResponse response) {
                return null;
            }
        };
    }
}
