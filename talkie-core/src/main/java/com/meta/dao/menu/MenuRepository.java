package com.meta.dao.menu;

import com.meta.model.menu.Menu;
import com.meta.query.BaseRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * Created by llin on 2017/9/29.
 */
public interface MenuRepository  extends BaseRepository<Menu, Long> {

    /**
     * 根据 查询子菜单
     * @param parentId
     * @return
     */
    public List<Menu> findByParentId(@Param("parentId")Long parentId);

    /**
     * 批量删除
     * @param ID
     */
    public  void deleteByIdIn(@Param("id")Collection<Long> ID);
}
