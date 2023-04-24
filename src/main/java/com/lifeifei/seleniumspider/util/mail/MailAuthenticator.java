package com.lifeifei.seleniumspider.util.mail;

import lombok.Data;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 邮箱验证器，验证密钥是否正确
 */
@Data
public class MailAuthenticator extends Authenticator {

    private String mailUser;

    private String mailToken;

    public MailAuthenticator() {}

    public MailAuthenticator(String mailUser, String mailToken) {
        this.mailUser = mailUser;
        this.mailToken = mailToken;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        // 用户名、密码
        return new PasswordAuthentication(this.mailUser, this.mailToken);
    }

}
