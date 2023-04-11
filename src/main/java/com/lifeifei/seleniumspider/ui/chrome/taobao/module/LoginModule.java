package com.lifeifei.seleniumspider.ui.chrome.taobao.module;

import com.lifeifei.seleniumspider.ui.chrome.taobao.page.LoginPage;
import com.lifeifei.seleniumspider.ui.core.element.JavaScriptEx;
import com.lifeifei.seleniumspider.ui.core.element.find.BrowserFindElement;
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
public class LoginModule extends BaseModule {

    public LoginModule(WebDriver driver, BrowserFindElement browserFindElement) {
        super(driver, browserFindElement);
    }

    /**
     * 用户登陆
     * 若用户登陆失败，需要刷新当前页面，让用户重新登陆
     */
    public void userLogin(Boolean needRefresh) {
        // 获取登陆二维码
        LoginPage loginPage = new LoginPage(webDriver, browserFindElement);
        System.out.println("开始查找：" + System.currentTimeMillis());
        WebElement QRCodeButton = loginPage.QRCodeButton();
        System.out.println("查找结束：" + System.currentTimeMillis());
        if (QRCodeButton == null) {
            if (needRefresh) {
                // 重试一次 获取二维码登陆按钮
                System.out.println("刷新登陆页面");
                webDriver.navigate().refresh();
                userLogin(false);
            } else {
                // TODO: 2023/4/8 失败截图留档
                throw new SeleniumException("登陆页面故障");
            }
        }
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
            return;
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
        browserFindElement.init(webDriver, 60);
        HomePage homePage = new HomePage(webDriver, browserFindElement);
        System.out.println("请在60s内扫码登陆");
        Long t2 = System.currentTimeMillis();
        try {
            WebElement memberNickName = homePage.MemberNickName();
            t2 = System.currentTimeMillis();
            if (memberNickName.getText().contains("你好")) {
                System.out.println("登陆失败,请重试》》》");
                userLogin(true);
            } else {
                System.out.println("恭喜你，登陆成功");
            }
            browserFindElement.init(webDriver, 5);
        } catch (SeleniumException e) {
            if (t2 - t1 > 60*1000-1) {
                // 超过60s，需进行重试，直至成功
                // TODO: 2023/4/8 可改成重试n次，若n次未成功则退出
                // 开启一个新的线程进行时间的监听
                userLogin(true);
            }
        }
    }

}
