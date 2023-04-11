package com.lifeifei.seleniumspider.service.impl;

import com.lifeifei.seleniumspider.service.MaoTaiService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;
import com.lifeifei.seleniumspider.ui.chrome.taobao.module.ItemDetailModule;
import com.lifeifei.seleniumspider.ui.chrome.taobao.module.LoginModule;
import com.lifeifei.seleniumspider.ui.chrome.taobao.page.HomePage;
import com.lifeifei.seleniumspider.ui.core.element.JavaScriptEx;
import com.lifeifei.seleniumspider.ui.core.element.find.BrowserFindElement;
import com.lifeifei.seleniumspider.ui.core.exceptions.SeleniumException;

import java.util.HashMap;

@Service
@Slf4j
public class MaoTaiServiceImpl implements MaoTaiService {

    private static final String tbUrl = "https://www.taobao.com/";

//    private static final String tbItemUrl = "https://chaoshi.detail.tmall.com/item.htm?id=20739895092&skuId=4227830352490";

    private BrowserFindElement browserFindElementWith5s = new BrowserFindElement();

    private BrowserFindElement browserFindElementWith60s = new BrowserFindElement();

    private WebDriver driver;

    /**
     * 初始化浏览器
     */
    private void taoBaoInit() {

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
        driver.get(tbUrl);
        browserFindElementWith5s.init(driver);
    }

    @Override
    public Boolean taoBaoExecute() {
        try {
            taoBaoInit();

            // 判断页面是否存在"亲，请登录"
            // step1. 用户登陆
            isLogin();

            // step2. 进入商详
            intoDetail();

            // step3. 监视是否可购买
            // 若可购买，直接下单
            ItemDetailModule itemDetailModule = new ItemDetailModule(driver, browserFindElementWith5s);
            itemDetailModule.buyItem();
            System.out.println("恭喜你，购买成功");
            return true;
        } catch (SeleniumException e) {
            e.printStackTrace();
            throw new SeleniumException(e.getMessage());
        } finally {
            driver.quit();
        }
    }

    /**
     * 判断用户是否已登陆
     */
    private boolean isLogin() {
        HomePage homePage = new HomePage(driver, browserFindElementWith5s);
        WebElement loginTip = homePage.loginTip();
        if (loginTip.getText().contains("亲，请登录")) {
            System.out.println("登陆点击开始时间：" + System.currentTimeMillis());
            JavaScriptEx.JavaScriptClick(driver, loginTip);
            System.out.println("登陆点击结束时间：" + System.currentTimeMillis());
            // 用户登陆
            try {
                LoginModule loginModule = new LoginModule(driver, browserFindElementWith5s);
                loginModule.userLogin(true);
            } catch (Exception e) {
                log.info("[MaoTaiServiceImpl:isLogin] refresh web, try login again!");
                driver.navigate().refresh();  // 刷新登陆页
                System.out.println("登陆失败，请重试！！！");
                isLogin();
            }
        }
        return true;
    }



    private void intoDetail() {
//        isLogin(); // 调试用，直接在这个页面登陆
        ItemDetailModule itemDetailModule = new ItemDetailModule(driver, browserFindElementWith5s);
        String expectItem = "飞天53%vol 500ml贵州茅台酒（带杯）白酒酒水";
        try {
            itemDetailModule.intoItemDetail(expectItem);
        } catch (Exception e) {
            throw new SeleniumException(e.getMessage());
        }
    }

}
