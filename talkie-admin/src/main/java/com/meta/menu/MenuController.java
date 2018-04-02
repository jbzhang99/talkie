package com.meta.menu;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.feignclient.menu.MeunClient;
import com.meta.model.menu.MMenu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lhq on 2017/11/8.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "menu", description = "菜单接口")
public class MenuController extends BaseControllerUtil {
    //日志
    private  static  final Logger logger= LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MeunClient meunClient;


    @RequestMapping(value = ServiceUrls.Menu.MUEN_FIND_BY_ROLE, method = RequestMethod.GET)
    @ApiOperation(value = "根据权限获取菜单", notes = "根据权限获取菜单")
    public Result<MMenu> findByRole(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id,
            @ApiParam(name = "roleId", value = "roleId", defaultValue = "")
            @RequestParam(value = "roleId") String roleId) throws Exception {
        Result<MMenu> result = null;
        try {
            result = meunClient.findByRole(id, roleId);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.Menu.MENU, method = RequestMethod.GET)
    @ApiOperation(value = "菜单列表树", notes = "菜单列表树")
    Result<MMenu> getMenuTree(){
        Result<MMenu> result = null;
        try {
            result = meunClient.getMenuTree();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.Menu.MENU, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改菜单信息", notes = "创建/修改菜单信息")
    public Result<MMenu> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody MMenu menu){
        Result<MMenu> result = null;

        try {
            result = meunClient.create(menu);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.Menu.MUEN, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取菜单信息", notes = "根据id获取菜单信息")
    public Result<MMenu> getById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id){
        Result<MMenu> result = null;
        try {
            result = meunClient.getById(id);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.Menu.MENU, method = RequestMethod.DELETE)
    @ApiOperation(value = "删除菜单信息", notes = "根据id删除菜单信息")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id){
        Result result = null;
        try {
            result = meunClient.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return result;
    }





}
