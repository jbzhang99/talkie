package com.meta.service.generalagent;

import com.meta.dao.enterprise.EnterpriseEventRepository;
import com.meta.dao.generalagent.GeneralAgentEventRepository;
import com.meta.model.GeneralAgent.GeneralAgentEvent;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by lhq on 2017/11/14.
 */
@Service
@Transactional
public class GeneralAgentEventService   extends BaseServiceImpl<GeneralAgentEvent, Long> implements BaseService<GeneralAgentEvent, Long> {

    @Autowired
    public void setBaseDao(GeneralAgentEventRepository generalAgentEventRepository) {
        super.setBaseDao(generalAgentEventRepository);
    }

    @Autowired
    private GeneralAgentEventRepository generalAgentEventRepository;

}
