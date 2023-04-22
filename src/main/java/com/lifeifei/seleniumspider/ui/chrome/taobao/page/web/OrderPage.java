package com.lifeifei.seleniumspider.ui.chrome.taobao.page.web;

import com.lifeifei.seleniumspider.ui.chrome.BasePage;
import com.lifeifei.seleniumspider.ui.core.element.find.LocatorInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.lifeifei.seleniumspider.ui.core.element.find.BrowserFindElement;
import com.lifeifei.seleniumspider.ui.core.enums.LocatorTypeEnum;
import com.lifeifei.seleniumspider.ui.core.exceptions.SeleniumException;

/**
 * Description:
 * 订单页
 * @date:2023/04/11 21:30
 * @author: lyf
 */
public class OrderPage extends BasePage {

    public OrderPage(WebDriver driver, BrowserFindElement browserFindElement) {
        super(driver, browserFindElement);
    }
    /**
     * 提交订单按钮
     * @return
     */
    public WebElement submit() {
        try {
            LocatorInfo locatorInfo = new LocatorInfo();
            locatorInfo.setExpression("a.go-btn");
            locatorInfo.setLocatedType(LocatorTypeEnum.ByCssSelector.getType());
            locatorInfo.setIndex(0); // 这边会找到两个，但是两个会是一样的
            element = browserFindElement.findElementByType(locatorInfo);
            return element;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SeleniumException("发生未知错误");
        }
    }
}
