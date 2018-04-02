package com.meta.service.user;

import com.meta.dao.user.UserGroupReposirory;
import com.meta.model.user.UserFriend;
import com.meta.model.user.UserGroup;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class UserGroupService extends BaseServiceImpl<UserGroup, Long> implements BaseService<UserGroup, Long> {
    //日志
    protected static Logger logger = LoggerFactory.getLogger(UserGroupService.class);
    @Autowired
    public void setBaseDao(UserGroupReposirory userGroupRepository) {
        super.setBaseDao(userGroupRepository);
    }

    @Autowired
    private  UserGroupReposirory userGroupReposirory;

    public boolean deleteByUserIdAndGroupId(Long userId, Long groupId) {
        boolean flag = false;
        try {
            Query query = this.entityManager.createNativeQuery("DELETE from  talkie_t_user_group  where  group_id=?1 and user_id=?2", UserGroup.class);
            query.setParameter("1", groupId);
            query.setParameter("2", userId);
            query.executeUpdate();
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }


    public boolean deleteByGroupId( Long groupId) {
        boolean flag = false;
        try {
            Query query = this.entityManager.createNativeQuery("DELETE from  talkie_t_user_group  where  group_id=?1 ", UserGroup.class);
            query.setParameter("1", groupId);
            query.executeUpdate();
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }

    public List<UserGroup> findByGroupId( Long  groupId){

        return  userGroupReposirory.findByGroupId(groupId);
    }

}
