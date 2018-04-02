package com.meta.dao.qmanage;

import com.meta.model.qmanage.QUser;
import com.meta.model.qmanage.QValidate;
import com.meta.query.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author:lhq
 * @date:2017/12/21 9:09
 */
public interface QValidateRepository extends BaseRepository<QValidate, Long> {


    @Modifying
    @Query("update QValidate  a  set a.password= :password where a.id= :id ")
    void modifyPasswordById(@Param("id") Long id, @Param("password") String password);

    QValidate findByUser_Id(@Param("user_id") Long userid);

}
