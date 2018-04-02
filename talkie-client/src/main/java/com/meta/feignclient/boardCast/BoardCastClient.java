package com.meta.feignclient.boardCast;

import com.meta.*;
import com.meta.feignfallback.boardcast.BoardCastFallBack;
import com.meta.model.boardcast.MBoardCast;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@FeignClient(name = ServiceNames.TALKIE_CORE, fallbackFactory = BoardCastFallBack.class)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface BoardCastClient {


    @Cacheable(value = RedisValue.FIND_SPECIAL_BOARD_CAST, key = "'search_special_boardcast_conditions_filters='+#p0+'_and_sorts='+#p1+'_and_size='+#p2+'_and_page='+#p3", unless = "!#result.successFlg")
    @RequestMapping(value = ServiceUrls.GeneralAgentBoardCast.GENERAL_AGENT_BOARDCASTS, method = RequestMethod.GET)
    @ApiOperation(value = "获取特殊播报记录", notes = "根据查询条件获特殊播报记录在前端表格展示")
    Result<MBoardCast> search(
            @ApiParam(name = "filters", value = "过滤器，为空检索所有条件", defaultValue = "")
            @RequestParam(value = "filters", required = false) String filters,
            @ApiParam(name = "sorts", value = "排序，规则参见说明文档", defaultValue = "")
            @RequestParam(value = "sorts", required = false) String sorts,
            @ApiParam(name = "size", value = "分页大小", defaultValue = "15")
            @RequestParam(value = "size", required = false) int size,
            @ApiParam(name = "page", value = "页码", defaultValue = "1")
            @RequestParam(value = "page", required = false) int page);

    @CacheEvict(value = RedisValue.FIND_SPECIAL_BOARD_CAST, allEntries = true)
    @RequestMapping(value = ServiceUrls.GeneralAgentBoardCast.GENERAL_AGENT_BOARDCAST_MODIFY_STATUS_BY_ID, method = RequestMethod.POST)
    @ApiOperation(value = "根据ID变更状态", notes = "根据ID变更状态")
    Result modifyStateById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "status", value = "status", defaultValue = "")
            @RequestParam(value = "status", required = false) String status);


    @CacheEvict(value = RedisValue.FIND_SPECIAL_BOARD_CAST, allEntries = true)
    @RequestMapping(value = ServiceUrls.GeneralAgentBoardCast.GENERAL_AGENT_BOARDCASTS, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除特殊播报", notes = "根据id删除特殊播报")
    Result delete(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id") Long id);

    @CacheEvict(value = RedisValue.FIND_SPECIAL_BOARD_CAST, allEntries = true)
    @RequestMapping(value = ServiceUrls.GeneralAgentBoardCast.GENERAL_AGENT_BOARDCAST, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改特殊播报", notes = "创建/修改特殊播报")
    Result create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MBoardCast mBoardCast
    );
}
