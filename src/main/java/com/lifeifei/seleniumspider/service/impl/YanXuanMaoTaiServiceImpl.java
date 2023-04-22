package com.lifeifei.seleniumspider.service.impl;

import com.lifeifei.seleniumspider.service.YanXuanMaoTaiService;
import com.lifeifei.seleniumspider.ui.core.config.BrowserConfig;
import com.lifeifei.seleniumspider.ui.core.element.find.BrowserFindElement;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Slf4j
@Service
public class YanXuanMaoTaiServiceImpl implements YanXuanMaoTaiService {

    @Autowired
    private BrowserConfig browserConfig;

    private static String yxUrl = "https://you.163.com/";

    private BrowserFindElement browserFindElementWith5s = new BrowserFindElement();

    private WebDriver driver;

    private void init() {
        ChromeOptions chromeOptions = browserConfig.ChromeConfig();
        driver = new ChromeDriver(chromeOptions);
        driver.get(yxUrl);
        browserFindElementWith5s.initWait(driver, 5, 50);
    }
    @Override
    public Boolean execute() {
        init();
        return null;
    }

}
