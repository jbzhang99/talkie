package com.meta.controller.map;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.model.map.BaiDuMap;
import com.meta.model.terminal.Terminal;
import com.meta.model.user.User;
import com.meta.regex.RegexUtil;
import com.meta.service.map.BaiDuMapService;
import com.meta.service.terminal.TerminalService;
import com.meta.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:lhq
 * @date:2017/12/11 9:29
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "baidu_map", description = "定位信息，基本百度地图接口")
public class BaiDuMapController extends BaseControllerUtil {

    @Autowired
    private BaiDuMapService baiDuMapService;
    @Autowired
    private TerminalService terminalService;
    @Autowired
    private UserService userService;


    @RequestMapping(value = ServiceUrls.BaiDuMap.BAIDU_MAP, method = RequestMethod.GET)
    @ApiOperation(value = "根据账号获取用户的轨迹", notes = "根据账号获取用户的轨迹")
    public Result<BaiDuMap> getById(
            @ApiParam(name = "files", value = "files", defaultValue = "")
            @RequestParam(value = "files") String files,
            @ApiParam(name = "startDate", value = "startDate", defaultValue = "")
            @RequestParam(value = "startDate", required = false) String startDate,
            @ApiParam(name = "endDate", value = "endDate", defaultValue = "")
            @RequestParam(value = "endDate", required = false) String endDate) {
        List<User> userList = userService.search(files);
        if (RegexUtil.isNull(userList.get(0))) {
            return error("无该账号，请重新搜索！");
        }
        Page<Terminal> terminalPage = terminalService.search("EQ_user.id=" + userList.get(0).getId(), "-createDate", 1, 1);

        if (RegexUtil.isNull(terminalPage.getContent().get(0).getBaiDuMapList().size() == 0)) {
            return error("暂无活动轨迹");
        } else if (RegexUtil.isNull(startDate) || RegexUtil.isNull(endDate)) {
            Page<BaiDuMap> baiDuMaps = baiDuMapService.search("EQ_terminal.id=" + terminalPage.getContent().get(0).getId(), "-createDate", 1, 1);
                List<BaiDuMap> list = new ArrayList<BaiDuMap>();
                list.add(baiDuMaps.getContent().get(0));
           return getResultList(list);
        } else{
            List<BaiDuMap> baiDuMaps = baiDuMapService.search("EQ_terminal.id=" + terminalPage.getContent().get(0).getId() + ";GT_createDate=" + startDate + ";LT_createDate=" + endDate);
            return getResultList(baiDuMaps);
        }
    }


}
