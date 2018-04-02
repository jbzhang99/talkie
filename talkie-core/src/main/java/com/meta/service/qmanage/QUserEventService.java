package com.meta.service.qmanage;

import com.meta.dao.qmanage.QUserEventRepository;
import com.meta.model.qmanage.QUserEvent;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

/**
 * Created by lhq on 2017/9/30.
 */
@Service
@Transactional
public class QUserEventService  extends BaseServiceImpl<QUserEvent, Long> implements BaseService<QUserEvent, Long> {

    @Autowired
    public void setBaseDao(QUserEventRepository qUserRepository) {
        super.setBaseDao(qUserRepository);
    }

    @Autowired
    private  QUserEventRepository qUserEventRepository;

}
