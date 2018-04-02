package com.meta.dao.enterprise;

import com.meta.model.enterprise.SecEnterPriseUserGroup;
import com.meta.model.user.UserGroup;
import com.meta.query.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by lhq on 2017/11/23.
 */
public interface SecEnterPriseUserGroupRepository extends BaseRepository<SecEnterPriseUserGroup, Long> {

    List<SecEnterPriseUserGroup> findByGroupId(@Param("groupId") Long groupId);

    @Modifying
    @Query("delete from SecEnterPriseUserGroup a where a.userId =:userId ")
    void  delByUserId(@Param("userId") Long userId);


    public int  deleteByUserIdAndEventType(@Param("userId")Long userId,@Param("type")int type);

}
