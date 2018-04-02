package com.meta.exchangeMoney;

/**
 * Created by linhq on 2017/7/25 0025.
 * 传入一个数字，转成大写的文字输入
 */
public  class TransitionMoney {
    private static String[] moneyTemp = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};

    public static  String exchangeMoney(String money) {
        int sLength = money.length();
        String newTemp = "";
        for(int x =0; x<sLength;x++){
            newTemp = newTemp + moneyTemp[money.charAt(x) - 48] ;
        }
        return newTemp;
    }

}
