package com.lifeifei.seleniumspider.ui.core.module;

import com.lifeifei.seleniumspider.ui.core.exceptions.SeleniumException;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@Slf4j
public class FrameOperate {

    /**
     * 切换至指定的frame
     * @param driver
     * @param iframe
     */
    public static void switchAssignIFrame(WebDriver driver, WebElement iframe) {
        try {
            driver.switchTo().frame(iframe);
        } catch (Exception e) {
            log.error("[FrameOperate:switchWindowWithTitle] switch assign iframe error, reason:", e.getStackTrace());
            throw new SeleniumException("切换指定frame出错");
        }
    }

    public static void switchDefaultFrame(WebDriver driver) {
        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            log.error("[FrameOperate:switchDefaultFrame] switch default iframe error, reason:", e.getStackTrace());
            throw new SeleniumException("切换默认frame出错");
        }
    }



}
