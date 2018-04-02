package com.meta.controller.boardCast;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.json.JacksonUtil;
import com.meta.model.boardCast.GeneralAgentBoardCast;
import com.meta.model.user.User;
import com.meta.regex.RegexUtil;
import com.meta.service.boardCast.GeneralAgentBoardCastService;
import com.meta.service.user.UserService;
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
 * Created by  on 2017/12/14.
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "boardcast", description = "总代理操作特殊播报")
public class GeneralAgentBoardCastController extends BaseControllerUtil {

    private final static Logger logger = LoggerFactory.getLogger(GeneralAgentBoardCastController.class);

    @Autowired
    private GeneralAgentBoardCastService generalAgentBoardCastService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = ServiceUrls.GeneralAgentBoardCast.GENERAL_AGENT_BOARDCASTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取特殊播报列表", notes = "根据查询条件获播报列表在前端表格展示")
    public Result<GeneralAgentBoardCast> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Page<GeneralAgentBoardCast> result = generalAgentBoardCastService.search(filters, sorts, page, size);


        return getResultList(result.getContent(), result.getTotalElements(), page, size);
    }

    @RequestMapping(value = ServiceUrls.GeneralAgentBoardCast.GENERAL_AGENT_BOARDCAST_BY_ID, method = RequestMethod.GET)
    @ApiOperation(value = "根据id加载特殊播报", notes = "根据id加载特殊播报")
    public Result<GeneralAgentBoardCast> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @PathVariable(value = "id") Long id) throws Exception {
        GeneralAgentBoardCast generalAgentBoardCast = generalAgentBoardCastService.findOne(id);
        return success(generalAgentBoardCast);
    }

    @RequestMapping(value = ServiceUrls.GeneralAgentBoardCast.GENERAL_AGENT_BOARDCAST_MODIFY_STATUS_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result modifyStateById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status) throws Exception {
        if (status.equals("1")) {
            boolean flag = generalAgentBoardCastService.modifyState("2");
            if (!flag) {
                return error("更改失败");
            }
        }

        boolean flag = generalAgentBoardCastService.modifyStateById(id, status);
        if (flag) {
            return success("");
        } else {
            return error("修改失败");
        }
    }


    @RequestMapping(value = ServiceUrls.GeneralAgentBoardCast.GENERAL_AGENT_BOARDCASTS, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ID删除特殊播报", notes = "根据ID删除特殊播报")
    public Result deleteById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id) throws Exception {
        generalAgentBoardCastService.delete(id);
        return success(null);
    }

    @RequestMapping(value = ServiceUrls.GeneralAgentBoardCast.GENERAL_AGENT_BOARDCAST, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改特殊播报信息", notes = "创建/修改特殊播报信息")
    public Result<GeneralAgentBoardCast> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid GeneralAgentBoardCast generalAgentBoardCast) {
        User user = userService.findOne(generalAgentBoardCast.getProxyId());

        if (RegexUtil.isNull(user)) {
            return error("查无此账号！");
        }

        if (generalAgentBoardCast.getStatus().equals("1")) {
            boolean flag = generalAgentBoardCastService.modifyState("2");
            if (!flag) {
                return error("更改失败");
            }
            generalAgentBoardCast.setUser(user);
            GeneralAgentBoardCast boardCast = generalAgentBoardCastService.save(generalAgentBoardCast);
            boolean flag1 = generalAgentBoardCastService.modifyStateById(boardCast.getId(), "1");
            if (!flag1) {
                return error("更改失败");
            }
            return success(boardCast);
        } else {
            GeneralAgentBoardCast boardCast = generalAgentBoardCastService.save(generalAgentBoardCast);
            return success(boardCast);
        }


    }


}

