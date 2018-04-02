package com.meta.model;

import com.alibaba.fastjson.JSONObject;
import com.meta.StringUtil;
import com.meta.datetime.DateTimeUtil;
import com.meta.jwt.JwtUtil;
import com.meta.regex.RegexUtil;
import com.meta.request.RequestUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;


/**Created by lhq on 2017/11/10.
 * Entity保存时自动修改创建时间跟修改时间
 */
public class EntityListener {
    @Autowired
    HttpServletRequest request;
    //日志
    protected static Logger logger = LoggerFactory.getLogger(EntityListener.class);

    /**
     * 保存前处理
     *
     * @param entity 基类
     */
    @PrePersist
    public void prePersist(BaseEntity entity) throws Exception {
        String token = RequestUtil.getRequest().getHeader("token");
        if(!RegexUtil.isNull(token)){
            entity.setCreateDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            entity.setModifyDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            Claims claims = JwtUtil.parseJWT(token);//验证
            String json = claims.getSubject();
            entity.setCreateUser(   Long.parseLong(JSONObject.parseObject(json, Map.class).get("userId").toString()));
        }
    }

    /**
     * 更新前处理
     *
     * @param entity 基类
     */
    @PreUpdate
    public void preUpdate(BaseEntity entity) throws Exception {
        String token = RequestUtil.getRequest().getHeader("token");
        if(!RegexUtil.isNull(token)){
            Claims claims = JwtUtil.parseJWT(token);//验证
            String json = claims.getSubject();
            entity.setModifyDate(DateTimeUtil.simpleDateTimeFormat(new Date()));
            entity.setModifyUser(   Long.parseLong(JSONObject.parseObject(json, Map.class).get("userId").toString()));
        }
    }

}