package com.lifeifei.seleniumspider.ui.chrome.taobao.module;

import com.lifeifei.seleniumspider.ui.chrome.BaseModule;
import com.lifeifei.seleniumspider.ui.chrome.taobao.page.web.CollectionPage;
import com.lifeifei.seleniumspider.ui.chrome.taobao.page.web.HomePage;
import com.lifeifei.seleniumspider.ui.chrome.taobao.page.web.ItemPage;
import com.lifeifei.seleniumspider.ui.chrome.taobao.page.web.OrderPage;
import com.lifeifei.seleniumspider.ui.core.element.JavaScriptEx;
import com.lifeifei.seleniumspider.ui.core.element.find.BrowserFindElement;
import com.lifeifei.seleniumspider.ui.core.module.FrameOperate;
import com.lifeifei.seleniumspider.ui.core.module.WindowOperate;
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

    private HomePage homePage = null;

    private ItemPage itemPage = null;

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
            WindowOperate.switchWindowWithTitle(webDriver, "收藏夹", 2000); // 切换到收藏夹窗口

            // 进入商品详情
            WebElement expectedItem = collectionPage.expectedItem(itemName);
            JavaScriptEx.JavaScriptClick(webDriver, expectedItem);
            WindowOperate.switchWindowWithTitle(webDriver, itemName, 2000); // 切换到商详窗口
            if (!itemPage.judgeItem(itemName)) {
                throw new SeleniumException("未进入正确的商详页！！！");
            }
        } catch (SeleniumException e) {
            log.error("[taobao:ItemDetailModule:intoItemDetail] into item detail fail, reason = {}", e.getStackTrace());
            throw new SeleniumException(e.getMessage());
        }
    }

    /**
     * 购买商品
     * todo 购买商品频繁刷新会出现人机验证
     * todo 区分web和wap购买
     */
    public Boolean buyItem() {
        try {
            if (itemPage == null) {
                itemPage = new ItemPage(webDriver, browserFindElement);
            }
            WebElement buyButton = null;
            // 监视是否可购买
            while (true) {
                try {
                    try {
                        buyButton = itemPage.buyButtonCanBuy();
                    } catch (SeleniumException e) {
                        // 未找到购买按钮时，判断是否需要进行人机验证，即二维码操作
                        WebElement codeFrame = itemPage.verificationCodeIFrame();
                        if (codeFrame != null) {
                            // 进行人机验证

                            // 验证通过再找一次
                            buyButton = itemPage.buyButtonCanBuy();
                        } else {
                            log.error("[ItemDetailModule:buyItem] buy item error, reason:", e.getStackTrace());
                            return false;
                        }
                    }
                    if (buyButton != null) { // 当前可购买
                        break;
                    } else {
                        // 不可购买刷新当前页面
                        log.info(String.format("当前时间：%s，不可购买，刷新页面", System.currentTimeMillis()));
                        webDriver.navigate().refresh();
                    }
                } catch (SeleniumException e) {
                    log.error("[ItemDetailModule:buyItem] find buy item button error, reason:", e.getStackTrace());
                    return false;
                }
            }
            JavaScriptEx.JavaScriptClick(webDriver, buyButton); // 当前页面直接转变为订单提交页

            // 进入订单提交页
            OrderPage orderPage = new OrderPage(webDriver, browserFindElement);
            WindowOperate.switchWindowWithTitle(webDriver, "确认订单", 200); // 切换到商详窗口
            try {
                WebElement submitButton = orderPage.submit();
                JavaScriptEx.JavaScriptClick(webDriver, submitButton);
                if (webDriver.getPageSource().contains("库存")) {
                    return false;
                }
                return true;
            } catch (SeleniumException e) {
                log.error("未找到提交订单按钮");
                return false;
            }
        } catch (SeleniumException e) {
            log.error("[ItemDetailModule:buyItem] buy item error, reason:", e.getStackTrace());
            return false;
        }
    }

    /**
     * 人机验证
     */
    private Boolean robotCheck(WebElement iframe) {
        try {
            FrameOperate.switchAssignIFrame(webDriver, iframe);
            WebElement slideButton = itemPage.verificationButton();
            WebElement buttonDiv = itemPage.verificationButtonDiv();
            Integer width = buttonDiv.getSize().getWidth();


            FrameOperate.switchDefaultFrame(webDriver);
        } catch (SeleniumException e) {
            log.error("[ItemDetailModule:robotCheck] robot check error, reason:", e.getStackTrace());
            return false;
        }
        return true;
    }


}
