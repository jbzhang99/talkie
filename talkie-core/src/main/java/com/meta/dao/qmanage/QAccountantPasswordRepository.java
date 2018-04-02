package com.meta.dao.qmanage;

import com.meta.model.qmanage.QAccountantPassword;
import com.meta.query.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * create by lhq
 * create date on  18-2-26上午9:38
 *
 * @version 1.0
 **/
public interface QAccountantPasswordRepository extends BaseRepository<QAccountantPassword, Long> {

    @Modifying
    @Query("update QAccountantPassword  a  set a.password= :password where a.id= :id ")
    void modifyPasswordById(@Param("id") Long id, @Param("password") String password);

    QAccountantPassword findByUser_Id(@Param("user_id") Long userid);

}
