package com.lifeifei.seleniumspider.ui.chrome.taobao.page;

import com.lifeifei.seleniumspider.ui.core.element.find.BrowserFindElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePage {

    public WebDriver webDriver;

    public BrowserFindElement browserFindElement;

    public WebElement element;

    public BasePage(WebDriver webDriver, BrowserFindElement browserFindElement) {
        this.webDriver = webDriver;
        this.browserFindElement = browserFindElement;
    }

}
