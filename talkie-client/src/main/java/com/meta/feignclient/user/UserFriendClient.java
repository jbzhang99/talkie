package com.meta.feignclient.user;

import com.meta.*;
import com.meta.model.user.MUserFriend;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * Created by lhq on 2017/10/27.
 */
@FeignClient(name = ServiceNames.TALKIE_CORE)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface UserFriendClient {


    @CacheEvict(value = RedisValue.FIND_Q_USER, allEntries = true)
    @RequestMapping(value = ServiceUrls.UserFriend.USER_FRIEND_BATCH_ADD_OR_DEL, method = RequestMethod.POST)
    @ApiOperation(value = "批量新增好友", notes = "批量新增好友")
     Result<MUserFriend> batchAddOrDelFriend(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MUserFriend mUserFriend);
}
