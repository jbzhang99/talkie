package com.meta.dao.merchant;

import com.meta.model.user.User;
import com.meta.query.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by lhq on 2017/11/20.
 */
public interface SecMerchantRAepository   extends BaseRepository<User, Long> {

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
    void modifyPasswordById(@Param("id") Long id, @Param("password") String password);


    /**
     * 根据父ID查询
     */
    List<User> findByParentId(@Param("ParentId") Long ParentId);

    /**
     * 根据账号查找
     */
    User  findByAccountAndParentId(@Param("account") String account,@Param("ParentId") Long ParentId);

    User  findByAccount(@Param("account") String account);

}
