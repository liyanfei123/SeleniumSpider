package com.lifeifei.seleniumspider.util;

import com.lifeifei.seleniumspider.ui.core.exceptions.SeleniumException;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 实现邮箱的功能
 */
@Slf4j
public class MailUtil {

//    @Value("${user.mail:710662952@qq.com}")
    private static String toUserMail = "710662952@qq.com";

    private final static String robotQQMail = "2795629348@qq.com";

    private final static String robotQQToken = "grwssamrnzukdedg";


    /**
     * 发送图片到QQ邮箱
     */
    public static void sendQQText() {
        try {
            // 邮件套接字协议工厂
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            Properties properties = new Properties();
            properties.put("mail.transport.protocol", "smtp");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.host", "smtp.qq.com");
            properties.setProperty("mail.debug", "true");
            properties.put("mail.user", robotQQMail);
            properties.put("mail.password", robotQQToken);
            // 特别需要注意，要将ssl协议设置为true,否则会报530错误
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);
            properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); // 解决(Could not connect to SMTP host:smtp.exmail.qq.com,port:465)
            Authenticator authenticator = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = properties.getProperty("mail.user");
                    String password = properties.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 创建邮件会话
            Session mailSession = Session.getInstance(properties, authenticator);
            mailSession.setDebug(true);
            // 创建邮件信息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            InternetAddress from = new InternetAddress(properties.getProperty("mail.user"));
            message.setFrom(from);
            // 设置收件人
            System.out.println(toUserMail);
            InternetAddress to = new InternetAddress(toUserMail);
            message.setRecipient(Message.RecipientType.TO, to);
            message.setSubject("登录验证码");
            message.setContent("测试", "text/html;charset=UTF-8");
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[MailUtil:sendQQPic] send qq mail pic fail, reason = {}", e.getStackTrace());
            throw new SeleniumException("邮箱发送失败");
        }
    }

    public static void sendQQPic() {}


    public static void main(String[] args) {
        MailUtil mailUtil = new MailUtil();
    }
}
