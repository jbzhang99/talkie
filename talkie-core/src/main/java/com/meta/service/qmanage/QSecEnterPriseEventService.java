package com.meta.service.qmanage;

import com.meta.dao.qmanage.QSecEnterPriseEventRepository;
import com.meta.dao.qmanage.QUserEventRepository;
import com.meta.model.qmanage.QMerchantEvent;
import com.meta.model.qmanage.QSecEnterPriseEvent;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by lhq on 2017/11/21.
 */
@Service
@Transactional
public class QSecEnterPriseEventService  extends BaseServiceImpl<QSecEnterPriseEvent, Long> implements BaseService<QSecEnterPriseEvent, Long> {


    @Autowired
    public void setBaseDao(QSecEnterPriseEventRepository qSecEnterPriseEventRepository) {
        super.setBaseDao(qSecEnterPriseEventRepository);
    }

    @Autowired
    private QSecEnterPriseEventRepository qSecEnterPriseEventRepository;



}
