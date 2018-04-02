package com.meta.service.qmanage;

import com.meta.dao.qmanage.QGeneralAgentRepository;
import com.meta.model.datatotal.*;
import com.meta.model.qmanage.QUser;
import com.meta.model.qmanage.Qtotal;
import com.meta.model.user.User;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import com.meta.regex.RegexUtil;
import com.meta.service.datatotal.QGeneralAgentDataTotalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lhq on 2017/11/15.
 */
@Service
@Transactional
public class QGeneralAgentService extends BaseServiceImpl<QUser, Long> implements BaseService<QUser, Long> {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    //日志
    protected static Logger logger = LoggerFactory.getLogger(QUserService.class);

    @Autowired
    public void setBaseDao(QGeneralAgentRepository qGeneralAgentRepository) {
        super.setBaseDao(qGeneralAgentRepository);
    }

    @Autowired
    private QGeneralAgentRepository qGeneralAgentRepository;
    @Autowired
    private QGeneralAgentDataTotalService qGeneralAgentDataTotalService;


    /**
     * 根据年份及月份 统计代理商的划分/回收记录
     */
    public List<QMerchantTotal> totalByYearAndMonth(String year, String month) {

        String s = "SELECT distinct b.* FROM talkie.talkie_q_general_agent_event a , talkie_t_user b where a.traget_user_id=b.id and b.is_del=1 and  b.merchant_level=2  and b.status=1 and a.create_date= "+year+"  and DATE_FORMAT(a.create_date,'%m') ="+month;
        javax.persistence.Query query = this.entityManager.createNativeQuery(s, User.class);

        List<User> userList = query.getResultList();
        List<QMerchantTotal> data = new ArrayList<QMerchantTotal>();
        if (!RegexUtil.isNull(userList)) {

            for (User temp : userList) {

                QMerchantTotal qMerchantTotal = new QMerchantTotal();
                qMerchantTotal.setAccount(temp.getAccount());
                qMerchantTotal.setName(temp.getName());
                qMerchantTotal.setPhone(temp.getPhone());
                //划分
                String str = "SELECT ifnull(sum(a.value),0) FROM talkie.talkie_q_general_agent_event a , talkie_t_user b where   a.traget_user_id=b.id and b.is_del=1 and  b.merchant_level=2  and b.status=1  and a.type=1  and a.create_date= " + year + " and DATE_FORMAT(a.create_date,'%m')= " + month + " and  a.traget_user_id= " + temp.getId();
                Double d = jdbcTemplate.queryForObject(str, Double.class);
                qMerchantTotal.setPlace(d);
                //回收
                String str1 = "SELECT ifnull(sum(a.value),0) FROM talkie.talkie_q_general_agent_event a , talkie_t_user b where   a.traget_user_id=b.id and b.is_del=1 and  b.merchant_level=2  and b.status=1  and a.type=2  and a.create_date= " + year + " and DATE_FORMAT(a.create_date,'%m')= " + month + " and  a.traget_user_id= " + temp.getId();
                Double a = jdbcTemplate.queryForObject(str1, Double.class);
                qMerchantTotal.setRecover( a);
                data.add(qMerchantTotal);
            }
        }
        return data;

    }


