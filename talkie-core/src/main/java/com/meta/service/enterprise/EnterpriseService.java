package com.meta.service.enterprise;

import com.meta.dao.enterprise.EnterpriseRepository;
import com.meta.model.totolInfo.EnterPriseTotalCountInfo;
import com.meta.model.user.User;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import com.meta.redissync.terminal.RedisSyncTerminal;
import com.meta.sync.utils.SyncUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by lhq on 2017/11/14.
 */
@Service
@Transactional
public class EnterpriseService extends BaseServiceImpl<User, Long> implements BaseService<User, Long> {
    @Autowired
    private EnterpriseRepository enterpriseRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    SyncUtils syncUtils;
    @Autowired
    public void setBaseDao(EnterpriseRepository enterpriseRepository) {
        super.setBaseDao(enterpriseRepository);
    }

    /**
     * 统计子代理的下属企业数量
     */
    public Integer countCompayByParentId(Long parentId, Integer isDel) {

        String queryTemp = "SELECT count(1) from talkie_t_user  a where a.parent_id= ? and a.is_del= ? ";
        Integer countNun = jdbcTemplate.queryForObject(queryTemp, Integer.class, parentId, isDel);
        return countNun;
    }

    /**
     * 根据ID，更改状态
     */
    public void modifyStatusById(Long id, String status) {
        enterpriseRepository.modifyStatusById(id, status);
    }

    /**
     * 根据ID。修改密码
     */
    public void modifyPassword(Long id, String password) {
        enterpriseRepository.modifyPassword(id, password);
    }

    /**
     * 根据父ID查询
     */
    public List<User> findByParentId(Long ParentId) {
        return enterpriseRepository.findByParentId(ParentId);
    }


    /**
     * 根据父ID统计 总数
     *
     * @param ParentId
     * @return
     */
    public Long countByParentId(Long ParentId) {
        return enterpriseRepository.countByParentId(ParentId);
    }

    /**
     * 根据父ID和等级统计 总数
     * @param parentId
     * @param merchantLevel
     * @return
     */
    public  Long countByParentIdAndMerchantLevel (Long parentId, String merchantLevel){
        return  enterpriseRepository.countByParentIdAndMerchantLevel(parentId, merchantLevel);
    }

    /**
     * 根据账号查询详情
     */
    public User findByAccountAndParentId(String account, Long parentId) {

        return enterpriseRepository.findByAccountAndParentId(account, parentId);
    }

    /**
     * 根据account查找用户信息
     *
     * @param account
     * @return
     */
    public User findByAccount(String account) {
        return enterpriseRepository.findByAccount(account);
    }


    /**
     * @param id
     * @param flag
     * @return
     */
    public EnterPriseTotalCountInfo getEnterPriseTotalInfo(Long id, boolean flag) {
        EnterPriseTotalCountInfo enterPriseTotalCountInfo = new EnterPriseTotalCountInfo();
        String groupSql = "SELECT count(1) from talkie_t_group a where a.user_id= ? ";
        Integer countgroup = jdbcTemplate.queryForObject(groupSql, Integer.class, id);
        enterPriseTotalCountInfo.setTotalGroup(countgroup);
        Integer count = 0;
        Integer countNOUser = 0;
        Integer totalOnlineUser =0;
        String sql = "SELECT count(1) from talkie_t_user  a where a.parent_id= ? and a.is_del= 1 and a.merchant_level =7";
        Integer intTemp = jdbcTemplate.queryForObject(sql, Integer.class, id);
        count += intTemp;

        String tempSql2 = "SELECT count(1) from talkie_t_user  a where a.parent_id= ? and a.is_del= 1 and a.status=1 and a.merchant_level =7 ";
        Integer intTemp2 = jdbcTemplate.queryForObject(tempSql2, Integer.class, id);
        countNOUser += intTemp2;



        List<User> userList = enterpriseRepository.findByParentIdAndMerchantLevel(id,"7");
        if(userList.size() >0){

            for(User temp: userList){
                Boolean isOnline = syncUtils.getOnlineStatusByUserId(temp.getId());
                if( isOnline) {
                    totalOnlineUser+=1;
                }
            }
            enterPriseTotalCountInfo.setTotalOnlineUser(totalOnlineUser);
        }


        if (flag) {
        List<User> list = enterpriseRepository.findByParentIdAndMerchantLevel(id,"14");
        if (list.size() > 0) {
            for (User temp : list) {
                String tempSql = "SELECT count(1) from talkie_t_user  a where a.parent_id= ? and a.is_del= 1 and a.merchant_level =7";
                Integer tempInt = jdbcTemplate.queryForObject(tempSql, Integer.class, temp.getId());
                count += tempInt;

                String tempSql1 = "SELECT count(1) from talkie_t_user  a where a.parent_id= ? and a.is_del= 1 and a.status=1 and a.merchant_level =7 ";
                Integer tempInt1 = jdbcTemplate.queryForObject(tempSql1, Integer.class, temp.getId());
                countNOUser += tempInt1;
            }
        }
        }

        enterPriseTotalCountInfo.setTotalReginUser(count);
        enterPriseTotalCountInfo.setTotalUsedUser(countNOUser);

        return enterPriseTotalCountInfo;
    }




}
