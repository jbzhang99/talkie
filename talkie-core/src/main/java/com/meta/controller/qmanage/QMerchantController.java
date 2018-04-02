package com.meta.controller.qmanage;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.datetime.DateTimeUtil;
import com.meta.email.SendEmailUtils;
import com.meta.model.qmanage.*;
import com.meta.model.user.User;
import com.meta.remark.remarkDict;
import com.meta.service.qmanage.QMerchantEventService;
import com.meta.service.qmanage.QMerchantService;
import com.meta.service.qmanage.QUserService;
import com.meta.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lhq on 2017/11/15.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "qmerchant", description = "Q币代理接口")
public class QMerchantController extends BaseControllerUtil {

    @Autowired
    private UserService userService;
    @Autowired
    private QMerchantService qMerchantService;
    @Autowired
    private QMerchantEventService qMerchantEventService;

    @RequestMapping(value = ServiceUrls.QMerchant.Q_MERCHANTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币代理表", notes = "根据查询条件获Q币代理在前端表格展示")
    public Result<QUser> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<QUser> result = qMerchantService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }

    @RequestMapping(value = ServiceUrls.QMerchant.Q_MERCHANTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改Q币代理信息", notes = "创建/修改Q币代理信息")
    public Result<QUser> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid QUser qUser) {
        return success(qMerchantService.save(qUser));
    }

    @RequestMapping(value = ServiceUrls.QMerchant.Q_MERCHANT_BY_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据idQ币代理信息", notes = "根据idQ币代理信息")
    public Result<QUser> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id", required = false) Long id) {
        QUser qUser = qMerchantService.findByUserId(id);
        return success(qUser);
    }


