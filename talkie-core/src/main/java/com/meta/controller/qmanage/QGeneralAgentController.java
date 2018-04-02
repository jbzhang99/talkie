package com.meta.controller.qmanage;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.datetime.DateTimeUtil;
import com.meta.email.SendEmailUtils;
import com.meta.model.datatotal.QGeneralagentDataTotal;
import com.meta.model.datatotal.QMerchantTotal;
import com.meta.model.datatotal.QYearExpenditureDataTotal;
import com.meta.model.qmanage.QGeneralAgentEvent;
import com.meta.model.qmanage.QUser;
import com.meta.model.qmanage.Qtotal;
import com.meta.model.user.User;
import com.meta.regex.RegexUtil;
import com.meta.remark.remarkDict;
import com.meta.service.qmanage.QGeneralAgentEventService;
import com.meta.service.qmanage.QGeneralAgentService;
import com.meta.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lhq on 2017/11/15.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "q_general_agent", description = "Q币总代接口")
public class QGeneralAgentController extends BaseControllerUtil {

    @Autowired
    private QGeneralAgentService qGeneralAgentService;
    @Autowired
    private QGeneralAgentEventService qGeneralAgentEventService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币总代表", notes = "根据查询条件获Q币总代在前端表格展示")
    public Result<QUser> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<QUser> result = qGeneralAgentService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }

    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_TOTAL_BY_YEAR_AND_MONTH, method = RequestMethod.GET)
    @ApiOperation(value = "根据年份及月份 统计代理商的划分/回收记录", notes = "根据年份及月份 统计代理商的划分/回收记录")
    public Result<QMerchantTotal> totalByYearAndMonth(
            @ApiParam(name = "year", value = "year", defaultValue = "")
            @RequestParam(value = "year") String year,
            @ApiParam(name = "month", value = "month", defaultValue = "")
            @RequestParam(value = "month") String month) throws Exception {
        List<QMerchantTotal> qMerchantTotalList = qGeneralAgentService.totalByYearAndMonth(year, month);
        return getResultList(qMerchantTotalList);
    }

    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_YEAR_TOTAL, method = RequestMethod.GET)
    @ApiOperation(value = "获取年度Q币统计信息", notes = "获取年度Q币统计信息")
    public Result<QYearExpenditureDataTotal> expAndEarByYear() {
        List<QYearExpenditureDataTotal> qYearExpenditureDataTotals = qGeneralAgentService.expAndEarByYear();
        return getResultList(qYearExpenditureDataTotals);
    }

    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_DATA_TOTAL, method = RequestMethod.GET)
    @ApiOperation(value = "根据年获取月份统计信息", notes = "根据年获取月份统计信息")
    public Result<QGeneralagentDataTotal> totalByYear(
            @ApiParam(name = "year", value = "year", defaultValue = "")
            @RequestParam(value = "year") Integer year) throws Exception {
        QGeneralagentDataTotal qGeneralagentDataTotal = qGeneralAgentService.totalByYear(year);
        return success(qGeneralagentDataTotal);
    }

    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改Q币企业信息", notes = "创建/修改Q币企业信息")
    public Result<QUser> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid QUser qUser) {
        return success(qGeneralAgentService.save(qUser));
    }

    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_MODIFY_BALANCE_BY_ID, method = RequestMethod.POST)
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
            @ApiParam(name = "language", value = "语言", defaultValue = "")
            @RequestParam(value = "language", required = false) String language) throws Exception {

        QUser qUserTarget = qGeneralAgentService.findByUserID(targetId);
        User user1 = userService.findOne(targetId);
        QUser qUserSource = null;
        User user = userService.findOne(sourceId);
        User userParent = null;
        if (user.getMerchantLevel().equals("9") || user.getMerchantLevel().equals("16")) {//是不是二级管理 判断真实qb来源
            userParent = userService.findOne(user.getParentId());
            qUserSource = qGeneralAgentService.findByUserID(user.getParentId());
        } else {
            qUserSource = qGeneralAgentService.findByUserID(sourceId);
        }

        //划分
        if (type == 1) {

            if (value > qUserSource.getBalance()) {
                if ("zh".equals(language)) {
                    return error("划分的值过大，请重新划分！");
                } else if ("en".equals(language)) {
                    return error("The value of the partition is too large, please redivide it！");
                }
            }
            qUserSource.setBalance(qUserSource.getBalance() - value);
            qUserSource.setAlreadyBalance(qUserSource.getAlreadyBalance() + value);

            qUserTarget.setBalance(qUserTarget.getBalance() + value);
            qGeneralAgentService.save(qUserSource);
            qGeneralAgentService.save(qUserTarget);

            QGeneralAgentEvent qGeneralAgentEvent = new QGeneralAgentEvent();
            qGeneralAgentEvent.setType(1);
            qGeneralAgentEvent.setValue(value);
            qGeneralAgentEvent.setSourceUserId(sourceId);
            qGeneralAgentEvent.setSourceAccount(user.getAccount());
            qGeneralAgentEvent.setCreateUser(user.getId());
            qGeneralAgentEvent.setTargetAccount(user1.getAccount());
            qGeneralAgentEvent.setTargetUserId(targetId);
            if (user.getMerchantLevel().equals("9")) {//是不是二级管理 判断真实qb来源
                qGeneralAgentEvent.setSourceAccount(userParent.getAccount());
                qGeneralAgentEvent.setSourceUserId(userParent.getId());
            } else {
                qGeneralAgentEvent.setSourceUserId(sourceId);
                qGeneralAgentEvent.setSourceAccount(user.getAccount());
            }
            if ("zh".equals(language)) {
                qGeneralAgentEvent.setRemark(qGeneralAgentEventService.getRemark(user, remarkDict.PLACE, (new Double(Math.abs(value))).intValue() + "", user1));

            } else if ("en".equals(language)) {
                qGeneralAgentEvent.setRemark(qGeneralAgentEventService.getRemarkEnglish(user, remarkDict.PLACE_ENGLISH, (new Double(Math.abs(value))).intValue() + "", user1));
            }
//            qGeneralAgentEvent.setRemark(qGeneralAgentEventService.getRemark(user, remarkDict.PLACE, (new Double(Math.abs(value))).intValue() + "", user1));
            qGeneralAgentEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new java.util.Date()));
            qGeneralAgentEvent.setUser(user);
            qGeneralAgentEventService.save(qGeneralAgentEvent);
            //多线程发送邮件
            List<String> toEamils = new ArrayList<>();
            if (user.getEamil() != null) toEamils.add(user.getEamil());//分配人的邮箱
            if (user1.getEamil() != null) toEamils.add(user1.getEamil());//被分配人的邮箱
            if (toEamils.size() > 0 && toEamils != null) {
                String eamilTitle = "数字公网QB分配";
                String context = "日志:" + qGeneralAgentEvent.getRemark();
                SendEmailUtils.ThreadSendQQEamil(eamilTitle, context, toEamils);
            }

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
            qUserSource.setAlreadyBalance(qUserSource.getAlreadyBalance() - value);
            qUserSource.setBalance(qUserSource.getBalance() + value);
            qGeneralAgentService.save(qUserSource);
            qGeneralAgentService.save(qUserTarget);

            QGeneralAgentEvent qGeneralAgentEvent = new QGeneralAgentEvent();
            qGeneralAgentEvent.setType(2);
            qGeneralAgentEvent.setValue(value);
            qGeneralAgentEvent.setCreateUser(user.getId());
            qGeneralAgentEvent.setSourceUserId(sourceId);
            qGeneralAgentEvent.setTargetUserId(targetId);
            qGeneralAgentEvent.setSourceAccount(user.getAccount());
            qGeneralAgentEvent.setTargetAccount(user1.getAccount());

            if (user.getMerchantLevel().equals("9")) {//是不是二级管理 判断真实qb来源
                qGeneralAgentEvent.setSourceUserId(userParent.getId());
                qGeneralAgentEvent.setSourceAccount(userParent.getAccount());
            } else {
                qGeneralAgentEvent.setSourceUserId(sourceId);
                qGeneralAgentEvent.setSourceAccount(user.getAccount());
            }

            if ("zh".equals(language)) {
                qGeneralAgentEvent.setRemark(qGeneralAgentEventService.getRemark(user, remarkDict.RECOVER, (new Double(Math.abs(value))).intValue() + "", user1));
            } else if ("en".equals(language)) {
                qGeneralAgentEvent.setRemark(qGeneralAgentEventService.getRemarkEnglish(user, remarkDict.RECOVER_ENGLISH, (new Double(Math.abs(value))).intValue() + "", user1));
            }

            qGeneralAgentEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new java.util.Date()));
            qGeneralAgentEvent.setUser(user);
            qGeneralAgentEventService.save(qGeneralAgentEvent);
            //多线程发送邮件
            List<String> toEamils = new ArrayList<>();
            if (user.getEamil() != null) toEamils.add(user.getEamil());//分配人的邮箱
            if (user1.getEamil() != null) toEamils.add(user1.getEamil());//被分配人的邮箱
            if (toEamils.size() > 0 && toEamils != null) {
                String eamilTitle = "数字公网QB分配";
                String context = "日志:" + qGeneralAgentEvent.getRemark();
                SendEmailUtils.ThreadSendQQEamil(eamilTitle, context, toEamils);
            }
            return success("");
        } else {
            return error("操作失败！");
        }
    }

    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_TOTAL_Q, method = RequestMethod.GET)
    @ApiOperation(value = "查询可用的Q币 ，及已分配的Q币", notes = "查询可用的Q币 ，及已分配的Q币")
    public Qtotal findQBalance(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id) throws Exception {
        Qtotal qtotal = qGeneralAgentService.totalQBalance(id);
        return qtotal;
    }


    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_UPDATE_BALANCE_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "查询可用的Q币 ，及已分配的Q币", notes = "查询可用的Q币 ，及已分配的Q币")
    public Result<QUser> updateQBalanceById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "balance", value = "balance", defaultValue = "")
            @RequestParam(value = "balance", required = false) Double balance) throws Exception {
        QUser qUser = qGeneralAgentService.findByUserID(id);
        if (RegexUtil.isNull(qUser)) {
            return error("查无此账号");
        }

        boolean flag = qGeneralAgentService.modifyBalanceByid(qUser.getId(), qUser.getBalance() + balance);
        if (flag) {
            User user = userService.findOne(id);
            QGeneralAgentEvent qGeneralAgentEvent = new QGeneralAgentEvent();
            qGeneralAgentEvent.getRemark();
            qGeneralAgentEvent.setType(3);
            qGeneralAgentEvent.setValue(balance);
            qGeneralAgentEvent.setRemark(remarkDict.GeneralAgent + remarkDict.Rechargevalue + balance + remarkDict.MONEY + remarkDict.SUCCESS);
            qGeneralAgentEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new java.util.Date()));
            qGeneralAgentEvent.setUser(user);
            qGeneralAgentEventService.save(qGeneralAgentEvent);
            return success(null);
        } else {
            return error("更改失败");
        }
    }


    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_FIND_BY_USER_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据idQ币总代理信息", notes = "根据idQ币总代理信息")
    public Result<QUser> findByUserId(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id) {
        QUser qUser = qGeneralAgentService.findByUserID(id);
        return success(qUser);
    }

}
