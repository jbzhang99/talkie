package com.meta.service.qmanage;

import com.meta.dao.qmanage.QAccountantPasswordRepository;
import com.meta.model.qmanage.QAccountantPassword;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * create by lhq
 * create date on  18-2-26上午9:39
 *
 * @version 1.0
 **/
@Service
@Transactional
public class QAccountantPasswordService  extends BaseServiceImpl<QAccountantPassword, Long> implements BaseService<QAccountantPassword, Long> {

    //日志
    protected static Logger logger = LoggerFactory.getLogger(QAccountantPasswordService.class);

    @Autowired
    public void setBaseDao(QAccountantPasswordRepository qAccountantPasswordRepository) {
        super.setBaseDao(qAccountantPasswordRepository);
    }

    @Autowired
    private QAccountantPasswordRepository qAccountantPasswordRepository;


    public  boolean modifyPasswordById(Long id , String password){
        boolean flag=false;
        try {
            qAccountantPasswordRepository.modifyPasswordById(id,password);
            flag=true;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return  flag;
    }

    public QAccountantPassword findByUserIdAndPassword(Long userId){
        return  qAccountantPasswordRepository.findByUser_Id(userId);
    }


}
