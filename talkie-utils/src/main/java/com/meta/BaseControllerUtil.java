package com.meta;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meta.jwt.JwtUtil;
import com.meta.regex.RegexUtil;
import com.meta.request.RequestUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * 控制器基类。提供模型转换，分页规范实现。
 *
 * @author lhq
 * @created 2016.08.08 13:54
 */
public class BaseControllerUtil extends AbstractController {
    //日志
    protected static Logger logger = LoggerFactory.getLogger(BaseControllerUtil.class);
    @Autowired
    protected ObjectMapper objectMapper;
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return null;
    }


    public Result getResultList(List detaiModelList, long totalCount, int currPage, int rows) {
        Result result = new Result();
        result.setSuccessFlg(true);
        result.setDetailModelList(detaiModelList);
        result.setTotalCount(totalCount);
        result.setCurrPage(currPage);
        result.setPageSize(rows);
        if (result.getTotalCount() % result.getPageSize() > 0) {
            result.setTotalPage(((int) result.getTotalCount() / result.getPageSize()) + 1);
        } else {
            result.setTotalPage((int) result.getTotalCount() / result.getPageSize());
        }
        return result;
    }

    public Result getResultList(List detaiModelList, Object o, long totalCount, int currPage, int rows) {
        Result result = new Result();
        result.setSuccessFlg(true);
        result.setDetailModelList(detaiModelList);
        result.setObj(o);
        result.setTotalCount(totalCount);
        result.setCurrPage(currPage);
        result.setPageSize(rows);
        if (result.getTotalCount() % result.getPageSize() > 0) {
            result.setTotalPage(((int) result.getTotalCount() / result.getPageSize()) + 1);
        } else {
            result.setTotalPage((int) result.getTotalCount() / result.getPageSize());
        }
        return result;
    }

    public Result getResultList(List detaiModelList, Object o) {
        Result result = new Result();
        result.setSuccessFlg(true);
        result.setDetailModelList(detaiModelList);
        result.setObj(o);
        return result;
    }


    public Result getResultList(List detaiModelList) {
        Result result = new Result();
        result.setSuccessFlg(true);
        result.setDetailModelList(detaiModelList);
        return result;
    }

    public Result success(Object o) {
        Result result = new Result();
        if (null != o)
            result.setObj(o);
        result.setSuccessFlg(true);
        return result;
    }

    public Result error(String errorMsg) {
        Result result = new Result();
        result.setSuccessFlg(false);
        result.setErrorMsg(errorMsg);
        return result;
    }

    public Result error(String errorMsg, Integer errorCode) {
        Result result = new Result();
        result.setSuccessFlg(false);
        result.setErrorMsg(errorMsg);
        result.setErrorCode(errorCode);
        return result;
    }

    public  String getLanguage(){
     return    RequestUtil.getRequest().getHeader("language");
    }


    public String  getAccount()throws Exception{

        String token = RequestUtil.getRequest().getHeader("Authorization");
        System.err.println(token);
        if(!RegexUtil.isNull(token)){
            Claims claims = JwtUtil.parseJWT(token);//验证
            String json = claims.getSubject();
            return    JSONObject.parseObject(json, Map.class).get("account").toString();
        }
        return  null;
    }

    public Long  getUserId()throws Exception{

        String token = RequestUtil.getRequest().getHeader("Authorization");
        System.err.println(token);
        if(!RegexUtil.isNull(token)){
            Claims claims = JwtUtil.parseJWT(token);//验证
            String json = claims.getSubject();
            return   Long.parseLong( JSONObject.parseObject(json, Map.class).get("userId").toString());
        }
        return  null;
    }
    /**
     * 获取当前网络ip
     *
     * @param
     * @return
     */
    public static String getIpAddr() {
        String ipAddress = RequestUtil.getRequest().getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = RequestUtil.getRequest().getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = RequestUtil.getRequest().getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = RequestUtil.getRequest().getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
            // = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }




//    /**
//     * 将实体转换为模型。
//     *
//     * @param source
//     * @param targetCls
//     * @param properties
//     * @param <T>
//     * @return
//     */
//    public <T> T convertToModel(Object source, Class<T> targetCls, String... properties) {
//        if (source == null) {
//            return null;
//        }
//        T target = BeanUtils.instantiate(targetCls);
//        BeanUtils.copyProperties(source, target, propertyDiffer(properties, targetCls));
//        return target;
//    }
//
//    /**
//     * 将实体集合转换为模型集合。
//     *
//     * @param sources
//     * @param targets
//     * @param properties
//     * @param <T>
//     * @return
//     */
//    public <T> Collection<T> convertToModels(Collection sources, Collection<T> targets, Class<T> targetCls, String properties) {
//        if (sources == null) {
//            return null;
//        }
//        Iterator iterator = sources.iterator();
//        while (iterator.hasNext()) {
//            Object source = iterator.next();
//
//            T target = (T) BeanUtils.instantiate(targetCls);
//            BeanUtils.copyProperties(source, target, propertyDiffer(StringUtils.isEmpty(properties) ? null : properties.split(","), targetCls));
//            targets.add(target);
//        }
//
//        return targets;
//    }
//
//    /**
//     * 计算不在类中的属性。
//     *
//     * @return
//     */
//    protected String[] propertyDiffer(String[] properties, Class targetCls) {
//        if (properties == null || properties.length == 0) return null;
//
//        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(targetCls);
//        List<String> propertiesList = Arrays.asList(properties);
//        List<String> arrayList = new ArrayList<>();
//
//        for (PropertyDescriptor targetPd : targetPds) {
//            Method writeMethod = targetPd.getWriteMethod();
//            if (writeMethod != null && !propertiesList.contains(targetPd.getName())) {
//                arrayList.add(targetPd.getName());
//            }
//        }
//
//        return arrayList.toArray(new String[arrayList.size()]);
//    }

}