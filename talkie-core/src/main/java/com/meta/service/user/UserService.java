package com.meta.service.user;

import com.meta.dao.user.UserRepository;
import com.meta.model.terminal.Terminal;
import com.meta.model.user.AssUserGroup;
import com.meta.model.user.User;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import com.meta.service.enterprise.SecEnterPriseUserGroupService;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


/**
 * Created by llin on 2017/9/11.
 */
@Service
@Transactional
public class UserService extends BaseServiceImpl<User, Long> implements BaseService<User, Long> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserFriendService userFriendService;
    @Autowired
    private SecEnterPriseUserGroupService secEnterPriseUserGroupService ;

    @Autowired
    public void setBaseDao(UserRepository userRepository) {
        super.setBaseDao(userRepository);
    }

    /**
     * 根据ACCOUNT和PASSWORD 登录
     */
    public User loginByAccount(String account, String password) {
        return userRepository.loginByAccount(account, password);
    }

    /**
     * 统计子代理的下属企业数量
     */
    public Integer countCompayByParentId(Long parentId, Integer isDel, String merchantLevel) {
        String queryTemp = "SELECT count(1) from talkie_t_user  a where a.parent_id= ? and a.is_del= ?  and  a.merchant_level =?";
        Integer countNun = jdbcTemplate.queryForObject(queryTemp, Integer.class, parentId, isDel, merchantLevel);
        return countNun;
    }

    /**
     * 根据ID，更改状态
     */
    public void modifyStatusById(Long id, String status) {
        userRepository.modifyStatusById(id, status);
    }

    /**
     * 根据ID。修改密码
     */
    public void modifyPassword(Long id, String password) {
        userRepository.modifyPassword(id, password);
    }

    /**
     * 根据父ID查询
     */
    public List<User> findByParentId(Long ParentId) {
        return userRepository.findByParentId(ParentId);
    }

    /**
     * 根据账号查询详情
     */
    public User findByAccountAndParentId(String account, Long parentId) {

        return userRepository.findByAccountAndParentId(account, parentId);
    }


    /**
     * 根据account查找用户信息
     *
     * @param account
     * @return
     */
    public User findByAccount(String account) {
        return userRepository.findByAccount(account);
    }



    /**
     * 根据手机号查找
     */
    public User findByPhone(String phone) {

        return userRepository.findByPhone(phone);
    }

    /**
     * 根据身份证查找
     */
    public User findByIdentityCard(String identityCard) {
        return userRepository.findByIdentityCard(identityCard);
    }

    /**
     * 查找待添加 的用户列表  (群组添加 用户)
     */
    Integer intOne = 1;
    String strOne = "1";
    String strSeven = "7";

    public List<User> findWaitUserGroup(Long parentId, Long groupId) {
      //  Query query = this.entityManager.createNativeQuery("SELECT  a.*  from talkie_t_user  a     where a.is_del=" + intOne + " and a.`status` =" + strOne + " and a.parent_id = ?1 and a.merchant_level=" + strSeven + " and a.id not in (SELECT user_id as id  FROM talkie_t_user_group where group_id= ?2 )  ORDER BY a.id  ", User.class);
        //我认为只要该企业所能控制的用户都能进行分配
        Query query = this.entityManager.createNativeQuery("SELECT  a.*  from talkie_t_user  a     where a.is_del=" + intOne +" and a.parent_id = ?1 and a.merchant_level=" + strSeven + " and a.id not in (SELECT user_id as id  FROM talkie_t_user_group where group_id= ?2 )  ORDER BY a.id  ", User.class);
        query.setParameter("1", parentId);
        query.setParameter("2", groupId);
        return query.getResultList();
    }


    /**
     * 查找已添加 的用户列表(群组用)
     */

    public List<User> findAlreadyUserGroup(Long groupId) {
        Query query = this.entityManager.createNativeQuery("SELECT a.* from talkie_t_user a  , talkie_t_user_group b  where a.id= b.user_id  and b.group_id = ?1  ORDER BY a.id ", User.class);
        query.setParameter("1", groupId);
        return query.getResultList();
    }

    /**
     * 好友列表 --查找可以添加的好友 屏蔽自己
     */
    public List<User> findCanAddFriend(Long id, Long parentId) {
        Query query = this.entityManager.createNativeQuery("SELECT  a.* from talkie_t_user  a  where   a.parent_id = ?2  and  a.is_del=1  and a.id != ?1 and  a.merchant_level='7' and a.id  not  in(SELECT friend_user_id  as id from talkie_t_user_friend  WHERE self_user_id =?1\n" +
                "\t\tUNION  SELECT self_user_id  as id from talkie_t_user_friend where  friend_user_id= ?1 ) ", User.class);
        query.setParameter("1", id);
        query.setParameter("2", parentId);
        return query.getResultList();
    }

    /**
     * 好友列表--查找已经添加的好友列表
     */
    public List<User> findAlreadyAddFriend(Long id) {
        Query query = this.entityManager.createNativeQuery("SELECT  a.* from  talkie_t_user a , talkie_t_user_friend b  where a.id = b.friend_user_id  and ( b.self_user_id = ?1 )  ", User.class);
        query.setParameter("1", id);
        return query.getResultList();
    }



    /**
     * 指派(旁听等) 群组功能用
     */
    public List<User> assignGroup(Long id) {
        Query query = this.entityManager.createNativeQuery("SELECT a.* from talkie_t_user a where a.id=  ?1 OR   a.parent_id = ?1  and a.merchant_level= 14 and a.id in(SELECT user_id as id  from talkie_t_group  )  ORDER BY a.id ", User.class);
        query.setParameter("1", id);
        return query.getResultList();
    }

    /**
     * 将Q币过期 时间为一年的用户,,同时删除 好友关系，，群组信息等。
     */

    public  void   delOverdueUser( Integer customData){

        Query query = this.entityManager.createNativeQuery("SELECT a.* from talkie_t_user a , talkie_q_user b  where a.is_del =1   and a.merchant_level = '7' and a.status != '3' and a.id = b.user_id  and TO_DAYS(NOW()) - TO_DAYS(b.modify_date) >"+ customData, User.class);
        List<User> list = query.getResultList();

        String sql = "DELETE FROM talkie_t_user a where a.id=?";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public int getBatchSize() {
                return list.size();
            }

            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                ps.setDouble(1, list.get(i).getId());
                userFriendService.deleteByUserId(list.get(i).getId());
                userFriendService.deleteByFriendId(list.get(i).getId());
                secEnterPriseUserGroupService.delByUserId(list.get(i).getId());

            }

        });


    }


    /**
     * 根据Terminal Id对应User
     * @param id
     */
    public User searchByTerminal_Id(Long id){ return userRepository.findByTerminalId(id); }

    public void Test(){


        Query query = this.entityManager.createNativeQuery("SELECT  merchant_level as aaa from talkie_t_user where account='accountTest01' ");
        query.unwrap(org.hibernate.SQLQuery.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String,String>> rows = query.getResultList();

        System.err.println(rows.get(0));

    }

}
