package com.meta.Scheduled;

import com.meta.service.qmanage.QUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by lhq on 2017/11/10.
 * /**
 * 一些定期Q币的操作  基于srping
 */
@Component
public class QModifyInfo {
    @Autowired
    private QUserService qUserService;

    /**
     * 定时检查 用户的结束时间，，如果到期了就变成欠费状态
     */
    @Scheduled(cron = "0 0 4 * * ?")   //每天半夜2点 执行一次
//    @Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次
    public void checkModifyDate() {
        qUserService.checkModifyDate();
    }


    /**
     * 每一个月执行一次，将非欠费的用户,Q币-1
     */

    @Scheduled(cron = "0 15 1 1 * ?")   //每个月1号 0：15分执行
//    @Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次
    public void checkModifyBalance() {
        qUserService.batchUpdateQUser();
    }


}
