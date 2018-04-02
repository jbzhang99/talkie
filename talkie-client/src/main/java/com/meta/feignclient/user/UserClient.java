package com.meta.feignclient.user;

import com.meta.*;
import com.meta.feignfallback.user.UserFallBack;
import com.meta.model.group.MGroupUser;
import com.meta.model.user.MUser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lhq on 2017/10/9.
 */
@Component
@FeignClient(name = ServiceNames.TALKIE_CORE,fallbackFactory = UserFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface UserClient {


    @RequestMapping(value = ServiceUrls.User.LOGIN_BY_ACCOUNT, method = RequestMethod.POST)
    @ApiOperation(value = "根据账号和密码登录", notes = "根据账号和密码登录")
    Result<MUser> loginByAccount(
            @ApiParam(name = "account", value = "账户", defaultValue = "")
            @RequestParam(value = "account", required = false) String account,
            @ApiParam(name = "password", value = "密码", defaultValue = "")
            @RequestParam(value = "password", required = false) String password);


    @Cacheable(value = RedisValue.FIND_Q_USER, key = "'search_user_conditions_filters='+#p0+'_and_sorts='+#p1+'_and_size='+#p2+'_and_page='+#p3", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.User.USERS, method = RequestMethod.GET)
    @ApiOperation(value = "获取用户列表", notes = "根据查询条件获用户列表在前端表格展示")
    Result<MUser> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);


    @Cacheable(value = RedisValue.FIND_Q_USER, key = "'search_user_conditions_account='+#p0", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.User.USER_FIND_BY_ACCOUNT, method = RequestMethod.GET)
    @ApiOperation(value = "根据账号查找", notes = "根据账号查找")
    Result<MUser> findByAccount(
            @ApiParam(name = "account", value = "账户", defaultValue = "")
            @RequestParam(value = "account", required = false) String account);


    @RequestMapping(value = ServiceUrls.User.USER_COUNT_COMPANY_BY_PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "统计子代理的下属企业数量", notes = "统计子代理的下属企业数量")
    Integer countCompayByParentId(
            @ApiParam(name = "parentId", value = "父ID", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "isDel", value = "是否删除", defaultValue = "")
            @RequestParam(value = "isDel", required = false) Integer isDel);

    @CacheEvict(value = RedisValue.FIND_Q_USER, allEntries = true)
    @RequestMapping(value = ServiceUrls.User.USERS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改用户信息", notes = "创建/修改用户信息")
    Result<MUser> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MUser mUser);

    @CacheEvict(value = RedisValue.FIND_Q_USER, allEntries = true)
    @RequestMapping(value = ServiceUrls.User.USERS, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户")
    Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "")
            @RequestParam(value = "merchantLevel", required = false) String merchantLevel,
            @ApiParam(name = "currentUserId", value = "currentUserId", defaultValue = "")
            @RequestParam(value = "currentUserId", required = false) Long currentUserId);

    @CacheEvict(value = RedisValue.FIND_Q_USER, allEntries = true)
    @RequestMapping(value = ServiceUrls.User.USER_MODIYY_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    Result modifyUserById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status);

    @RequestMapping(value = ServiceUrls.User.USER_ACCOUNT, method = RequestMethod.GET)
    @ApiOperation(value = "获取用户列表(account)", notes = "获取用户列表(account)")
    Result<MUser> searchByAccount(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);


    @RequestMapping(value = ServiceUrls.User.USER_BY_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据ID获取用户", notes = "根据ID获取用户")
    Result<MUser> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id);

    @CacheEvict(value = RedisValue.FIND_Q_USER, allEntries = true)
    @RequestMapping(value = ServiceUrls.User.USER_MODIFY_PASSWORD_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    Result modifyPassword(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password);

    @RequestMapping(value = ServiceUrls.User.USER_FIND_BY_ACCOUNT_AND_MERCHANT_LEVEL_AND_PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据账号，用户等级，父ID查找详情", notes = "根据账号，用户等级，父ID查找详情")
    Result<MUser> findByAccountAndParentId(
            @ApiParam(name = "account", value = "account", defaultValue = "")
            @RequestParam(value = "account", required = false) String account,
            @ApiParam(name = "parentId", value = "ParentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId);


    @Cacheable(value = RedisValue.FIND_Q_USER, key = "'find_wait_user_group_conditions_parentId='+#p0+'_and_groupId='+#p1", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.User.USER_WAIT_USER_GROUP, method = RequestMethod.GET)
    @ApiOperation(value = "群组用，查找待添加的用户", notes = "群组用，查找待添加的用户")
    Result<MGroupUser> findWaitUserGroup(
            @ApiParam(name = "parentId", value = "parentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "groupId", value = "groupId", defaultValue = "")
            @RequestParam(value = "groupId", required = false) String groupId);

    @Cacheable(value = RedisValue.FIND_Q_USER, key = "'find_already_user_group_conditions_parentId='+#p0", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.User.USER_FIND_ALREADY_USER_GROUP, method = RequestMethod.GET)
    @ApiOperation(value = "查找已添加 的用户列表(群组用)", notes = "查找已添加 的用户列表(群组用)")
    Result<MGroupUser> findAlreadyUserGroup(
            @ApiParam(name = "groupId", value = "groupId", defaultValue = "")
            @RequestParam(value = "groupId", required = false) Long groupId);

    @Cacheable(value = RedisValue.FIND_Q_USER, key = "'find_can_add_friend_conditions_id='+#p0+'_and_parentId='+#p1", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.User.USER_FIND_CAN_ADD_FRIEND, method = RequestMethod.GET)
    @ApiOperation(value = "查找可以添加的好友 屏蔽自己", notes = "查找可以添加的好友 屏蔽自己")
    public Result<MUser> findCanAddFriend(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "parentId", value = "parentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId);

    @Cacheable(value = RedisValue.FIND_Q_USER, key = "'find_already_add_friend_conditions_parentId='+#p0", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.User.USER_ALREADY_ADD_FRIEND, method = RequestMethod.GET)
    @ApiOperation(value = "好友列表--查找已经添加的好友列表", notes = "好友列表--查找已经添加的好友列表")
    public Result<MUser> findAlreadyAddFriend(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id);

    @CacheEvict(value = RedisValue.FIND_Q_USER, allEntries = true)
    @RequestMapping(value = ServiceUrls.User.USER_FIND_BY_ACCOUNT, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除账号", notes = "根据id删除账号")
    public Result delById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id);

    @CacheEvict(value = RedisValue.FIND_Q_USER, allEntries = true)
    @RequestMapping(value = ServiceUrls.User.USERS_BATCH_SAVE, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改用户信息", notes = "创建/修改用户信息")
    public Result<MUser> batchSave(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MUser[] mUsers) throws Exception;

    @Cacheable(value = RedisValue.FIND_Q_USER, key = "'search_user_and_group_conditions_filters='+#p0+'_and_currGroupList='+#p1+'_and_sorts='+#p2+'_and_size='+#p3+'_and_page='+#p4", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.User.USER_SEARCH_AND_GROUP, method = RequestMethod.GET)
    @ApiOperation(value = "查找好友列表，有含群组ID", notes = "查找好友列表，有含群组ID")
    public Result<MUser> searchUserAndGroup(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "currGroupList", value = "currGroupList", defaultValue = "")
            @RequestParam(value = "currGroupList", required = false) Long currGroupList,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);

    @Cacheable(value = RedisValue.FIND_Q_USER, key = "'search_user_and_group_and_date_conditions_filters='+#p0+'_and_currGroupList='+#p1+'_and_GT_createDate='+#p2+'_and_LT_createDate='+#p3+'_and_sorts='+#p4+'_and_size='+#p5+'_and_page='+#p6", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.User.USER_SEARCH_AND_GROUP_DATE, method = RequestMethod.GET)
    @ApiOperation(value = "查找好友列表，有含群组ID,有日期(Q币分配用)", notes = "查找好友列表，有含群组ID,有日期(Q币分配用)")
     Result<MUser> searchUserAndGroupAndDate(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "currGroupList", value = "currGroupList", defaultValue = "")
            @RequestParam(value = "currGroupList", required = false) Long currGroupList,
            @ApiParam(name = "GT_createDate", value = "GT_createDate", defaultValue = "")
            @RequestParam(value = "GT_createDate", required = false) String GT_createDate,
            @ApiParam(name = "LT_createDate", value = "LT_createDate", defaultValue = "")
            @RequestParam(value = "LT_createDate", required = false) String LT_createDate,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);

    @RequestMapping(value = ServiceUrls.User.USER_ONLINE_STATUS, method = RequestMethod.GET)
    @ApiOperation(value = "查询在线状态", notes = "查询在线状态")
    Boolean queryUserOnlineStatus(
            @ApiParam(name = "userId", value = "用户id", defaultValue = "")
            @RequestParam(value = "userId", required = false) Long userId);


    @Cacheable(value = RedisValue.FIND_Q_USER, key = "'search_user_isOnline_filters='+#p0", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.User.user_search_user_isOnline, method = RequestMethod.GET)
    @ApiOperation(value = "查找看用户是否在线", notes = "实际为查看全部终端")
    public Result<MUser> searchUserIsOnline(
            @ApiParam(name = "currGroupList", value = "currGroupList", defaultValue = "")
            @RequestParam(value = "currGroupList", required = false) Long currGroupList,
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters);


    @PostMapping(value = ServiceUrls.User.ICCIDUSERS_BATCH_SAVE)
    @ApiOperation(value = "iccID用户 批量创建 or 编辑" ,notes = "如果一条信息失败会触发事务回滚")
    public  Result<MUser> iccIdUserBatchSave(@ApiParam(name = "json_model", value = "", defaultValue = "")
                                             @RequestBody  MUser[] users);
}
