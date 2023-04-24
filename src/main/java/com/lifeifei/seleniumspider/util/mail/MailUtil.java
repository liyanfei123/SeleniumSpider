package com.lifeifei.seleniumspider.util.mail;

import com.lifeifei.seleniumspider.ui.core.exceptions.SeleniumException;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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
     * 获取qq邮件对象
     * @return
     * @throws Exception
     */
    public static MimeMessage QQMailPropInit() throws Exception {
        Properties properties = new Properties();
        // 邮件套接字协议工厂
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.qq.com");
        properties.put("mail.smtp.port", "465");
        properties.setProperty("mail.debug", "true");
        properties.put("mail.user", robotQQMail);
        properties.put("mail.token", robotQQToken);
        // 特别需要注意，要将ssl协议设置为true,否则会报530错误
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); // 解决(Could not connect to SMTP host:smtp.exmail.qq.com,port:465)
        MailAuthenticator authenticator = new MailAuthenticator(properties.getProperty("mail.user"),
                properties.getProperty("mail.token"));
        // 创建邮件会话
        Session mailSession = Session.getInstance(properties, authenticator);
        mailSession.setDebug(true);
        // 创建邮件信息
        MimeMessage message = new MimeMessage(mailSession);
        return message;
    }

    /**
     * 发送文字到QQ邮箱
     */
    public static void sendQQText(Sender sender) {
        try {
            MimeMessage message = QQMailPropInit();
            // 设置发件人
            InternetAddress from = new InternetAddress(robotQQMail);
            message.setFrom(from);
            // 设置收件人
            InternetAddress to = new InternetAddress(toUserMail);
            message.setRecipient(Message.RecipientType.TO, to);
            message.setSubject(sender.getSubject());
            message.setContent(sender.getTextContent(), "text/html;charset=UTF-8");
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[MailUtil:sendQQPic] send qq mail text fail, reason = {}", e.getStackTrace());
            throw new SeleniumException("邮箱发送失败");
        }
    }

    public static void sendQQPic(Sender sender) {
        try {
            sender.setHtmlContent("请于一分钟内扫描验证码：<br/><img src='cid:QRCODE' height='100px'/>");
            MimeMessage message = QQMailPropInit();
            // 设置发件人
            InternetAddress from = new InternetAddress(robotQQMail);
            // 设置收件人
            InternetAddress to = new InternetAddress(toUserMail);
            // 邮件主体容器
            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart image = new MimeBodyPart();
            MimeBodyPart html = new MimeBodyPart();
            // 添加图片
            DataHandler dataHandler = new DataHandler(new FileDataSource(sender.getPicPath()));
            image.setDataHandler(dataHandler);
            image.setContentID("QRCODE"); // 为节点设置一个唯一编号，若需要添加多张图片则需要使用id来区分
            // 设置文本 用处是用来获取邮件内容，并且通过传入的cid:QRCODE来设置图片
            html.setContent(sender.getHtmlContent(),"text/html;charset=utf-8");
            // 将html容器放到总容器中，并设置他们之间的关系
            multipart.addBodyPart(image);
            multipart.addBodyPart(html);
            multipart.setSubType("related");
            message.setFrom(from);
            message.setRecipient(Message.RecipientType.TO, to);
            message.setSubject(sender.getSubject());
            message.setContent(multipart);
//
//            MailcapCommandMap mailcapCommandMap=(MailcapCommandMap) CommandMap.getDefaultCommandMap();
//            mailcapCommandMap.addMailcap("text/html;;x-java-content-handler=com.sun.mail.handlers.text_html");
//            mailcapCommandMap.addMailcap("text/xml;;x-java-context-handler=com.sun.mail.handlers.text_xml");
//            mailcapCommandMap.addMailcap("text/plain;;x-java-content-handler=com.sun.mail.handlers.text_plain");
//            mailcapCommandMap.addMailcap("multipart/*;;x-java-content-handler=com.sun.mail.handlers.text_mixed");
//            mailcapCommandMap.addMailcap("message/rfc822;;x-java-content-handler.sun.mail.handlers.message_rfc822");
//            CommandMap.setDefaultCommandMap(mailcapCommandMap);
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[MailUtil:sendQQPic] send qq mail pic fail, reason = {}", e.getStackTrace());
            throw new SeleniumException("邮箱发送失败");
        }
    }


    public static void main(String[] args) {
        MailUtil mailUtil = new MailUtil();
//        Sender sender = new Sender("测试主题", "测试正文", null, null);
//        MailUtil.sendQQText(sender);
        Sender picSender = new Sender("测试发送图片", null,
                "/Users/liyanfei01/Desktop/st/SeleniumSpider/pic/测试图片2.png", null);
        MailUtil.sendQQPic(picSender);
    }
}
