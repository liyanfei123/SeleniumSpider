package com.lifeifei.seleniumspider.task.Jd;

import com.lifeifei.seleniumspider.core.Result;
import com.lifeifei.seleniumspider.service.JDMaoTaiService;
import com.lifeifei.seleniumspider.task.ITaskHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * jd抢购定时任务
 */
@Slf4j
@EnableAsync // 开启多线程
@Component
public class JDBuyTask extends ITaskHandler {

    @Autowired
    private JDMaoTaiService jdMaoTaiService;

    @Override
    @Async
    @Scheduled(cron = "*/3 * * * * ?")
    public Result<String> execute() throws Exception {
        log.info("开始抢购");
        Thread.sleep(7*1000);
        return null;
    }
}
