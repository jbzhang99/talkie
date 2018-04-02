package com.meta.sys.interceptor;

import com.meta.jwt.JwtUtil;
import com.meta.regex.RegexUtil;
import io.jsonwebtoken.Claims;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器 ybh
 * 验证token
 */

public class InterceptorHandler extends HandlerInterceptorAdapter {


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            String token = request.getHeader("Authorization");
            if(!RegexUtil.isNull(token)){
                Claims claims = JwtUtil.parseJWT(token);//验证
                return true ;
            }
        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(403);//拒绝访问 验证不通过
            return false ;
        }
        response.setStatus(403);//拒绝访问 验证不通过
        return false ;

    }
}
