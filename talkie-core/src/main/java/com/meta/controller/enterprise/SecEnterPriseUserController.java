package com.meta.controller.enterprise;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.datetime.DateTimeUtil;
import com.meta.model.enterprise.SecEnterPriseUser;
import com.meta.model.user.User;
import com.meta.regex.RegexUtil;
import com.meta.service.enterprise.SecEnterPriseUserService;
import com.meta.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Created by lhq on 2017/11/22.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "sec_enterprise_user", description = "二级企业与用户信息接口")
public class SecEnterPriseUserController extends BaseControllerUtil {

    @Autowired
    private SecEnterPriseUserService secEnterPriseUserService;
    @Autowired
    private UserService userService;


    @RequestMapping(value = ServiceUrls.SecEnterPriseUser.SEC_ENTER_PRISE_USERS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改二级企业与用户信息", notes = "创建/修改二级企业与用户信息")
    public Result<SecEnterPriseUser> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid SecEnterPriseUser secEnterPriseUser) throws Exception {
        User user = userService.findOne(secEnterPriseUser.getByUserId());
        secEnterPriseUser.setUser(user);
        return success(secEnterPriseUserService.save(secEnterPriseUser));

    }


    @RequestMapping(value = ServiceUrls.SecEnterPriseUser.SEC_ENTER_PRISE_USERS, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户")
    public Result deleteById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) throws Exception {
        secEnterPriseUserService.delete(id);
        return success(null);
    }

    @RequestMapping(value = ServiceUrls.SecEnterPriseUser.SEC_ENTER_PRISE_USERS, method = RequestMethod.GET)
    @ApiOperation(value = "根据userId和PID查找二级企业", notes = "根据ID和PID查找二级企业")
    public Result<User> findByUserIdAndParentId(
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId") Long userId,
            @ApiParam(name = "parentId", value = "parentId", defaultValue = "")
            @RequestParam(value = "parentId") Long parentId) throws Exception {
        List<User> userList = secEnterPriseUserService.findByUserIdAndParentId(userId, parentId);
        return getResultList(userList);
    }


    @RequestMapping(value = ServiceUrls.SecEnterPriseUser.SEC_ENTER_PRISE_USER, method = RequestMethod.GET)
    @ApiOperation(value = "根据userId 查找二级企业", notes = "根据ID 查找二级企业")
    public Result<SecEnterPriseUser> findByUserId(
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId") Long userId) throws Exception {
        SecEnterPriseUser secEnterPriseUser = secEnterPriseUserService.findByByUserId(userId);
        return success(secEnterPriseUser);
    }
}
