package com.lifeifei.seleniumspider.ui.core.element.wait;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.lifeifei.seleniumspider.ui.core.element.wait.base.BaseWait;
import com.lifeifei.seleniumspider.ui.core.element.wait.base.WaitI;
import com.lifeifei.seleniumspider.ui.core.exceptions.SeleniumException;

import java.time.Duration;

/**
 * Description:
 * 显式等待
 * 页面元素在页面中存在
 * @date:2022/10/24 22:13
 * @author: lyf
 */
@Slf4j
public class ExplicitPresenceWait extends BaseWait implements WaitI {

    @Override
    public void wait(By by) {
        try {
            log.info("[ExplicitPresenceWait:wait] wait by by, by = {}", by);
            this.driverWait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Exception e) {
            log.error("[ExplicitPresenceWait:wait] wait by by error, reason = {}", e);
            throw new SeleniumException("页面元素在页面中存在等待-元素控件未出现");
        }
    }

    @Override
    public void wait(By by, Integer time) throws Exception {
        try {
            log.info("[ExplicitPresenceWait:wait] wait by by & time, by = {}, time = {}",
                    by, time);
            this.driverWait.withTimeout(Duration.ofSeconds(time));
            wait(by);
        } catch (Exception e) {
            throw new SeleniumException(e.getMessage());
        }
    }

    @Override
    public void setTime(Integer time) {
        this.driverWait.withTimeout(Duration.ofSeconds(time));
    }


}
