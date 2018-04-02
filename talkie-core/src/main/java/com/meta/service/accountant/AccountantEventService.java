package com.meta.service.accountant;

import com.meta.dao.accountant.AccountantEventRepository;
import com.meta.model.accountant.AccountantEvent;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * create by lhq
 * create date on  18-3-1下午5:06
 *
 * @version 1.0
 **/
@Service
@Transactional
public class AccountantEventService extends BaseServiceImpl<AccountantEvent, Long> implements BaseService<AccountantEvent, Long> {


    @Autowired
    private AccountantEventRepository accountantEventRepository;

    @Autowired
    public void setBaseDao(AccountantEventRepository accountantEventRepository) {
        super.setBaseDao(accountantEventRepository);
    }



}
