package com.meta.service.sim;

import com.meta.dao.SIMRepository;
import com.meta.model.sim.SIM;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by y747718944 on 2018/2/28
 */
@Service
public class SIMservice extends BaseServiceImpl<SIM,Long>  implements BaseService<SIM,Long> {

    @Autowired
    public void setBaseDao(SIMRepository simRepository) {

        super.setBaseDao(simRepository);
    }

    @Autowired
    private SIMRepository simRepository;
}
