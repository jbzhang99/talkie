package com.meta.sys;

import com.alibaba.fastjson.JSONObject;
import com.meta.BaseControllerUtil;
import com.meta.Result;
import com.meta.ServiceUrls;
import com.meta.VersionNumbers;
import com.meta.fileData.AudioController;
import com.meta.jwt.Constant;
import com.meta.jwt.JwtUtil;
import com.meta.model.user.MUser;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ybh 刷新token凭证
 */
@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "refreshToken",description = "刷新令牌")
public class RefreshTokenController extends BaseControllerUtil{

    //日志
    private final static Logger logger = LoggerFactory.getLogger(RefreshTokenController.class);

    @ApiOperation(value = "刷新令牌",notes = "刷新令牌")
    @RequestMapping(value = ServiceUrls.System.REFRESHTOKEN,method = RequestMethod.GET)
    public Result refreshToken(@ApiParam(value = "token",name = "token",defaultValue = "")
                                   @RequestParam(value = "token",required = false)String token){
        String refreshToken=null;

        try {
            Claims claims = JwtUtil.parseJWT(token);
            String json = claims.getSubject();//token内的信息
             refreshToken = JwtUtil.createJWT(Constant.JWT_ID, json, Constant.JWT_TTL);

        }catch (Exception e){
            logger.error("刷新令牌失败!");
            logger.error(e.getMessage(),e);
        }
        //返回新令牌
        return  success(refreshToken);

    }
}