    /**
     * 统计以本年度及前推5年的年度 收/支数据
     */
    public List<QYearExpenditureDataTotal> expAndEarByYear() {
        Calendar c = Calendar.getInstance();
        int curryear = c.get(Calendar.YEAR);
        List<QYearExpenditureDataTotal> data = new ArrayList<QYearExpenditureDataTotal>();
        for (int x = 0; x < 5; x++) {

            String sql = "SELECT sum(a.value) as value  FROM talkie.talkie_q_general_agent_event a where a.type=3  and a.create_date=" + (curryear - x);
            String yearMoney = jdbcTemplate.queryForObject(sql, String.class);

            QYearExpenditureDataTotal qYearExpenditureDataTotal = new QYearExpenditureDataTotal();

            QYearEarningDataTotal qYearEarningDataTotal = new QYearEarningDataTotal();
            if (RegexUtil.isNull(yearMoney)) {
                qYearEarningDataTotal.setValue("0");
            } else {
                qYearEarningDataTotal.setValue(yearMoney);
            }
            qYearEarningDataTotal.setName(String.valueOf((curryear - x)));
            qYearExpenditureDataTotal.setqYearEarningDataTotal(qYearEarningDataTotal);


            QGeneralagentDataTotal aa = qGeneralAgentDataTotalService.findByYear(curryear - x);
            Double bb = 0D;
            if (!RegexUtil.isNull(aa)) {

                if (!RegexUtil.isNull(aa.getJanExpenditure())) {
                    bb += aa.getJanExpenditure();
                }
                if (!RegexUtil.isNull(aa.getFebExpenditure())) {
                    bb += aa.getFebExpenditure();
                }
                if (!RegexUtil.isNull(aa.getMarExpenditure())) {
                    bb += aa.getMarExpenditure();
                }
                if (!RegexUtil.isNull(aa.getAprExpenditure())) {
                    bb += aa.getAprExpenditure();
                }
                if (!RegexUtil.isNull(aa.getMayExpenditure())) {
                    bb += aa.getMayExpenditure();
                }
                if (!RegexUtil.isNull(aa.getJunExpenditure())) {
                    bb += aa.getJunExpenditure();
                }
                if (!RegexUtil.isNull(aa.getJulExpenditure())) {
                    bb += aa.getJulExpenditure();
                }
                if (!RegexUtil.isNull(aa.getAugExpenditure())) {
                    bb += aa.getAugExpenditure();
                }
                if (!RegexUtil.isNull(aa.getSeptExpenditure())) {
                    bb += aa.getSeptExpenditure();
                }
                if (!RegexUtil.isNull(aa.getOctExpenditure())) {
                    bb += aa.getOctExpenditure();
                }
                if (!RegexUtil.isNull(aa.getNovExpenditure())) {
                    bb += aa.getNovExpenditure();
                }
                if (!RegexUtil.isNull(aa.getDecExpenditure())) {
                    bb += aa.getDecExpenditure();
                }

                qYearExpenditureDataTotal.setValue(bb.toString());
                qYearExpenditureDataTotal.setName(String.valueOf((curryear - x)));

            } else {
                qYearExpenditureDataTotal.setName(String.valueOf((curryear - x)));
                qYearExpenditureDataTotal.setValue("0");
            }

            data.add(qYearExpenditureDataTotal);

        }
        return data;

    }

    ;


