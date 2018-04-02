package com.meta.service.user;

import com.meta.dao.user.UserEventRepository;
import com.meta.model.user.UserEvent;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.persistence.Query;
import javax.transaction.Transactional;


import java.util.List;

/**
 * Created by llin on 2017/9/28.
 */
@Service
@Transactional
public class UserEventService extends BaseServiceImpl<UserEvent, Long> implements BaseService<UserEvent, Long> {
    @Autowired
    public void setBaseDao(UserEventRepository userEventRepository) {
        super.setBaseDao(userEventRepository);
    }

    @Autowired
    private UserEventRepository userEventRepository;

    /**
     * 根据USERID 返回用户操作信息
     * @param userId
     * @return
     */
    public List<UserEvent> findByUserId(Long userId) {
        return userEventRepository.findByUserId(userId);
    }

    public  void delByUserId(String id){
        Query query = this.entityManager.createNativeQuery("DELETE from  talkie_t_user_event where user_id =?1",UserEvent.class);
        query.setParameter("1",id);
        query.executeUpdate();

    }




}
