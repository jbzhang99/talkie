package com.meta.service.enterprise;

import com.meta.dao.enterprise.EnterpriseRepository;
import com.meta.dao.enterprise.SecEnterPriseRepository;
import com.meta.model.user.User;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by lhq on 2017/11/21.
 */
@Service
@Transactional
public class SecEnterPriseService  extends BaseServiceImpl<User, Long> implements BaseService<User, Long> {

    @Autowired
    private SecEnterPriseRepository secEnterPriseRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setBaseDao(SecEnterPriseRepository secEnterPriseRepository) {
        super.setBaseDao(secEnterPriseRepository);
    }

    /**
     * 统计子代理的下属企业数量
     */
    public Integer countCompayByParentId(Long parentId, Integer isDel  ) {

        String queryTemp = "SELECT count(1) from talkie_t_user  a where a.parent_id= ? and a.is_del= ? ";
        Integer countNun = jdbcTemplate.queryForObject(queryTemp, Integer.class, parentId, isDel);
        return countNun;
    }

    /**
     * 根据ID，更改状态
     */
    public void modifyStatusById(Long id, String status) {
        secEnterPriseRepository.modifyStatusById(id, status);
    }

    /**
     * 根据ID。修改密码
     */
    public void modifyPasswordById(Long id, String password) {
        secEnterPriseRepository.modifyPassword(id, password);
    }

    /**
     * 根据父ID查询
     */
    public List<User> findByParentId(Long ParentId) {
        return secEnterPriseRepository.findByParentId(ParentId);
    }

    /**
     * 根据父ID和等级统计 总数
     * @param parentId
     * @param merchantLevel
     * @return
     */
    public  Long countByParentIdAndMerchantLevel (Long parentId, String merchantLevel){
        return  secEnterPriseRepository.countByParentIdAndMerchantLevel(parentId, merchantLevel);
    }


    /**
     * 根据账号查询详情
     */
    public User findByAccountAndParentId(String account, Long parentId) {

        return secEnterPriseRepository.findByAccountAndParentId(account, parentId);
    }

    /**
     * 根据account查找用户信息
     *
     * @param account
     * @return
     */
    public User findByAccount(String account) {
        return secEnterPriseRepository.findByAccount(account);
    }



    /**
     * 查找已添加 的用户列表(群组用)
     */

    public List<User> findAlreadyUserGroup(Long groupId) {
        Query query = this.entityManager.createNativeQuery("SELECT a.* from talkie_t_user a  , talkie_t_sec_enterprise_user_group b  where a.id= b.user_id  and b.group_id = ?1  ORDER BY a.id ", User.class);
        query.setParameter("1", groupId);
        return query.getResultList();
    }

    /**
     * 查找待添加 的用户列表  (群组添加 用户)
     */
    public List<User> findWaitUserGroup(Long userId, Long groupId) {
        Query query = this.entityManager.createNativeQuery("  SELECT a.* from talkie_t_user a   where a.id in (SELECT by_user_id as id  FROM  talkie_t_sec_enterprise_user  where sec_enterprise_id= ?1)  and a.id not in (SELECT user_id as id  FROM talkie_t_sec_enterprise_user_group where group_id= ?2 )    ", User.class);
        query.setParameter("1", userId);
        query.setParameter("2", groupId);
        return query.getResultList();
    }

}