    /**
     * 根据年份统计每个月的数据
     *
     * @param year
     * @return
     */
    public QGeneralagentDataTotal totalByYear(Integer year) {
        String sql = "SELECT sum(a.value) as yearEarning  FROM talkie.talkie_q_general_agent_event a where a.type=3  and a.create_date=" + year;
        Double yearMoney = jdbcTemplate.queryForObject(sql, Double.class);
        QGeneralagentDataTotal data = qGeneralAgentDataTotalService.findByYear(year);
        try {
            if (!RegexUtil.isNull(data)) {

                QGeneralagentEarning qGeneralagentDataTotal = new QGeneralagentEarning();

                String jan = "SELECT IFNULL(SUM(a.value),0) FROM talkie.talkie_q_general_agent_event a where   DATE_FORMAT(a.create_date,'%m')=01   and a.type=3 and a.create_date=" + year;
                qGeneralagentDataTotal.setJanEarning(jdbcTemplate.queryForObject(jan, Double.class));

                String feb = "SELECT IFNULL(SUM(a.value),0) FROM talkie.talkie_q_general_agent_event a where   DATE_FORMAT(a.create_date,'%m')=02   and a.type=3 and a.create_date=" + year;
                qGeneralagentDataTotal.setFebEarning(jdbcTemplate.queryForObject(feb, Double.class));

                String mar = "SELECT IFNULL(SUM(a.value),0) FROM talkie.talkie_q_general_agent_event a where   DATE_FORMAT(a.create_date,'%m')=03   and a.type=3 and a.create_date=" + year;
                qGeneralagentDataTotal.setMarEarning(jdbcTemplate.queryForObject(mar, Double.class));

                String apr = "SELECT IFNULL(SUM(a.value),0) FROM talkie.talkie_q_general_agent_event a where   DATE_FORMAT(a.create_date,'%m')=04   and a.type=3 and a.create_date=" + year;
                qGeneralagentDataTotal.setAprEarning(jdbcTemplate.queryForObject(apr, Double.class));

                String may = "SELECT IFNULL(SUM(a.value),0) FROM talkie.talkie_q_general_agent_event a where   DATE_FORMAT(a.create_date,'%m')=05   and a.type=3 and a.create_date=" + year;
                qGeneralagentDataTotal.setMayEarning(jdbcTemplate.queryForObject(may, Double.class));

                String jun = "SELECT IFNULL(SUM(a.value),0) FROM talkie.talkie_q_general_agent_event a where   DATE_FORMAT(a.create_date,'%m')=06   and a.type=3 and a.create_date=" + year;
                qGeneralagentDataTotal.setJunEarning(jdbcTemplate.queryForObject(jun, Double.class));

                String jul = "SELECT IFNULL(SUM(a.value),0) FROM talkie.talkie_q_general_agent_event a where   DATE_FORMAT(a.create_date,'%m')=07   and a.type=3 and a.create_date=" + year;
                qGeneralagentDataTotal.setJulEarning(jdbcTemplate.queryForObject(jul, Double.class));

                String aug = "SELECT IFNULL(SUM(a.value),0) FROM talkie.talkie_q_general_agent_event a where   DATE_FORMAT(a.create_date,'%m')=08   and a.type=3 and a.create_date=" + year;
                qGeneralagentDataTotal.setAugEarning(jdbcTemplate.queryForObject(aug, Double.class));

                String sept = "SELECT IFNULL(SUM(a.value),0) FROM talkie.talkie_q_general_agent_event a where   DATE_FORMAT(a.create_date,'%m')=09   and a.type=3 and a.create_date=" + year;
                qGeneralagentDataTotal.setSeptEarning(jdbcTemplate.queryForObject(sept, Double.class));

                String oct = "SELECT IFNULL(SUM(a.value),0) FROM talkie.talkie_q_general_agent_event a where   DATE_FORMAT(a.create_date,'%m')=10   and a.type=3 and a.create_date=" + year;
                qGeneralagentDataTotal.setOctEarning(jdbcTemplate.queryForObject(oct, Double.class));

                String nov = "SELECT IFNULL(SUM(a.value),0) FROM talkie.talkie_q_general_agent_event a where   DATE_FORMAT(a.create_date,'%m')=11   and a.type=3 and a.create_date=" + year;
                qGeneralagentDataTotal.setNovEarning(jdbcTemplate.queryForObject(nov, Double.class));

                String dec = "SELECT IFNULL(SUM(a.value),0) FROM talkie.talkie_q_general_agent_event a where   DATE_FORMAT(a.create_date,'%m')=12   and a.type=3 and a.create_date=" + year;
                qGeneralagentDataTotal.setDecEarning(jdbcTemplate.queryForObject(dec, Double.class));

                data.setqGeneralagentEarning(qGeneralagentDataTotal);
                return data;
            }

            // return data;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }


    /**
     * 根据ID，更改余额
     *
     * @param id
     * @param balance
     * @return
     */
    public boolean modifyBalanceByid(Long id, Double balance) {
        boolean flag = false;
        try {
            qGeneralAgentRepository.modifyBalanceByid(id, balance);
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }

    /**
     * 查询用户QB信息
     *
     * @param userid
     * @return
     */
    public QUser findByUserID(Long userid) {
        return qGeneralAgentRepository.findByUser_Id(userid);
    }

    /**
     * 查询可用的Q币 ，及已分配的Q币
     *
     * @param id
     * @return
     */
    public Qtotal totalQBalance(Long id) {
        //可用余额
        Qtotal qtotal = new Qtotal();
        String balanceSql = "SELECT a.balance  from  talkie_q_user a  where a.user_id= ? ";
        Double balance = jdbcTemplate.queryForObject(balanceSql, Double.class, id);
        qtotal.setBalance(balance);
        // 已分配
        String assignSql = "SELECT a.already_balance  from  talkie_q_user a  where a.user_id= ?";
        Double assignQ = jdbcTemplate.queryForObject(assignSql, Double.class, id);
        qtotal.setAlreadyAssign(assignQ);
        return qtotal;
    }


}
