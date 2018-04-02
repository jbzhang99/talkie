package com.meta.email;

import com.sun.mail.util.MailSSLSocketFactory;
import org.omg.CORBA.DATA_CONVERSION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by y747718944 on 2018/2/27
 * 发送邮件 工具类
 * 运行原理：
 *          实际上java mail.jar的效果其实就是登陆到xx邮箱然后利用该邮箱进行发送邮件。
 *          大概就是这个流程
 *          邮箱账号需要开启 POP3/SMTP服务 并获得 授权码token
 */
public class    SendEmailUtils {

    /**
     *  1066279646@qq.com   Meta1234
     *  厦门美拓通讯
     *  3月14号可以用
     */

    // 发件人电子邮箱
    private  static  final   String fromEamil = "747718944@qq.com";

    // 指定发送邮件的主机为 smtp.qq.com
    private  static  final  String host = "smtp.qq.com";  //QQ 邮件服务器

    private  static  final String emailToken = "psolcwkbheblbdce";

    private  static final String charset = "UTF-8";

    private  static final Logger logger = LoggerFactory.getLogger(SendEmailUtils.class);

    /**
     * Send QQEmail OneToMany
     * @param emailTitle  邮件标题
     * @param content    邮件主题内容
     * @param toEamilS   需要发送的邮箱多个
     */
   public static void SendQQEmailOneToMany(String emailTitle ,String content ,List<String> toEamilS){
       if (toEamilS == null ||toEamilS.size()<1) return;
       try {
           // 获取系统属性
           Properties properties = System.getProperties();
           // 设置邮件服务器
           properties.setProperty("mail.smtp.host", host);
           properties.put("mail.smtp.auth", "true");
           //QQ邮箱需要设置ssl加密
           MailSSLSocketFactory sf = new MailSSLSocketFactory();
           sf.setTrustAllHosts(true);
           properties.put("mail.smtp.ssl.enable", "true");
           properties.put("mail.smtp.ssl.socketFactory", sf);
           // 获取默认session对象
           Session session = Session.getDefaultInstance(properties, new Authenticator() {
               public PasswordAuthentication getPasswordAuthentication() {
                   return new PasswordAuthentication(fromEamil, emailToken); //发件人邮件用户名、授权码
               }
           });
           MimeMessage message = new MimeMessage(session);
           message.setFrom(new InternetAddress(fromEamil));
           toEamilS.forEach(item -> {
               try {
                   message.addRecipient(Message.RecipientType.TO,
                           new InternetAddress(item));
               } catch (MessagingException e) {
                   e.printStackTrace();
               }
           });
           //邮件标题
           message.setSubject(emailTitle, charset);
           //邮件内容
           message.setText(content,charset);
           message.setSentDate(new Date());
           // 发送消息
           Transport.send(message);
       }catch (GeneralSecurityException e){
           e.printStackTrace();
       }catch (MessagingException mex) {
           mex.printStackTrace();
       }
    }


    /**
     * Send QQEmail OneToOne
     * @param emailTitle  邮件标题
     * @param content    邮件主题内容
     * @param toEamil   需要发送的邮箱
     */
    public static void SendQQEmailOneToOne(String emailTitle ,String content ,String  toEamil){
        try {
            // 获取系统属性
            Properties properties = System.getProperties();

            // 设置邮件服务器
            properties.setProperty("mail.smtp.host", host);
            properties.put("mail.smtp.auth", "true");
            //QQ邮箱需要设置ssl加密
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);
            // 获取默认session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEamil, emailToken); //发件人邮件用户名、授权码
                }
            });
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEamil));

            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(toEamil));

            //邮件标题
            message.setSubject(emailTitle, charset);
            //邮件内容
            message.setText(content,charset);
            message.setSentDate(new Date());
            // 发送消息
            Transport.send(message);
        }catch (GeneralSecurityException e){
            e.printStackTrace();
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


    /**
     * 多线程发送邮件
     * @param emailTitle
     * @param content
     * @param toEamil
     */
    public static  void  ThreadSendQQEamil(String emailTitle ,String content ,String  toEamil){
        new Thread(()->{
            SendQQEmailOneToOne(emailTitle,content,toEamil);
        }).start();
    }

    /**
     * 多线程发送邮件
     * @param emailTitle
     * @param content
     * @param toEamils
     */
    public static  void  ThreadSendQQEamil(String emailTitle ,String content ,List<String> toEamils){

        new Thread(()->{
            SendQQEmailOneToMany(emailTitle,content, toEamils);
        }).start();
    }

    public static void qbVarietyMsg(String eamilOne,String eamilTwo,String remark){
        List<String> toEamils = new ArrayList<>();
        if (eamilOne != null) toEamils.add(eamilOne);//分配人的邮箱
        if (eamilTwo != null) toEamils.add(eamilTwo);//被分配人的邮箱
        String eamilTitle="数字公网QB分配";
        String context="日志:"+remark;
        if (toEamils.size() >0 && toEamils !=null) {
            ThreadSendQQEamil(eamilTitle, context, toEamils);
        }
    }

    public static void main(String[] args) {
        ThreadSendQQEamil("测试","ces","1589593447@qq.com");
        System.err.println("ok");
    }


}
