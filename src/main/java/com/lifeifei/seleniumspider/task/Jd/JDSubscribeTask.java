package com.lifeifei.seleniumspider.task.Jd;

import com.lifeifei.seleniumspider.core.Result;
import com.lifeifei.seleniumspider.task.ITaskHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * jd预约定时任务
 */
@Slf4j
@EnableAsync
@Component
public class JDSubscribeTask extends ITaskHandler {

    @Override
//    @Async // 开启异步，不用等待上次任务执行完成
//    @Scheduled(cron = "*/1 * * * * ?")
    public Result<String> execute() throws Exception {
        log.info("开始预约");
        Thread.sleep(5*1000);
        return null;
    }
}
