package com.meta.service.terminal;

import com.meta.dao.terminal.TerminalRepository;
import com.meta.model.terminal.Terminal;
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
public class TerminalService extends BaseServiceImpl<Terminal, Long> implements BaseService<Terminal, Long> {
    @Autowired
    private TerminalRepository terminalRepository;

    @Autowired
    public void setBaseDao(TerminalRepository terminalRepository) {
        super.setBaseDao(terminalRepository);
    }

    /**
     * 根据UserId删除对应终端
     * @param id
     */
    public void deleteByUser_Id(Long id){ terminalRepository.deleteByUser_Id(id); }

    /**
     * 根据User account对应终端
     * @param account
     */
    public Terminal searchByUser_Account(String account){ return terminalRepository.findByUser_Account(account); }

}
