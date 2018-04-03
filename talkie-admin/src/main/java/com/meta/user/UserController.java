package com.meta.user;

import com.alibaba.fastjson.JSONObject;
import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.commonUtil.CommonUtils;
import com.meta.commonUtil.EnglishCommonUtils;
import com.meta.feignclient.qmanage.QUserClient;
import com.meta.feignclient.user.UserClient;
import com.meta.jwt.Constant;
import com.meta.jwt.JwtUtil;
import com.meta.model.group.MGroupUser;
import com.meta.model.qmanage.MQUser;
import com.meta.model.user.MUser;
import com.meta.regex.RegexUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lhq on 2017/10/9.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "user", description = "会员信息接口")
public class UserController extends BaseControllerUtil {
    //日志
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserClient userClient;
    @Autowired
    private QUserClient qUserClient;


    @RequestMapping(value = ServiceUrls.User.LOGIN_BY_ACCOUNT, method = RequestMethod.POST)
    @ApiOperation(value = "根据账号和密码登录", notes = "根据账号和密码登录")
    public Result<MUser> loginByAccount(
            @ApiParam(name = "account", value = "账户", defaultValue = "")
            @RequestParam(value = "account", required = false) String account,
            @ApiParam(name = "password", value = "密码", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) throws Exception {
        Result<MUser> result = null;
        try {
            result = userClient.loginByAccount(account, password);
            if (RegexUtil.isNull(result)) {
                return error("账号或密码不正确！");
            }
            //颁发签证
            JSONObject jo = new JSONObject();
            jo.put("userId", result.getObj().getId());
            jo.put("roleId", result.getObj().getMerchantLevel());
            String token = JwtUtil.createJWT(Constant.JWT_ID, jo.toString(), Constant.JWT_TTL);
            result.getObj().setToken(token);
            result.getObj().setPassword("");
        } catch (Exception e) {
            logger.error("账号或密码不正确！");
            logger.error(e.getMessage(), e);
            return error("账号或密码不正确！");
        }
        return result;
    }



    @RequestMapping(value = ServiceUrls.User.USER_FIND_BY_ACCOUNT, method = RequestMethod.GET)
    @ApiOperation(value = "根据账号查找", notes = "根据账号查找")
    public Result<MUser> findByAccount(
            @ApiParam(name = "account", value = "账户", defaultValue = "")
            @RequestParam(value = "account", required = false) String account) throws Exception {
        Result<MUser> result = null;
        try {
            result = userClient.findByAccount(account);
        } catch (Exception e) {
            logger.error("无此账号！");
            logger.error(e.getMessage(), e);
            return error("无此账号！");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.User.USERS, method = RequestMethod.GET)
    @ApiOperation(value = "获取用户列表", notes = "根据查询条件获用户列表在前端表格展示")
    public Result<MUser> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "language", value = "语言", defaultValue = "")
            @RequestParam(value = "language", required = false) String language,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MUser> result = null;
        try {
            result = userClient.search(filters, "-createDate", size, page);
            if (result.getDetailModelList().size() > 0) {
                findDetail(result);
            }
        } catch (Exception e) {
            logger.error("获取列表异常！");
            logger.error(e.getMessage(), e);
            return error("获取列表异常！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.User.USERS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改用户信息", notes = "创建/修改用户信息")
    public Result<MUser> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MUser mUser) {
        Result<MUser> result = null;
        result = userClient.create(mUser);
        return result;
    }

    @RequestMapping(value = ServiceUrls.User.USERS_BATCH_SAVE, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改用户信息", notes = "创建/修改用户信息")
    public Result<MUser> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MUser[] mUser) {
        Result<MUser> result = null;
        try {

            result = userClient.batchSave(mUser);
        }catch (Exception e) {
            logger.error("创建失败！");
            logger.error(e.getMessage(), e);
            return error("创建失败！");
        }


        return result;
    }

    @RequestMapping(value = ServiceUrls.User.USERS, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "")
            @RequestParam(value = "merchantLevel", required = false) String merchantLevel,
            @ApiParam(name = "currentUserId", value = "currentUserId", defaultValue = "")
            @RequestParam(value = "currentUserId", required = false) Long currentUserId) throws Exception {
        Result<MUser> result = null;
        result = userClient.delete(id, merchantLevel, currentUserId);
        return result;
    }

