package com.lifeifei.seleniumspider.ui.chrome.taobao.module;

import com.lifeifei.seleniumspider.ui.core.element.find.BrowserFindElement;
import org.openqa.selenium.WebDriver;

/**
 * Description:
 *
 * @date:2023/04/08 15:12
 * @author: lyf
 */
public class BaseModule {

    public WebDriver webDriver;

    public BrowserFindElement browserFindElement;

    public BaseModule(WebDriver driver, BrowserFindElement browserFindElement) {
        this.webDriver = driver;
        this.browserFindElement = browserFindElement;
    }

}
