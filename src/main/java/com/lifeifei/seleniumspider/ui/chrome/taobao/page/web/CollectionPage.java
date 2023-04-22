package com.lifeifei.seleniumspider.ui.chrome.taobao.page.web;

import com.lifeifei.seleniumspider.ui.chrome.BasePage;
import com.lifeifei.seleniumspider.ui.core.element.find.BrowserFindElement;
import com.lifeifei.seleniumspider.ui.core.element.find.LocatorInfo;
import com.lifeifei.seleniumspider.ui.core.enums.LocatorTypeEnum;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.lifeifei.seleniumspider.ui.core.exceptions.SeleniumException;

/**
 * Description:
 * 收藏夹
 * @date:2023/04/08 18:07
 * @author: lyf
 */
public class CollectionPage extends BasePage {

    WebElement element;

    public CollectionPage(WebDriver webDriver, BrowserFindElement browserFindElement) {
        super(webDriver, browserFindElement);
    }

    /**
     * 找到期望的商品
     * 暂不支持翻页
     * @return
     */
    public WebElement expectedItem(String expectItemName) {
        try {
            // 浏览器滚动刷新一次
            LocatorInfo locatorInfo = new LocatorInfo();
            locatorInfo.setExpression("a.img-item-title-link");
            locatorInfo.setLocatedType(LocatorTypeEnum.ByCssSelector.getType());
            locatorInfo.setIndex(null);
            locatorInfo.setExpectedTitle(expectItemName);
            element = browserFindElement.findElementByType(locatorInfo);
            if (element == null) {
                throw new SeleniumException("未找到 期望的商品，请先去收藏");
            }
            return element;
        } catch (Exception e) {
            throw new SeleniumException("未找到 期望的商品，请先去收藏");
        }
    }
}
