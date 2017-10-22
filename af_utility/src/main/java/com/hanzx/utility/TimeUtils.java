package com.hanzx.utility;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间处理工具
 * <p>
 * Created by: iHanzhx
 * Created on: 2017/8/26 16:24
 * Email: iHanzhx@gmail.com
 */

public class TimeUtils {
    /**
     * 默认时间格式
     */
    private static final String DEF_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat(DEF_FORMAT);
    /**
     * 毫秒与毫秒的倍数
     */
    private static final int MSEC = 1;
    /**
     * 秒与毫秒的倍数
     */
    private static final int SEC = 1000;
    /**
     * 分与毫秒的倍数
     */
    private static final int MIN = 60000;
    /**
     * 时与毫秒的倍数
     */
    private static final int HOUR = 3600000;
    /**
     * 天与毫秒的倍数
     */
    private static final int DAY = 86400000;

    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param milliseconds 毫秒时间戳
     *
     * @return 时间字符串
     */
    public static String milliseconds2String(long milliseconds) {
        return milliseconds2String(milliseconds, DEFAULT_SDF);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为用户自定义</p>
     *
     * @param milliseconds 毫秒时间戳
     * @param format       时间格式
     *
     * @return 时间字符串
     */
    public static String milliseconds2String(long milliseconds, SimpleDateFormat format) {
        return format.format(new Date(milliseconds));
    }

    /**
     * 将时间字符串转为时间戳
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     *
     * @return 毫秒时间戳
     */
    public static long string2Milliseconds(String time) {
        return string2Milliseconds(time, DEFAULT_SDF);
    }

    /**
     * 将时间字符串转为时间戳
     * <p>格式为用户自定义</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     *
     * @return 毫秒时间戳
     */
    public static long string2Milliseconds(String time, SimpleDateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 将时间字符串转为Date类型
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     *
     * @return Date类型
     */
    public static Date string2Date(String time) {
        return string2Date(time, DEFAULT_SDF);
    }

    /**
     * 将时间字符串转为Date类型
     * <p>格式为用户自定义</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     *
     * @return Date类型
     */
    public static Date string2Date(String time, SimpleDateFormat format) {
        return new Date(string2Milliseconds(time, format));
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time Date类型时间
     *
     * @return 时间字符串
     */
    public static String date2String(Date time) {
        return date2String(time, DEFAULT_SDF);
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为用户自定义</p>
     *
     * @param time   Date类型时间
     * @param format 时间格式
     *
     * @return 时间字符串
     */
    public static String date2String(Date time, SimpleDateFormat format) {
        return format.format(time);
    }

    /**
     * 将Date类型转为时间戳
     *
     * @param time Date类型时间
     *
     * @return 毫秒时间戳
     */
    public static long date2Milliseconds(Date time) {
        return time.getTime();
    }

    /**
     * 将时间戳转为Date类型
     *
     * @param milliseconds 毫秒时间戳
     *
     * @return Date类型时间
     */
    public static Date milliseconds2Date(long milliseconds) {
        return new Date(milliseconds);
    }

    /**
     * 毫秒时间戳单位转换（单位：unit）
     *
     * @param milliseconds 毫秒时间戳
     * @param unit         <ul>
     *                     <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *                     <li>{@link TimeUnit#SEC }: 秒</li>
     *                     <li>{@link TimeUnit#MIN }: 分</li>
     *                     <li>{@link TimeUnit#HOUR}: 小时</li>
     *                     <li>{@link TimeUnit#DAY }: 天</li>
     *                     </ul>
     *
     * @return unit时间戳
     */
    private static long milliseconds2Unit(long milliseconds, TimeUnit unit) {
        switch (unit) {
            case MSEC:
                return milliseconds / MSEC;
            case SEC:
                return milliseconds / SEC;
            case MIN:
                return milliseconds / MIN;
            case HOUR:
                return milliseconds / HOUR;
            case DAY:
                return milliseconds / DAY;
        }
        return -1;
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time1和time2格式都为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time0 时间字符串1
     * @param time1 时间字符串2
     * @param unit  <ul>
     *              <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *              <li>{@link TimeUnit#SEC }: 秒</li>
     *              <li>{@link TimeUnit#MIN }: 分</li>
     *              <li>{@link TimeUnit#HOUR}: 小时</li>
     *              <li>{@link TimeUnit#DAY }: 天</li>
     *              </ul>
     *
     * @return unit时间戳
     */
    public static long getIntervalTime(String time0, String time1, TimeUnit unit) {
        return getIntervalTime(time0, time1, unit, DEFAULT_SDF);
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time1和time2格式都为format</p>
     *
     * @param time0  时间字符串1
     * @param time1  时间字符串2
     * @param unit   <ul>
     *               <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *               <li>{@link TimeUnit#SEC }: 秒</li>
     *               <li>{@link TimeUnit#MIN }: 分</li>
     *               <li>{@link TimeUnit#HOUR}: 小时</li>
     *               <li>{@link TimeUnit#DAY }: 天</li>
     *               </ul>
     * @param format 时间格式
     *
     * @return unit时间戳
     */
    public static long getIntervalTime(String time0, String time1, TimeUnit unit,
                                       SimpleDateFormat format) {
        return Math.abs(milliseconds2Unit(string2Milliseconds(time0, format)
                - string2Milliseconds(time1, format), unit));
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time1和time2都为Date类型</p>
     *
     * @param time0 Date类型时间1
     * @param time1 Date类型时间2
     * @param unit  <ul>
     *              <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *              <li>{@link TimeUnit#SEC }: 秒</li>
     *              <li>{@link TimeUnit#MIN }: 分</li>
     *              <li>{@link TimeUnit#HOUR}: 小时</li>
     *              <li>{@link TimeUnit#DAY }: 天</li>
     *              </ul>
     *
     * @return unit时间戳
     */
    public static long getIntervalTime(Date time0, Date time1, TimeUnit unit) {
        return Math.abs(milliseconds2Unit(date2Milliseconds(time1)
                - date2Milliseconds(time0), unit));
    }

    /**
     * 获取当前时间
     *
     * @return 毫秒时间戳
     */
    public static long getCurTimeMills() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @return 时间字符串
     */
    public static String getCurTimeString() {
        return date2String(new Date());
    }

    /**
     * 获取当前时间
     * <p>格式为用户自定义</p>
     *
     * @param format 时间格式
     *
     * @return 时间字符串
     */
    public static String getCurTimeString(SimpleDateFormat format) {
        return date2String(new Date(), format);
    }

    /**
     * 获取当前时间
     * <p>Date类型</p>
     *
     * @return Date类型时间
     */
    public static Date getCurTimeDate() {
        return new Date();
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @param unit <ul>
     *             <li>{@link TimeUnit#MSEC}:毫秒</li>
     *             <li>{@link TimeUnit#SEC }:秒</li>
     *             <li>{@link TimeUnit#MIN }:分</li>
     *             <li>{@link TimeUnit#HOUR}:小时</li>
     *             <li>{@link TimeUnit#DAY }:天</li>
     *             </ul>
     *
     * @return unit时间戳
     */
    public static long getIntervalByNow(String time, TimeUnit unit) {
        return getIntervalByNow(time, unit, DEFAULT_SDF);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param unit   <ul>
     *               <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *               <li>{@link TimeUnit#SEC }: 秒</li>
     *               <li>{@link TimeUnit#MIN }: 分</li>
     *               <li>{@link TimeUnit#HOUR}: 小时</li>
     *               <li>{@link TimeUnit#DAY }: 天</li>
     *               </ul>
     * @param format 时间格式
     *
     * @return unit时间戳
     */
    public static long getIntervalByNow(String time, TimeUnit unit, SimpleDateFormat format) {
        return getIntervalTime(getCurTimeString(), time, unit, format);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time为Date类型</p>
     *
     * @param time Date类型时间
     * @param unit <ul>
     *             <li>{@link TimeUnit#MSEC}: 毫秒</li>
     *             <li>{@link TimeUnit#SEC }: 秒</li>
     *             <li>{@link TimeUnit#MIN }: 分</li>
     *             <li>{@link TimeUnit#HOUR}: 小时</li>
     *             <li>{@link TimeUnit#DAY }: 天</li>
     *             </ul>
     *
     * @return unit时间戳
     */
    public static long getIntervalByNow(Date time, TimeUnit unit) {
        return getIntervalTime(getCurTimeDate(), time, unit);
    }

    /**
     * 判断闰年
     *
     * @param year 年份
     *
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 将date转换成format格式的日期
     *
     * @param format 格式
     * @param date   日期
     *
     * @return
     */
    public static String simpleDateFormat(String format, Date date) {
        if (isNullString(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 将指定格式的日期转换成时间戳
     *
     * @param mDate
     *
     * @return
     */
    public static String Date2Timestamp(Date mDate) {
        return String.valueOf(mDate.getTime()).substring(0, 10);
    }

    /**
     * 将日期字符串 按照 指定的格式 转换成 DATE
     * 转换失败时 return null;
     *
     * @param format
     * @param datess
     *
     * @return
     */
    public static Date string2Date(String format, String datess) {
        SimpleDateFormat sdr = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdr.parse(datess);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将 yyyy年MM月dd日 转换成 时间戳
     *
     * @param format
     * @param datess
     *
     * @return
     */
    public static String string2Timestamp(String format, String datess) {
        Date date = string2Date(format, datess);
        return Date2Timestamp(date);
    }

    /**
     * 获取当前日期时间 / 得到今天的日期
     * str yyyyMMddhhMMss 之类的
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDateTime(String format) {
        return simpleDateFormat(format, new Date());
    }

    /**
     * 时间戳  转换成 指定格式的日期
     * 如果format为空，则默认格式为
     *
     * @param times  时间戳
     * @param format 日期格式 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDate(String times, String format) {
        return simpleDateFormat(format, new Date(stringToInt(times) * 1000L));
    }

    /**
     * 得到昨天的日期
     *
     * @param format 日期格式 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getYestoryDate(String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return simpleDateFormat(format, calendar.getTime());
    }

    /**
     * 视频时间 转换成 "mm:ss"
     *
     * @param milliseconds
     *
     * @return
     */
    public static String formatTime(long milliseconds) {
        String format = "mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        return sdf.format(milliseconds);
    }

    /**
     * "mm:ss" 转换成 视频时间
     *
     * @param time
     *
     * @return
     */
    public static long formatSeconds(String time) {
        String format = "mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        Date date;
        long times = 0;
        try {
            date = sdf.parse(time);
            times = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return times;
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        return a.get(Calendar.DATE);
    }

    /**
     * 判断当前日期是星期几
     *
     * @param strDate 修要判断的时间
     *
     * @return dayForWeek 判断结果
     *
     * @Exception 发生异常<br>
     */
    public static int stringForWeek(String strDate) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(strDate));
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return 7;
        } else {
            return c.get(Calendar.DAY_OF_WEEK) - 1;
        }
    }

    /**
     * 判断当前日期是星期几
     *
     * @param strDate 修要判断的时间
     *
     * @return dayForWeek 判断结果
     *
     * @Exception 发生异常<br>
     */
    public static int stringForWeek(String strDate, SimpleDateFormat format) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(strDate));
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return 7;
        } else {
            return c.get(Calendar.DAY_OF_WEEK) - 1;
        }
    }

    private static boolean isNullString(@Nullable String str) {
        return null == str || str.length() == 0 || "".equals(str) || "null".equals(str);
    }

    /**
     * 字符串转换成整数 ,转换失败将会 return 0;
     *
     * @param str 字符串
     *
     * @return 转换后的int
     */
    private static int stringToInt(String str) {
        if (isNullString(str)) {
            return 0;
        } else {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }
}
