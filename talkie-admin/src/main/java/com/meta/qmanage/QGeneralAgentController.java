package com.meta.qmanage;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.feignclient.qmanage.QGeneralAgentClient;
import com.meta.model.datatotal.MQGeneralagentDataTotal;
import com.meta.model.datatotal.MQMerchantTotal;
import com.meta.model.datatotal.MQYearExpenditureDataTotal;
import com.meta.model.qmanage.MQGeneralAgent;
import com.meta.model.qmanage.MQtotal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by lhq on 2017/11/15.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "q_general_agent", description = "Q币总代接口")
public class QGeneralAgentController extends BaseControllerUtil {
    //日志

    protected static Logger logger = LoggerFactory.getLogger(QGeneralAgentController.class);
    @Autowired
    private QGeneralAgentClient qGeneralAgentClient;

    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取Q币总代表", notes = "根据查询条件获Q币总代在前端表格展示")
    public Result<MQGeneralAgent> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MQGeneralAgent> result = null;
        try {
            result = qGeneralAgentClient.search(filters, "-createDate", size, page);
        } catch (Exception e) {
            logger.error("获取Q币总代失败!");
            logger.error(e.getMessage(),e);
            return error("获取Q币总代失败!");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改Q币企业信息", notes = "创建/修改Q币企业信息")
    public Result<MQGeneralAgent> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MQGeneralAgent mqGeneralAgent) {
        Result<MQGeneralAgent> result = null;
        try {
            result = qGeneralAgentClient.create(mqGeneralAgent);
        } catch (Exception e) {
            logger.error("创建/修改Q币失败!");
            logger.error(e.getMessage(),e);
            return error("创建/修改Q币失败!");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_MODIFY_BALANCE_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID更改余额(回收/划分)", notes = "根据ID更改余额(回收/划分)")
    public Result<MQGeneralAgent> modifyBalanceById(
            @ApiParam(name = "sourceId", value = "来源ID", defaultValue = "")
            @RequestParam(value = "sourceId", required = false) Long sourceId,
            @ApiParam(name = "targetId", value = "目标ID", defaultValue = "")
            @RequestParam(value = "targetId", required = false) Long targetId,
            @ApiParam(name = "value", value = "值", defaultValue = "")
            @RequestParam(value = "value", required = false) Double value,
            @ApiParam(name = "type", value = "类型，划分还是收回", defaultValue = "")
            @RequestParam(value = "type", required = false) Integer type,
            @ApiParam(name = "language", value = "语言", defaultValue = "")
            @RequestParam(value = "language", required = false) String language) {
        Result<MQGeneralAgent> result = null;
        try {
            result = qGeneralAgentClient.modifyBalanceById(sourceId, targetId, value, type, language);
        } catch (Exception e) {
            logger.error("回收/划分Q币失败!");
            logger.error(e.getMessage(),e);
            return error("回收/划分Q币失败!");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_TOTAL_Q, method = RequestMethod.GET)
    @ApiOperation(value = "查询可用的Q币 ，及已分配的Q币", notes = "查询可用的Q币 ，及已分配的Q币")
    public MQtotal findQBalance(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id) throws Exception {
        MQtotal qtotal = qGeneralAgentClient.findQBalance(id);
        return qtotal;
    }


    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_UPDATE_BALANCE_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "查询可用的Q币 ，及已分配的Q币", notes = "查询可用的Q币 ，及已分配的Q币")
    public Result<MQGeneralAgent> updateQBalanceById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "balance", value = "balance", defaultValue = "")
            @RequestParam(value = "balance", required = false) Double balance) throws Exception {
        Result<MQGeneralAgent> result = null;
        try {
            result = qGeneralAgentClient.updateQBalanceById(id, balance);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("回收/划分Q币失败!");
        }
        return result;
    }



    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_FIND_BY_USER_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据idQ币总代理信息", notes = "根据idQ币总代理信息")
    public Result<MQGeneralAgent> findByUserId(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id) {
        Result<MQGeneralAgent> result = null;
        try {
            result = qGeneralAgentClient.findByUserId(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("获取Q币失败!");
        }
        return result;
    }



    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_DATA_TOTAL, method = RequestMethod.GET)
    @ApiOperation(value = "根据月份获取统计信息", notes = "根据月份获取统计信息")
    public Result<MQGeneralagentDataTotal> totalByYear(
            @ApiParam(name = "year", value = "year", defaultValue = "")
            @RequestParam(value = "year") Integer  year) throws Exception {
        Result<MQGeneralagentDataTotal> result = null;
        try {
            result = qGeneralAgentClient.totalByYear(year);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("获取信息失败!");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_YEAR_TOTAL, method = RequestMethod.GET)
    @ApiOperation(value = "获取年度Q币统计信息", notes = "获取年度Q币统计信息")
    public Result<MQYearExpenditureDataTotal> expAndEarByYear() {
        Result<MQYearExpenditureDataTotal> result = null;
        try {
            result = qGeneralAgentClient.expAndEarByYear();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("获取Q币失败!");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.QGeneralAgent.Q_GENERAL_AGENT_TOTAL_BY_YEAR_AND_MONTH, method = RequestMethod.GET)
    @ApiOperation(value = "根据年份及月份 统计代理商的划分/回收记录", notes = "根据年份及月份 统计代理商的划分/回收记录")
    public Result<MQMerchantTotal> totalByYearAndMonth(
            @ApiParam(name = "year", value = "year", defaultValue = "")
            @RequestParam(value = "year") String year,
            @ApiParam(name = "month", value = "month", defaultValue = "")
            @RequestParam(value = "month") String month) throws Exception {
        Result<MQMerchantTotal> result = null;
        try {
            result = qGeneralAgentClient.totalByYearAndMonth(year, month);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return error("获取记录失败!");
        }
        return result;
    }


}
