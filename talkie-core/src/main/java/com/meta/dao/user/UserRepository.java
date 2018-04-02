package com.meta.dao.user;

import com.meta.model.user.User;
import com.meta.query.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by llin on 2017/9/11.
 */
public interface UserRepository extends BaseRepository<User, Long> {

    /**
     * 根据账户和密码登录
     *
     * @param account
     * @param password
     * @return
     */
    @Query("select o  from  User o  where o.account = :account and o.password = :password and o.isDel =1")
    public User loginByAccount(@Param("account") String account, @Param("password") String password);

    /**
     * 根据ID更改状态
     *
     * @param id
     * @param status
     * @return
     */
    @Modifying
    @Query("UPDATE User a  set a.status= :status where a.id= :id")
     void modifyStatusById(@Param("id") Long id, @Param("status") String status);

    /**
     * 根据ID修改密码
     */
    @Modifying
    @Query("UPDATE User a  set a.password= :password where a.id= :id")
     void modifyPassword(@Param("id") Long id, @Param("password") String password);


    /**
     * 根据父ID查询
     */
     List<User> findByParentId(@Param("ParentId") Long ParentId);

    /**
     * 根据账号查找
     */
  User  findByAccountAndParentId(@Param("account") String account,@Param("ParentId") Long ParentId);


  User  findByAccount(@Param("account") String account);

  User findByPhone(@Param("phone") String phone);
  User findByIdentityCard(@Param("identityCard") String identityCard);

  User findByTerminalId(@Param("terminal") Long id);

}


