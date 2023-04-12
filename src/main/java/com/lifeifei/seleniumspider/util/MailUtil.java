package com.lifeifei.seleniumspider.util;

import org.springframework.beans.factory.annotation.Value;

/**
 * 实现邮箱的功能
 */
public class MailUtil {

    @Value("user.mail")
    private Boolean toUserMail;


    /**
     * 发送图片邮箱
     */
    public static void sendPic() {

    }


}
