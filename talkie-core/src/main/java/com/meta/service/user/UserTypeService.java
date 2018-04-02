package com.meta.service.user;

import com.meta.model.user.User;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;


/**
 * Created by llin on 2017/9/29.
 */
@Service
@Transactional
public class UserTypeService  extends BaseServiceImpl<User, Long> implements BaseService<User,Long> {
}
