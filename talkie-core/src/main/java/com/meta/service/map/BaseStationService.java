package com.meta.service.map;


import com.meta.dao.map.BaseStationRepository;
import com.meta.model.map.BaseStation;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
/**
 * create by lhq
 * create date on  18-1-30上午11:41
 *
 * @version 1.0
 **/
@Service
@Transactional
public class BaseStationService extends BaseServiceImpl<BaseStation, Long> implements BaseService<BaseStation, Long> {

    //日志
    protected static Logger logger = LoggerFactory.getLogger(BaseStationService.class);

    @Autowired
    public void setBaseDao(BaseStationRepository baseStationRepository) {
        super.setBaseDao(baseStationRepository);
    }

    @Autowired
    BaseStationRepository baseStationRepository;

    public BaseStation findByCellId(String cellId){
        return  baseStationRepository.findByCellId(cellId);
    }




}