    @RequestMapping(value = ServiceUrls.User.USER_MODIYY_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result<MUser> modifyUserById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status) throws Exception {
        Result<MUser> result = null;
        result = userClient.modifyUserById(id, status);
        return result;
    }


    @RequestMapping(value = ServiceUrls.User.USERS_BY_ID_AND_LANGUAGE, method = RequestMethod.GET)
    @ApiOperation(value = "根据ID获取用户", notes = "根据ID获取用户")
    public Result<MUser> getUser(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) throws Exception {
        Result<MUser> result = null;
        result = userClient.get(id);
        if(!RegexUtil.isNull(result.getObj())){
            findObj(result);
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.User.USER_MODIFY_PASSWORD_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    public Result<MUser> modifyPassword(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) throws Exception {
        Result<MUser> result = null;
        result = userClient.modifyPassword(id, password);
        return result;
    }

    @RequestMapping(value = ServiceUrls.User.USER_FIND_BY_ACCOUNT_AND_MERCHANT_LEVEL_AND_PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据账号，用户等级，父ID查找详情", notes = "根据账号，用户等级，父ID查找详情")
    public Result<MUser> findByAccountAndParentId(
            @ApiParam(name = "account", value = "account", defaultValue = "")
            @RequestParam(value = "account", required = false) String account,
            @ApiParam(name = "parentId", value = "ParentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId) throws Exception {
        Result<MUser> result = null;
        result = userClient.findByAccountAndParentId(account, parentId);
        if (!RegexUtil.isNull(result.getObj())) {
            if (!RegexUtil.isNull(result.getObj().getStatus())) {
                result.getObj().setStatusName(CommonUtils.findByStatusName(result.getObj().getStatus()));
            }
            Result<MQUser> mqUser = null;
            mqUser = qUserClient.findByUserId(result.getObj().getId());
            if (!RegexUtil.isNull(mqUser.getObj().getBalance())) {
                result.getObj().setRemainQ(mqUser.getObj().getBalance());
            } else {
                result.getObj().setRemainQ(0D);
            }

        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.User.USER_WAIT_USER_GROUP, method = RequestMethod.GET)
    @ApiOperation(value = "群组用，查找待添加的用户", notes = "群组用，查找待添加的用户")
    public Result<MGroupUser> findWaitUserGroup(
            @ApiParam(name = "parentId", value = "parentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "groupId", value = "groupId", defaultValue = "")
            @RequestParam(value = "groupId", required = false) String groupId) throws Exception {
        Result<MGroupUser> result = null;
        try {
            result = userClient.findWaitUserGroup(parentId, groupId);
        } catch (Exception e) {
            logger.error("查找用户失败");
            logger.error(e.getMessage(), e);
            return error("查找用户失败");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.User.USER_FIND_ALREADY_USER_GROUP, method = RequestMethod.GET)
    @ApiOperation(value = "查找已添加 的用户列表(群组用)", notes = "查找已添加 的用户列表(群组用)")
    public Result<MGroupUser> findAlreadyUserGroup(
            @ApiParam(name = "groupId", value = "groupId", defaultValue = "")
            @RequestParam(value = "groupId", required = false) Long groupId) throws Exception {
        Result<MGroupUser> result = null;
        try {
            result = userClient.findAlreadyUserGroup(groupId);
        } catch (Exception e) {
            logger.error("查找用户失败");
            logger.error(e.getMessage(), e);
            return error("查找用户失败");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.User.USER_FIND_CAN_ADD_FRIEND, method = RequestMethod.GET)
    @ApiOperation(value = "查找可以添加的好友 屏蔽自己", notes = "查找可以添加的好友 屏蔽自己")
    public Result<MUser> findCanAddFriend(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "parentId", value = "parentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId) throws Exception {
        Result<MUser> result = null;
        try {
            result = userClient.findCanAddFriend(id, parentId);
        } catch (Exception e) {
            logger.error("查找用户失败");
            logger.error(e.getMessage(), e);
            return error("查找用户失败");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.User.USER_ALREADY_ADD_FRIEND, method = RequestMethod.GET)
    @ApiOperation(value = "好友列表--查找已经添加的好友列表", notes = "好友列表--查找已经添加的好友列表")
    public Result<MUser> findAlreadyAddFriend(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id) throws Exception {
        Result<MUser> result = null;
        try {
            result = userClient.findAlreadyAddFriend(id);
        } catch (Exception e) {
            logger.error("查找用户失败");
            logger.error(e.getMessage(), e);
            return error("查找用户失败");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.User.USER_FIND_BY_ACCOUNT, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除账号", notes = "根据id删除账号")
    public Result<MUser> delById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id) throws Exception {
        Result<MUser> result = null;
        try {
            result = userClient.delById(id);
        } catch (Exception e) {
            logger.error("删除账号失败");
            logger.error(e.getMessage(), e);
            return error("删除账号失败");
        }
        return result;
    }

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
            @ApiParam(name = "language", value = "语言", defaultValue = "")
            @RequestParam(value = "language", required = false) String language,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) throws Exception {
        Result<MUser> result = null;
        try {
            result = userClient.searchUserAndGroup(filters, currGroupList, "-createDate", size, page);
            if (result.getDetailModelList().size() > 0) {
                findDetail(result);
            }
        } catch (Exception e) {
            logger.error("获取列表异常！");
            logger.error(e.getMessage(), e);
            return error("获取列表异常！");
        }
        return result;
    }



    @RequestMapping(value = ServiceUrls.User.user_search_user_isOnline, method = RequestMethod.GET)
    @ApiOperation(value = "查找看用户是否在线", notes = "查找好友列表，有含群组ID+查找看用户是否在线")
    public Result<MUser> searchUserIsOnline(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "currGroupList", value = "currGroupList", defaultValue = "")
            @RequestParam(value = "currGroupList", required = false) Long currGroupList,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "language", value = "语言", defaultValue = "")
            @RequestParam(value = "language", required = false) String language,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page,
            @ApiParam(name = "isOnline", value = "是否在线 1否 0是", defaultValue = "0")
            @RequestParam(value = "isOnline", required = false) int isOnline
            ) throws Exception {
        Result<MUser> result = null;
        try {
            result = userClient.searchUserIsOnline(currGroupList,filters);
            if (result.getDetailModelList().size() > 0) {
                findIsOnline(result, language,isOnline==0?true:false);
                //进行分页整理
                List<MUser> MUserBeanPage = new ArrayList<MUser>();
                int currIdx = (page > 1 ? (page -1) * size : 0);
                int countSize=result.getDetailModelList().size();
                for (int i = 0; i < size && i <countSize - currIdx; i++) {
                    MUser temp =   result.getDetailModelList().get(currIdx + i);
                    MUserBeanPage.add(temp);
                }
                result.setDetailModelList(MUserBeanPage);
                result.setTotalCount(countSize);
                result.setTotalPage(countSize/size);//总页数
                result.setPageSize(size);//分页大大小
                result.setCurrPage(page);//当前页
            }




        } catch (Exception e) {
            logger.error("获取列表异常！");
            logger.error(e.getMessage(), e);
            return error("获取列表异常！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.User.USER_SEARCH_AND_GROUP_DATE, method = RequestMethod.GET)
    @ApiOperation(value = "查找好友列表，有含群组ID,有日期(Q币分配用)", notes = "查找好友列表，有含群组ID,有日期(Q币分配用)")
    public Result<MUser> searchUserAndGroupAndDate(
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
            @ApiParam(name = "language", value = "语言", defaultValue = "")
            @RequestParam(value = "language", required = false) String language,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) throws Exception {
        Result<MUser> result = null;
        try {
            result = userClient.searchUserAndGroupAndDate(filters, currGroupList, GT_createDate, LT_createDate, "-createDate", size, page);

            findDetail(result);
        } catch (Exception e) {
            logger.error("获取列表异常！");
            logger.error(e.getMessage(), e);
            return error("获取列表异常！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.User.USER_BY_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据ID获取用户", notes = "根据ID获取用户")
    public Result<MUser> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id) throws Exception {
        return userClient.get(id);
    }

//    @RequestMapping(value = ServiceUrls.User.USER_ONLINE_STATUS, method = RequestMethod.GET)
//    @ApiOperation(value = "查询在线状态", notes = "查询在线状态")
//    public Result<Boolean> queryUserOnlineStatus(
//            @ApiParam(name = "userId", value = "用户id", defaultValue = "")
//            @RequestParam(value = "userId", required = false) Long userId) {
//        Result<Boolean> result = null;
//        result = userClient.queryUserOnlineStatus(userId);
//        return result;
//    }

    private Result<MUser> findDetail(Result<MUser> mUser) {
        mUser.getDetailModelList().stream().forEach(a->{
            Result<MQUser> mqUser = null;
            //可能会出现空值问题
            mqUser = qUserClient.findByUserId(a.getId());
            if (mqUser.getObj() !=null){
                a.setModifyDate(mqUser.getObj().getModifyDate()!=null ? mqUser.getObj().getModifyDate() :null);
                a.setRemainQ(mqUser.getObj().getBalance());
            }
            if ("zh".equals(getLanguage())) {
                a.setIsOnLine(CommonUtils.isOnLine(userClient.queryUserOnlineStatus(a.getId())));
                a.setStatusName(CommonUtils.findByStatusName(a.getStatus()));
                if (!RegexUtil.isNull(a.getFuncs())) {
                    String[] strArray = a.getFuncs().split(",");
                    String strFuns = "";
                    for (int x = 0; x < strArray.length; x++) {
                        if (x + 1 == strArray.length) {
                            strFuns += CommonUtils.findByUserFuns(strArray[x]);
                        } else {
                            strFuns += CommonUtils.findByUserFuns(strArray[x]) + ",";
                        }
                        if (RegexUtil.isNull(strArray[x])) {
                            continue;
                        }
                    }
                    a.setFuncsName(strFuns);
                }
            } else if ("en".equals(getLanguage())) {
                a.setIsOnLine(EnglishCommonUtils.isOnLine(userClient.queryUserOnlineStatus(a.getId())));
                a.setStatusName(EnglishCommonUtils.findByStatusName(a.getStatus()));
                if (!RegexUtil.isNull(a.getFuncs())) {
                    String strFuns = "";
                    String[] strArray = a.getFuncs().split(",");
                    for (int x = 0; x < strArray.length; x++) {
                        if (x + 1 == strArray.length) {
                            strFuns += EnglishCommonUtils.findByUserFuns(strArray[x]);
                        } else {
                            strFuns += EnglishCommonUtils.findByUserFuns(strArray[x]) + ",";
                        }
                        if (RegexUtil.isNull(strArray[x])) {
                            continue;
                        }
                    }
                    a.setFuncsName(strFuns);
                }
            }
        });

        return mUser;
    }
    private Result<MUser> findObj(Result<MUser> mUserResult) {
        if ("zh".equals(getLanguage())) {
            //中文
            mUserResult.getObj().setStatusName(CommonUtils.findByStatusName(mUserResult.getObj().getStatus()));
        } else if ("en".equals(getLanguage())) {
            mUserResult.getObj().setStatusName(EnglishCommonUtils.findByStatusName(mUserResult.getObj().getStatus()));
        }
        return mUserResult;
    }

    /**
     * 遍历是否在线
     * @param mUser
     * @param language
     * @return
     */
    private Result<MUser> findIsOnline(Result<MUser> mUser, String language,boolean flag) {
        String strFuns = "";
        List<MUser>  tempList=new ArrayList<MUser>();
        for (MUser temp : mUser.getDetailModelList()) {
            Result<MQUser> mqUser = null;
            mqUser = qUserClient.findByUserId(temp.getId());
            temp.setModifyDate(mqUser.getObj().getModifyDate());
            temp.setRemainQ(mqUser.getObj().getBalance());
           boolean isOnline=userClient.queryUserOnlineStatus(temp.getId());
            //查看是否在线 如果跟要查询的不相等则跳出循环
            if (isOnline!=flag){
                continue;//忽略本次
            }
            temp.setIsOnLine(CommonUtils.isOnLine(isOnline));


            if ("zh".equals(language)) {
                temp.setStatusName(CommonUtils.findByStatusName(temp.getStatus()));
                if (!RegexUtil.isNull(temp.getFuncs())) {
                    String[] strArray = temp.getFuncs().split(",");
                    for (int x = 0; x < strArray.length; x++) {
                        if (RegexUtil.isNull(strArray[x])) {
                            continue;
                        }
                        if (x + 1 == strArray.length) {
                            strFuns += CommonUtils.findByUserFuns(strArray[x]);
                        } else {
                            strFuns += CommonUtils.findByUserFuns(strArray[x]) + ",";
                        }

                    }
                }
            } else if ("en".equals(language)) {
                temp.setStatusName(EnglishCommonUtils.findByStatusName(temp.getStatus()));
                if (!RegexUtil.isNull(temp.getFuncs())) {
                    String[] strArray = temp.getFuncs().split(",");
                    for (int x = 0; x < strArray.length; x++) {
                        if (RegexUtil.isNull(strArray[x])) {
                            continue;
                        }
                        if (x + 1 == strArray.length) {
                            strFuns += EnglishCommonUtils.findByUserFuns(strArray[x]);
                        } else {
                            strFuns += EnglishCommonUtils.findByUserFuns(strArray[x]) + ",";
                        }

                    }
                }
            }
            temp.setFuncsName(strFuns);
            strFuns = "";
            tempList.add(temp);
        }
        return mUser.setDetailModelList(tempList);
    }

    @PostMapping(value = ServiceUrls.User.ICCIDUSERS_BATCH_SAVE)
    @ApiOperation(value = "iccID用户 批量创建 or 编辑" ,notes = "如果一条信息失败会触发事务回滚")
    public  Result<MUser> iccIdUserBatchSave(@ApiParam(name = "json_model", value = "", defaultValue = "")
                                            @RequestBody  MUser users) {
        try{


        int offset = users.getOffset() ;//获取用户的偏移量
        String iccId = users.getIccId();
        String serialNumber = iccId.substring(0,iccId.length()-7);
        int index = Integer.parseInt(iccId.substring(iccId.length()-7,iccId.length()-1));//起始索引值
        String  endIndex =iccId.substring(iccId.length()-1,iccId.length());
        List<MUser> userList = new ArrayList<MUser>();
        for (int i=0;i<=offset;i++){
            MUser tempUser = new MUser();
            BeanUtils.copyProperties(users,tempUser);
            tempUser.setAccount(serialNumber+index+endIndex);
            tempUser.setName(users.getName()+(i==0?"":i));
            tempUser.setIccId(serialNumber+index+endIndex);
            userList.add(tempUser);
            index++;
        }
        MUser[] bean = new MUser[userList.size()];
      userClient.iccIdUserBatchSave(userList.toArray(bean));
        }catch (Exception e){
            return  error(e.getMessage());
        }
        return success(null);
    }




}
