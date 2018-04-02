package com.meta.dao.accountmanage;

import com.meta.model.user.User;
import com.meta.query.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by lhq on 2017/11/17.
 */
public interface MerchantAccountRepository  extends BaseRepository<User, Long> {

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

    /**
     * 统计该代理账号下拥有多少账号了  MerchantLv 9 或8
     * @param parentId
     * @return
     */
    Long countByParentIdAndMerchantLevelOrMerchantLevel(Long parentId,String lv1,String lv2);

}
