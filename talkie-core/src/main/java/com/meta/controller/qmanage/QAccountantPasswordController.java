package com.meta.controller.qmanage;

import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.model.qmanage.QAccountantPassword;
import com.meta.model.qmanage.QValidate;
import com.meta.model.user.User;
import com.meta.regex.RegexUtil;
import com.meta.service.qmanage.QAccountantPasswordService;
import com.meta.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PUT;
import javax.xml.ws.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * create by lhq
 * create date on  18-2-26上午9:43
 *
 * @version 1.0
 **/
@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@Api(value = "q_accountant_password", description = "分配给代理Q币的密码接口")
public class QAccountantPasswordController extends BaseControllerUtil {

    @Autowired
    private QAccountantPasswordService qAccountantPasswordService;
    @Autowired
    private UserService userService;


    @RequestMapping(value = ServiceUrls.QAccountandPassword.Q_ACCOUNTANT_PASSWORD, method = RequestMethod.GET)
    @ApiOperation(value = "查询支付是否正确", notes = "验证密码是否正确")
    public Result get(
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(name = "password", value = "password") String password) {
        try {
            Long userId = getUserId();
            QAccountantPassword qAccountantPassword = qAccountantPasswordService.findByUserIdAndPassword(userId);
            if (RegexUtil.isNull(qAccountantPassword)) {
                return error("请先创建密码");
            } else if (RegexUtil.isNull(qAccountantPassword.getPassword())) {
                return error("请先创建密码");
            } else if (password.equals(qAccountantPassword.getPassword())) {
                return success(null);
            }
            return error("分配密码错误！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return error("系统错误！");
    }

    @GetMapping(ServiceUrls.QAccountandPassword.Q_ACCOUNTANT_PASSWORD_ISNULL)
    @ApiOperation(value = "验证是否存在用户密码")
    public Result passWordIsNull() {
        try {
            Long userId = getUserId();
            User user = userService.findOne(userId);
            if (user == null) return error("不存在该用户");
            QAccountantPassword bean = qAccountantPasswordService.findOneByFilter("EQ_user.id=" + user.getId());
            if (bean != null && bean.getPassword() != null) return success(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return error("还未创建密码");
    }


    @PostMapping(ServiceUrls.QAccountandPassword.Q_ACCOUNTANT_PASSWORD)
    @ApiOperation(value = "新增or更新支付密码", notes = "根据用户user_id创建密码")
    public Result save(
            @ApiParam(name = "password", value = "password", defaultValue = "")
            @RequestParam(name = "password", value = "password") String password) {
        try {
            Long userId = getUserId();
            User user = userService.findOne(userId);
            if (user == null) return error("不存在该用户");
            QAccountantPassword bean = qAccountantPasswordService.findOneByFilter("EQ_user.id=" + user.getId());
            //判断是否存在对应的用户不存在就新建
            bean = bean != null ? bean : new QAccountantPassword(user);
            bean.setPassword(password);
            qAccountantPasswordService.save(bean);
            return success(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return error("操作失败");
    }


}
