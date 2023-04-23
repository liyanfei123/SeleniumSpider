package com.lifeifei.seleniumspider.ui.core.module;

import com.lifeifei.seleniumspider.util.DateUtil;
import com.lifeifei.seleniumspider.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.lifeifei.seleniumspider.ui.core.exceptions.SeleniumException;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Description:
 *
 * @date:2023/04/09 13:49
 * @author: lyf
 */
@Slf4j
public class WindowOperate {

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
                    for (int i = 0; i <= allWindowHandlers.size() - 1; i++) { // 逆序
                        driver.switchTo().window(allWindowHandlers.get(i));
                        log.info("now title: {}, expected title: {}", driver.getTitle(), expectTitle);
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
        log.info("switch to origin window");
        driver.switchTo().window(parentWindowHandle);
    }


    public static void takeScreenShot(WebDriver driver, String logInfo) {
        try {
            // 生成日期对象
            Date date = new Date();
            // 生成截图所在的文件夹的名称
            String picDir = "/Users/liyanfei01/Desktop/st/SeleniumSpider/screenshot"
                    + String.valueOf(DateUtil.getYear(date)) + "-"
                    + String.valueOf(DateUtil.getMonth(date)) + "-"
                    + String.valueOf(DateUtil.getDay(date));
            if (! new File(picDir).exists()) {
                FileUtil.createDir(picDir);
            }

            // 生成截图文件
            String filePath = picDir
                    + String.valueOf(DateUtil.getYear(date)) + "-"
                    + String.valueOf(DateUtil.getMonth(date)) + "-"
                    + String.valueOf(DateUtil.getDay(date)) + logInfo + ".png";
            // 进行截图
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
