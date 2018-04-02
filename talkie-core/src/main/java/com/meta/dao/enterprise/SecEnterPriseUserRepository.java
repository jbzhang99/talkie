package com.meta.dao.enterprise;

import com.meta.model.enterprise.SecEnterPriseUser;
import com.meta.query.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by lhq on 2017/11/22.
 */
public interface SecEnterPriseUserRepository extends BaseRepository<SecEnterPriseUser, Long> {

    @Modifying
    @Query("delete  from  SecEnterPriseUser sep   where sep.byUserId = ?1 ")
    void delByUserId(Long userId);

    SecEnterPriseUser   findByByUserId(@Param("byUserId") Long byUserId);

}
