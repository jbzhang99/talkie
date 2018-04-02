package com.meta.dao.qmanage;


import com.meta.model.qmanage.QUser;
import com.meta.query.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by llin on 2017/9/29.
 */
public interface QUserRepository extends BaseRepository<QUser, Long> {


    @Modifying
    @Query("update QUser  a  set a.balance= :balance where a.id= :id ")
    void modifyBalanceByid(@Param("id") Long id ,@Param("balance") Double balance);

    /**
     * 查询user对应的信息
     * @param userid
     * @return
     */
    public QUser findByUser_Id(@Param("user_id") Long userid);


}
