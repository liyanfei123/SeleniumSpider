package com.lifeifei.seleniumspider.core;

import lombok.Data;

/**
 * Description:
 *
 * @date:2021/07/14 23:10
 * @author: lyf
 */
@Data
public class Result<T> {

    private int code = 200;
    private String msg;
    private T data;

    public static Result error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static Result error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static Result error(int code, String msg) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static Result ok(String msg) {
        Result r = new Result();
        r.setCode(HttpStatus.SC_OK);
        r.setMsg(msg);
        return r;
    }

    public static Result ok(Object data) {
        Result r = new Result();
        r.setCode(HttpStatus.SC_OK);
        r.setData(data);
        return r;
    }

    public static Result ok() {
        Result r = new Result();
        r.setCode(HttpStatus.SC_OK);
        return r;
    }
}
