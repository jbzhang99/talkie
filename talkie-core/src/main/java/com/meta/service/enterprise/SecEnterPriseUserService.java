package com.meta.service.enterprise;

import com.meta.dao.enterprise.SecEnterPriseRepository;
import com.meta.dao.enterprise.SecEnterPriseUserRepository;
import com.meta.model.enterprise.SecEnterPriseUser;
import com.meta.model.user.User;
import com.meta.model.user.UserFriend;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import com.meta.service.user.UserFriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by lhq on 2017/11/22.
 */
@Service
@Transactional
public class SecEnterPriseUserService extends BaseServiceImpl<SecEnterPriseUser, Long> implements BaseService<SecEnterPriseUser, Long> {

    @Autowired
    private SecEnterPriseUserRepository secEnterPriseUserRepository;

    @Autowired
    public void setBaseDao(SecEnterPriseUserRepository secEnterPriseUserRepository) {
        super.setBaseDao(secEnterPriseUserRepository);
    }

    //日志
    protected static Logger logger = LoggerFactory.getLogger(UserFriendService.class);

    /**
     * 根据USERID 删除
     * @param userId
     * @return
     */
    public boolean delByUserId(Long userId) {
        boolean flag = false;
        try {
            secEnterPriseUserRepository.delByUserId(userId);
            flag = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return flag;
    }


    public   SecEnterPriseUser findByByUserId(Long byUserId){
        return  secEnterPriseUserRepository.findByByUserId(byUserId);
    }

    public List<User>  findByUserIdAndParentId(Long userId, Long parentId){

        Query query =this.entityManager.createNativeQuery(" SELECT a.* from talkie_t_user a   where    a.merchant_level=14 and  a.`status` =1 and a.is_del =1 and a.parent_id = ?2   and a.id  not in \n" +
                "(SELECT sec_enterprise_id as id  from talkie_t_sec_enterprise_user  where  by_user_id   = ?1   )\n",User.class);
        query.setParameter("1",userId);
        query.setParameter("2",parentId);
        return  query.getResultList();

    }

}
