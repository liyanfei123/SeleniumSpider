package com.lifeifei.seleniumspider.ui.chrome.taobao.module;

import com.lifeifei.seleniumspider.ui.chrome.taobao.page.LoginPage;
import com.lifeifei.seleniumspider.ui.core.element.JavaScriptEx;
import com.lifeifei.seleniumspider.ui.core.element.find.BrowserFindElement;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.lifeifei.seleniumspider.ui.chrome.taobao.page.HomePage;
import com.lifeifei.seleniumspider.ui.core.exceptions.SeleniumException;

/**
 * Description:
 * 当用户未登陆时，使用该模块
 * @date:2023/04/08 14:47
 * @author: lyf
 */
@Slf4j
public class LoginModule extends BaseModule {

    public LoginModule(WebDriver driver, BrowserFindElement browserFindElement) {
        super(driver, browserFindElement);
    }

    /**
     * 判断用户是否已登陆
     * 如果未登录，则进行登录操作
     */
    public boolean isLogin() {
        HomePage homePage = new HomePage(webDriver, browserFindElement);
        WebElement loginTip = homePage.loginTip();
        if (loginTip.getText().contains("亲，请登录")) {
            JavaScriptEx.JavaScriptClick(webDriver, loginTip);
            // 用户登陆
            try {
                return this.QRlogin();
            } catch (Exception e) {
                log.error("[taobao:LoginModule:isLogin] login fail!");
                return false;
            }
        }
        return true;
    }

    /**
     * 获取登录二维码
     * 并具有重试功能
     * @return
     */
    private Boolean getLoginQR() {
        try {
            int count = 3; // 重试n次
            Boolean first = true;
            while (count >= 1) {
                if (first) {
                    log.info("[taobao:LoginModule:getLoginQR] start login again");
                } else {
                    first = false;
                    log.info("[taobao:LoginModule:getLoginQR] start login");
                }
                LoginPage loginPage = new LoginPage(webDriver, browserFindElement);
                WebElement QRCodeButton = loginPage.QRCodeButton();
                if (QRCodeButton == null) {
                    // 重试一次 获取二维码登陆按钮
                    log.info("[taobao:LoginModule:getLoginQR] refresh login page");
                    webDriver.navigate().refresh();
                    count--;
                    continue;
                } else {
                    log.info("[taobao:LoginModule:getLoginQR] get QR button success");
                    try {
                        log.info("[taobao:LoginModule:QRlogin] click QR button");
                        //        QRCodeButton.click();
                        JavaScriptEx.JavaScriptClick(webDriver, QRCodeButton);
                        Thread.sleep(6000);            // 由于二维码加载慢，故需要等待
                        // TODO: 2023/4/12 添加一个直接判断二维码是否加载完成的步骤
                    } catch (Exception e) {
                        throw new SeleniumException(e.getMessage());
                    }
                    return true;
                }
            }
            // TODO: 2023/4/8 失败截图留档
            log.error("[taobao:LoginModule:getLoginQR] login page error");
            throw new SeleniumException("登录页面故障，无法获取到登录二维码");
        } catch (SeleniumException e) {
            log.error("[taobao:LoginModule:getLoginQR] login error, reason = {}", e.getStackTrace());
            throw new SeleniumException(e.getMessage());

        }
    }

