package com.lifeifei.seleniumspider.ui.core.element.wait;

import lombok.Data;

/**
 * Description:
 * 等待情况信息
 * @date:2022/11/18 19:49
 * @author: lyf
 */
@Data
public class WaitInfo {

    Integer waitMode;

    Integer waitTime;

    public WaitInfo(Integer waitMode, Integer waitTime) {
        this.waitMode = waitMode;
        this.waitTime = waitTime;
    }

}
