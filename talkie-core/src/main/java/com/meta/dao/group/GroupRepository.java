package com.meta.dao.group;


import com.meta.model.group.Group;
import com.meta.query.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by llin on 2017/9/29.
 */
public interface GroupRepository extends BaseRepository<Group, Long> {

    @Modifying
    @Query("update Group  a set a.status= :status where a.id= :id ")
    void modifyStatusById(@Param("id") Long id ,  @Param("status") String status);


    /**
     * 统计该企业的群组个数
     * @param userid
     * @return
     */
    Long countByUser_Id(@Param("user_id")Long userid);



}
