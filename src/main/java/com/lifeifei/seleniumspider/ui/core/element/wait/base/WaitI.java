package com.lifeifei.seleniumspider.ui.core.element.wait.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public interface WaitI {

   void wait(By by);

   void wait(By by, Integer time) throws Exception;

   void setWebDriver(WebDriver webDriver);

   void setTime(Integer time);

//   void setIdentity(String identity);

   void setWebDriverWait(WebDriver driver, Integer time);

   /**
    * 指定间隔多少时间判定刷新
    * @param driver
    * @param time
    * @param sleepTime 间隔时间
    */
   void setWebDriverWait(WebDriver driver, Integer time, Integer sleepTime);
}
