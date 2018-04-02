package com.meta.service.role;

import com.meta.dao.role.RoleRepository;
import com.meta.model.role.Role;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;


/**
 * Created by llin on 2017/9/28.
 */
@Service
@Transactional
public class RoleService extends BaseServiceImpl<Role, Long> implements BaseService<Role, Long> {

    @Autowired
    public void setBaseDao(RoleRepository roleRepository) {
        super.setBaseDao(roleRepository);
    }

    @Autowired
    private  RoleRepository roleRepository;

}
