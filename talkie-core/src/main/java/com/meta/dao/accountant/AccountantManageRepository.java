package com.meta.dao.accountant;

import com.meta.model.user.User;
import com.meta.query.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * create by lhq
 * create date on  18-3-1下午3:58
 *
 * @version 1.0
 **/
public interface AccountantManageRepository  extends BaseRepository<User, Long> {

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

    User  findByAccount(@Param("account") String account);

}
