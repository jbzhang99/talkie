package com.meta.service.qmanage;

import com.meta.dao.qmanage.QUserRepository;
import com.meta.model.datatotal.QGeneralagentDataTotal;
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
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lhq on 2017/9/30.
 */
@Service
@Transactional
public class QUserService extends BaseServiceImpl<QUser, Long> implements BaseService<QUser, Long> {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    //日志
    protected static Logger logger = LoggerFactory.getLogger(QUserService.class);

    @Autowired
    public void setBaseDao(QUserRepository qUserRepository) {
        super.setBaseDao(qUserRepository);
    }

    @Autowired
    private QUserRepository qUserRepository;
    @Autowired
    private QGeneralAgentDataTotalService  qGeneralAgentDataTotalService;


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
            qUserRepository.modifyBalanceByid(id, balance);
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
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
        String balanceSql = "SELECT a.balance  from  talkie_q_user a  where a.user_id= ?";
        Double balance = jdbcTemplate.queryForObject(balanceSql, Double.class, id);
        qtotal.setBalance(balance);
        // 已分配
        String assignSql = "SELECT a.already_balance  from  talkie_q_user a  where a.user_id= ?";
        Double assignQ = jdbcTemplate.queryForObject(assignSql, Double.class, id);
        qtotal.setAlreadyAssign(assignQ);
        return qtotal;
    }


    /**
     * 查询用户QB信息
     *
     * @param userid
     * @return
     */
    public QUser queryUserByUserID(Long userid) {
        System.err.println(userid);
        return qUserRepository.findByUser_Id(userid);
    }


    /**
     * 查询用户余额 并批量更新
     */
    public void batchUpdateQUser() {

        Query query = this.entityManager.createNativeQuery("SELECT a.*  from talkie_q_user a ,talkie_t_user b where a.user_id=b.id and b.merchant_level=7 and b.is_del =1 and b.`status` !=2 and a.balance >0 ", QUser.class);

        List<QUser> list = query.getResultList();

        if (!RegexUtil.isNull(list)) {
            Calendar c = Calendar.getInstance();
            int curryear = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);

            String sql1 = "SELECT count(a.id) from talkie_q_user a ,talkie_t_user b where a.user_id=b.id and b.merchant_level=7 and b.is_del =1 and b.`status` !=2 and a.balance >0";
            Double yearMoney = jdbcTemplate.queryForObject(sql1, Double.class);
            // 新一年的1月需统计 的是上一年的12月
            if(month ==0){
                QGeneralagentDataTotal  data=qGeneralAgentDataTotalService.findByYear(curryear-1) ;
                if(RegexUtil.isNull(data)){
                    QGeneralagentDataTotal   qGeneralagentDataTotal = new QGeneralagentDataTotal();
                    qGeneralagentDataTotal.setYear(curryear);
                    qGeneralagentDataTotal.setDecExpenditure(yearMoney);
                    qGeneralAgentDataTotalService.save(qGeneralagentDataTotal);
                }else {
                    data.setDecExpenditure(yearMoney);
                    qGeneralAgentDataTotalService.save(data);
                }


            }else{
                QGeneralagentDataTotal  data=qGeneralAgentDataTotalService.findByYear(curryear) ;

                if(RegexUtil.isNull(data)){
                    QGeneralagentDataTotal   qGeneralagentDataTotal = new QGeneralagentDataTotal();
                    qGeneralagentDataTotal.setYear(curryear);
                    switch (month){
                        case 1:qGeneralagentDataTotal.setJanExpenditure(yearMoney);
                            break;
                        case 2:qGeneralagentDataTotal.setFebExpenditure(yearMoney);
                            break;
                        case 3:qGeneralagentDataTotal.setMarExpenditure(yearMoney);
                            break;
                        case 4:qGeneralagentDataTotal.setAprExpenditure(yearMoney);
                            break;
                        case 5:qGeneralagentDataTotal.setMayExpenditure(yearMoney);
                            break;
                        case 6:qGeneralagentDataTotal.setJunExpenditure(yearMoney);
                            break;
                        case 7:qGeneralagentDataTotal.setJulExpenditure(yearMoney);
                            break;
                        case 8:qGeneralagentDataTotal.setAugExpenditure(yearMoney);
                            break;
                        case 9:qGeneralagentDataTotal.setSeptExpenditure(yearMoney);
                            break;
                        case 10:qGeneralagentDataTotal.setOctExpenditure(yearMoney);
                            break;
                        case 11:qGeneralagentDataTotal.setNovExpenditure(yearMoney);
                            break;
                    }

                    qGeneralAgentDataTotalService.save(qGeneralagentDataTotal);

                }else{
                    switch (month){
                        case 1:data.setJanExpenditure(yearMoney);
                            break;
                        case 2:data.setFebExpenditure(yearMoney);
                            break;
                        case 3:data.setMarExpenditure(yearMoney);
                            break;
                        case 4:data.setAprExpenditure(yearMoney);
                            break;
                        case 5:data.setMayExpenditure(yearMoney);
                            break;
                        case 6:data.setJunExpenditure(yearMoney);
                            break;
                        case 7:data.setJulExpenditure(yearMoney);
                            break;
                        case 8:data.setAugExpenditure(yearMoney);
                            break;
                        case 9:data.setSeptExpenditure(yearMoney);
                            break;
                        case 10:data.setOctExpenditure(yearMoney);
                            break;
                        case 11:data.setNovExpenditure(yearMoney);
                            break;
                    }
                    qGeneralAgentDataTotalService.save(data);
                }

            }






//            String sql = "UPDATE  talkie_q_user  a set a.balance =?,a.modify_date= DATE_ADD(a.modify_date, INTERVAL 1 month) where a.id= ?";
//            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
//                @Override
//                public int getBatchSize() {
//                    return list.size();
//                }
//
//                @Override
//                public void setValues(PreparedStatement ps, int i)
//                        throws SQLException {
//                    ps.setDouble(1, list.get(i).getBalance() - 1D);
//                    ps.setLong(2, list.get(i).getId());
//
//                }
//
//            });
        }
    }


    /**
     * 查询 普通用户余额过期的用户,并将其设成欠费状态
     */

    public void checkModifyDate() {
        Query query = this.entityManager.createNativeQuery("SELECT a.* from talkie_t_user a , talkie_q_user b  where a.is_del =1   and a.merchant_level = '7' and a.status != '2' and a.id = b.user_id  and TO_DAYS(NOW()) - TO_DAYS(b.modify_date) >1", User.class);
        List<User> list = query.getResultList();

        if (!RegexUtil.isNull(list)) {
            String sql = "UPDATE talkie_t_user a set a.status=2 where a.id=?";
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public int getBatchSize() {
                    return list.size();
                }

                @Override
                public void setValues(PreparedStatement ps, int i)
                        throws SQLException {
                    ps.setDouble(1, list.get(i).getId());

                }

            });
        }
    }


    public List<User> finddate(String date1, String date2) {
        Query query = this.entityManager.createNativeQuery("SELECT b.*  from talkie_q_user a , talkie_t_user b where a.create_date >= ?1 and  a.create_date <= ?2 and a.user_id = b.id", User.class);
        query.setParameter("1", date1);
        query.setParameter("2", date2);
        return query.getResultList();
    }


}
