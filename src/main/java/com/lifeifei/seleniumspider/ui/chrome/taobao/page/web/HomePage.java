package com.lifeifei.seleniumspider.ui.chrome.taobao.page.web;

import com.lifeifei.seleniumspider.ui.chrome.BasePage;
import com.lifeifei.seleniumspider.ui.core.element.find.BrowserFindElement;
import com.lifeifei.seleniumspider.ui.core.element.find.LocatorInfo;
import com.lifeifei.seleniumspider.ui.core.enums.LocatorTypeEnum;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.lifeifei.seleniumspider.ui.core.exceptions.SeleniumException;

/**
 * 淘宝主页
 */
public class HomePage extends BasePage {

    private WebElement element;

    public HomePage(WebDriver webDriver, BrowserFindElement browserFindElement) {
        super(webDriver, browserFindElement);
    }

    /**
     * 登录提示 亲，请登录
     * @return
     * @throws Exception
     */
    public WebElement loginTip() {
        try {
            LocatorInfo locatorInfo = new LocatorInfo();
            locatorInfo.setExpression("div.site-nav-sign > a.h");
            locatorInfo.setLocatedType(LocatorTypeEnum.ByCssSelector.getType());
            locatorInfo.setIndex(0);
            element = browserFindElement.findElementByType(locatorInfo);
            if (element == null) {
                throw new SeleniumException("未找到登陆提示按钮");
            }
            return element;
        } catch (Exception e) {
            throw new SeleniumException("未找到元素");
        }
    }

    /**
     * 获取用户名
     * @return
     */
    public WebElement MemberNickName() {
        try {
            LocatorInfo locatorInfo = new LocatorInfo();
            locatorInfo.setExpression("strong.J_UserMemberNick.member-nick");
            locatorInfo.setLocatedType(LocatorTypeEnum.ByCssSelector.getType());
            locatorInfo.setIndex(0);
            element = browserFindElement.findElementByType(locatorInfo);
            return element;
        } catch (Exception e) {
            throw new SeleniumException("未找到 用户名元素");
        }
    }

    /**
     * 获取购物车按钮
     * @return
     */
    public WebElement CartButton() {
        try {
            LocatorInfo locatorInfo = new LocatorInfo();
            locatorInfo.setExpression("a.member-cart");
            locatorInfo.setLocatedType(LocatorTypeEnum.ByCssSelector.getType());
            locatorInfo.setIndex(0);
            element = browserFindElement.findElementByType(locatorInfo);
            return element;
        } catch (Exception e) {
            throw new SeleniumException("未找到 用户名元素");
        }
    }

    /**
     * 获取商品收藏按钮
     * @return
     */
    public WebElement CollectionButton() {
        try {
            LocatorInfo locatorInfo = new LocatorInfo();
            locatorInfo.setExpression("a.mytao-collectitem");
            locatorInfo.setLocatedType(LocatorTypeEnum.ByCssSelector.getType());
            locatorInfo.setIndex(0);
            element = browserFindElement.findElementByType(locatorInfo);
            return element;
        } catch (Exception e) {
            throw new SeleniumException("未找到 用户名元素");
        }
    }
}
