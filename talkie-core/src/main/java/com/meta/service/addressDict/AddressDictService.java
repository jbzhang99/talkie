package com.meta.service.addressDict;

import com.meta.dao.addressDict.AddressDictRepository;
import com.meta.model.addressDict.AddressDict;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by lhq on 2017/10/12.
 */
@Service
@Transactional
public class AddressDictService extends BaseServiceImpl<AddressDict, Long> implements BaseService<AddressDict, Long> {

    @Autowired
    public void setBaseDao(AddressDictRepository addressDictRepository) {
        super.setBaseDao(addressDictRepository);
    }

    @Autowired
    AddressDictRepository addressDictRepository;

}
