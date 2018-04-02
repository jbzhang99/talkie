package com.meta.service.qmanage;

import com.meta.dao.qmanage.QUserRepository;
import com.meta.dao.qmanage.QValidateRepository;
import com.meta.model.qmanage.QUser;
import com.meta.model.qmanage.QValidate;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author:lhq
 * @date:2017/12/21 9:09
 */
@Service
@Transactional
public class QValidateService extends BaseServiceImpl<QValidate, Long> implements BaseService<QValidate, Long> {

    //日志
    protected static Logger logger = LoggerFactory.getLogger(QValidateService.class);

    @Autowired
    public void setBaseDao(QValidateRepository qValidateRepository) {
        super.setBaseDao(qValidateRepository);
    }

    @Autowired
    private QValidateRepository qValidateRepository;


    public  boolean modifyPasswordById(Long id , String password){
        boolean flag=false;
        try {
            qValidateRepository.modifyPasswordById(id,password);
            flag=true;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return  flag;
    }

    public  QValidate findByUserId(Long userId){
        return  qValidateRepository.findByUser_Id(userId);
    }



}
