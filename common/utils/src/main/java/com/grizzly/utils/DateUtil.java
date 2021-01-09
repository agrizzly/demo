package com.grizzly.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {


    /**
     * 根据年月日获取当天零点零分零秒
     * date: 20201028
     * */
    public static Date getZeroPointByString(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.parse(date);
    }

    /**
     * 根据年月日获取当天23点59分59秒
     * date: 20201028
     * */
    public static Date getMidnightByString(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        return sdf.parse(date + " 23:59:59");
    }

    /**
     * 获得当天零点零时零分
     * */
    public static Date getMidnight() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        return zero;
    }

    /**
     * 获得昨天零点零时零分
     * */
    public static Date getYesterdayMidnight() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        return zero;
    }

    /**
     * 获得本周第一天零点零时零分
     * */
    public static Date getThisWeekMidnight() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        return zero;
    }

    /**
     * 获得当月1号零时零分零秒
     * @return
     */
    public static Date initDateByMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获得n天之前时间
     * @return
     */
    public static Date nDaysAgo(Integer n) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1*n);
        return calendar.getTime();
    }

    /**
     * 获得n天之前的零时零分零秒
     * @return
     */
    public static Date nDaysAgoMidnight(Integer n) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1*n);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
}
