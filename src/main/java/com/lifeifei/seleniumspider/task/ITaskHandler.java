package com.lifeifei.seleniumspider.task;

import com.lifeifei.seleniumspider.core.Result;

public abstract class ITaskHandler {

    public ITaskHandler() {
    }

    public abstract Result<String> execute() throws Exception;
}
