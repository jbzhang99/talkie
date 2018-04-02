package com.meta.service.datatotal;

import com.meta.dao.datatotal.QGeneralAgentDataTotalRepository;
import com.meta.model.datatotal.QGeneralagentDataTotal;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * create by lhq
 * create date on  18-2-27上午11:45
 *
 * @version 1.0
 **/
@Service
@Transactional
public class QGeneralAgentDataTotalService  extends BaseServiceImpl<QGeneralagentDataTotal, Long> implements BaseService<QGeneralagentDataTotal, Long> {

    //日志
    protected static Logger logger = LoggerFactory.getLogger(QGeneralAgentDataTotalService.class);

    @Autowired
    private QGeneralAgentDataTotalRepository qGeneralAgentDataTotalRepository;

    @Autowired
    public void setBaseDao(QGeneralAgentDataTotalRepository qGeneralAgentDataTotalRepository) {
        super.setBaseDao(qGeneralAgentDataTotalRepository);
    }




    public  QGeneralagentDataTotal findByYear(Integer year){
        return  qGeneralAgentDataTotalRepository.findByYear(year);
    }




}
