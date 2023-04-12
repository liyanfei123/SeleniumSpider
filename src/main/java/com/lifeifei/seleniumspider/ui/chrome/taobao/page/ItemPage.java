package com.lifeifei.seleniumspider.ui.chrome.taobao.page;

import com.lifeifei.seleniumspider.ui.core.element.find.BrowserFindElement;
import com.lifeifei.seleniumspider.ui.core.element.find.LocatorInfo;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.lifeifei.seleniumspider.ui.core.enums.LocatorTypeEnum;
import com.lifeifei.seleniumspider.ui.core.exceptions.SeleniumException;

/**
 * Description:
 *
 * @date:2023/04/08 18:16
 * @author: lyf
 */
@Slf4j
public class ItemPage extends BasePage {

    private WebElement element;

    public ItemPage(WebDriver webDriver, BrowserFindElement browserFindElement) {
        super(webDriver, browserFindElement);
    }

    /**
     * 判断当前进入的商详是否正确
     * @param expectItem
     * @return
     */
    public Boolean judgeItem(String expectItem) {
        try {
            LocatorInfo locatorInfo = new LocatorInfo();
//            locatorInfo.setExpression("h1[class^=ItemHeader--mainTitle]");
            locatorInfo.setExpression("div[class^=ItemHeader--root]");
            locatorInfo.setLocatedType(LocatorTypeEnum.ByCssSelector.getType());
            locatorInfo.setIndex(0);
            element = browserFindElement.findElementByType(locatorInfo);
            if (element.getText().contains(expectItem)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SeleniumException("未找到 商品标题文案");
        }
    }

    /**
     *购买按钮, 并判断当前是否能够购买
     * 如果可购买的话，会展示"购买文案"
     * 如果当前不可购买，会展示"即将开始，04.12 10:00开售"
     * @return
     */
    public WebElement buyButtonCanBuy() {
        try {
            LocatorInfo locatorInfo = new LocatorInfo();
            locatorInfo.setExpression("button[class*=Actions--primaryBtn] > span");
            locatorInfo.setLocatedType(LocatorTypeEnum.ByCssSelector.getType());
            locatorInfo.setIndex(0); // 这边会找到两个，但是两个会是一样的
            element = browserFindElement.findElementByType(locatorInfo);
            if (element.getText().contains("开售")) {
                log.info(String.format("当前时间：%s，还未开始购买", System.currentTimeMillis()));
                return null;
            } else if (element.getText().contains("卖光了")) {
                log.info(String.format("当前时间：%s，商品卖光了", System.currentTimeMillis()));
                throw new SeleniumException("商品已经卖光了，不再继续抢购");
            } else {
                log.info(String.format("当前时间：%s，商品可购买了", System.currentTimeMillis()));
                return element;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SeleniumException("未找到 购买按钮");
        }
    }


}
