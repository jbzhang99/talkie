package com.meta.service.accountmanage;

import com.meta.dao.accountmanage.MerchantAccountRepository;
import com.meta.model.user.User;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by lhq on 2017/11/17.
 */
@Service
@Transactional
public class MerchantAccountService  extends BaseServiceImpl<User, Long> implements BaseService<User, Long> {

    @Autowired
    private MerchantAccountRepository merchantAccountRepository;

    @Autowired
    public void setBaseDao(MerchantAccountRepository merchantAccountRepository) {
        super.setBaseDao(merchantAccountRepository);
    }


    /**
     * 根据ID，更改状态
     */
    public void modifyStatusById(Long id, String status) {
        merchantAccountRepository.modifyStatusById(id, status);
    }

    /**
     * 根据ID。修改密码
     */
    public void modifyPasswordById(Long id, String password) {
        merchantAccountRepository.modifyPassword(id, password);
    }

    /**
     * 根据account查找用户信息
     *
     * @param account
     * @return
     */
    public User findByAccount(String account) {
        return merchantAccountRepository.findByAccount(account);
    }

    /**
     * 统计该代理账号下拥有多少账号了  MerchantLv 9 或8
     * @param parentId
     * @return
     */
    public Long countByParentId(Long parentId,String lv1,String lv2){ return  merchantAccountRepository.countByParentIdAndMerchantLevelOrMerchantLevel(parentId,lv1,lv2);}

}
