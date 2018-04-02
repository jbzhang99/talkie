package com.meta.service.merchant;

import com.meta.dao.merchant.MerchantRepository;
import com.meta.dao.merchant.SecMerchantRAepository;
import com.meta.model.user.User;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by lhq on 2017/11/20.
 */
@Service
@Transactional
public class SecMerchantService  extends BaseServiceImpl<User, Long> implements BaseService<User, Long> {

    @Autowired
    private SecMerchantRAepository secMerchantRAepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setBaseDao(SecMerchantRAepository secMerchantRAepository) {
        super.setBaseDao(secMerchantRAepository);
    }


    /**
     * 统计子代理的下属企业数量
     */
    public Integer countCompayByParentId(Long parentId, Integer isDel) {

        String queryTemp = "SELECT count(1) from talkie_t_user  a where a.parent_id= ? and a.is_del= ?   ";
        Integer countNun = jdbcTemplate.queryForObject(queryTemp, Integer.class, parentId, isDel);
        return countNun;
    }



    /**
     * 根据ID，更改状态
     */
    public void modifyStatusById(Long id, String status) {
        secMerchantRAepository.modifyStatusById(id, status);
    }

    /**
     * 根据ID。修改密码
     */
    public void modifyPasswordById(Long id, String password) {
        secMerchantRAepository.modifyPasswordById(id, password);
    }

    /**
     * 根据父ID查询
     */
    public List<User> findByParentId(Long ParentId) {
        return secMerchantRAepository.findByParentId(ParentId);
    }

    /**
     * 根据账号查询详情
     */
    public User findByAccountAndParentId(String account, Long parentId) {

        return secMerchantRAepository.findByAccountAndParentId(account, parentId);
    }


    /**
     * 根据account查找用户信息
     *
     * @param account
     * @return
     */
    public User findByAccount(String account) {
        return secMerchantRAepository.findByAccount(account);
    }


}
