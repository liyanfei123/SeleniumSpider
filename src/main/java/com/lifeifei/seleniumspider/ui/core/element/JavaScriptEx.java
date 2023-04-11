package com.lifeifei.seleniumspider.ui.core.element;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.lifeifei.seleniumspider.ui.core.exceptions.SeleniumException;

/**
 * Description:
 *
 * @date:2023/04/08 17:28
 * @author: lyf
 */
public class JavaScriptEx {

    JavascriptExecutor js;

    public static void JavaScriptClick(WebDriver driver, WebElement element){
        try {
            if (element.isEnabled() && element.isDisplayed()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            } else {
                System.out.println("元素无法单击");
            }
        } catch (NoSuchElementException e) {
            System.out.println("未找到元素");
            throw new SeleniumException("js单击时未找到元素");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("单击异常");
            throw new SeleniumException("js单击时发生异常");
        }
    }

    public static String getValue(WebDriver driver, WebElement element){
        try {
            if (element.isEnabled() && element.isDisplayed()) {
               return (String) ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='inline';", element);
            } else {
                System.out.println("元素无法单击");
            }
        } catch (NoSuchElementException e) {
            System.out.println("未找到元素");
            throw new SeleniumException("js单击时未找到元素");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("单击异常");
            throw new SeleniumException("js单击时发生异常");
        }
        return null;
    }

    public static void scrollingByCoordinateOfPage(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 800)");
    }
}
