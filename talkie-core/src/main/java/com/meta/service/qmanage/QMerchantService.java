package com.meta.service.qmanage;

import com.meta.dao.qmanage.QMerchantRepository;
import com.meta.dao.qmanage.QUserRepository;
import com.meta.model.qmanage.QUser;
import com.meta.model.qmanage.Qtotal;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by lhq on 2017/11/15.
 */
@Service
@Transactional
public class QMerchantService  extends BaseServiceImpl<QUser, Long> implements BaseService<QUser, Long> {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    //日志
    protected static Logger logger = LoggerFactory.getLogger(QUserService.class);

    @Autowired
    public void setBaseDao(QMerchantRepository qMerchantRepository) {
        super.setBaseDao(qMerchantRepository);
    }

    @Autowired
    private QMerchantRepository qMerchantRepository;

public  QUser findByUserId(Long  id ){
    return  qMerchantRepository.findByUser_Id(id);
}


    /**
     * 根据ID，更改余额
     * @param id
     * @param balance
     * @return
     */
    public boolean modifyBalanceByid(Long id,Double balance) {
        boolean flag = false;
        try {
            qMerchantRepository.modifyBalanceByid(id, balance);
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }


    /**
     * 查询可用的Q币 ，及已分配的Q币
     * @param id
     * @return
     */
    public Qtotal totalQBalance(Long id ){
        //可用余额
        Qtotal qtotal = new Qtotal();
        String balanceSql="SELECT a.balance  from  talkie_q_user a  where a.user_id= ?";
        Double balance = jdbcTemplate.queryForObject(balanceSql, Double.class, id);
        qtotal.setBalance(balance);
        // 已分配
        String  assignSql="SELECT a.already_balance  from  talkie_q_user a  where a.user_id= ?";
        Double assignQ = jdbcTemplate.queryForObject(assignSql, Double.class, id);
        qtotal.setAlreadyAssign(assignQ);
        return  qtotal;
    }


}
