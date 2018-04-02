package com.meta.dao.enterprise;

import com.meta.model.user.User;
import com.meta.query.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by lhq on 2017/11/14.
 */
public interface EnterpriseRepository  extends BaseRepository<User, Long> {


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
     * 根据父ID查询及等级
     */
    List<User> findByParentIdAndMerchantLevel(@Param("ParentId") Long ParentId,@Param("merchantLevel") String merchantLevel);


    /**
     * 根据账号查找
     */
    User  findByAccountAndParentId(@Param("account") String account,@Param("ParentId") Long ParentId);

    User  findByAccount(@Param("account") String account);

    /**
     *  根据父ID统计 总数
     * @param ParentId
     * @return
     */
    Long countByParentId(@Param("ParentId") Long ParentId);

    /**
     * 根据父ID和等级统计总数
     */
Long countByParentIdAndMerchantLevel(@Param("parentId")Long parentId,@Param("merchantLevel") String merchantLevel);


}
