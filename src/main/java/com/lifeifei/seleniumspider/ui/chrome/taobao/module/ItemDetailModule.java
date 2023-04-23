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
import com.lifeifei.seleniumspider.util.DateUtil;
import com.lifeifei.seleniumspider.util.code.SlidingCode;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.lifeifei.seleniumspider.ui.core.exceptions.SeleniumException;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @date:2023/04/08 17:59
 * @author: lyf
 */
@Slf4j
public class ItemDetailModule extends BaseModule {

    private final long CAN_BUY_TIME_1 = DateUtil.getAppointTimeStampByHour(10); // 每天20点可购买

    private final long CAN_BUY_TIME_2 = DateUtil.getAppointTimeStampByHour(20); // 每天20点可购买

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
            browserFindElement.initWait(webDriver, 2, 50);
            // 避免提前进入导致触发人机验证
            // 可购买前15s开始刷新
            Long nowTime = System.currentTimeMillis();
            while ((CAN_BUY_TIME_1 - nowTime >= 15*1000 && nowTime < CAN_BUY_TIME_1)  // 上午场
                || (CAN_BUY_TIME_2 - nowTime >= 15*1000 && nowTime > CAN_BUY_TIME_1)) { // 下午场
                log.info("[ItemDetailModule:buyItem] wait time, avoid robot check");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    continue;
                }
                nowTime = System.currentTimeMillis();
            }
            // 监视是否可购买
            WebElement buyButton = null;
            while (true) {
                try {
                    // 先判断是否有出现人机验证
                    // 判断是否需要进行人机验证，即二维码操作
                    WebElement codeFrame = itemPage.verificationCodeIFrame(); // 这个判断的时候会超时
                    if (codeFrame != null) {
                        // 进行人机验证
                        log.info("[ItemDetailModule:buyItem] start robot check");
                        Boolean checkResult = this.robotCheck(codeFrame);
                        log.info("[ItemDetailModule:buyItem] robot check end");
                        if (!checkResult) {
                            log.error("[ItemDetailModule:buyItem] robot check fail");
                            return false;
                        }
                    }
                    buyButton = itemPage.buyButtonCanBuy();
                    if (buyButton != null) { // 当前可购买
                        break;
                    } else {
                        // 不可购买刷新当前页面
                        log.info(String.format("当前时间：%s，不可购买，刷新页面", System.currentTimeMillis()));
                        webDriver.navigate().refresh();
                    }
                } catch (SeleniumException e) {
                    log.error("[ItemDetailModule:buyItem] find buy item button error, reason:", e.getStackTrace());
                    WindowOperate.takeScreenShot(webDriver, "查找购买按钮失败");
                    return false;
                }
            }
            JavaScriptEx.JavaScriptClick(webDriver, buyButton); // 当前页面直接转变为订单提交页

            // 进入订单提交页
            OrderPage orderPage = new OrderPage(webDriver, browserFindElement);
            WindowOperate.switchWindowWithTitle(webDriver, "确认订单", 200); // 切换到确认订单窗口
            try {
                WebElement submitButton = orderPage.submit();
                JavaScriptEx.JavaScriptClick(webDriver, submitButton);
                if (webDriver.getPageSource().contains("库存")) {
                    return false;
                }
                return true;
            } catch (SeleniumException e) {
                log.error("[ItemDetailModule:buyItem] find submit button error, reason:", e.getStackTrace());
                WindowOperate.takeScreenShot(webDriver, "查找订单提交按钮失败");
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
            // 计算滑块的运行轨迹，模拟用户先加速，再减速，每次运动多少距离
            List<Double> traces = new ArrayList<>();
            int threshold = width * 3/5; // 减速阈值
            int current = 0;
            double t = 0.1; // 计算间隔时间
            double v = 0.0; // 初始速度
            double a = 0;
            while (current < width) {
                if (current < threshold) {
                    a = 2; // 加速
                } else {
                    a = -4; // 减速
                }
                // s = v*t+1/2 *a*t^2
                double tmpV = v;
                double distance = tmpV*t + 0.5*a*t*t;
                current += distance;
                traces.add(distance);
            }
            // 补偿超出的情况
            if (current > width) {
                double distance = traces.get(traces.size()-1) - (current - width);
                traces.set(traces.size()-1, distance);
            }
            SlidingCode.move(webDriver, slideButton, traces);

            FrameOperate.switchDefaultFrame(webDriver);
        } catch (SeleniumException e) {
            WindowOperate.takeScreenShot(webDriver, "滑块移动失败");
            log.error("[ItemDetailModule:robotCheck] robot check error, reason:", e.getStackTrace());
            return false;
        }
        return true;
    }


}
