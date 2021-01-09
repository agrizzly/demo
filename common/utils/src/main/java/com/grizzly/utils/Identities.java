package com.grizzly.utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 *
 * 封装id生成的方法
 * @author itguang
 * @create 2018-01-12 10:41
 **/
public class Identities {

    private static SecureRandom random = new SecureRandom();

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid2() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    /**
     * 主要功能:生成流水号 yyyyMMddHHmmssSSS + 3位随机数
     *
     */
    public static String generateFlowNumber() {
        // 精确到毫秒
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String suffix = fmt.format(new Date());
        suffix = suffix  + Math.round((Math.random() * 100000));
        return suffix;
    }

    public  static  void  main(){
        System.out.println(Math.random());
    }


}
