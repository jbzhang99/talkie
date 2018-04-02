package com.meta.service.merchant;

import com.meta.dao.merchant.MerchantRepository;
import com.meta.model.totolInfo.MerchantTotalCountInfo;
import com.meta.model.totolInfo.TotalCountInfo;
import com.meta.model.user.User;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import com.meta.sync.utils.SyncUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Created by lhq on 2017/11/14.
 */
@Service
@Transactional
public class MerchantService extends BaseServiceImpl<User, Long> implements BaseService<User, Long> {

    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    SyncUtils syncUtils;

    @Autowired
    public void setBaseDao(MerchantRepository merchantRepository) {
        super.setBaseDao(merchantRepository);
    }

    /**
     * 统计子代理的下属企业数量
     *
     */
    public Integer countCompayByParentId(Long parentId, Integer isDel) {

        String queryTemp = "SELECT count(1) from talkie_t_user  a where a.parent_id= ? and a.is_del= ?   ";

        Integer countNun = jdbcTemplate.queryForObject(queryTemp, Integer.class, parentId, isDel);
        return countNun;
    }



    /**
     * 统计子代理的下属企业数量 以及 终端数量
     *
     */
    public Map<String,Integer> getcountCompayAndCountTerminalByproxyId(Long parentId, Integer isDel) {

        String queryTemp = "SELECT COUNT(1) AS countCompany, SUM(( SELECT COUNT(1) FROM talkie_t_user WHERE parent_id = a.id AND merchant_level = '7' )) AS countTerminal FROM talkie_t_user a WHERE a.parent_id = :id   AND a.is_del = :isDel AND merchant_level = '4'";
        Query query= this.entityManager.createNativeQuery(queryTemp);
        query.setParameter("id",parentId);
        query.setParameter("isDel",isDel);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String,Integer> > tempList=query.getResultList();
        return tempList!=null?tempList.get(0):null;
    }

    /**
     * 根据ID，更改状态
     */
    public void modifyStatusById(Long id, String status) {
        merchantRepository.modifyStatusById(id, status);
    }

    /**
     * 根据ID。修改密码
     */
    public void modifyPasswordById(Long id, String password) {
        merchantRepository.modifyPasswordById(id, password);
    }

    /**
     * 根据父ID查询
     */
    public List<User> findByParentId(Long ParentId) {
        return merchantRepository.findByParentId(ParentId);
    }

    /**
     * 根据账号查询详情
     */
    public User findByAccountAndParentId(String account, Long parentId) {

        return merchantRepository.findByAccountAndParentId(account, parentId);
    }


    /**
     * 根据account查找用户信息
     *
     * @param account
     * @return
     */
    public User findByAccount(String account) {
        return merchantRepository.findByAccount(account);
    }

    /**
     * 总代统计信息
     */
    public TotalCountInfo getTotalCountInfo(Long id) {
        TotalCountInfo totalCountInfo = new TotalCountInfo();

        String queryTemp = "SELECT count(1) from talkie_t_user  a where a.parent_id= ? and a.is_del= 1  ";
        Integer countMerchant = jdbcTemplate.queryForObject(queryTemp, Integer.class, id);
        totalCountInfo.setTotalMerchant(countMerchant);

        String regeditUser = "SELECT count(1) from talkie_t_user  a where a.merchant_level =7 ";
        Integer totalReginUser = jdbcTemplate.queryForObject(regeditUser, Integer.class);
        totalCountInfo.setTotalReginUser(totalReginUser);

        String userUser = "SELECT count(1) from talkie_t_user  a where a.merchant_level =7  and a.`status`=1";
        Integer totalUsedUser = jdbcTemplate.queryForObject(userUser, Integer.class);
        totalCountInfo.setTotalUsedUser(totalUsedUser);
        return totalCountInfo;
    }

    /**
     * 统计代理及子代理
     * @param id
     * @param falg
     * @return
     */
    public MerchantTotalCountInfo getMerchantTotalCountInfo(Long id, boolean falg) {
        MerchantTotalCountInfo merchantTotalCountInfo = new MerchantTotalCountInfo();

        String queryTemp = "SELECT count(1) from talkie_t_user  a where a.parent_id= ? and a.is_del= 1 and a.merchant_level =3 ";
        Integer countMerchant = jdbcTemplate.queryForObject(queryTemp, Integer.class, id);
        merchantTotalCountInfo.setTotalSecMerchant(countMerchant);

        String enterpriseSql = "SELECT count(1) from talkie_t_user  a where a.parent_id= ? and a.is_del= 1 and a.merchant_level =4 ";
        Integer countEnterPrise = jdbcTemplate.queryForObject(enterpriseSql, Integer.class, id);
        merchantTotalCountInfo.setTotalEnterPrise(countEnterPrise);



        //注册用户
        List<User> list = merchantRepository.findByParentId(id);

        if (list.size() > 0) {
            Integer count = 0;
            Integer countNOUser = 0;
            Integer totalOnlineUser =0;
            for (User temp : list) {

                List<User> userList = merchantRepository.findByParentIdAndMerchantLevel(temp.getId(),"7");
                if(userList.size() >0){
                    for(User user: userList){
                        Boolean isOnline = syncUtils.getOnlineStatusByUserId(user.getId());
                        if(isOnline) {
                            totalOnlineUser+=1;
                        }
                    }
                    merchantTotalCountInfo.setTotalOnlineUser(totalOnlineUser);
                }

                //企业
                if (temp.getMerchantLevel().equals("4")) {
                    String tempSql = "SELECT count(1) from talkie_t_user  a where a.parent_id= ? and a.is_del= 1 and a.merchant_level =7 ";
                    Integer tempInt = jdbcTemplate.queryForObject(tempSql, Integer.class, temp.getId());
                    count += tempInt;

                    String tempSql1 = "SELECT count(1) from talkie_t_user  a where a.parent_id= ? and a.is_del= 1 and a.status=1 and a.merchant_level =7 ";
                    Integer tempInt1 = jdbcTemplate.queryForObject(tempSql1, Integer.class, temp.getId());
                    countNOUser += tempInt1;
                }
                if (falg) {
                    //子代理
                    if (temp.getMerchantLevel().equals("3")) {

                        String tempSql = "SELECT count(1) from talkie_t_user  a where a.parent_id= ? and a.is_del= 1 and a.merchant_level =7 ";
                        Integer tempInt = jdbcTemplate.queryForObject(tempSql, Integer.class, temp.getId());
                        count += tempInt;

                        String tempSql1 = "SELECT count(1) from talkie_t_user  a where a.parent_id= ? and a.is_del= 1 and a.status=1 and a.merchant_level =7 ";
                        Integer tempInt1 = jdbcTemplate.queryForObject(tempSql1, Integer.class, temp.getId());
                        countNOUser += tempInt1;
                    }
                }
            }
            merchantTotalCountInfo.setTotalReginUser(count);
            merchantTotalCountInfo.setTotalUsedUser(countNOUser);
        }

return merchantTotalCountInfo;
    }

    /**
     *  根据父ID和等 级统计
     * @param parentId
     * @param merchantLevel
     * @return
     */
    public  Long countParentIdAndMerchantLevel(Long parentId,String merchantLevel){
        return  merchantRepository.countByParentIdAndMerchantLevel(parentId, merchantLevel);
    }

}
