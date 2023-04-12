package com.lifeifei.seleniumspider.ui.core.element.find;

import com.alibaba.fastjson.JSON;
import com.lifeifei.seleniumspider.ui.core.element.wait.ExplicitPresenceWait;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.lifeifei.seleniumspider.ui.core.element.wait.base.WaitI;
import com.lifeifei.seleniumspider.ui.core.exceptions.SeleniumException;

import java.time.Duration;
import java.util.List;

/**
 * Description:
 * 浏览器查找元素
 * @date:2022/10/24 21:07
 * @author: lyf
 */
@Slf4j
public class BrowserFindElement {

    private WebDriver driver;

    private WaitI globalWait = new ExplicitPresenceWait();

    private List<WebElement> elements;

    public BrowserFindElement() {}

    public BrowserFindElement(WebDriver webDriver) {
        this.driver = webDriver;
    }

    public void setDriver(WebDriver webDriver) {
        this.driver = webDriver;
    }

    public void init(WebDriver driver) {
        try {
            this.driver = driver;
            // 全局的等待方式
            globalWait.setWebDriverWait(this.driver, 5, 50);
            log.info("[BrowserFindElement:init] init global wait");
        } catch (Exception e) {
            e.printStackTrace();
            throw new SeleniumException("元素等待初始化失败");
        }
    }

    public void init(WebDriver driver, Integer time, Integer sleepTime) {
        try {
            this.driver = driver;
            // 全局的等待方式
            globalWait.setWebDriverWait(this.driver, time, sleepTime);
            log.info("[BrowserFindElement:init] init global wait");
        } catch (Exception e) {
            e.printStackTrace();
            throw new SeleniumException("元素等待初始化失败");
        }
    }

    public WebElement findElementByType(LocatorInfo locatorInfo) {
        String express = locatorInfo.getExpression();
        if (express == null || express.equals("")) {
            return null;
        }
        try {
            By by = FindFormFactory.createBy(locatorInfo.getLocatedType(), express);
            try {
                System.out.println("判断元素存在时间：" + System.currentTimeMillis());
                globalWait.wait(by); // 先确保元素存在
                System.out.println("判断元素结束时间：" + System.currentTimeMillis());
                elements = driver.findElements(by);
            } catch (Exception e) {
                return null;
            }
            if (elements.isEmpty()) {
                throw new SeleniumException(String.format("无当前控件, %s", express));
            }
            System.out.println("寻找元素结束时间：" + System.currentTimeMillis());
            if (locatorInfo.getExpectedTitle() != null && !locatorInfo.getExpectedTitle().trim().equals("")) {
                for (WebElement element : elements) {
                    Boolean flag = element.isDisplayed();
                    String s = element.getAttribute("innerHTML");
                    if (element.getText().contains(locatorInfo.getExpectedTitle())
                            || element.getAttribute("title").contains(locatorInfo.getExpectedTitle())) // 避免在isDisplayed是false时无法获得文案
                    {
                        return element;
                    }
                }
            }
            Integer index = locatorInfo.getIndex();
            if (index == null) {
                return null;
            } else if (index == -1) {
                return elements.get(elements.size() - 1);
            } else {
                return elements.get(index);
            }
        } catch (Exception e) {
            log.error("[BrowserFindElement:findElementByType] can not find element by {}, reason = {}, stack = {}",
                    JSON.toJSONString(locatorInfo), e.getMessage(), e);
            throw new SeleniumException(String.format("查找控件 %s 出现异常", express));
        }
    }

}