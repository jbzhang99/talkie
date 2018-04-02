package com.meta.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import javassist.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author:lhq
 * @date:2017/12/20 16:24
 */
public interface AopService {

    /**
     * 获得切面方法的所有参数名称
     *
     * @param cls 指定是由哪个的aop类
     * @param clazzName  指定切面的那个实体类名
     * @param methodName 指定切面实体类当前切面的方法
     * @return
     */
    public  String[] getFieldsName(Class<?> cls, String clazzName, String methodName) throws NotFoundException;

    /**
     * 获得方法切面参数所有的值
     * @param point
     * @return
     */
    public Object[] getParamsValues(ProceedingJoinPoint  point);

    /**
     * 获得切面方法对应参数token的值
     * @param tokenName
     * @return
     */
    public String getTokenFieldValues(ProceedingJoinPoint point,String tokenName);

    /**
     * 获得切面方法的完整类名（字符串）
     * @param clazz
     * @return
     */
    public String getPointClassName(Class<?> clazz);

    /**
     * 获得切面方法的完整类名（类）
     * @param point
     * @return
     */
    public Class<?> getPointClass(ProceedingJoinPoint point)throws Exception;

    /**
     * 获得切面对应的方法
     * @param point
     * @return
     */
    public String getPointMethodName(ProceedingJoinPoint point);


    /**
     * 获得对应的切面的实体类对应的方法的参数名称
     * @return
     */
    public String[] getParamsByTargetMethod(ProceedingJoinPoint point);

    /**
     * 获得request请求
     * @return
     */
    public HttpServletRequest getRequest();

    /**
     * 获得response请求
     * @return
     */
    public HttpServletResponse getResponse();



}
