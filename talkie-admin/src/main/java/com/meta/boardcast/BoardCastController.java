package com.meta.boardcast;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.commonUtil.CommonUtils;
import com.meta.feignclient.boardCast.BoardCastClient;
import com.meta.feignclient.user.UserClient;
import com.meta.model.boardcast.MBoardCast;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "boardCast", description = "会员信息接口")
public class BoardCastController extends BaseControllerUtil {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(BoardCastController.class);
    @Autowired
    private BoardCastClient boardCastClient;
    @Autowired
    private UserClient userClient;


    @RequestMapping(value = ServiceUrls.GeneralAgentBoardCast.GENERAL_AGENT_BOARDCASTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取特殊播报列表", notes = "根据查询条件获播报列表在前端表格展示")
    public Result<MBoardCast> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page) {
        Result<MBoardCast> result = null;
        try {
            result = boardCastClient.search(filters, "-createDate", size, page);
            if (result.getDetailModelList().size() > 0) {
                result.getDetailModelList().forEach(temp->
                        {

                            temp.setStatusName(CommonUtils.findByBoardCastType(temp.getStatus()));
                        });
//                for (MBoardCast temp : result.getDetailModelList()) {
//                    temp.setStatusName(CommonUtils.findByBoardCastType(temp.getStatus()));
//                }

            }
        } catch (Exception e) {
            logger.error("获取特殊播报列表异常！");
            logger.error(e.getMessage(), e);
            return error("获取特殊播报列表异常！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.GeneralAgentBoardCast.GENERAL_AGENT_BOARDCASTS, method = RequestMethod.DELETE)
    @ApiOperation(value = "删除特殊播报信息", notes = "删除特殊播报信息")
    public Result<MBoardCast> delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id) throws Exception {
        Result<MBoardCast> result = boardCastClient.delete(id);
        return result;
    }

    @RequestMapping(value = ServiceUrls.GeneralAgentBoardCast.GENERAL_AGENT_BOARDCASTS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改特殊播报信息", notes = "创建/修改特殊播报信息")
    public Result<MBoardCast> create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MBoardCast mBoardCast) {
        Result<MBoardCast> result = null;
        try {
            result = boardCastClient.create(mBoardCast);
        } catch (Exception e) {
            logger.error("创建/修改特殊播报信息失败！");
            logger.error(e.getMessage(), e);
            return error("创建/修改特殊播报信息失败！");
        }
        return result;
    }

    @RequestMapping(value = ServiceUrls.GeneralAgentBoardCast.GENERAL_AGENT_BOARDCAST_MODIFY_STATUS_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    public Result<MBoardCast> modifyUserById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status) throws Exception {
        Result<MBoardCast> result = boardCastClient.modifyStateById(id, status);
        return result;
    }


}

