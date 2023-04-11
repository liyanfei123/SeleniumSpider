package com.lifeifei.seleniumspider.ui.core.exceptions;

/**
 * Description:
 * selenium执行时所抛出的异常
 * @date:2022/11/19 12:03
 * @author: lyf
 */
public class SeleniumException extends RuntimeException {

    public SeleniumException() {}

    public SeleniumException(String msg) {
        super(msg);
    }

}
