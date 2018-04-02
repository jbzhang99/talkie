package com.meta.service.boardCast;

import com.meta.dao.boardCast.GeneralAgentBoardCastRepository;
import com.meta.model.boardCast.GeneralAgentBoardCast;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by  on 2017/12/14.
 */
@Service
@Transactional
public class GeneralAgentBoardCastService extends BaseServiceImpl<GeneralAgentBoardCast, Long> implements BaseService<GeneralAgentBoardCast, Long>{

    //日志
    protected static Logger logger = LoggerFactory.getLogger(GeneralAgentBoardCastService.class);

    @Autowired
    private GeneralAgentBoardCastRepository generalAgentBoardCastRepository;

    @Autowired
    public void setBaseDao(GeneralAgentBoardCastRepository generalAgentBoardCastRepository) {
        super.setBaseDao(generalAgentBoardCastRepository);
    }


    /**
     * 根据ID更改状态
     */
    public boolean modifyStateById(Long id, String state) {
        boolean flag = false;
        try {
            generalAgentBoardCastRepository.modifyStatusById(id, state);
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }

    /**
     * 更改状态
     */
    public boolean modifyState( String state) {
        boolean flag = false;
        try {
            generalAgentBoardCastRepository.modifyStatus( state);
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }

}