    @RequestMapping(value = ServiceUrls.QMerchant.Q_MERCHANT_MODIFY_BALANCE_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID更改余额(划分/回收)", notes = "根据ID更改余额(回收/划分)")
    public Result modifyBalanceById(
            @ApiParam(name = "sourceId", value = "来源ID", defaultValue = "")
            @RequestParam(value = "sourceId", required = false) Long sourceId,
            @ApiParam(name = "targetId", value = "目标ID", defaultValue = "")
            @RequestParam(value = "targetId", required = false) Long targetId,
            @ApiParam(name = "value", value = "值", defaultValue = "")
            @RequestParam(value = "value", required = false) Double value,
            @ApiParam(name = "type", value = "类型，划分还是收回", defaultValue = "")
            @RequestParam(value = "type", required = false) Integer type,
            @ApiParam(name = "language", value = "语言", defaultValue = "")
            @RequestParam(value = "language", required = false) String language) throws Exception {

        User user = userService.findOne(sourceId);//先判断是否是二级管理
        User userParen = null;
        QUser qUserSource = null;//来源
        User targetUser = null;//目标id
        QUser qUserTarget = null;//目标
        if (user.getMerchantLevel().equals("9")) {//是二级管理操作

            userParen = userService.findOne(user.getParentId());
            qUserSource = qMerchantService.findByUserId(user.getParentId());//二级代理分配的QB实际是父级代理的
        } else {
            qUserSource = qMerchantService.findByUserId(sourceId);

        }
        targetUser = userService.findOne(targetId);
        qUserTarget = qMerchantService.findByUserId(targetId);

        //划分
        if (type == 1) {
            if (value > qUserSource.getBalance()) {
                if ("zh".equals(language)) {
                    return error("划分的值过大，请重新划分 ！");
                } else if ("en".equals(language)) {
                    return error("The value of the partition is too large, please redivide it！");
                }
            }
            qUserSource.setBalance(qUserSource.getBalance() - value);
            qUserTarget.setBalance(qUserTarget.getBalance() + value);
            qMerchantService.save(qUserSource);
            qMerchantService.save(qUserTarget);
            QMerchantEvent qMerchantEvent = new QMerchantEvent();
            qMerchantEvent.setType(1);
            qMerchantEvent.setValue(value);
            qMerchantEvent.setTargetAccount(user.getAccount());
            qMerchantEvent.setTargetUserId(targetId);
            qMerchantEvent.setCreateUser(sourceId);
            //日志处理
            if (user.getMerchantLevel().equals("8")) {//是二级管理操作 来源为代理 但是是二级操作的
                qMerchantEvent.setSourceAccount(userParen.getAccount());
                qMerchantEvent.setSourceUserId(userParen.getId());
            } else {
                qMerchantEvent.setSourceUserId(sourceId);
                qMerchantEvent.setSourceAccount(user.getAccount());
            }

            if ("zh".equals(language)) {
                qMerchantEvent.setRemark(qMerchantEventService.getRemark(user, remarkDict.PLACE, (new Double(Math.abs(value))).intValue() + "", targetUser));

            } else if ("en".equals(language)) {
                qMerchantEvent.setRemark(qMerchantEventService.getRemarkEnglish(user, remarkDict.PLACE_ENGLISH, (new Double(Math.abs(value))).intValue() + "", targetUser));

            }
            qMerchantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new java.util.Date()));
            qMerchantEvent.setUser(user);
            qMerchantEventService.save(qMerchantEvent);
            //多线程发送邮件
            SendEmailUtils.qbVarietyMsg(user.getEamil(), targetUser.getEamil(), qMerchantEvent.getRemark());

            return success("");
        } else if (type == 2) { // 回收
            if (value > qUserTarget.getBalance()) {
                if ("zh".equals(language)) {
                    return error("回收的值过大，请重新划分！");
                } else {
                    return error("The value of the recovery is too large, please redivide it！");
                }
            }
            qUserTarget.setBalance(qUserTarget.getBalance() - value);
            qUserSource.setBalance(qUserSource.getBalance() + value);
            qMerchantService.save(qUserSource);
            qMerchantService.save(qUserTarget);

            QMerchantEvent qMerchantEvent = new QMerchantEvent();
            qMerchantEvent.setType(2);
            qMerchantEvent.setValue(value);
            qMerchantEvent.setCreateUser(sourceId);

            qMerchantEvent.setTargetUserId(targetId);
            qMerchantEvent.setTargetAccount(targetUser.getAccount());
            //日志处理
            if (user.getMerchantLevel().equals("8")) {//是二级管理操作 来源为代理 但是是二级操作的
                qMerchantEvent.setSourceUserId(userParen.getId());
                qMerchantEvent.setSourceAccount(userParen.getAccount());
            } else {
                qMerchantEvent.setSourceUserId(sourceId);
                qMerchantEvent.setSourceAccount(user.getAccount());
            }
            if ("zh".equals(language)) {
                qMerchantEvent.setRemark(qMerchantEventService.getRemark(user, remarkDict.RECOVER, (new Double(Math.abs(value))).intValue() + "", targetUser));
            } else if ("en".equals(language)) {
                qMerchantEvent.setRemark(qMerchantEventService.getRemarkEnglish(user, remarkDict.RECOVER_ENGLISH, (new Double(Math.abs(value))).intValue() + "", targetUser));
            }
            qMerchantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new java.util.Date()));
            qMerchantEvent.setUser(user);
            qMerchantEventService.save(qMerchantEvent);
            //多线程发送邮件
            SendEmailUtils.qbVarietyMsg(user.getEamil(), targetUser.getEamil(), qMerchantEvent.getRemark());
            return success("");
        } else {
            return error("操作失败！");
        }
    }

    @RequestMapping(value = ServiceUrls.QMerchant.Q_MERCHANT_BATCH_ADD_OR_DEL, method = RequestMethod.POST)
    @ApiOperation(value = "批量操作(增/减)Q币", notes = "批量操作(增/减)Q币")
    public Result batchAddOrDel(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) String id,
            @ApiParam(name = "value", value = "value", defaultValue = "")
            @RequestParam(value = "value", required = false) Double value,
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId", required = false) Long userId,
            @ApiParam(name = "language", value = "语言", defaultValue = "")
            @RequestParam(value = "language", required = false) String language) throws Exception {
        String[] ids = id.split(",");
        User user = userService.findOne(userId); // 父Id
        User userParent = null;

        if (user.getMerchantLevel().equals("9")) {//二级代理  实际分配的是其父级代理
            userParent = userService.findOne(user.getParentId());
            userId = userParent.getId();
        }

        Date currDate = new Date();
        // 新增
        if (value > 0) {
            for (int x = 0; x < ids.length; x++) {
                QUser parent = qMerchantService.findByUserId(userId);
                User user1 = userService.findOne(Long.parseLong(ids[x]));
                QUser result = qMerchantService.findByUserId(Long.parseLong(ids[x]));
                Date dt = DateTimeUtil.simpleDateTimeParse(result.getModifyDate());
                //早已是过期的日期
                if (currDate.getTime() > dt.getTime()) {
                    Calendar rightNow = Calendar.getInstance();
                    rightNow.setTime(currDate);
                    rightNow.add(Calendar.MONTH, value.intValue());//日期加
                    //      rightNow.add(Calendar.DAY_OF_YEAR,10);//日期加10天
                    Date dt1 = rightNow.getTime();
                    result.setModifyDate(DateTimeUtil.simpleDateTimeFormat(dt1));
                    result.setBalance(result.getBalance() + value - 1D);
                } else {
                    Calendar rightNow = Calendar.getInstance();
                    rightNow.setTime(dt);
                    rightNow.add(Calendar.MONTH, value.intValue());//日期加
                    //      rightNow.add(Calendar.DAY_OF_YEAR,10);//日期加10天
                    Date dt2 = rightNow.getTime();
                    result.setModifyDate(DateTimeUtil.simpleDateTimeFormat(dt2));
                    result.setBalance(result.getBalance() + value);
                }


                if (result.getBalance() >= 0D) {
                    user1.setStatus("1");
                    userService.save(user1);
                }
                qMerchantService.save(result);
                // 操作方 -q币
                parent.setBalance(parent.getBalance() - value);
                parent.setAlreadyBalance(parent.getAlreadyBalance() + Math.abs(value));
                qMerchantService.save(parent);
                //操作记录
                QMerchantEvent qMerchantEvent = new QMerchantEvent();
                qMerchantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                qMerchantEvent.setType(1);
                qMerchantEvent.setValue(Double.valueOf(value));
                qMerchantEvent.setTargetAccount(user1.getAccount());
                qMerchantEvent.setTargetUserId(Long.parseLong(ids[x]));

                if (user.getMerchantLevel().equals("9")) {//二级代理  实际来源的是其父级代理
                    qMerchantEvent.setSourceUserId(userParent.getId());
                    qMerchantEvent.setSourceAccount(userParent.getAccount());
                } else {
                    qMerchantEvent.setSourceAccount(user.getAccount());
                    qMerchantEvent.setSourceUserId(userId);
                }
                if ("zh".equals(language)) {
                    qMerchantEvent.setRemark(qMerchantEventService.getRemark(user, remarkDict.PLACE, (new Double(Math.abs(value))).intValue() + "", user1));
                } else if ("en".equals(language)) {
                    qMerchantEvent.setRemark(qMerchantEventService.getRemarkEnglish(user, remarkDict.PLACE_ENGLISH, (new Double(Math.abs(value))).intValue() + "", user1));
                }
                qMerchantEvent.setUser(user);
                qMerchantEventService.save(qMerchantEvent);
                //多线程发送邮件
                SendEmailUtils.qbVarietyMsg(user.getEamil(), user1.getEamil(), qMerchantEvent.getRemark());
            }


        } else if (value < 0) {
            for (int x = 0; x < ids.length; x++) {
                QUser parent = qMerchantService.findByUserId(userId);
                User user1 = userService.findOne(Long.parseLong(ids[x]));
                QUser result = qMerchantService.findByUserId(Long.parseLong(ids[x]));
                Calendar rightNow = Calendar.getInstance();
                Date dt = DateTimeUtil.simpleDateTimeParse(result.getModifyDate());
                rightNow.setTime(dt);
                rightNow.add(Calendar.MONTH, value.intValue());//日期加
                //      rightNow.add(Calendar.DAY_OF_YEAR,10);//日期加10天
                Date dt1 = rightNow.getTime();
                result.setModifyDate(DateTimeUtil.simpleDateTimeFormat(dt1));
                result.setBalance(result.getBalance() + value);
                // 操作方 +q币
                parent.setBalance(parent.getBalance() + Math.abs(value));
                parent.setAlreadyBalance(parent.getAlreadyBalance() + Math.abs(value));
                qMerchantService.save(parent);
                qMerchantService.save(result);
//                //操作记录
                QMerchantEvent qMerchantEvent = new QMerchantEvent();
                qMerchantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                qMerchantEvent.setType(2);
                qMerchantEvent.setValue(Double.valueOf(value));
                qMerchantEvent.setTargetAccount(user1.getAccount());
                qMerchantEvent.setTargetUserId(Long.parseLong(ids[x]));
                if (user.getMerchantLevel().equals("9")) {//二级代理  实际来源的是其父级代理
                    qMerchantEvent.setSourceAccount(userParent.getAccount());
                    qMerchantEvent.setSourceUserId(userParent.getId());
                } else {
                    qMerchantEvent.setSourceAccount(user.getAccount());
                    qMerchantEvent.setSourceUserId(userId);
                }
                if ("zh".equals(language)) {
                    qMerchantEvent.setRemark(qMerchantEventService.getRemark(user, remarkDict.RECOVER, (new Double(Math.abs(value))).intValue() + "", user1));
                } else if ("en".equals(language)) {
                    qMerchantEvent.setRemark(qMerchantEventService.getRemark(user, remarkDict.RECOVER_ENGLISH, (new Double(Math.abs(value))).intValue() + "", user1));
                }
                qMerchantEvent.setUser(user);
                qMerchantEventService.save(qMerchantEvent);
                //多线程发送邮件
                SendEmailUtils.qbVarietyMsg(user.getEamil(), user1.getEamil(), qMerchantEvent.getRemark());
            }
        } else {
            return error("操作失败！");
        }
        return success("");

    }

    @RequestMapping(value = ServiceUrls.QMerchant.Q_MERCHANT_TOTAL_Q, method = RequestMethod.GET)
    @ApiOperation(value = "查询可用的Q币 ，及已分配的Q币", notes = "查询可用的Q币 ，及已分配的Q币")
    public Qtotal findQBalance(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id) throws Exception {
        Qtotal qtotal = qMerchantService.totalQBalance(id);
        return qtotal;
    }

}
