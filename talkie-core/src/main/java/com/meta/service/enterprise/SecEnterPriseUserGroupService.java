package com.meta.service.enterprise;

import com.meta.dao.enterprise.SecEnterPriseUserGroupRepository;
import com.meta.dao.user.UserGroupReposirory;
import com.meta.model.enterprise.SecEnterPriseUserGroup;
import com.meta.model.user.UserGroup;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import com.meta.service.user.UserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by lhq on 2017/11/23.
 */
@Service
@Transactional
public class SecEnterPriseUserGroupService extends BaseServiceImpl<SecEnterPriseUserGroup, Long> implements BaseService<SecEnterPriseUserGroup, Long> {

    //日志
    protected static Logger logger = LoggerFactory.getLogger(UserGroupService.class);

    @Autowired
    public void setBaseDao(SecEnterPriseUserGroupRepository secEnterPriseUserGroupRepository) {
        super.setBaseDao(secEnterPriseUserGroupRepository);
    }

    @Autowired
    private SecEnterPriseUserGroupRepository secEnterPriseUserGroupRepository;


    public boolean deleteByUserIdAndGroupId(Long userId, Long groupId) {
        boolean flag = false;
        try {
            Query query = this.entityManager.createNativeQuery("DELETE from  talkie_t_sec_enterprise_user_group  where  group_id=?1 and user_id=?2", SecEnterPriseUserGroup.class);
            query.setParameter("1", groupId);
            query.setParameter("2", userId);
            query.executeUpdate();
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }


    public boolean deleteByGroupId(Long groupId) {
        boolean flag = false;
        try {
            Query query = this.entityManager.createNativeQuery("DELETE from  talkie_t_sec_enterprise_user_group  where  group_id=?1 ", SecEnterPriseUserGroup.class);
            query.setParameter("1", groupId);
            query.executeUpdate();
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }

    public List<SecEnterPriseUserGroup> findByGroupId(Long groupId) {

        return secEnterPriseUserGroupRepository.findByGroupId(groupId);
    }


    public boolean  delByUserId(Long UserId){
        boolean flag = false;
        try {
            secEnterPriseUserGroupRepository.delByUserId(UserId);
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }


    /**
     * 移除 用户 旁听或指派事件
     * @return
     */
    public int  deleteByUserIdAndType(Long userId, int type) {
        try {
              secEnterPriseUserGroupRepository.deleteByUserIdAndEventType(userId,type);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

}
