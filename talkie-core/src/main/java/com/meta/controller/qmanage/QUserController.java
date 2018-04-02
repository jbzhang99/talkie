package com.meta.controller.qmanage;

import com.meta.*;
import com.meta.datetime.DateTimeUtil;
import com.meta.model.qmanage.QEnterpriseEvent;
import com.meta.model.qmanage.QUser;
import com.meta.model.qmanage.QUserEvent;
import com.meta.model.qmanage.Qtotal;
import com.meta.model.user.User;
import com.meta.remark.remarkDict;
import com.meta.service.qmanage.QEnterpriseEventService;
import com.meta.service.qmanage.QUserEventService;
import com.meta.service.qmanage.QUserService;
import com.meta.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lhq on 2017/9/30.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "quser", description = "Q币用户接口")
public class QUserController extends BaseControllerUtil {
    private final static Logger logger = LoggerFactory.getLogger(QUserController.class);
    @Autowired
    private QUserService qUserService;
    @Autowired
    private QEnterpriseEventService qEnterpriseEventService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = ServiceUrls.QUser.Q_USERS, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币用户表", notes = "根据查询条件获Q币用户在前端表格展示")
    public Result<QUser> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<QUser> result = qUserService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }

    @RequestMapping(value = ServiceUrls.QUser.Q_USERS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改Q币用户信息", notes = "创建/修改Q币用户信息")
    public Result<QUser> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid QUser qUser) {
        return success(qUserService.save(qUser));
    }

    @RequestMapping(value = ServiceUrls.QUser.Q_USER_BY_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取Q币用户信息", notes = "根据id获取Q币用户信息")
    public Result<QUser> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id) {
        QUser qUser = qUserService.findOne(id);
        return success(qUser);
    }


    @RequestMapping(value = ServiceUrls.QUser.Q_USERS, method = RequestMethod.DELETE)
    @ApiOperation(value = "删除Q币用户信息", notes = "根据id删除Q币用户信息")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) {
        qUserService.delete(id);
        return success(null);
    }

    @RequestMapping(value = ServiceUrls.QUser.Q_USER_MODIFY_BALANCE_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID更改余额(回收/划分)", notes = "根据ID更改余额(回收/划分)")
    public Result modifyBalanceById(
            @ApiParam(name = "sourceId", value = "来源ID", defaultValue = "")
            @RequestParam(value = "sourceId", required = false) Long sourceId,
            @ApiParam(name = "targetId", value = "目标ID", defaultValue = "")
            @RequestParam(value = "targetId", required = false) Long targetId,
            @ApiParam(name = "value", value = "值", defaultValue = "")
            @RequestParam(value = "value", required = false) Double value,
            @ApiParam(name = "type", value = "类型，划分还是收回", defaultValue = "")
            @RequestParam(value = "type", required = false) Integer type,
            @ApiParam(name = "currentId", value = "操作者ID", defaultValue = "")
            @RequestParam(value = "currentId", required = false) Long currentId) throws Exception {

        QUser qUserSource = qUserService.queryUserByUserID(sourceId);
        QUser qUserTarget = qUserService.queryUserByUserID(targetId);
        User user = userService.findOne(sourceId);
        User user1 = userService.findOne(targetId);

        //划分
        if (type == 1) {
            if (value > qUserSource.getBalance()) {
                return error("划分的值过大，请重新划分！");
            }
            qUserSource.setBalance(qUserSource.getBalance() - value);
            qUserTarget.setBalance(qUserTarget.getBalance() + value);
            qUserService.save(qUserSource);
            qUserService.save(qUserTarget);

            QEnterpriseEvent qEnterpriseEvent = new QEnterpriseEvent();
            qEnterpriseEvent.setType(1);
            qEnterpriseEvent.setValue(value);
            qEnterpriseEvent.setSourceUserId(sourceId);
            qEnterpriseEvent.setSourceAccount(user.getAccount());
            qEnterpriseEvent.setCreateUser(qUserSource.getId());
            qEnterpriseEvent.setTargetAccount(user1.getAccount());
            qEnterpriseEvent.setRemark(qEnterpriseEventService.getRemark_PLACE(user.getName(), (new Double(Math.abs(value))).intValue()+"",user1.getName()));
            qEnterpriseEvent.setTargetUserId(targetId);
            qEnterpriseEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new java.util.Date()));
            qEnterpriseEvent.setUser(user);
            qEnterpriseEventService.save(qEnterpriseEvent);

            return success("");
        } else if (type == 2) { // 回收
            if (value > qUserTarget.getBalance()) {
                return error("回收的值过大，请重新划分！");
            }
            qUserSource.setBalance(qUserSource.getBalance() + value);
            qUserTarget.setBalance(qUserTarget.getBalance() - value);
            qUserService.save(qUserSource);
            qUserService.save(qUserTarget);


            QEnterpriseEvent qEnterpriseEvent = new QEnterpriseEvent();
            qEnterpriseEvent.setType(2);
            qEnterpriseEvent.setValue(value);
            qEnterpriseEvent.setCreateUser(qUserSource.getId());
            qEnterpriseEvent.setSourceUserId(sourceId);
            qEnterpriseEvent.setTargetUserId(targetId);
            qEnterpriseEvent.setSourceAccount(user.getAccount());
            qEnterpriseEvent.setRemark(remarkDict.RECOVER + value + remarkDict.MONEY + remarkDict.MERCHANT + remarkDict.GIVE + user1.getName());
            qEnterpriseEvent.setTargetAccount(user1.getAccount());
            qEnterpriseEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new java.util.Date()));
            qEnterpriseEvent.setUser(user);
            qEnterpriseEventService.save(qEnterpriseEvent);

            return success("");
        } else {
            return error("操作失败！");
        }
    }

    @RequestMapping(value = ServiceUrls.QUser.Q_USER_TOTAL_Q, method = RequestMethod.GET)
    @ApiOperation(value = "查询可用的Q币 ，及已分配的Q币", notes = "查询可用的Q币 ，及已分配的Q币")
    public Qtotal findQBalance(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id) throws Exception {
        Qtotal qtotal = qUserService.totalQBalance(id);
        return qtotal;
    }

    @RequestMapping(value = ServiceUrls.QUser.Q_USER_QUERY_USER_BY_USER_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据userid查询", notes = "根据userid查询")
    public Result<QUser> findByUserId(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id) throws Exception {
        QUser qUser=qUserService.queryUserByUserID(id);
        return  success(qUser);
    }


    @RequestMapping(value = ServiceUrls.QUser.Q_USER_BATCH_ADD_OR_DEL, method = RequestMethod.POST)
    @ApiOperation(value = "批量操作(增/减)Q币", notes = "批量操作(增/减)Q币")
    public Result batchAddOrDel(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) String id,
            @ApiParam(name = "value", value = "value", defaultValue = "")
            @RequestParam(value = "value", required = false) Double value,
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId", required = false) Long userId) throws Exception {
        String[] ids = id.split(",");
        User user = userService.findOne(userId); // 父Id
        Date currDate = new Date();
        // 新增

        if (value > 0) {
            for (int x = 0; x < ids.length; x++) {
                QUser parent = qUserService.queryUserByUserID(userId);
                User user1 = userService.findOne(Long.parseLong(ids[x]));
                QUser result = qUserService.queryUserByUserID(Long.parseLong(ids[x]));
                Date dt = DateTimeUtil.simpleDateTimeParse(result.getModifyDate());
                //早已是过期的日期
                if (currDate.getTime() > dt.getTime()) {
                    Calendar rightNow = Calendar.getInstance();
                    rightNow.setTime(currDate);
                    rightNow.add(Calendar.MONTH, value.intValue());//日期加
                    //      rightNow.add(Calendar.DAY_OF_YEAR,10);//日期加10天
                    Date dt1 = rightNow.getTime();
                    result.setBalance(result.getBalance() + value - 1D);
                    result.setModifyDate(DateTimeUtil.simpleDateTimeFormat(dt1));
                } else {
                    Calendar rightNow = Calendar.getInstance();
                    rightNow.setTime(dt);
                    rightNow.add(Calendar.MONTH, value.intValue());//日期加
                    //      rightNow.add(Calendar.DAY_OF_YEAR,10);//日期加10天
                    Date dt2 = rightNow.getTime();
                    result.setBalance(result.getBalance() + value);
                    result.setModifyDate(DateTimeUtil.simpleDateTimeFormat(dt2));
                }


                    if (result.getBalance() >= 0D) {
                        user1.setStatus("1");
                        userService.save(user1);
                    }
                    qUserService.save(result);

                // 操作方 -q币
                parent.setBalance(parent.getBalance() - value);
                parent.setAlreadyBalance(parent.getAlreadyBalance() + Math.abs(value));
                qUserService.save(parent);
                //操作记录
                QEnterpriseEvent qEnterpriseEvent = new QEnterpriseEvent();
                qEnterpriseEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                qEnterpriseEvent.setType(1);
                qEnterpriseEvent.setSourceAccount(user.getAccount());
                qEnterpriseEvent.setValue(value);
                qEnterpriseEvent.setSourceUserId(userId);
                qEnterpriseEvent.setTargetAccount(user1.getAccount());
                qEnterpriseEvent.setTargetUserId(Long.parseLong(ids[x]));

                qEnterpriseEvent.setRemark(qEnterpriseEventService.getRemark_PLACE(user.getName(),(new Double(Math.abs(value))).intValue()+"",user1.getName()));

//                qEnterpriseEvent.setRemark(user.getName()+ remarkDict.PLACE + remarkDict.GIVE + remarkDict.USER + user1.getName() + value + remarkDict.MONEY);
                qEnterpriseEvent.setUser(user);
                qEnterpriseEventService.save(qEnterpriseEvent);
            }


        } else if (value < 0) {
            for (int x = 0; x < ids.length; x++) {
                QUser parent = qUserService.queryUserByUserID(userId);
                User user1 = userService.findOne(Long.parseLong(ids[x]));
                QUser result = qUserService.queryUserByUserID(Long.parseLong(ids[x]));
                Calendar rightNow = Calendar.getInstance();
                Date dt = DateTimeUtil.simpleDateTimeParse(result.getModifyDate());
                rightNow.setTime(dt);
                rightNow.add(Calendar.MONTH, value.intValue());//日期加
                //      rightNow.add(Calendar.DAY_OF_YEAR,10);//日期加10天
                Date dt1 = rightNow.getTime();
                result.setModifyDate(DateTimeUtil.simpleDateTimeFormat(dt1));
                result.setBalance(result.getBalance() + value);
                // 操作方 +q币
                parent.setBalance(parent.getBalance() - value);
                parent.setAlreadyBalance(parent.getAlreadyBalance() + Math.abs(value));
                qUserService.save(parent);
                qUserService.save(result);
                //操作记录
                QEnterpriseEvent qEnterpriseEvent = new QEnterpriseEvent();
                qEnterpriseEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                qEnterpriseEvent.setType(2);
                qEnterpriseEvent.setValue(Double.valueOf(value));
                qEnterpriseEvent.setSourceAccount(user.getAccount());
                qEnterpriseEvent.setTargetAccount(user1.getAccount());
                qEnterpriseEvent.setSourceUserId(userId);
                qEnterpriseEvent.setTargetUserId(Long.parseLong(ids[x]));
                qEnterpriseEvent.setRemark(qEnterpriseEventService.getRemark_RECOVER(user.getName(),(new Double(Math.abs(value))).intValue()+"",user1.getName()));
//                qEnterpriseEvent.setRemark(remarkDict.RECOVER + remarkDict.USER + user1.getName() + value + remarkDict.MONEY);
                qEnterpriseEvent.setUser(user);
                qEnterpriseEventService.save(qEnterpriseEvent);
            }
        } else {
            return error("操作失败！");
        }
        return success("");

        }


        @RequestMapping(value = ServiceUrls.QUser.Q_USER_BATCH_MODIFY_BALANCE, method = RequestMethod.POST)
        @ApiOperation(value = "批量更新余额", notes = "批量更新余额")
        public Result batchUpDateBalance () throws Exception {
            qUserService.batchUpdateQUser();
            return success("");
        }

    }
