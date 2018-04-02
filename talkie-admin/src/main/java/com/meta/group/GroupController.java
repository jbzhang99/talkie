package com.meta.group;

import com.alibaba.fastjson.JSONObject;
import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.commonUtil.CommonUtils;
import com.meta.commonUtil.EnglishCommonUtils;
import com.meta.feignclient.group.Groupclient;
import com.meta.model.group.MGroup;
import com.meta.model.merchant.MMerchant;
import com.meta.model.qmanage.MQMerchant;
import com.meta.model.user.MAssUserGroup;
import com.meta.model.user.MUser;
import com.meta.regex.RegexUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by lhq on 2017/10/20.
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "group", description = "组接口")
public class GroupController extends BaseControllerUtil {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
    @Autowired
    private Groupclient groupclient;


    @RequestMapping(value = ServiceUrls.Group.GROUPS, method = RequestMethod.GET)
    @ApiOperation(value = "获取组表", notes = "根据查询条件获组在前端表格展示")
    public Result<MGroup> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MGroup> result = null;
        try {
            result = groupclient.search(filters, "-createDate", size, page);
            if (result.getDetailModelList().size() > 0) {
                findDetail(result, getLanguage());
            }
        } catch (Exception e) {
            logger.error("获取群组列表异常！");
            logger.error(e.getMessage(), e);
            return error("获取群组列表异常！");
        }
        return result;
    }

    private Result<MGroup> findDetail(Result<MGroup> mGroupResult, String language) {
        for (MGroup temp : mGroupResult.getDetailModelList()) {
            if ("zh".equals(language)) {
                //中文
                temp.setStatusName(CommonUtils.findByStatusName(temp.getStatus()));
                if (!RegexUtil.isNull(temp.getStatus())) {
                    temp.setStatusName(CommonUtils.findByStatusName(temp.getStatus().toString()));
                }
                if (!RegexUtil.isNull(temp.getType())) {
                    temp.setTypeName(CommonUtils.findByGroupType(temp.getType().toString()));
                }


            } else if ("en".equals(language)) {
                if (!RegexUtil.isNull(temp.getStatus())) {
                    temp.setStatusName(EnglishCommonUtils.findByStatusName(temp.getStatus().toString()));
                }
                if (!RegexUtil.isNull(temp.getType())) {
                    temp.setTypeName(EnglishCommonUtils.findByGroupType(temp.getType().toString()));
                }
            }
        }
        return mGroupResult;
    }


    @RequestMapping(value = ServiceUrls.Group.GROUPS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改组信息", notes = "创建/修改组信息")
    public Result<MGroup> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MGroup mGroup) {
        Result<MGroup> result = null;
        try {
            result = groupclient.create(mGroup);
        } catch (Exception e) {
            logger.error("创建/修改群组列表异常！");
            logger.error(e.getMessage(), e);
            return error("创建/修改群组列表异常！");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.Group.GROUP_BY_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取组信息", notes = "根据id获取组信息")
    public Result<MGroup> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id) {
        Result<MGroup> result = null;
        try {
            result = groupclient.get(id);
        } catch (Exception e) {
            logger.error("获取群组列表异常！");
            logger.error(e.getMessage(), e);
            return error("获取群组列表异常！");
        }
        return result;
    }


    @RequestMapping(value = ServiceUrls.Group.GROUPS, method = RequestMethod.DELETE)
    @ApiOperation(value = "删除组信息", notes = "根据id删除组信息")
    public Result<MGroup> delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) {
        Result<MGroup> result = null;
        try {
            result = groupclient.delete(id, getUserId());
        } catch (Exception e) {
            logger.error("删除群组列表异常！");
            logger.error(e.getMessage(), e);
            return error("删除群组列表异常！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.Group.GROUP_MODIFY_BY_STATUS, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result<MGroup> modifyStatusById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status") String status) throws Exception {
        Result<MGroup> result = null;
        try {
            result = groupclient.modifyStatusById(id, status);
        } catch (Exception e) {
            logger.error("变更异常！");
            logger.error(e.getMessage(), e);
            return error("变更异常！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.Group.GROUP_FIND_WAIT_BY_USER, method = RequestMethod.GET)
    @ApiOperation(value = "查找未添加用户的组", notes = "查找未添加用户的组")
    public Result<MGroup> findWaitByUser(
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId") Long userId,
            @ApiParam(name = "currId", value = "currId", defaultValue = "")
            @RequestParam(value = "currId") Long currId) throws Exception {
        Result<MGroup> result = null;
        try {
            result = groupclient.findWaitByUser(userId, currId);
        } catch (Exception e) {
            logger.error("查找失败！");
            logger.error(e.getMessage(), e);
            return error("查找失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.Group.GROUP_FIND_ALREADY_USER, method = RequestMethod.GET)
    @ApiOperation(value = "查找已添加用户的组", notes = "查找已添加用户的组")
    public Result<MGroup> findAlreadyUser(
            @ApiParam(name = "userId", value = "userId", defaultValue = "")
            @RequestParam(value = "userId") Long userId) throws Exception {
        Result<MGroup> result = null;
        try {
            result = groupclient.findAlreadyUser(userId);
        } catch (Exception e) {
            logger.error("查找失败！");
            logger.error(e.getMessage(), e);
            return error("查找失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.Group.GROUP_ASSIGNMENT, method = RequestMethod.GET)
    @ApiOperation(value = "获取组表(群组功能用)", notes = "根据查询条件获组(群组功能用)在前端表格展示")
    public Result<MAssUserGroup> searchAssignment(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id) throws Exception {
        Result<MAssUserGroup> result = null;
        try {
            result = groupclient.searchAssignment(id);
        } catch (Exception e) {
            logger.error("查找失败！");
            logger.error(e.getMessage(), e);
            return error("查找失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.Group.GROUP_SEARCH_NOPAGE, method = RequestMethod.GET)
    @ApiOperation(value = "查找群组不分页", notes = "查找群组不分页")
    public Result<MGroup> searchNoPage(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters) throws Exception {
        Result<MGroup> result = null;
        try {
            result = groupclient.searchNoPage(filters);
        } catch (Exception e) {
            logger.error("查找失败！");
            logger.error(e.getMessage(), e);
            return error("查找失败！");
        }
        return result;
    }

}
