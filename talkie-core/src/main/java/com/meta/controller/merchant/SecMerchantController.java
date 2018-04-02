package com.meta.controller.merchant;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.commonUtil.CommonUtils;
import com.meta.commonUtil.EnglishCommonUtils;
import com.meta.datetime.DateTimeUtil;
import com.meta.error.errorMsgDict;
import com.meta.model.merchant.MerchantEvent;
import com.meta.model.qmanage.QUser;
import com.meta.model.qmanage.QUserEvent;
import com.meta.model.user.User;
import com.meta.model.user.UserEvent;
import com.meta.regex.RegexUtil;
import com.meta.remark.remarkDict;
import com.meta.service.merchant.MerchantEventService;
import com.meta.service.merchant.SecMerchantService;
import com.meta.service.qmanage.QUserEventService;
import com.meta.service.qmanage.QUserService;
import com.meta.service.user.UserFriendService;
import com.meta.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;

/**
 * Created by lhq on 2017/11/20.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "sec_merchant", description = "子代理信息接口")
public class SecMerchantController extends BaseControllerUtil {

    @Autowired
    private SecMerchantService secMerchantService;
    @Autowired
    private MerchantEventService merchantEventService;
    @Autowired
    private QUserService qUserService;
    @Autowired
    private UserFriendService userFriendService;
    @Autowired
    private QUserEventService qUserEventService;


    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取子代理列表", notes = "根据查询条件获子代理列表在前端表格展示")
    public Result<User> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<User> result = secMerchantService.search(filters, sorts, page, size);
        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }


    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改子代理信息", notes = "创建/修改子代理信息")
    public Result<User> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid User user) throws Exception {
        User userParent = secMerchantService.findOne(getUserId());


        if (RegexUtil.isNull(user.getId())) {
            User userTemp = secMerchantService.findByAccount(user.getAccount());
            if (!RegexUtil.isNull(userTemp)) {
                return error("该账号已存在，请重新填写！");
            }
            if (userParent.getMerchantLevel().equals("9")) {//二级
                user.setParentId(userParent.getParentId());//新增实际属于他的代理
            }

            user.setIsDel(1);
            /**
             * Q币余额
             */
            QUser qUser = new QUser();
            qUser.setBalance(0D);
            qUser.setAlreadyBalance(0D);
            qUser.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            qUser.setModifyDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            qUser.setUser(user);
            user.setqUser(qUser);

            /**
             * 操作记录
             */
            //代理操作子代理
            if (userParent.getMerchantLevel().equals("3") || userParent.getMerchantLevel().equals("2") || userParent.getMerchantLevel().equals("9")) {
                MerchantEvent merchantEvent = new MerchantEvent();
                merchantEvent.setByUserAccount(user.getAccount());
                merchantEvent.setType(3);
                merchantEvent.setCreateUser(userParent.getId());
                merchantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                merchantEvent.setIp(getIpAddr());
                if (user.getLanguage().equals("zh")) {
                    merchantEvent.setRemark(CommonUtils.findMerchantLevel(userParent.getMerchantLevel()) + remarkDict.ADD + remarkDict.USER + user.getName());
                } else if (user.getLanguage().equals("en")) {
                    merchantEvent.setRemark(EnglishCommonUtils.findMerchantLevel(userParent.getMerchantLevel()) + remarkDict.ADD + remarkDict.USER + user.getName());
                }
                merchantEvent.setUser(userParent);
                merchantEventService.save(merchantEvent);
            }

            return success(secMerchantService.save(user));
        } else {
            /**
             * 操作记录
             */
            if (userParent.getMerchantLevel().equals("3") || userParent.getMerchantLevel().equals("2") || userParent.getMerchantLevel().equals("9")) {
                MerchantEvent merchantEvent = new MerchantEvent();
                merchantEvent.setByUserAccount(user.getAccount());
                merchantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                merchantEvent.setType(5);
                merchantEvent.setCreateUser(userParent.getId());
                if (user.getLanguage().equals("zh")) {
                    merchantEvent.setRemark(CommonUtils.findMerchantLevel(userParent.getMerchantLevel()) + remarkDict.ADD + remarkDict.USER + user.getName());
                } else if (user.getLanguage().equals("en")) {
                    merchantEvent.setRemark(EnglishCommonUtils.findMerchantLevel(userParent.getMerchantLevel()) + remarkDict.ADD + remarkDict.USER + user.getName());
                }
                merchantEvent.setIp(getIpAddr());
                merchantEvent.setUser(userParent);
                merchantEventService.save(merchantEvent);
            }
            return success(secMerchantService.save(user));
        }
    }

    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANTS, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户")
    public Result deleteById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "merchantLevel")
            @RequestParam(value = "merchantLevel") String merchantLevel,
            @ApiParam(name = "currentUserId", value = "currentUserId", defaultValue = "")
            @RequestParam(value = "currentUserId") Long currentUserId,
            @ApiParam(name = "account", value = "account", defaultValue = "")
            @RequestParam(value = "account") String account,
            @ApiParam(name = "name", value = "name", defaultValue = "")
            @RequestParam(value = "name") String name
    ) throws Exception {

        if (secMerchantService.countCompayByParentId(id, 1) > 0) {//如果代理下面还有用户就不让删除
            return error(errorMsgDict.DEL_SEC_MERCHANTS_EXIST_COMPANY);
        }
        User user = secMerchantService.findOne(id); //查询 上传的用户信息
        User userParent = secMerchantService.findOne(currentUserId); //查询 上传信息的操作者信息

        Double moneyTotal = 0D;
        //回收QB到父级
        List<QUser> qUserList = qUserService.search("EQ_user.id=" + id);
        moneyTotal += qUserList.get(0).getBalance();
        QUser qUser = qUserService.queryUserByUserID(user.getParentId());//查询父级的QB信息
        if (qUser != null) {
            qUser.setBalance(qUser.getBalance() + moneyTotal);//回收QB
            qUser.setAlreadyBalance(qUser.getAlreadyBalance() - moneyTotal);//减少已分配数量
            qUserService.save(qUser);
        }

        //Q币回收记录
        QUserEvent qUserEvent = new QUserEvent();
        qUserEvent.setValue(qUserList.get(0).getBalance());
        qUserEvent.setType(1);
        qUserEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
        qUserEvent.setRemark(remarkDict.RECOVER + qUserList.get(0).getBalance() + remarkDict.MONEY);
        qUserEvent.setUser(userParent);
        qUserEventService.save(qUserEvent);
        /**
         * 代理操作记录
         */
        MerchantEvent merchantEvent = new MerchantEvent();
        merchantEvent.setType(4);
        merchantEvent.setByUserAccount(user.getAccount());
        merchantEvent.setIp(getIpAddr());
        merchantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
        if (user.getLanguage().equals("zh")) {
            merchantEvent.setRemark(remarkDict.DEL + remarkDict.USER + user.getName());
        } else if (user.getLanguage().equals("en")) {
            merchantEvent.setRemark(remarkDict.DEL_ENGLISH + "  " + remarkDict.USER_ENGLISH + "  " + user.getName());
        }
        merchantEvent.setUser(userParent);
        merchantEventService.save(merchantEvent);
        secMerchantService.delete(id);
        return success(null);


    }


    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANT_BY_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据ID获取代理", notes = "根据ID获取代理")
    public Result<User> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id) throws Exception {
        User user = secMerchantService.findOne(id);
        return success(user);
    }

    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANT, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID修改密码", notes = "根据ID修改密码")
    public Result modifyPasswordById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) throws Exception {
        secMerchantService.modifyPasswordById(id, password);
        return success("");
    }

    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANT_FIND_BY_ACCOUNT_AND__PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据账号，用户等级，父ID查找详情", notes = "根据账号，用户等级，父ID查找详情")
    public Result<User> findByAccountAndParentId(
            @ApiParam(name = "account", value = "account", defaultValue = "")
            @RequestParam(value = "account", required = false) String account,
            @ApiParam(name = "parentId", value = "ParentId", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId) throws Exception {
        User user = secMerchantService.findByAccountAndParentId(account, parentId);
        return success(user);
    }

    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANT_MODIFY_STATUS_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status) throws Exception {
        secMerchantService.modifyStatusById(id, status);
        return success("");
    }

    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANT_COUNT_COMPANY_BY_PARENT_ID, method = RequestMethod.GET)
    @ApiOperation(value = "统计子代理的下属企业数量", notes = "统计子代理的下属企业数量")
    public Integer countCompayByParentId(
            @ApiParam(name = "parentId", value = "父ID", defaultValue = "")
            @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(name = "isDel", value = "是否删除", defaultValue = "")
            @RequestParam(value = "isDel", required = false) Integer isDel) {
        Integer intTemp = secMerchantService.countCompayByParentId(parentId, isDel);
        return intTemp;
    }


    @RequestMapping(value = ServiceUrls.SecMerchant.SEC_MERCHANT, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除代理", notes = "根据ID删除代理")
    public Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "merchantLevel", value = "merchantLevel", defaultValue = "")
            @RequestParam(value = "merchantLevel", required = false) String merchantLevel,
            @ApiParam(name = "currentUserId", value = "currentUserId", defaultValue = "")
            @RequestParam(value = "currentUserId", required = false) Long currentUserId) throws Exception {
        UserEvent userEvent = new UserEvent();
        Double moneyTotal = 0D;
        User user = secMerchantService.findOne(id); //查询 上传的用户信息
        User userParent = secMerchantService.findOne(currentUserId); //查询 上传信息的操作者信息


        //查询(企业)
        List<User> userListMerchant = secMerchantService.findByParentId(id);
        if (userListMerchant.size() > 0) {
            for (User temp : userListMerchant) {
                //查出企业的Q币
                List<QUser> qUserList = qUserService.search("EQ_user.id=" + temp.getId());
                moneyTotal += qUserList.get(0).getBalance();
                //查企业下面的用户
                List<User> userListUserE = secMerchantService.findByParentId(temp.getId());
                if (userListUserE.size() > 0) {
                    for (User aa : userListUserE) {
                        List<QUser> qUserList3 = qUserService.search("EQ_user.id=" + aa.getId());
                        moneyTotal += qUserList3.get(0).getBalance();
                        //操作记录
                        MerchantEvent merchantEvent = new MerchantEvent();
                        merchantEvent.setByUserAccount(aa.getAccount());
                        merchantEvent.setType(4);
                        merchantEvent.setIp(getIpAddr());
                        merchantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                        merchantEvent.setRemark(remarkDict.DEL + remarkDict.USER + aa.getName());
                        merchantEvent.setUser(userParent);
                        merchantEventService.save(merchantEvent);
                        //删除用户
                        userFriendService.deleteByUserId(aa.getId());
                        userFriendService.deleteByFriendId(aa.getId());
                        secMerchantService.delete(aa.getId());
                    }
                }
                //
                //删除企业
                //操作记录
                MerchantEvent merchantEvent = new MerchantEvent();
                merchantEvent.setType(4);
                merchantEvent.setByUserAccount(temp.getAccount());
                merchantEvent.setIp(getIpAddr());
                merchantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
                merchantEvent.setRemark(remarkDict.DEL + remarkDict.USER + temp.getName());
                merchantEvent.setUser(userParent);
                merchantEventService.save(merchantEvent);
                secMerchantService.delete(temp.getId());


            }
        }
        //  加钱啦
        List<QUser> qUserList = qUserService.search("EQ_user.id=" + user.getParentId());
        qUserList.get(0).setBalance(qUserList.get(0).getBalance() + moneyTotal);
        qUserService.save(qUserList.get(0));
        //删除代理
        //操作记录
        MerchantEvent merchantEvent = new MerchantEvent();
        merchantEvent.setType(4);
        merchantEvent.setByUserAccount(user.getAccount());
        merchantEvent.setIp(getIpAddr());
        merchantEvent.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
        merchantEvent.setRemark(remarkDict.DEL + remarkDict.USER + user.getName());
        merchantEvent.setUser(userParent);
        merchantEventService.save(merchantEvent);
        secMerchantService.delete(id);
        return success(null);

    }
}
