package com.grizzly.utils;

/**
 * @author DurantSimpson
 * @desc 经纬度算距离
 * @create 2018-07-21 13:35
 **/
public class LocationUtils {
    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 通过经纬度获取距离(单位：米)
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double getMeterDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        return getKilometreDistance(lat1, lng1, lat2, lng2)*1000;
    }

    /**
     * 通过经纬度获取距离(单位：千米)
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double getKilometreDistance(double lat1, double lng1, double lat2,
                                          double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        return s;
    }


}
