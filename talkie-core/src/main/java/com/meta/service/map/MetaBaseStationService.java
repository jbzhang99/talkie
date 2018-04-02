package com.meta.service.map;

import com.meta.dao.map.MetaStationRepository;
import com.meta.model.map.MetaBaseStation;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * create by lhq
 * create date on  18-1-30上午11:47
 * @version 1.0
 **/
@Service
@Transactional
public class MetaBaseStationService extends BaseServiceImpl<MetaBaseStation, Long> implements BaseService<MetaBaseStation, Long> {

    //日志
    protected static Logger logger = LoggerFactory.getLogger(MetaBaseStationService.class);

    @Autowired
    public void setBaseDao(MetaStationRepository metaStationRepository) {
        super.setBaseDao(metaStationRepository);
    }

    @Autowired
    MetaStationRepository metaStationRepository;


    public  MetaBaseStation findByCellIdAndTerminalId(String cellId,Long terminalId){
        return metaStationRepository.findByCellIdAndTerminal_Id(cellId,terminalId);
    }

}
