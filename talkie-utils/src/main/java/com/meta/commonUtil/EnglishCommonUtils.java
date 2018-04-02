package com.meta.commonUtil;

/**
 * @author:lhq 英文版的状态说明表
 * @date:2018/1/5 14:33
 */
public class EnglishCommonUtils {

    /**
     * 判断是否在线
     * @param state
     * @return
     */
    public static String isOnLine(Boolean state) {
        String strTemp = "";
        if (state) {
            strTemp = "Yes";
        } else {
            strTemp = "No";
        }
        return strTemp;
    }


    /**
     * 查询　用户的等级(merchantLevel)
     */
    public static String findMerchantLevel(String str) {
        String strTemp = "";
        if ("1".equals(str)) {
            strTemp = "GeneralAgent";
        } else if ("2".equals(str)) {
            strTemp = "Agency";
        } else if ("3".equals(str)) {
            strTemp = "Subagent";
        } else if ("4".equals(str)) {
            strTemp = "Enterprise";
        } else if ("5".equals(str)) {
            strTemp = "AgencyManagement";
        } else if ("6".equals(str)) {
            strTemp = "messageQuery";
        } else if ("7".equals(str)) {
            strTemp = "User";
        } else if ("8".equals(str)) {
            strTemp = "messageQuery";
        } else if ("9".equals(str)) {
            strTemp = "EnterpriseManagement ";
        } else if ("10".equals(str)) {
            strTemp = "messageQuery ";
        } else if ("11".equals(str)) {
            strTemp = "EnterpriseManagement ";
        } else if ("12".equals(str)) {
            strTemp = "messageQuery ";
        } else if ("13".equals(str)) {
            strTemp = "UserManageent ";
        }else if ("14".equals(str)){
            strTemp="SECEnterprise";
        }else if ("15".equals(str)){
            strTemp="";
        }else if ("16".equals(str)){
            strTemp="AccountantManage";
        }
        else {
            strTemp = "Without this permission";
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
        if ("1".equals(str)) {
            strTemp = "normal";
        } else if ("2".equals(str)) {
            strTemp = "arrears";
        } else if ("3".equals(str)) {
            strTemp = "pause";
        } else {
            strTemp = " Without this state";
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
            strTemp = "place";
        }
        if (str.equals("2")) {
            strTemp = "recover";
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
            strTemp = "employ";
        }
        if (str.equals("2")) {
            strTemp = "pause";
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
            strTemp = "normalGroup";
        }
        if (str.equals("2")) {
            strTemp = "temporaryGroup";
        }
        if (str.equals("3")) {
            strTemp = "nestingGroup";
        }
        return strTemp;
    }

    /**
     *
     * @param str
     * @return
     */
    public static String findByUserFuns(String str) {
        String strTemp = "";
        if (str.equals("1")) {
            strTemp = "singleCall";
        }
        if (str.equals("2")) {
            strTemp = "position";
        }
        if (str.equals("3")) {
            strTemp = "friend";
        }
        if (str.equals("4")) {
            strTemp = "record";
        }
        if (str.equals("5")) {
            strTemp = "group";
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
            strTemp = "login";
        }
        if (str.equals("2")) {
            strTemp = "login out";
        }
        if (str.equals("3")) {
            strTemp = "add user";
        }
        if (str.equals("4")) {
            strTemp = "del user";
        }
        if (str.equals("5")) {
            strTemp = "modify user";
        }
        if (str.equals("6")) {
            strTemp = "add user";
        }
        if (str.equals("7")) {
            strTemp = "modify group";
        }
        if (str.equals("8")) {
            strTemp = "add friend";
        }
        if (str.equals("9")) {
            strTemp = "del friend";
        }
        return strTemp;
    }



}
