package com.meta.service.terminal;

import com.meta.dao.terminal.TerminalEventRepository;
import com.meta.model.terminal.TerminalEvent;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by lhq on 2017/11/16.
 */
@Service
@Transactional
public class TerminalEventService  extends BaseServiceImpl<TerminalEvent, Long> implements BaseService<TerminalEvent, Long> {

    @Autowired
    private TerminalEventRepository terminalEventRepository;

    @Autowired
    public void setBaseDao(TerminalEventRepository terminalEventRepository) {
        super.setBaseDao(terminalEventRepository);
    }

}
