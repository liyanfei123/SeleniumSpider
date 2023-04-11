package com.lifeifei.seleniumspider.ui.core.module;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import com.lifeifei.seleniumspider.ui.core.exceptions.SeleniumException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Description:
 *
 * @date:2023/04/09 13:49
 * @author: lyf
 */
@Slf4j
public class WindowModule {

    private static String parentWindowHandle;

    public static void switchWindowWithTitle(WebDriver driver, String expectTitle, Integer time) {
        parentWindowHandle = driver.getWindowHandle();
        try {
            if (driver.getTitle().contains(expectTitle)) {
                return;
            }
            Set<String> allWindowHandlerSet = driver.getWindowHandles(); // 获取所有到浏览器窗口
            List<String> allWindowHandlers = new ArrayList<>(allWindowHandlerSet);
            if (!allWindowHandlers.isEmpty()) {
                int loop = 5;
                while (loop >= 1) {
                    // 重试一次，避免加载/刷新导致的title获取不到
                    if (loop == 1) {
                        try {
                            Thread.sleep(time);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            throw new SeleniumException("等待错误");
                        }
                    }
                    for (int i = allWindowHandlers.size() - 1; i >= 0; i--) {
                        driver.switchTo().window(allWindowHandlers.get(i));
                        System.out.println("当前title: " + driver.getTitle());
                        if (driver.getTitle().contains(expectTitle)) {
                            return;
                        }
                    }
                    loop--;
                }
            }
            log.info("[WindowAction:switchWindow] switchWindow execute");
        } catch (Exception e) {
            e.printStackTrace();
            throw new SeleniumException("切换窗口失败");
        }
        System.out.println("切回原来的屏幕");
        driver.switchTo().window(parentWindowHandle);
    }

    public static void test () {
        try {
            if (2 == 2) {
                return;
            }
        } catch (Exception e) {
            throw new SeleniumException("");
        }
        System.out.println("test test");
    }
    public static void main(String[] args) {
        WindowModule windowModule = new WindowModule();
        WindowModule.test();
    }

}
