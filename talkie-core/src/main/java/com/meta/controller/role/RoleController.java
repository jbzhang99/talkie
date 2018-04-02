package com.meta.controller.role;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.model.role.Role;
import com.meta.service.role.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by llin on 2017/9/28.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "role", description = "权限接口")
public class RoleController extends BaseControllerUtil {

    private final static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = ServiceUrls.Role.ROLES, method = RequestMethod.GET)
    @ApiOperation(value = "获取权限列表", notes = "根据查询条件获权限在前端表格展示")
    public Result<Role> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<Role> result = roleService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }


    @RequestMapping(value = ServiceUrls.Role.ROLES, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改权限信息", notes = "创建/修改权限信息")
    public Result<Role> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid Role role) {
        return success(roleService.save(role));
    }

    @RequestMapping(value = ServiceUrls.Role.ROLE, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取权限信息", notes = "根据id获取权限信息")
    public Result<Role> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) {
        Role role = roleService.findOne(id);
        return success(role);
    }


    @RequestMapping(value = ServiceUrls.Role.ROLES, method = RequestMethod.DELETE)
    @ApiOperation(value = "删除权限信息", notes = "根据id删除权限信息")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id) {
        roleService.delete(id);
        return success(null);
    }
}
