package com.meta.random;

import java.util.Random;

/**
 * Created by linhq on 2017/1/6 0006.
 * *
 * 随机生成6位数的邀请码
 */
public class RandomUtil {

    private static final String FIGURE = "0,1,2,3,4,5,6,7,8,9";    //数字验证码字符集
    private static final String STR = "A,B,C,D,E,F,G,H,I,G,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";    //验证码字符集
    public String getRandomInvite() {
        String parms[] = STR.split(",");
        String parmss[] = FIGURE.split(",");

        Random rand = new Random();
        int index = 0;
        int indexTemp = 0;
        String randStr = "";
        randStr = "";
        //生成字母
        for (int i = 0; i < 2; ++i) {
            index = rand.nextInt(parms.length - 1);
            randStr += parms[index];
        }
        //生成数字
        for (int i = 0; i < 4; ++i) {
            indexTemp = rand.nextInt(parmss.length - 1);
            randStr += parms[index];
        }

        return randStr;
    }


}
