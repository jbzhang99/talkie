package com.meta.qmanage;

import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.feignclient.qmanage.QAccountantPasswordClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * Created by y747718944 on 2018/2/26
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "q_accountant_password", description = "分配给代理Q币的密码接口")
public class QAccountantPasswordController {

    private Logger logger= LoggerFactory.getLogger(QAccountantPasswordController.class);
    @Autowired
    private QAccountantPasswordClient qAccountantPasswordClient;

    @RequestMapping(value = ServiceUrls.QAccountandPassword.Q_ACCOUNTANT_PASSWORD, method = RequestMethod.GET)
    @ApiOperation(value = "查询支付是否正确", notes = "验证密码是否正确")
    public Result get(
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(name = "password", value = "password" ,required=false) String password ) {
        return  qAccountantPasswordClient.get(password);
    }

    @GetMapping(ServiceUrls.QAccountandPassword.Q_ACCOUNTANT_PASSWORD_ISNULL)
    @ApiOperation(value = "验证是否存在用户密码")
    public Result passWordIsNull(){
        return  qAccountantPasswordClient.passWordIsNull();
    }


    @PostMapping( ServiceUrls.QAccountandPassword.Q_ACCOUNTANT_PASSWORD)
    @ApiOperation(value ="新增or更新支付密码",notes = "根据用户user_id创建密码")
    public Result save(
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(name = "password", value = "password",required=false)
                    String password ){
        System.err.println(password);
        return  qAccountantPasswordClient.save(password);
    }

}
