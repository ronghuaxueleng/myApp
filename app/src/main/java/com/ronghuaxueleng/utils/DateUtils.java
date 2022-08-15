package com.ronghuaxueleng.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    /**
     * 获取当前日期【yyyy-MM-dd】
     *
     * @return
     */
    public static String getNowDate() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    /**
     * 输入格式化时间yyyy-MM-dd HH:mm:ss返回时间戳
     * 日期格式可修改
     */
    public static long getTimeStamp(String time) {
        String SEPARATE = " ";//时间分隔符
        String TIME_FORMAT = "yyyy-MM-dd" + SEPARATE + "HH:mm:ss";//时间格式
        SimpleDateFormat sdr = new SimpleDateFormat(TIME_FORMAT, Locale.CHINA);
        Date date;
        long timestamp;
        try {
            date = sdr.parse(time);
            timestamp = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return System.currentTimeMillis();
        }
        return timestamp;
    }

    /**
     * 比较两个格式化时间的大小
     *
     * @param date1 2004-03-26 13:31:40.120
     * @param date2 2004-01-02 11:30:24.102
     * @return date1>date2 true;date1<=date2 false;
     */
    public static boolean compareDate(String date1, String date2) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
        if (date1 == null || date2 == null || "".equals(date2) || "".equals(date1)) {
            return false;
        }
        Date d1;
        Date d2;
        try {
            d1 = df.parse(date1);
            d2 = df.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        long l = d1.getTime() - d2.getTime();
        return l > 0;
    }

}
