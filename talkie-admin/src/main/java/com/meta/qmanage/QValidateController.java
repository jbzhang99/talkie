package com.meta.qmanage;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.feignclient.qmanage.QValidateClient;
import com.meta.model.qmanage.MQValidate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:lhq
 * @date:2017/12/21 9:42
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "q_validate", description = "Q币验证接口")
public class QValidateController extends BaseControllerUtil {
    //日志
    protected static Logger logger = LoggerFactory.getLogger(QValidateController.class);
    @Autowired
    private QValidateClient qValidateClient;

    @RequestMapping(value = ServiceUrls.QValidate.Q_VALIDATE, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取密码", notes = "根据id获取密码")
    public Result<MQValidate> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id) {
        Result<MQValidate> result = null;
        try {
            result=qValidateClient.get(id);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return  result;
    }


    @RequestMapping(value = ServiceUrls.QValidate.Q_VALIDATES, method = RequestMethod.POST)
    @ApiOperation(value = "根据修改密码", notes = "根据id修改密码")
    public  Result<MQValidate>  modifyPasswordById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) {
        Result<MQValidate> result = null;
        try {
            result=qValidateClient.modifyPasswordById(id, password);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return  result;
    }

}
