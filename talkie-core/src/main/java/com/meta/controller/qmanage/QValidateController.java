package com.meta.controller.qmanage;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.model.qmanage.QValidate;
import com.meta.regex.RegexUtil;
import com.meta.service.qmanage.QValidateService;
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
 * @date:2017/12/21 9:12
 */
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "q_validate", description = "Q币验证接口")
public class QValidateController extends BaseControllerUtil {

    @Autowired
    private QValidateService qValidateService;

    //日志
    protected static Logger logger = LoggerFactory.getLogger(QValidateController.class);

    @RequestMapping(value = ServiceUrls.QValidate.Q_VALIDATE, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取密码", notes = "根据id获取密码")
    public Result<QValidate> get(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id) {
        QValidate qValidate = qValidateService.findByUserId(id);
        if (RegexUtil.isNull(qValidate)) {
            return error("查无此账号");
        }
        return success(qValidate);
    }

    @RequestMapping(value = ServiceUrls.QValidate.Q_VALIDATES, method = RequestMethod.POST)
    @ApiOperation(value = "根据id修改密码", notes = "根据id修改密码")
    public Result modifyPasswordById(
            @ApiParam(name = "id", value = "id", defaultValue = "")
            @RequestParam(value = "id", required = false) Long id,
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(value = "password", required = false) String password) {
        boolean flag = qValidateService.modifyPasswordById(id, password);
        if (!flag) {
            return error("修改失败");
        } else {
            return success(null);
        }
    }


}
