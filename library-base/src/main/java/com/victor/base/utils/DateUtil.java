package com.victor.base.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.goldze.mvvmhabit.utils.KLog;


/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/8
 * 邮箱：jxfengmtx@gmail.com
 */
public class DateUtil {

    public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";
    public static final String HOUR_MINUTE = "HH:mm";
    public static final String HOUR_MINUTE_SECOND = "HH:mm:ss";
    public static final String HOUR = "HH";
    public static final String ALL = YEAR_MONTH_DAY + " " + HOUR_MINUTE_SECOND;
    public static final String FILEPATTERN = "yyyyMMddHHmmssSSS";


    //1.  Calendar 转化 String
    public static String calendarTOString(Calendar calendar, String pattern) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dateStr = sdf.format(calendar.getTime());
        return dateStr;
    }


    //2.String 转化Calendar
    public static Calendar StringToCalendar(String str, String pattern) {

//    String str="2012-5-27";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            KLog.i(e.getMessage());
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }


    //3.Date 转化String

    public static String dateToString(Date date, String pattern) {
        if (date == null) {
            date = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        String dateStr = sdf.format(date);
        return dateStr;
    }

    //4.    String 转化Date
    public static Date stringToDate(String str, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            KLog.i("StringToCalendar: " + e.getMessage());
            date = new Date();
        }
        return date;
    }


    //5.Date 转化Calendar
    public static Calendar dateToCalendar(Date date, String pattern) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }


    //6.Calendar转化Date
    public static Date calendarToDate(Calendar calendar) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }

        Date date = calendar.getTime();
        return date;
    }

    //    7.    String 转成Timestamp
    public static Timestamp StringToTimestamp(String str) {

        Timestamp ts = Timestamp.valueOf(str);
        return ts;
    }

    //        8.Date 转TimeStamp
    public static Timestamp dateToTimestamp(Date date, String pattern) {
        if (date == null) {
            date = new Date();
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        String time = df.format(date);
        Timestamp ts = Timestamp.valueOf(time);
        return ts;
    }

    public static String millsecondsToStrDate(long millseconds, String pattern) {
        Date date = new Date();
        date.setTime(millseconds);
        return dateToString(date, pattern);
    }

    /**
     * @param str
     * @param pattern 匹配的只有时分的话，默认时间戳从1970算
     * @return
     */
    public static long strDateToMillseconds(String str, String pattern) {
        Date date = stringToDate(str, pattern);
        long ml = date.getTime();
        if (pattern.equals(HOUR_MINUTE_SECOND) || pattern.equals(HOUR_MINUTE)) { //时间戳从今天算
            String s = dateToString(null, YEAR_MONTH_DAY) + " " + str;
            ml = strDateToMillseconds(s, ALL);
            /*Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            ml = calendar.getTime().getTime() + ml;*/
        }
        return ml;
    }

    /**
     * 时间加减小时
     *
     * @param startDate 要处理的时间，Null则为当前时间
     * @param hours     加减的小时
     * @return Date
     */
    public static Date dateAddHours(Date startDate, int hours) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.HOUR, c.get(Calendar.HOUR) + hours);
        return c.getTime();
    }

    /**
     * 时间加减分钟
     *
     * @param startDate 要处理的时间，Null则为当前时间
     * @param minutes   加减的分钟
     * @return Date
     */
    public static Date dateAddMinutes(Date startDate, int minutes) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + minutes);
        return c.getTime();
    }

    /**
     * 时间加减秒数
     *
     * @param startDate 要处理的时间，Null则为当前时间
     * @param seconds   加减的秒数
     * @return Date
     */
    public static Date dateAddSeconds(Date startDate, int seconds) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.SECOND, c.get(Calendar.SECOND) + seconds);
        return c.getTime();
    }

    /**
     * 时间加减年数
     *
     * @param startDate 要处理的时间，Null则为当前时间
     * @param years     加减的年数
     * @return Date
     */
    public static Date dateAddYears(Date startDate, int years) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) + years);
        return c.getTime();
    }

    /**
     * 时间加减月数
     *
     * @param startDate 要处理的时间，Null则为当前时间
     * @param months    加减的月数
     * @return Date
     */
    public static Date dateAddMonths(Date startDate, int months) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + months);
        return c.getTime();
    }

    /**
     * 时间加减天数
     *
     * @param startDate 要处理的时间，Null则为当前时间
     * @param days      加减的天数
     * @return Date
     */
    public static Date dateAddDays(Date startDate, int days) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.DATE, c.get(Calendar.DATE) + days);
        return c.getTime();
    }

    /**
     * 时间比较（如果myDate>compareDate返回1，<返回-1，相等返回0）
     *
     * @param myDate      时间
     * @param compareDate 要比较的时间
     * @return int
     */
    public static int dateCompare(Date myDate, Date compareDate) {
        Calendar myCal = Calendar.getInstance();
        Calendar compareCal = Calendar.getInstance();
        myCal.setTime(myDate);
        compareCal.setTime(compareDate);
        return myCal.compareTo(compareCal);
    }

    /**
     * 获取两个时间中最小的一个时间
     *
     * @param date
     * @param compareDate
     * @return Date
     */
    public static Date dateMin(Date date, Date compareDate) {
        if (date == null) {
            return compareDate;
        }
        if (compareDate == null) {
            return date;
        }
        if (1 == dateCompare(date, compareDate)) {
            return compareDate;
        } else if (-1 == dateCompare(date, compareDate)) {
            return date;
        }
        return date;
    }

    /**
     * 获取两个时间中最大的一个时间
     *
     * @param date
     * @param compareDate
     * @return Date
     */
    public static Date dateMax(Date date, Date compareDate) {
        if (date == null) {
            return compareDate;
        }
        if (compareDate == null) {
            return date;
        }
        if (1 == dateCompare(date, compareDate)) {
            return date;
        } else if (-1 == dateCompare(date, compareDate)) {
            return compareDate;
        }
        return date;
    }


    /**
     * 获取时间当年某个月的最后一天
     *
     * @param startDate
     * @param month     月份
     * @return int 天数
     */
    public static int getLastDayOfMonth(Date startDate, int month) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(c.get(Calendar.YEAR), month, 1);
        c.add(Calendar.DATE, -1);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * 获取时间当年某个月的最后一天
     *
     */
    public static String getNowTime() {
        return dateToString(new Date(), ALL);
    }
}
