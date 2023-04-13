package com.lifeifei.seleniumspider.service;

/**
 * 京东
 */
public interface JDMaoTaiService {

    /**
     * 京东茅台抢购，需要先预约，才能抢购
     * 先通过一个定时任务，执行预约流程，预约成功后，暂停预约
     * 然后再通过一个定时任务，执行抢购流程，一般为前10分钟开始进行
     * 对于登录的二维码，前十分钟通过邮箱发送给用户
     * @return
     */
    Boolean execute();

}
