package com.meta.service.menu;

import com.meta.dao.menu.MenuRepository;
import com.meta.model.menu.Menu;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
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
public class MenuService extends BaseServiceImpl<Menu, Long> implements BaseService<Menu, Long> {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    public void setBaseDao(MenuRepository menuRepository) {
        super.setBaseDao(menuRepository);
    }


    /**
     * 根据权限获取菜单
     */
    public List<Menu> findMenuByRole(Long id, String roleId) {
        Query query = this.entityManager.createNativeQuery("SELECT a.* from talkie_t_menu a , talkie_t_user b , talkie_t_menu_role c where b.merchant_level=  ?2  and b.id= ?1 and c.role_id = b.merchant_level  and c.menu_id = a.id  and a.is_hide=1  ORDER BY   a.parent_id ASC ,a.sorts ASC", Menu.class);
        query.setParameter("2", roleId);
        query.setParameter("1", id);
        return query.getResultList();
    }

    /**
     * 根据 查询子菜单
     * @param parentId
     * @return
     */
    public List<Menu> findByParentId(Long parentId){

        return menuRepository.findByParentId(parentId);
    }

}
