package com.meta.feignclient.fileData;

import com.meta.*;
import com.meta.feignfallback.filldate.AudioFallBack;
import com.meta.model.FileData.MAudio;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;

/**
 * ybh 17/12/11
 */
@FeignClient(name = ServiceNames.TALKIE_CORE,fallbackFactory = AudioFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface Audioclient {

    @CacheEvict(value = RedisValue.FIND_FILEDATA_AUDIO,allEntries = true)
    @ApiOperation(value = "录音上传",notes = "上传音频文件")
    @RequestMapping(value = ServiceUrls.FileData.AUDIO,method = RequestMethod.POST)
    Result upload( @ApiParam(name = "file",value = "上传的文件",defaultValue = "")
                      @RequestParam(value = "file",required = true) MultipartFile multipartFile,
                   @ApiParam(name = "json_model", value = "", defaultValue = "")
                   @RequestParam(value = "json_model") String json_model);


    @Cacheable(value = RedisValue.FIND_FILEDATA_AUDIO,key = "'search_audios_conditions_fifters='+#p0+'_and_sorts='+#p1+'_and_size='+#p2+'_and_page='+#p3", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.FileData.AUDIO,method = RequestMethod.GET)
    @ApiOperation(value = "获取录音", notes = "根据查询条件获组在前端表格展示")
    Result<MAudio> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page
    );

    @CacheEvict(value = RedisValue.FIND_FILEDATA_AUDIO,allEntries = true)
    @RequestMapping(value = ServiceUrls.FileData.AUDIO,method=RequestMethod.DELETE)
    @ApiOperation(value = "根据id+url删除录音数据",notes = "根据id+url删除录音数据")
    Result delete(
            @ApiParam(value = "录音id+URL",name = "audios",defaultValue = "")
            @RequestParam(value = "audios",required = false)String audios);

    @ApiOperation(value = "录音下载",notes = "下载音频文件！")
    @RequestMapping(value = ServiceUrls.FileData.AUDIO_DOWN ,method = RequestMethod.GET)
    Result down(
            @ApiParam(value = "url",name = "文件路径")
            @RequestParam(value = "url",required = true) String url,
            HttpServletResponse response);



}
