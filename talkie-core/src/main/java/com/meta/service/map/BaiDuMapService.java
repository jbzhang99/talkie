package com.meta.service.map;

import com.meta.dao.group.GroupRepository;
import com.meta.dao.map.BaiDuMapRepository;
import com.meta.model.group.Group;
import com.meta.model.map.BaiDuMap;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import com.meta.service.group.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author:lhq
 * @date:2017/12/11 9:27
 */
@Service
@Transactional
public class BaiDuMapService  extends BaseServiceImpl<BaiDuMap, Long> implements BaseService<BaiDuMap, Long> {

    //日志
    protected static Logger logger = LoggerFactory.getLogger(BaiDuMapService.class);

    @Autowired
    public void setBaseDao(BaiDuMapRepository baiDuMapRepository) {
        super.setBaseDao(baiDuMapRepository);
    }

    @Autowired
    BaiDuMapRepository baiDuMapRepository;





}
