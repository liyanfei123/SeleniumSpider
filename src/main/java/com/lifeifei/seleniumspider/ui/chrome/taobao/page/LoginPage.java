package com.lifeifei.seleniumspider.ui.chrome.taobao.page;


import com.lifeifei.seleniumspider.ui.core.element.find.BrowserFindElement;
import com.lifeifei.seleniumspider.ui.core.element.find.LocatorInfo;
import com.lifeifei.seleniumspider.ui.core.enums.LocatorTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.lifeifei.seleniumspider.ui.core.exceptions.SeleniumException;

/**
 * 淘宝登录页
 */
@Slf4j
public class LoginPage extends BasePage{

    private WebElement element;

    public LoginPage(WebDriver webDriver, BrowserFindElement browserFindElement) {
        super(webDriver, browserFindElement);
    }

    /**
     * 获取二维码登陆按钮
     * @return
     */
    public WebElement QRCodeButton() {
        try {
            LocatorInfo locatorInfo = new LocatorInfo();
            locatorInfo.setExpression("i.iconfont.icon-qrcode");
            locatorInfo.setLocatedType(LocatorTypeEnum.ByCssSelector.getType());
            locatorInfo.setIndex(0);
            element = browserFindElement.findElementByType(locatorInfo);
            if (element == null) {
                locatorInfo.setExpression("i.iconfont.icon-qrcode");
                element = browserFindElement.findElementByType(locatorInfo);
            }
            return element;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SeleniumException("未找到 二维码登陆按钮");
        }
    }

    /**
     * 获取二维码
     * @return
     */
    public WebElement QRCode() {
        try {
            LocatorInfo locatorInfo = new LocatorInfo();
            locatorInfo.setExpression("div.qrcode-img > canvas");
            locatorInfo.setLocatedType(LocatorTypeEnum.ByCssSelector.getType());
            locatorInfo.setIndex(0);
            element = browserFindElement.findElementByType(locatorInfo);
            return element;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[LoginPage:QRCode] get QRCode fail!");
            throw new SeleniumException("获取二维码失败");
        }
    }



}
