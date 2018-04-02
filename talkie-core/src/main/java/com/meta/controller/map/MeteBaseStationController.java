package com.meta.controller.map;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.model.map.BaseStation;
import com.meta.model.map.MetaBaseStation;
import com.meta.model.map.resultDemo;
import com.meta.model.terminal.Terminal;
import com.meta.regex.RegexUtil;
import com.meta.service.map.BaseStationService;
import com.meta.service.map.MetaBaseStationService;
import com.meta.service.terminal.TerminalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * create by lhq
 * create date on  18-1-30上午11:49
 *
 * @version 1.0
 **/
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "meta_base_station", description = "美拓定位信息")
public class MeteBaseStationController extends BaseControllerUtil {

    @Autowired
    private BaseStationService baseStationService;
    @Autowired
    private MetaBaseStationService metaBaseStationService;
    @Autowired
    private TerminalService terminalService;

    @RequestMapping(value = ServiceUrls.BaseStation.BASESTATIONS, method = RequestMethod.POST)
    @ApiOperation(value = "创建/修改基站定位", notes = "创建/修改基站定位")
    public Result create(
            @ApiParam(name = "json_model", value = "", defaultValue = "")
            @RequestBody @Valid MetaBaseStation metaBaseStation) throws Exception {

        Terminal terminal = terminalService.findOne(metaBaseStation.getTerminalId());
        if (RegexUtil.isNull(terminal)) {
            return error("无此终端!");
        }
        Result result = new Result();
        //基站
        if (metaBaseStation.getType().equals("1")) {
            BaseStation baseStation = baseStationService.findByCellId(metaBaseStation.getCellId());
            MetaBaseStation metaBaseStation1 = metaBaseStationService.findByCellIdAndTerminalId(metaBaseStation.getCellId(), metaBaseStation.getTerminalId());

            if (RegexUtil.isNull(baseStation) || RegexUtil.isNull(metaBaseStation1)) {
                resultDemo mbs = JuHeBaseStationUtil.getRequest1(metaBaseStation);
                if (mbs.getResultcode().equals("200") && mbs.getReason().equals("Successed!")) {
                    if (RegexUtil.isNull(baseStation)) {
                        BaseStation bs = new BaseStation();
                        bs.setAddress(mbs.getResult().getAddress());
                        bs.setCellId(mbs.getResult().getBid());
                        bs.setNid(mbs.getResult().getNid());
                        bs.setNid(mbs.getResult().getNid());
                        bs.setLatitude(Double.parseDouble(mbs.getResult().getLat()));
                        bs.setLongitude(Double.parseDouble(mbs.getResult().getLon()));
                        bs.setoLatitude(Double.parseDouble(mbs.getResult().getO_lat()));
                        bs.setoLongitude(Double.parseDouble(mbs.getResult().getO_lon()));
                        bs.setRaggio(mbs.getResult().getRaggio());
                        baseStationService.save(bs);
                        result.setSuccessFlg(true);
                    }

                    if (RegexUtil.isNull(metaBaseStation1)) {
                        metaBaseStation.setAddress(mbs.getResult().getAddress());
                        metaBaseStation.setLatitude(Double.parseDouble(mbs.getResult().getLat()));
                        metaBaseStation.setLongitude(Double.parseDouble(mbs.getResult().getLon()));
                        metaBaseStation.setoLatitude(Double.parseDouble(mbs.getResult().getO_lat()));
                        metaBaseStation.setoLongitude(Double.parseDouble(mbs.getResult().getO_lon()));
                        metaBaseStation.setRaggio(mbs.getResult().getRaggio());
                        metaBaseStation.setTerminal(terminal);
                        metaBaseStationService.save(metaBaseStation);
                        result.setSuccessFlg(true);
                    }
                } else {
                    result.setSuccessFlg(false);
                }
            }


        }
        return result;
    }

}
