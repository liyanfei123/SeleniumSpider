package com.lifeifei.seleniumspider.service.impl;

import com.lifeifei.seleniumspider.service.YanXuanMaoTaiService;
import com.lifeifei.seleniumspider.ui.core.element.find.BrowserFindElement;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Slf4j
@Service
public class YanXuanMaoTaiServiceImpl implements YanXuanMaoTaiService {


    private static String yxUrl = "https://you.163.com/";

    private BrowserFindElement browserFindElementWith5s = new BrowserFindElement();

    private WebDriver driver;

    @Override
    public Boolean execute() {
        init();
        return null;
    }

    private void init() {
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

        driver = new ChromeDriver(chromeOptions);
        driver.get(yxUrl);
        browserFindElementWith5s.init(driver);
    }
}
