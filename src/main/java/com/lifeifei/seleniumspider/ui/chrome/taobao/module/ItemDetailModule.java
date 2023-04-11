package com.lifeifei.seleniumspider.ui.chrome.taobao.module;

import com.lifeifei.seleniumspider.ui.chrome.taobao.page.CollectionPage;
import com.lifeifei.seleniumspider.ui.chrome.taobao.page.HomePage;
import com.lifeifei.seleniumspider.ui.chrome.taobao.page.ItemPage;
import com.lifeifei.seleniumspider.ui.chrome.taobao.page.OrderPage;
import com.lifeifei.seleniumspider.ui.core.element.JavaScriptEx;
import com.lifeifei.seleniumspider.ui.core.element.find.BrowserFindElement;
import com.lifeifei.seleniumspider.ui.core.module.WindowModule;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.lifeifei.seleniumspider.ui.core.exceptions.SeleniumException;

/**
 * Description:
 *
 * @date:2023/04/08 17:59
 * @author: lyf
 */
@Slf4j
public class ItemDetailModule extends BaseModule {

    public ItemDetailModule(WebDriver driver, BrowserFindElement browserFindElement) {
        super(driver, browserFindElement);
    }

    /**
     * 进入商详
     * 这边是通过商品收藏的方式进入的
     */
    public void intoItemDetail(String itemName) {
        try {
            HomePage homePage = new HomePage(webDriver, browserFindElement);
            ItemPage itemPage = new ItemPage(webDriver, browserFindElement);

            // 进入收藏夹
            WebElement collectionButton = homePage.CollectionButton();
            JavaScriptEx.JavaScriptClick(webDriver, collectionButton);
            CollectionPage collectionPage = new CollectionPage(webDriver, browserFindElement);
            WindowModule.switchWindowWithTitle(webDriver, "收藏夹", 2000); // 切换到收藏夹窗口

            // 进入商品详情
            WebElement expectedItem = collectionPage.expectedItem(itemName);
            JavaScriptEx.JavaScriptClick(webDriver, expectedItem);
            WindowModule.switchWindowWithTitle(webDriver, itemName, 2000); // 切换到商详窗口
            if (!itemPage.judgeItem(itemName)) {
                throw new SeleniumException("未进入正确的商详页！！！");
            }
        } catch (SeleniumException e) {
            e.printStackTrace();
            throw new SeleniumException(e.getMessage());
        }
    }

    /**
     * 购买商品
     */
    public WebElement buyItem() {
        try {
            ItemPage itemPage = new ItemPage(webDriver, browserFindElement);
            WebElement buyButton = null;
            while (true) {
                try {
                    buyButton = itemPage.buyButtonCanBuy();
                } catch (SeleniumException e) {
                    throw new SeleniumException(e.getMessage());
                }
                if (buyButton != null) {
                    break;
                } else {
                    // 不可购买刷新当前页面
                    log.info(String.format("当前时间：%s，不可购买，刷新页面", System.currentTimeMillis()));
                    System.out.println(String.format("当前时间：%s，不可购买，刷新页面", System.currentTimeMillis()));
                    webDriver.navigate().refresh();
                }
            }
            JavaScriptEx.JavaScriptClick(webDriver, buyButton); // 当前页面直接转变为订单提交页

            // 进入订单提交页
            OrderPage orderPage = new OrderPage(webDriver, browserFindElement);
            WindowModule.switchWindowWithTitle(webDriver, "确认订单", 200); // 切换到商详窗口
            WebElement submitButton = orderPage.submit();
            JavaScriptEx.JavaScriptClick(webDriver, submitButton);
            return buyButton;
        } catch (SeleniumException e) {
            e.printStackTrace();
            throw new SeleniumException("");
        }
    }


}
