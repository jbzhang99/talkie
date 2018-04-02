package com.meta.service.merchant;

import com.meta.dao.merchant.MerchantEventRepository;
import com.meta.model.merchant.MerchantEvent;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by lhq on 2017/11/10.
 */
@Service
@Transactional
public class MerchantEventService  extends BaseServiceImpl<MerchantEvent, Long> implements BaseService<MerchantEvent, Long> {

    @Autowired
    public void setBaseDao(MerchantEventRepository merchantEventRepository) {
        super.setBaseDao(merchantEventRepository);
    }

    @Autowired
    private MerchantEventRepository merchantEventRepository;




}
