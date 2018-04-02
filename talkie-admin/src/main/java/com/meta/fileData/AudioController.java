package com.meta.fileData;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.feignclient.fileData.Audioclient;
import com.meta.file.FilePath;
import com.meta.model.FileData.MAudio;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sys.OsUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;


/**
 * ybh 17/12/11
 * 档案数据 ->录音管理
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "audio",description = "录音管理接口")
public class AudioController extends BaseControllerUtil{
    //日志
    private final static Logger logger = LoggerFactory.getLogger(AudioController.class);

    @Autowired
    private Audioclient audioclient;

    @ApiOperation(value = "录音上传",notes = "上传音频文件")
    @RequestMapping(value = ServiceUrls.FileData.AUDIO,method = RequestMethod.POST)
    public  Result upload(
            @ApiParam(name = "file",value = "上传的文件",defaultValue = "")
            @RequestParam(value = "file",required = true) MultipartFile multipartFile,
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestParam(value = "json_model") String json_model
    ){
        Result<MAudio> result=null;
        try{
            result=audioclient.upload(multipartFile,json_model);
        }catch (Exception e){
            logger.error("上传失败!");
            logger.error(e.getMessage(),e);
            return error("上传失败!");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.FileData.AUDIO,method = RequestMethod.GET)
    @ApiOperation(value = "获取录音数据",notes ="根据条件进行筛选" )
    public Result<MAudio> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page){
        Result<MAudio> result=null;
    try{
        result=audioclient.search(filters,"-createDate",size,page);

    }catch (Exception e){
        logger.error("获取录音列表异常!");
        logger.error(e.getMessage(),e);
        return error("获取录音列表异常!");
    }
        return result;
    }

    @RequestMapping(value = ServiceUrls.FileData.AUDIO,method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID和URL删除录音数据",notes = "根据ID和URL删除录音数据")
    public  Result delete(@ApiParam(value = "录音id+URL",name = "audios",defaultValue = "")
                                @RequestParam(value = "audios",required = false)String audios){
        Result result=null;
        try{
            result=audioclient.delete(audios);
        }catch (Exception e){
            logger.error("删除录音失败!");
            logger.error(e.getMessage(),e);
            return error("删除录音失败!");
        }
        return result;
    }

    @ApiOperation(value = "录音下载",notes = "下载音频文件！")
    @RequestMapping(value = ServiceUrls.FileData.AUDIO_DOWN ,method = RequestMethod.GET)
    public Result down(
            @ApiParam(value = "url",name = "文件路径")
            @RequestParam(value = "url",required = true) String url,
            HttpServletResponse response){
        String realPath ="";
        try {

            if (OsUtil.isWindows()) {//什么系统上
                realPath+=FilePath.AUDIO_FILE_UPLOAD_WIN_PATH;
            } else {
                realPath+=FilePath.AUDIO_FILE_UPLOAD_LUX_PATH;
            }
            realPath+=url;//获取要下载的文件的绝对路径
            String fileName = realPath.substring(realPath.lastIndexOf("\\")+1);//获取要下载的文件名
            //设置content-disposition响应头控制浏览器以下载的形式打开文件，中文文件名要使用URLEncoder.encode方法进行编码，否则会出现文件名乱码
            response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
            InputStream in = new FileInputStream(realPath);//获取文件输入流
            int len = 0;
            byte[] buffer = new byte[1024];
            OutputStream out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer,0,len);//将缓冲区的数据输出到客户端浏览器
            }
            in.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("下载音频失败！"+realPath);
            logger.error(e.getMessage(),e);

        } catch (FileNotFoundException e) {
            logger.error("下载音频失败！"+realPath);
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        } catch (IOException e) {
            logger.error("下载音频失败！"+realPath);
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        }
        return success(null);
    }


}
