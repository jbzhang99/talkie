package com.meta.feignclient.menu;

import com.meta.*;
import com.meta.feignfallback.menu.MenuFallBack;
import com.meta.model.menu.MMenu;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by lhq on 2017/11/8.
 */
@FeignClient(name = ServiceNames.TALKIE_CORE,fallbackFactory = MenuFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface MeunClient {


    @Cacheable(value = RedisValue.FIND_MENU, key = "'menu_find_by_role_conditions_roleId='+#p1", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.Menu.MUEN_FIND_BY_ROLE, method = RequestMethod.GET)
    @ApiOperation(value = "根据权限获取菜单", notes = "根据权限获取菜单")
    Result<MMenu> findByRole(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id,
            @ApiParam(name = "roleId", value = "roleId", defaultValue = "")
            @RequestParam(value = "roleId") String roleId);


    @Cacheable(value = RedisValue.FIND_MENU,key = "'getMenuTree'",unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.Menu.MENU, method = RequestMethod.GET)
    @ApiOperation(value = "菜单列表树", notes = "菜单列表树")
    Result<MMenu> getMenuTree();


    @CacheEvict(value = RedisValue.FIND_MENU, allEntries = true)
    @RequestMapping(value = ServiceUrls.Menu.MENU, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改菜单信息", notes = "创建/修改菜单信息")
    public Result<MMenu> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody MMenu menu);

    @Cacheable(value = RedisValue.FIND_MENU, key = "'menu_find_id='+#p1", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.Menu.MUEN, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取菜单信息", notes = "根据id获取菜单信息")
    public Result<MMenu> getById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id);

    @CacheEvict(value = RedisValue.FIND_MENU, allEntries = true)
    @RequestMapping(value = ServiceUrls.Menu.MENU, method = RequestMethod.DELETE)
    @ApiOperation(value = "删除菜单信息", notes = "根据id删除菜单信息")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) ;

}
