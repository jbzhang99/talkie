package com.meta.quartz.job;

import com.meta.file.FilePath;
import com.meta.file.FileUtil;
import com.meta.quartz.config.SpringContextAware;
import com.meta.service.FileData.AudioService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sys.OsUtil;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by y747718944 on 2018/2/5
 * 清理计划
 */
@SuppressWarnings("all")
@DisallowConcurrentExecution
public class CleanUpJob implements Job {
    private final static Logger logger= LoggerFactory.getLogger(CleanUpJob.class);
//    @Autowired
//    private AudioService audioService;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("正在执行 --->文件清理");
        //判断当前操作系统
        String root=OsUtil.isWindows()?FilePath.AUDIO_FILE_UPLOAD_WIN_PATH:FilePath.AUDIO_FILE_UPLOAD_LUX_PATH;
        File rootFile=new File(root);
        DateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -30);//计算30天后的时间
        String oldTime=s.format(c.getTime());
        Date flagTime=c.getTime();
        logger.info("清理文件--->时间小于:"+oldTime);
        //数据库操作
        AudioService audioService= SpringContextAware.getBean("AudioService");
        audioService.deleteByCreateDateLessThan(oldTime);
        //实体类操作
        if (rootFile.exists()) {
            File[] files = rootFile.listFiles();
            if (files.length == 0) {
                logger.info("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    String fileName = file2.getName();
                    try {
                        if (flagTime.getTime()>s.parse(file2.getName()).getTime()){
                            FileUtil.delete(file2.getAbsolutePath());
                            logger.info("清理文件--->删除文件:"+file2.getAbsolutePath()+"成功!");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        logger.error("清理文件--->转换日期失败:"+file2.getName());
                        logger.error(e.getMessage(),e);
                    }
                }
            }
        } else {
            logger.info("文件不存在!");
        }


    }


//    public static void main(String[] args) {
//        logger.info("正在执行 --->文件清理");
//        //判断当前操作系统
//        String root=OsUtil.isWindows()?FilePath.AUDIO_FILE_UPLOAD_WIN_PATH:FilePath.AUDIO_FILE_UPLOAD_LUX_PATH;
//        File rootFile=new File(root);
//        DateFormat s = new SimpleDateFormat("yyyy-MM-dd");
//        Date date=new Date();
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.DATE, -30);//计算30天后的时间
//        String oldTime=s.format(c.getTime());
//        Date flagTime=c.getTime();
//
//        logger.info("清理文件--->时间小于:"+oldTime);
//        //数据库操作
//
//        //实体类操作
//        if (rootFile.exists()) {
//            File[] files = rootFile.listFiles();
//            if (files.length == 0) {
//                logger.info("文件夹是空的!");
//                return;
//            } else {
//                for (File file2 : files) {
//                    String fileName = file2.getName();
//                    try {
//                        if (flagTime.getTime()>s.parse(file2.getName()).getTime()){
//
//                            logger.info("清理文件--->删除文件:"+file2.getName()+"成功!");
//                        }
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                        logger.error("清理文件--->转换日期失败:"+file2.getName());
//                        logger.error(e.getMessage(),e);
//                    }
//                }
//            }
//        } else {
//
//            logger.info("文件不存在!");
//        }
//    }

}
