package com.meta.error;

/**
 *  17/11/29
 *  ybh
 *  错误信息提示字典
 */
public class errorMsgDict {


    /**
     * 通用
     */
    //账号上限
    public static final String ADD_ACCOUNT_TO_LIMIT="最多只能建20个账号";
    /**
     *  企业相关操作错误信息
     */
    public static final String DEL_COMPANY="删除企业失败！";
    public static final String DEL_COMPANY_EXIST_CHILD="请先清空该企业的用户或群组！";

    /**
     * 总代理相关
     */

    public static final String DEL_MERCHANTS_EXIST_COMPANY="请先清空代理的用户！";
    /**
     * 代理相关
     */
    public static final String ADD_MERCHANTS_ACCOUNT_TO_LIMIT="最多只能建20个账号";

    /**
     * 子代理相关操作
     */
    public static final String ADD_SEC_MERCHANTS_ACCOUNT_TO_LIMIT="最多只能建20个账号";

    public static final String DEL_SEC_MERCHANTS="删除子代理失败！";
    public static final String DEL_SEC_MERCHANTS_EXIST_COMPANY="请先清空用户的企业！";
}
