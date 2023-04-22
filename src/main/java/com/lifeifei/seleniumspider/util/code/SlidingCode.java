package com.lifeifei.seleniumspider.util.code;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import static org.openqa.selenium.interactions.PointerInput.Kind.MOUSE;

/**
 * 滑动验证码
 * 滑动到底
 */
public class SlidingCode {

    private Actions actions;

    /**
     * 移动滑块
     * @param driver
     * @param button
     * @param traces 滑块轨迹，滑块中存在模拟人类操作
     */
    public void move(WebDriver driver, WebElement button, List<Integer> traces) {
        // 按下滑块按钮
        actions.clickAndHold(button).perform();
        Iterator iterator = traces.iterator();
        while (iterator.hasNext()) {
            // 位移一次
            int dis = (int) iterator.next();
        }
    }

    /**
     * 消除selenium中移动操作的卡顿感
     * 这种卡顿感是因为selenium中自带的moveByOffset是默认有200ms的延时的
     * 可参考:https://blog.csdn.net/fx9590/article/details/113096513
     * @param x x轴方向位移距离
     * @param y y轴方向位移距离
     */
    private void moveWithOutWait(int x, int y) {
        PointerInput defaultMouse = new PointerInput(MOUSE, "default mouse");
        actions.tick(defaultMouse.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.pointer(), x, y)).perform();
    }
}
