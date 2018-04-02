package com.meta.service.user;

import com.meta.dao.user.UserFriendRepository;
import com.meta.model.user.User;
import com.meta.model.user.UserFriend;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by llin on 2017/9/28.
 */
@Service
@Transactional
public class UserFriendService extends BaseServiceImpl<UserFriend, Long> implements BaseService<UserFriend, Long> {

    //日志
    protected static Logger logger = LoggerFactory.getLogger(UserFriendService.class);
    @Autowired
    private UserFriendRepository userFriendRepository;
    @Autowired
    private UserService userService;

    @Autowired
    public void setBaseDao(UserFriendRepository userFriendRepository) {
        super.setBaseDao(userFriendRepository);
    }

    public boolean deleteByUserId(Long userId) {
        boolean flag = false;
        try {
            Query query = this.entityManager.createNativeQuery("DELETE FROM talkie_t_user_friend  where user_id=?1", UserFriend.class);
            query.setParameter("1", userId);
            query.executeUpdate();
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }

    public boolean deleteByUserIdAndFriendId(Long userId,Long friendId) {
        boolean flag = false;
        try {
            Query query = this.entityManager.createNativeQuery("DELETE from  talkie_t_user_friend  where (self_user_id= ?1 and friend_user_id= ?2) or(self_user_id= ?2 and friend_user_id= ?1) ", UserFriend.class);
            query.setParameter("1", userId);
            query.setParameter("2", friendId);
            query.executeUpdate();
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }

    public boolean deleteByFriendId( Long friendId) {
        boolean flag = false;
        try {
            Query query = this.entityManager.createNativeQuery("DELETE from  talkie_t_user_friend  where  friend_user_id= ?1", UserFriend.class);
            query.setParameter("1", friendId);
            query.executeUpdate();
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }

    public List<User> getFriendUserList(Long userId) {
        List<UserFriend> listUserSelf = search("EQ_selfUserId=" + userId);
        List<UserFriend> listUserFriend = search("EQ_friendUserId=" + userId);
        Set<Long> listUserId = new HashSet<>();
        for(UserFriend uf : listUserSelf) {
            listUserId.add(uf.getSelfUserId());
            listUserId.add(uf.getFriendUserId());
        }
        for(UserFriend uf : listUserFriend) {
            listUserId.add(uf.getSelfUserId());
            listUserId.add(uf.getFriendUserId());
        }
        listUserId.remove(userId);
        List<User> listUser = new ArrayList<>();
        for(Long fuId : listUserId) {
            User user = userService.findOne(fuId);
            listUser.add(user);
        }

        return listUser;
    }
}