    /**
     * 二维码登录
     * @return
     */
    private Boolean QRlogin() {
        try {
            getLoginQR();
            int count = 3; // 重试n次
            while (count >= 1) {
                LoginPage loginPage = new LoginPage(webDriver, browserFindElement);
                // 截取二维码，并发送给用户邮箱
                WebElement QRCode = loginPage.QRCode();
//            try {
//                String QRpath = "/Users/liyanfei/MyCode/Purchase/Purchase/image/taobao/QR_new.PNG";
//                FileUtil.savePic(QRCode.getScreenshotAs(OutputType.FILE), QRpath);
//                FileUtil.openPic(QRpath);
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new SeleniumException(e.getMessage());
//            }
                browserFindElement.init(webDriver, 5 * 60, 500);
                HomePage homePage = new HomePage(webDriver, browserFindElement);
                log.info("[taobao:LoginModule:QRlogin] wait user scan QR img in 5*60s");
                try {
                    WebElement memberNickName = homePage.MemberNickName(); // 通过等待机制，不断轮询，判断当前用户是否登录成功
                    if (memberNickName.getText().contains("你好")) {
                        // 用户登录失败，进行重试
                        log.info("[taobao:LoginModule:QRlogin] login fail, reLogin start");
                        webDriver.navigate().refresh();
                        getLoginQR();
                        count--;
                        continue;
                    } else {
                        log.info("[taobao:LoginModule:QRlogin] login success");
                        browserFindElement.init(webDriver, 5, 500);
                        return true;
                    }
                } catch (SeleniumException e) {
                    // 用户超时登录失败，进行重试
                    log.error("[taobao:LoginModule:QRlogin] login fail, reLogin start, reason = {}", e.getStackTrace());
                    webDriver.navigate().refresh();
                    getLoginQR();
                    count--;
                    continue;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("[taobao:LoginModule:QRlogin] login fail, reason = {}", e.getStackTrace());
            throw new SeleniumException(e.getMessage());
        }
    }



    /**
     * 用户登陆
     * 若用户登陆失败，需要刷新当前页面，让用户重新登陆
     */
    @Deprecated
    public Boolean userLogin(Boolean needRefresh) {
        // 获取登陆二维码
        if (needRefresh) {
            log.info("[taobao:LoginModule:userLogin] start login again");
        } else {
            log.info("[taobao:LoginModule:userLogin] start login");
        }
        LoginPage loginPage = new LoginPage(webDriver, browserFindElement);
        WebElement QRCodeButton = loginPage.QRCodeButton();
        if (QRCodeButton == null) {
            if (needRefresh) {
                // 重试一次 获取二维码登陆按钮
                log.info("[taobao:LoginModule:userLogin] refresh login page");
                System.out.println("刷新登陆页面");
                webDriver.navigate().refresh();
                return userLogin(false);
            } else {
                // TODO: 2023/4/8 失败截图留档
                log.error("[taobao:LoginModule:userLogin] login page error");
                throw new SeleniumException("登陆页面故障");
            }
        }
        log.info("[taobao:LoginModule:userLogin] click QR button");
        System.out.println("二维码登陆开始点击：" + System.currentTimeMillis());
        try {
            //        QRCodeButton.click();
            JavaScriptEx.JavaScriptClick(webDriver, QRCodeButton);
        } catch (Exception e) {
            throw new SeleniumException(e.getMessage());
        }
        System.out.println("二维码登陆点击结束：" + System.currentTimeMillis());
        try {
            // 由于二维码加载慢，故需要等待
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            return false;
        }
        WebElement element = loginPage.QRCode();

//        try {
//            String QRpath = "/Users/liyanfei/MyCode/Purchase/Purchase/image/taobao/QR_new.PNG";
//            FileUtil.savePic(element.getScreenshotAs(OutputType.FILE), QRpath);
//            FileUtil.openPic(QRpath);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new SeleniumException(e.getMessage());
//        }

        // 用户扫码登陆
        Long t1 = System.currentTimeMillis();
        browserFindElement.init(webDriver, 5*60, 500);
        HomePage homePage = new HomePage(webDriver, browserFindElement);
        System.out.println("请在5min内扫码登陆");
        Long t2 = System.currentTimeMillis();
        try {
            WebElement memberNickName = homePage.MemberNickName(); // 通过等待机制，不断轮询，判断当前用户是否登录成功
            t2 = System.currentTimeMillis();
            if (memberNickName.getText().contains("你好")) {
                System.out.println("登陆失败,请重试》》》");
                userLogin(true);
            } else {
                System.out.println("恭喜你，登陆成功");
            }
            browserFindElement.init(webDriver, 5, 500);
        } catch (SeleniumException e) {
            if (t2 - t1 > 5*60*1000-1) {
                // 超过5*60s，需进行重试，直至成功
                // TODO: 2023/4/8 可改成重试n次，若n次未成功则退出
                // 开启一个新的线程进行时间的监听
                return userLogin(true);
            }
        }
        return true;
    }

}
