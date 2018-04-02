package com.meta.enterprise;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.feignclient.enterprise.SecEnterPriseUserClient;
import com.meta.model.enterprise.MSecEnterPriseUser;
import com.meta.model.user.MUser;
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

/**
 * Created by lhq on 2017/11/22.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "sec_enterprise_user", description = "二级企业与用户信息接口")
public class SecEnterPriseUserController extends BaseControllerUtil {

    //日志
    private  static  final Logger logger= LoggerFactory.getLogger(SecEnterPriseUserController.class);
    @Autowired
    private SecEnterPriseUserClient secEnterPriseUserClientl;


    @RequestMapping(value = ServiceUrls.SecEnterPriseUser.SEC_ENTER_PRISE_USERS,method = RequestMethod.GET)
    @ApiOperation(value = "根据userId和PID查找二级企业",notes = "根据ID和PID查找二级企业")
    public  Result<MUser> findByUserIdAndParentId(
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId") Long userId,
            @ApiParam(name = "parentId", value = "parentId", defaultValue = "")
            @RequestParam(value = "parentId") Long parentId) throws  Exception{
        Result<MUser> result = null;
        try {
            result = secEnterPriseUserClientl.findByUserIdAndParentId(userId, parentId);
        } catch (Exception e) {
            logger.error("查找失败！");
            logger.error(e.getMessage(),e);
            return error("查找失败！");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.SecEnterPriseUser.SEC_ENTER_PRISE_USERS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改二级企业与用户信息", notes = "创建/修改二级企业与用户信息")
    public Result<MSecEnterPriseUser> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MSecEnterPriseUser mSecEnterPriseUser) throws Exception {
        Result<MSecEnterPriseUser> result = null;
        try {
            result = secEnterPriseUserClientl.create(mSecEnterPriseUser);
        } catch (Exception e) {
            logger.error("创建/修改二级企业与用户失败！");
            logger.error(e.getMessage(),e);
            return error("创建/修改二级企业与用户失败！");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.SecEnterPriseUser.SEC_ENTER_PRISE_USERS, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户")
    public Result deleteById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) throws Exception {
        Result<MSecEnterPriseUser> result = null;
        try {
            result = secEnterPriseUserClientl.deleteById(id);
        } catch (Exception e) {
            logger.error("删除失败！");
            logger.error(e.getMessage(),e);
            return error("删除失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.SecEnterPriseUser.SEC_ENTER_PRISE_USER,method = RequestMethod.GET)
    @ApiOperation(value = "根据userId和PID查找二级企业",notes = "根据ID和PID查找二级企业")
    public  Result<MSecEnterPriseUser> findByUserId(
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId") Long userId) throws  Exception{
        Result<MSecEnterPriseUser> result = null;
        try {
            result = secEnterPriseUserClientl.findByUserId(userId);
        } catch (Exception e) {
            logger.error("查找失败！");
            logger.error(e.getMessage(),e);
            return error("查找失败！");
        }
        return result;
    }

    public static void main(String[] args) {

    }
}
