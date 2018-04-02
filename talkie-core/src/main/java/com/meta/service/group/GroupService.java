package com.meta.service.group;

import com.meta.dao.group.GroupRepository;
import com.meta.model.group.Group;
import com.meta.model.user.User;
import com.meta.model.user.UserFriend;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import com.meta.service.user.UserFriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by lhq on 2017/9/30.
 */
@Service
@Transactional
public class GroupService extends BaseServiceImpl<Group, Long> implements BaseService<Group, Long> {

    //日志
    protected static Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    public void setBaseDao(GroupRepository groupRepository) {
        super.setBaseDao(groupRepository);
    }

    @Autowired
    GroupRepository groupRepository;

    /**
     * 根据USERID删除
     * @param userId
     * @return
     */
    public boolean deleteByUserId(Long userId) {
        boolean flag = false;
        try {
            Query query = this.entityManager.createNativeQuery("DELETE FROM talkie_t_group  where user_id=?1", Group.class);
            query.setParameter("1", userId);
            query.executeUpdate();
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }

    /**
     * 根据ID变更状态
     * @param id
     * @param status
     * @return
     */
    public boolean modifyStatusById(Long id,String status) {
        boolean flag = false;
        try {
            groupRepository.modifyStatusById(id,status);
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }

    /**
     * 查找未添加用户的组
     * @param userId
     * @return
     */
  public List<Group> findWaitByUser(Long userId,Long currId){
      Query query = this.entityManager.createNativeQuery("SELECT * from  talkie_t_group a   where a.id not in(SELECT group_id as id from talkie_t_user_group where user_id = ?1)  and a.user_id =?2 ORDER BY a.id  ", Group.class);
      query.setParameter("1", userId);
      query.setParameter("2", currId);
      return query.getResultList();
  }

    /**
     *  查找已添加 的用户列表()
     */

    public  List<Group> findAlreadyUser(Long UserId){
        Query query = this.entityManager.createNativeQuery("SELECT a.* from  talkie_t_group a, talkie_t_user_group b  where b.user_id = ?1 and a.id=b.group_id  ORDER BY a.id  ", Group.class);
        query.setParameter("1", UserId);
        return query.getResultList();
    }



    /**
     * 统计该企业的群组个数
     * @param userid
     * @return
     */
    public   Long countByUser_Id(Long userid){ return  groupRepository.countByUser_Id(userid);}
}
