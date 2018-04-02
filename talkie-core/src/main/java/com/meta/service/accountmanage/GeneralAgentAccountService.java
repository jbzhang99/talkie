package com.meta.service.accountmanage;

import com.meta.dao.accountmanage.GeneralAgentAccountRepository;
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
public class GeneralAgentAccountService extends BaseServiceImpl<User, Long> implements BaseService<User, Long> {

    @Autowired
    private GeneralAgentAccountRepository generalAgentAccountRepository;

    @Autowired
    public void setBaseDao(GeneralAgentAccountRepository generalAgentAccountRepository) {
        super.setBaseDao(generalAgentAccountRepository);
    }

    /**
     * 根据ID，更改状态
     */
    public void modifyStatusById(Long id, String status) {
        generalAgentAccountRepository.modifyStatusById(id, status);
    }

    /**
     * 根据ID。修改密码
     */
    public void modifyPasswordById(Long id, String password) {
        generalAgentAccountRepository.modifyPassword(id, password);
    }

    /**
     * 根据account查找用户信息
     *
     * @param account
     * @return
     */
    public User findByAccount(String account) {
        return generalAgentAccountRepository.findByAccount(account);
    }


    /**
     * 统计该代理账号下拥有多少账号了  MerchantLv 9 或8
     * @param parentId
     * @return
     */
    public Long countByParentId(Long parentId,String lv1,String lv2){ return  generalAgentAccountRepository.countByParentIdAndMerchantLevelOrMerchantLevel(parentId,lv1,lv2);}

}
