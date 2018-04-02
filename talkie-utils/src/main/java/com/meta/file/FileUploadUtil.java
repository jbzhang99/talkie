package com.meta.file;

import com.meta.datetime.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import sys.OsUtil;
import org.apache.commons.codec.binary.Base64;
import java.io.*;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author:lhq
 * @date:2017/12/11 9:38
 */
public class FileUploadUtil  {

    //日志
    protected static Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);

    /**
     * 单个文件上传
     *
     * 12/13 ybh修改 增加文件类型
     *
     * fileType 1、IMG(图片类型)  2、AUDIO(音频类型)
     * @param multipartFile
     * @throws Exception
     * @throws IOException
     * @throws IllegalStateException
     */
    public static String upload(MultipartFile multipartFile,String fileType) throws IllegalStateException {
        String originalFilename = multipartFile.getOriginalFilename();
        //源文件
        StringBuffer sourcePath = new StringBuffer();
        //要替换文件
        StringBuffer targetPath = new StringBuffer();
        switch (fileType)
        {
            case "IMG"://类型为图片
                if (OsUtil.isWindows()) {
                    sourcePath.append(FilePath.IMG_FILE_UPLOAD_WIN_PATH);
                } else {
                    sourcePath.append(FilePath.IMG_FILE_UPLOAD_LUX_PATH);
                }
                break;
            case "AUDIO"://类型为音频
                if (OsUtil.isWindows()) {
                    sourcePath.append(FilePath.AUDIO_FILE_UPLOAD_WIN_PATH);


                } else {
                    sourcePath.append(FilePath.AUDIO_FILE_UPLOAD_LUX_PATH);

                }
                break;
            default:
                return  null;
        }

        sourcePath.append(File.separator);
        sourcePath.append(DateUtil.toString(Calendar.getInstance().getTime()));
        createUploadPath(sourcePath.toString());

        sourcePath.append(File.separator);
        sourcePath.append(UUID.randomUUID());
        sourcePath.append("-");
        sourcePath.append(originalFilename);
        File file = new File(sourcePath.toString());
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        String substr = "";

        if (OsUtil.isWindows()) {
            substr = sourcePath.toString().substring(FilePath.IMG_FILE_UPLOAD_WIN_PATH.length() + File.separator.length() + File.separator.length(), sourcePath.toString().length());
        } else {
            substr = sourcePath.toString().substring(FilePath.IMG_FILE_UPLOAD_LUX_PATH.length() + File.separator.length() + File.separator.length(), sourcePath.toString().length());
        }
        substr = substr.replaceAll("\\\\", "/");
        return substr;
    }

    /**
     * 单个APP文件上传
     *
     * @param multipartFile
     * @throws Exception
     * @throws IOException
     * @throws IllegalStateException
     */
    public static String uploadAPPFile(MultipartFile multipartFile,String version) throws IllegalStateException {
        String originalFilename = multipartFile.getOriginalFilename();
        StringBuffer sb = new StringBuffer();
        if (OsUtil.isWindows()) {
            sb.append(FilePath.APP_FILE_UPLOAD_WIN_PATH).append(File.separator).append(version);
        } else {
            sb.append(FilePath.APP_FILE_UPLOAD_LUX_PATH).append(File.separator).append(version);
        }
        createUploadPath(sb.toString());

        sb.append(File.separator);
        sb.append(originalFilename);
        File file = new File(sb.toString());
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        String substr = "";

        if (OsUtil.isWindows()) {
            substr = sb.toString().substring(FilePath.APP_FILE_UPLOAD_WIN_PATH.length() + File.separator.length(), sb.toString().length());
        } else {
            substr = sb.toString().substring(FilePath.APP_FILE_UPLOAD_LUX_PATH.length() + File.separator.length(), sb.toString().length());
        }
        substr = substr.replaceAll("\\\\", "/");
        return substr;
    }

    public static String fileStreamUpload(String fileStream, String suffix){
        StringBuffer sb = new StringBuffer();
        if (OsUtil.isWindows()) {
            sb.append(FilePath.IMG_FILE_UPLOAD_WIN_PATH);
        } else {
            sb.append(FilePath.IMG_FILE_UPLOAD_LUX_PATH);
        }
        sb.append(File.separator);
        sb.append(DateUtil.toString(Calendar.getInstance().getTime()));
        //创建目录
        createUploadPath(sb.toString());

        sb.append(File.separator);
        sb.append(UUID.randomUUID());
        sb.append("." + suffix);
        File file = new File(sb.toString());
        try {

            OutputStream outputStream = new FileOutputStream(file);
            try {
                Base64 b64 = new Base64();
                byte[] stream = b64.decode(fileStream.replaceFirst("^data:image\\/([^;]+);base64,",""));

                outputStream.write(stream);
                outputStream.flush();

            } catch (IOException e) {
                logger.info("文件写入失败 :" + e);
            } finally {
                outputStream.close();
            }

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        String substr = "";

        if (OsUtil.isWindows()) {
            substr = sb.toString().substring(FilePath.IMG_FILE_UPLOAD_WIN_PATH.length() + File.separator.length(), sb.toString().length());
        } else {
            substr = sb.toString().substring(FilePath.IMG_FILE_UPLOAD_LUX_PATH.length() + File.separator.length(), sb.toString().length());
        }

        substr = substr.replaceAll("\\\\", "/");
        return substr;
    }


    public static String getFileByteString(String filePath){
        StringBuffer sb = new StringBuffer();
        if (OsUtil.isWindows()) {
            sb.append(FilePath.IMG_FILE_UPLOAD_WIN_PATH);
        } else {
            sb.append(FilePath.IMG_FILE_UPLOAD_LUX_PATH);
        }
        sb.append(File.separator);
        sb.append(filePath);
        Base64 b64 = new Base64();
        File file = new File(sb.toString());
        try {
            FileInputStream fis = new FileInputStream(file);
            System.out.print(file.length());
            byte[] buffer = new byte[(int) file.length()];
            System.out.print(buffer.length);
            fis.read(buffer);
            fis.close();
            return b64.encodeToString(buffer);
        }catch (FileNotFoundException e) {
            logger.error("{},不存在",sb.toString());
        } catch (IOException e) {
            logger.error("{}",e.getMessage());
        }
        return null;
    }

    public static String uploadStreamDriverAuthentication(String fileStream, String suffix,String useid){
        StringBuffer sb = new StringBuffer();
        if (OsUtil.isWindows()) {
            sb.append(FilePath.DRIVER_UPLOAD_WIN_PATH).append(File.separator).append(useid);
        } else {
            sb.append(FilePath.DRIVER_UPLOAD_LUX_PATH).append(File.separator).append(useid);
        }
        sb.append(File.separator);
        sb.append(DateUtil.toString(Calendar.getInstance().getTime()));
        createUploadPath(sb.toString());

        sb.append(File.separator);
        sb.append(UUID.randomUUID());
        sb.append("." + suffix);
        File file = new File(sb.toString());
        try {

            OutputStream outputStream = new FileOutputStream(file);
            try {
                Base64 b64 = new Base64();
                byte[] stream = b64.decode(fileStream.replaceFirst("^data:image\\/([^;]+);base64,",""));

                outputStream.write(stream);
                outputStream.flush();

            } catch (IOException e) {
                logger.info("文件写入失败 :" + e);
            } finally {
                outputStream.close();
            }

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        String substr = "";

        if (OsUtil.isWindows()) {
            substr = sb.toString().substring(FilePath.DRIVER_UPLOAD_WIN_PATH.length() + File.separator.length(), sb.toString().length());
        } else {
            substr = sb.toString().substring(FilePath.DRIVER_UPLOAD_LUX_PATH.length() + File.separator.length(), sb.toString().length());
        }

        substr = substr.replaceAll("\\\\", "/");
        return substr;
    }

    /**
     * 单个身份证图片上传
     *
     * @param multipartFile
     * @throws Exception
     * @throws IOException
     * @throws IllegalStateException
     */
    public static String uploadIdCard(MultipartFile multipartFile,String useid) throws IllegalStateException {
        String originalFilename = multipartFile.getOriginalFilename();
        StringBuffer sb = new StringBuffer();
        if (OsUtil.isWindows()) {
            sb.append(FilePath.IDCARD_IMG_UPLOAD_WIN_PATH).append(File.separator).append(useid);
        } else {
            sb.append(FilePath.IDCARD_IMG_UPLOAD_LUX_PATH).append(File.separator).append(useid);
        }
        sb.append(File.separator);
        sb.append(DateUtil.toString(Calendar.getInstance().getTime()));
        createUploadPath(sb.toString());

        sb.append(File.separator);
        sb.append(UUID.randomUUID());
        sb.append(originalFilename.substring(originalFilename.lastIndexOf(".")));
        File file = new File(sb.toString());
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        String substr = "";

        if (OsUtil.isWindows()) {
            substr = sb.toString().substring(FilePath.IDCARD_IMG_UPLOAD_WIN_PATH.length() + File.separator.length(), sb.toString().length());
        } else {
            substr = sb.toString().substring(FilePath.IDCARD_IMG_UPLOAD_LUX_PATH.length() + File.separator.length(), sb.toString().length());
        }
        substr = substr.replaceAll("\\\\", "/");
        return substr;
    }


    /**
     * 创建上传文件路径
     *
     * @param
     */
    public static void createUploadPath(String path) {
        File uploadDir = new File(path);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

}
