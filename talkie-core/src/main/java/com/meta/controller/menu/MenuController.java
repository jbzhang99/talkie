package com.meta.controller.menu;

import com.meta.*;
import com.meta.model.menu.Menu;
import com.meta.regex.RegexUtil;
import com.meta.service.menu.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lhq on 2017/9/30.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "menu", description = "菜单接口")
public class MenuController extends BaseControllerUtil {

    private final static Logger logger = LoggerFactory.getLogger(MenuController.class);
    @Autowired
    private MenuService menuService;

//    @RequestMapping(value = ServiceUrls.Menu.MUENS, method = RequestMethod.GET)
//    @ApiOperation(value = "获取菜单表", notes = "根据查询条件获菜单在前端表格展示")
//    public Result<Menu> searchs(
//            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
//            @RequestParam(value = "filters", required = false) String filters,
//            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
//            @RequestParam(value = "sorts", required = false) String sorts,
//            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
//            @RequestParam(value = "size", required = false) int size,
//            @ApiParam(name = "page", value = "页码", defaultValue = "1")
//            @RequestParam(value = "page", required = false) int page) {
////        Page<Menu> result = menuService.search(filters, sorts, page, size);
//        List<Menu> menus =    menuService.findAll();
//        List<Menu> mMenuList = new ArrayList<Menu>();
//        if(menus.size() >0 && menus != null){
//            for (int x = 0; x < menus.size(); x++) {
//                if (menus.get(x).getParentId().equals(0L)) {
//                    mMenuList.add(menus.get(x));
//                    List<Menu> mMenuList1 = new ArrayList<Menu>();
//                    for (int y = 0; y < menus.size(); y++) {
//                        if (menus.get(y).getParentId() .equals(menus.get(x).getId())) {
//                            mMenuList1.add(menus.get(y));
//                        }
//                    }
//                    mMenuList.get(x).setChildren(mMenuList1);
//
//                }
//
//            }
//
//        }
//        return  getResultList(mMenuList);
//    }


    @RequestMapping(value = ServiceUrls.Menu.MENU, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改菜单信息", notes = "创建/修改菜单信息")
    public Result<Menu> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid Menu menu) {
        return success(menuService.save(menu));
    }

    @RequestMapping(value = ServiceUrls.Menu.MUEN, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取菜单信息", notes = "根据id获取菜单信息")
    public Result<Menu> getById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) {
        Menu menu = menuService.findOne(id);
        return success(menu);
    }

    @RequestMapping(value = ServiceUrls.Menu.MUEN_FIND_BY_ROLE, method = RequestMethod.GET)
    @ApiOperation(value = "根据权限获取菜单", notes = "根据权限获取菜单")
    public   Result<Menu> findByRole(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id,
            @ApiParam(name = "roleId", value = "roleId", defaultValue = "")
            @RequestParam(value = "roleId") String roleId) throws Exception {
        List<Menu> menus = menuService.findMenuByRole(id, roleId);
        List<Menu> mMenuList = new ArrayList<Menu>();
        if(menus.size() >0 && menus != null){
            for (int x = 0; x < menus.size(); x++) {
                if (menus.get(x).getParentId().equals(0L)) {
                    List<Menu> mMenuList1 = new ArrayList<Menu>();
                    for (int y = 0; y < menus.size(); y++) {
                        if (menus.get(y).getParentId() .equals(menus.get(x).getId())) {
                            mMenuList1.add(menus.get(y));
                        }
                    }
                    mMenuList.add(menus.get(x));
                    menus.get(x).setChildren(mMenuList1);
                }

            }
        }
        return getResultList(mMenuList);
    }

    @RequestMapping(value = ServiceUrls.Menu.MENU,method = RequestMethod.GET)
    @ApiOperation(value = "获取全部菜单",notes = "菜单列表树")
    public Result<Menu> getMenuList(){
        List<Menu> menus =   menuService.findAll();
        List<Menu> mMenuList = new ArrayList<Menu>();
        if(menus.size() >0 && menus != null){
            for (int x = 0; x < menus.size(); x++) {
                if (menus.get(x).getParentId().equals(0L)) {
                    List<Menu> mMenuList1 = new ArrayList<Menu>();
                    for (int y = 0; y < menus.size(); y++) {
                        if (menus.get(y).getParentId() .equals(menus.get(x).getId())) {
                            mMenuList1.add(menus.get(y));
                        }
                    }
                    menus.get(x).setChildren(mMenuList1);
                    mMenuList.add(menus.get(x));
                }

            }

        }
        return getResultList(mMenuList);
    }

    @RequestMapping(value = ServiceUrls.Menu.MENU, method = RequestMethod.DELETE)
    @ApiOperation(value = "删除菜单信息", notes = "根据id删除菜单信息")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) throws Exception {
        //删除菜单项需要顺便删除子菜单
      List<Long> ids= getChild(id);
      ids.add(id);
       menuService.delete(ids);
        return success(null);
    }

    public List<Long> getChild(Long id){//获取儿子
        List<Menu> menuList=menuService.findByParentId(id);
        List<Long> ids=new ArrayList<Long>();
        if(menuList!=null){
            for (Menu temp:menuList){
                System.err.println(temp.getId());
                ids.add(temp.getId());
                ids.addAll(getChild(temp.getId()));
            }
        }
        return ids;
    }

}
