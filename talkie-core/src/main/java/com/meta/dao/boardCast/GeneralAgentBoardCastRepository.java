package com.meta.dao.boardCast;


import com.meta.model.boardCast.GeneralAgentBoardCast;
import com.meta.query.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Created by  on 2017/12/14.
 */
public interface GeneralAgentBoardCastRepository extends BaseRepository<GeneralAgentBoardCast, Long> {


    /**
     * 根据ID更改状态
     *
     * @param id
     * @param status
     * @return
     */
    @Modifying
    @Query("UPDATE GeneralAgentBoardCast a  set a.status= :status where a.id= :id")
    void modifyStatusById(@Param("id") Long id, @Param("status") String status);

    @Modifying
    @Query("UPDATE GeneralAgentBoardCast a  set a.status= :status  ")
    void modifyStatus(@Param("status") String status);


    /**
     * 根据ID修改备注（即内容）
     */
    @Modifying
    @Query("UPDATE GeneralAgentBoardCast a set a.remark= :remark where a.id= :id")
    void modifyRemark(@Param("id") Long id, @Param("remark") String remark);


}


