package com.lifeifei.seleniumspider.ui.core.element.find;

import lombok.Data;

/**
 * Description:
 * 定位方式
 * @date:2022/10/24 21:17
 * @author: lyf
 */
@Data
public class LocatorInfo {

    Integer locatedType;

    String expression;

    // 根据索引来选择操作
    // 若为1个或没有，则默认使用findElement
    private Integer index;

    private String expectedTitle;

}
