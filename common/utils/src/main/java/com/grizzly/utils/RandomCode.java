package com.grizzly.utils;

import java.util.Random;

/**
 * 生成数字验证码
 *
 * @author itguang
 * @create 2018-01-12 9:15
 **/
public class RandomCode {


    /**
     * 生成数字验证码
     *
     * @param n 验证码的位数
     * @return
     */
    public static String genCode(int n) {

        String str = "0123456789";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();

    }
}
