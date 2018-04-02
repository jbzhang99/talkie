//package com.meta.Scheduled;
//
//import com.meta.service.user.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
///**
// * Created by lhq on 2017/11/10.
// * /**
// *  一些定期用户的操作  基于srping
// */
//@Component
//public class ModifyUserInfo {
//
//    @Autowired
//    private UserService userService;
//
//    @Scheduled(cron = "0 0 3 * * ?")   //每天半夜3点 执行一次
//    public void checkModifyDate() {
//        userService.delOverdueUser(365);
//        System.out.println("更新成功！");
//    }
//
//}
