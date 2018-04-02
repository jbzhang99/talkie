package com.meta.service.accountant;

import com.meta.dao.accountant.AccountantManageRepository;
import com.meta.model.user.User;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * create by lhq
 * create date on  18-3-1下午3:58
 *
 * @version 1.0
 **/
@Service
@Transactional

public class AccountantManageService  extends BaseServiceImpl<User, Long> implements BaseService<User, Long> {

    @Autowired
    private AccountantManageRepository accountantManageRepository;

    @Autowired
    public void setBaseDao(AccountantManageRepository accountantManageRepository) {
        super.setBaseDao(accountantManageRepository);
    }



    /**
     * 根据ID，更改状态
     */
    public void modifyStatusById(Long id, String status) {
        accountantManageRepository.modifyStatusById(id, status);
    }

    /**
     * 根据ID。修改密码
     */
    public void modifyPasswordById(Long id, String password) {
        accountantManageRepository.modifyPassword(id, password);
    }

    /**
     * 根据account查找用户信息
     *
     * @param account
     * @return
     */
    public User findByAccount(String account) {
        return accountantManageRepository.findByAccount(account);
    }


}
