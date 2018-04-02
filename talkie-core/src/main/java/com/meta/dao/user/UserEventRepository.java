package com.meta.dao.user;

import com.meta.model.user.UserEvent;
import com.meta.query.BaseRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by llin on 2017/9/28.
 */
public interface UserEventRepository   extends BaseRepository<UserEvent, Long> {

    /**
     * 根据USERID 返回用户操作信息
     * @param userId
     * @return
     */
    public List<UserEvent> findByUserId(@Param("userId") Long userId);



}
