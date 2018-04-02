package com.meta.controller.FileData;

import com.meta.*;
import com.meta.commonUtil.CommonDict;
import com.meta.file.FilePath;
import com.meta.file.FileUploadUtil;
import com.meta.file.FileUtil;
import com.meta.json.JacksonUtil;
import com.meta.model.FileData.Audio;
import com.meta.model.user.User;
import com.meta.regex.RegexUtil;
import com.meta.service.FileData.AudioService;
import com.meta.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sys.OsUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "audio",description = "录音数据接口")
public class AudioController  extends BaseControllerUtil{
    //日志
    private final static Logger logger = LoggerFactory.getLogger(AudioController.class);
    //录音
    @Autowired
    private AudioService audioService;
    //用户
    @Autowired
    private UserService userService;

    @ApiOperation(value = "录音上传",notes = "上传音频文件")
    @RequestMapping(value = ServiceUrls.FileData.AUDIO,method = RequestMethod.POST)
    public Result<Audio>  upLoad(
            @ApiParam(name = "file",value = "上传的文件",defaultValue = "")
            @RequestParam(value = "file",required = true) MultipartFile multipartFile,
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestParam(value = "json_model") String json_model
    )throws Exception{
            Audio audio= JacksonUtil.readValue(json_model,Audio.class);
            if (RegexUtil.isNull(audio)) return error("上传失败");
            User user = userService.findByAccount(audio.getAccount());
            if (RegexUtil.isNull(audio)) return error("不存在该用户");
            String uploadUrl= FileUploadUtil.upload(multipartFile, CommonDict.FileType.AUDIO);//上传的文件
            logger.info("有新录音上传地址->"+uploadUrl);
            audio.setCreateUser(user.getId());
            audio.setUser(user);
            audio.setFileUrl(uploadUrl);
            audioService.save(audio);
        return success(null);
    }

    @ApiOperation(value = "获取录音列表",notes = "根据条件查询获取在前端表格展示")
    @RequestMapping(value = ServiceUrls.FileData.AUDIO ,method = RequestMethod.GET)
    public Result<Audio> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page){

            Page<Audio> result=audioService.search(filters,sorts,page,size);
        return getResultList(result.getContent(),result.getTotalElements(),page,size);
    }

    @ApiOperation(value = "删除录音(可批量)",notes = "根据id和url删除录音")
    @RequestMapping(value = ServiceUrls.FileData.AUDIO ,method = RequestMethod.DELETE)
    public Result delete(
            @ApiParam(value = "录音id+URL",name = "audios",defaultValue = "")
            @RequestParam(value = "audios",required = false)String audios) throws Exception{


        if(!StringUtil.isEmpty(audios)){
            String realPath ="";
            if (OsUtil.isWindows()) {//什么系统上
                realPath+=FilePath.AUDIO_FILE_UPLOAD_WIN_PATH;
            } else {
                realPath+=FilePath.AUDIO_FILE_UPLOAD_LUX_PATH;
            }
            String[] temps=audios.split(";;");// id--url;;
            List<String> idAndUrl= Arrays.asList(temps);
            for (String temp:temps){
                String[] audio=temp.split("--");
                String fileUrl=realPath+audio[1];//获取要删除的文件的绝对路径
                // 删除单个文件
                FileUtil.deleteFile(fileUrl);
                logger.info("删除录音记录->"+fileUrl);
                audioService.delete(Long.parseLong(audio[0]));//删除id
            }
        }
        return success(null);
    }


    /**
     * 不需要
     * @param url
     * @param response
     * @return
     */
    @ApiOperation(value = "录音下载",notes = "下载音频文件！")
    @RequestMapping(value = ServiceUrls.FileData.AUDIO_DOWN ,method = RequestMethod.GET)
    public Result down(
            @ApiParam(value = "url",name = "文件路径")
            @RequestParam(value = "url",required = true) String url,
            HttpServletResponse response){
        try {
            String realPath = "D:/upload/audio/2017-12-13/dafd9e0c-e354-4766-a8dd-d101dfce218c-horse.mp3";;//获取要下载的文件的绝对路径
//	            // path是指欲下载的文件的路径。
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
            logger.error("下载音频失败！");
            logger.error(e.getMessage(),e);

        } catch (FileNotFoundException e) {
            logger.error("下载音频失败！");
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        } catch (IOException e) {
            logger.error("下载音频失败！");
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        }
        return success(null);
    }


}
