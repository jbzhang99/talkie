package com.meta.user;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.feignclient.user.UserFriendClient;
import com.meta.model.user.MUserFriend;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by lhq on 2017/10/27.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "userFriend", description = "用户好友接口")
public class UserFriendController extends BaseControllerUtil {
    //日志
    private  static  final Logger logger= LoggerFactory.getLogger(UserFriendController.class);

    @Autowired
    private UserFriendClient userFriendClient;

    @RequestMapping(value = ServiceUrls.UserFriend.USER_FRIEND_BATCH_ADD_OR_DEL, method = RequestMethod.POST)
    @ApiOperation(value = "批量新增好友", notes = "批量新增好友")
    public Result<MUserFriend> batchAddOrDelFriend(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MUserFriend mUserFriend) throws Exception {
        Result<MUserFriend> result = null;
        try {
            result = userFriendClient.batchAddOrDelFriend(mUserFriend);
        } catch (Exception e) {
            logger.error("新增/删除失败！");
            logger.error(e.getMessage(),e);
            return error("新增/删除失败！");
        }
        return result;
    }

}
