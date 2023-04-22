package com.lifeifei.seleniumspider.ui.core.config;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@Slf4j
public class BrowserConfig {


    /**
     * 谷歌浏览器配置
     */
    public ChromeOptions ChromeConfig() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        // 优化加载策略
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        chromeOptions.addArguments("--disable-gpu");//禁止gpu渲染
        chromeOptions.addArguments("–-no-sandbox");//关闭沙盒模式
        chromeOptions.addArguments("--remote-allow-origins=*");
//
        HashMap<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_settings", 2);
        chromeOptions.setExperimentalOption("prefs", prefs);
        chromeOptions.addArguments("blink-settings=imagesEnabled=false");//禁用图片
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-images");
        chromeOptions.addArguments("--disable-css-animations");

        return chromeOptions;
    }


}
