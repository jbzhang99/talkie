package com.meta.commonUtil;

/**
 * Created by lhq on 2017/10/18.
 */
public class CommonUtils {

    /**
     * 判断是否在线
     * @param state
     * @return
     */
    public static String isOnLine(Boolean state) {
        String strTemp = "";
        if (state) {
            strTemp = "是";
        } else {
            strTemp = "否";
        }
        return strTemp;
    }


    /**
     * 查询　用户的等级(merchantLevel)
     */
    public static String findMerchantLevel(String str) {
        String strTemp = "";
        if (str.equals("1")) {
            strTemp = "总代理";
        } else if (str.equals("2")) {
            strTemp = "代理";
        } else if (str.equals("3")) {
            strTemp = "子代理";
        } else if (str.equals("4")) {
            strTemp = "企业";
        } else if (str.equals("5")) {
            strTemp = "代理管理";
        } else if (str.equals("6")) {
            strTemp = "信息查询";
        } else if (str.equals("7")) {
            strTemp = "用户";
        } else if (str.equals("8")) {
            strTemp = "信息查询"; // 代理级
        } else if (str.equals("9")) {
            strTemp = "企业管理";// 代理级
        } else if (str.equals("10")) {
            strTemp = "信息查询"; // 子代理级
        } else if (str.equals("11")) {
            strTemp = "企业管理";// 子代理级
        } else if (str.equals("12")) {
            strTemp = "信息查询";// 企业级
        } else if (str.equals("13")) {
            strTemp = "用户管理";// 企业级
        } else if ("14".equals(str)) {
            strTemp = "二级企业";
        }else if("15".equals(str)){
            strTemp = "二级企业的信息查询";
        } else if ("16".equals(str)){
            strTemp = "会计";
        } else {
            strTemp = "无该权限 ";
        }
        return strTemp;
    }


    /**
     * 用户状态
     *
     * @param str
     * @return
     */
    public static String findByStatusName(String str) {
        String strTemp = "";
        if (str.equals("1")) {
            strTemp = "正常";
        }
        if (str.equals("2")) {
            strTemp = "欠费";
        }
        if (str.equals("3")) {
            strTemp = "暂停";
        }
        return strTemp;
    }

    /**
     * Q币划分/回收
     *
     * @param str
     * @return
     */
    public static String findByQUserEventType(String str) {
        String strTemp = "";
        if (str.equals("1")) {
            strTemp = "划分";
        }
        if (str.equals("2")) {
            strTemp = "回收";
        }
        return strTemp;
    }

    /**
     * 特殊播报
     * 停用/使用
     */
    public static String findByBoardCastType(String str) {
        String strTemp = "";
        if (str.equals("1")) {
            strTemp = "使用";
        }
        if (str.equals("2")) {
            strTemp = "停用";
        }
        return strTemp;
    }

    /**
     * 用户操作
     *
     * @param str
     * @return
     */
    public static String findByUserEventType(String str) {

        String strTemp = "";
        if (str.equals("1")) {
            strTemp = "用户登录";
        }
        if (str.equals("2")) {
            strTemp = "用户登出";
        }
        if (str.equals("3")) {
            strTemp = "新增用户";
        }
        if (str.equals("4")) {
            strTemp = "删除用户";
        }
        if (str.equals("5")) {
            strTemp = "修改用户";
        }
        if (str.equals("6")) {
            strTemp = "新增用户";
        }
        if (str.equals("7")) {
            strTemp = "修改群组";
        }
        if (str.equals("8")) {
            strTemp = "新增好友";
        }
        if (str.equals("9")) {
            strTemp = "删除好友";
        }
        return strTemp;
    }

    public static String findByGroupStatus(String str) {
        String strTemp = "";
        if (str.equals("1")) {
            strTemp = "激活";
        }
        if (str.equals("2")) {
            strTemp = "灭活";
        }
        return strTemp;
    }

    /**
     * 查找组的类型
     *
     * @param str
     * @return
     */
    public static String findByGroupType(String str) {
        String strTemp = "";
        if (str.equals("1")) {
            strTemp = "普通组";
        }
        if (str.equals("2")) {
            strTemp = "临时组";
        }
        if (str.equals("3")) {
            strTemp = "嵌套组";
        }
        return strTemp;
    }

    public static String findByUserFuns(String str) {
        String strTemp = "";
        if (str.equals("1")) {
            strTemp = "单呼";
        }
        if (str.equals("2")) {
            strTemp = "定位";
        }
        if (str.equals("3")) {
            strTemp = "好友";
        }
        if (str.equals("4")) {
            strTemp = "录音";
        }
        if (str.equals("5")) {
            strTemp = "群组";
        }
        return strTemp;
    }


}
