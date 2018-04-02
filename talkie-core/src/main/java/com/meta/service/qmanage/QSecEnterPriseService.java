package com.meta.service.qmanage;

import com.meta.dao.qmanage.QSecEnterPriseRepository;
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
 * Created by lhq on 2017/11/21.
 */
@Service
@Transactional
public class QSecEnterPriseService  extends BaseServiceImpl<QUser, Long> implements BaseService<QUser, Long> {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    //日志
    protected static Logger logger = LoggerFactory.getLogger(QUserService.class);

    @Autowired
    public void setBaseDao(QSecEnterPriseRepository qSecEnterPriseRepository) {
        super.setBaseDao(qSecEnterPriseRepository);
    }

    @Autowired
    private  QSecEnterPriseRepository qSecEnterPriseRepository;
    public  QUser findByUserId(Long  id ){
        return  qSecEnterPriseRepository.findByUser_Id(id);
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
            qSecEnterPriseRepository.modifyBalanceByid(id, balance);
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }




}
