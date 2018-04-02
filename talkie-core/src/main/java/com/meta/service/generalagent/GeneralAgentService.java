package com.meta.service.generalagent;

import com.meta.dao.user.UserRepository;
import com.meta.model.user.User;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by lhq on 2017/11/14.
 */
@Service
@Transactional
public class GeneralAgentService extends BaseServiceImpl<User, Long> implements BaseService<User, Long> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setBaseDao(UserRepository userRepository) {
        super.setBaseDao(userRepository);
    }

}
