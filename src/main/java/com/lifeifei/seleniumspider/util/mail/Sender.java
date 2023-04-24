package com.lifeifei.seleniumspider.util.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sender {

    private String subject;

    private String textContent;

    private String picPath;

    private String htmlContent;
}
