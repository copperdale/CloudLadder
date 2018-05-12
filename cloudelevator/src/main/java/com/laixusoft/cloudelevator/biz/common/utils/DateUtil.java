package com.laixusoft.cloudelevator.biz.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil extends wint.lang.utils.DateUtil {

    public static final String DEFAULT_DATE_FMT = "yyyy-MM-dd HH:mm:ss";

    public static final String DEFAULT_DATE_FMT_NODATE = "HH:mm:ss";

    public static final String DEFAULT_DATE_FMT_NO = "yyyy-MM-dd";

    public static final String DEFAULT_DATE_FMT_NOSS = "yyyy-MM-dd HH:mm";

    public static final String DATE_FMT_YMD = "yyyy年MM月dd日";

    public static final String DATE_FMT_MD = "MM月dd日";

    public static final String DATE_FMT_MD_HM = "MM月dd日 HH:mm";

    public static final String DATE_YMDHMS = "yyyyMMddHHmmss";


    public static Date parse(String input) {
        return parse(input, DEFAULT_DATE_FMT);
    }

    public static Date parseNoException(String input) {
        try {
            return parse(input, DEFAULT_DATE_FMT);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseNoSSNoException(String input) {
        try {
            return parse(input, DEFAULT_DATE_FMT);
        } catch (Exception e) {
            try {
                return parse(input, DEFAULT_DATE_FMT_NOSS);
            } catch (Exception e1) {
                return null;
            }
        }
    }

    public static String format(Date date, String fmt) {
        try {
            if (date == null) {
                return "";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(fmt);
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String format(Date date) {
        return format(date, DEFAULT_DATE_FMT);
    }

    public static String formatNoException(Date date) {
        try {
            return format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatNoTimeNoException(Date date) {
        try {
            return format(date, DEFAULT_DATE_FMT_NO);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatNoDateNoException(Date date) {
        try {
            return format(date, DEFAULT_DATE_FMT_NODATE);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatNoYearAndNoTime(Date date) {
        try {
            return format(date, DATE_FMT_MD);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatToYMDNoException(Date date) {
        try {
            return format(date, DATE_FMT_YMD);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parse(String input, String fmt) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        try {
            return sdf.parse(input);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date formatCurrentDayMax(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    public static Date formatCurrentDayMin(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static int dayOfWeek(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    public static Date currentMonthMax(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);
        c.roll(Calendar.DATE, -1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    public static Date currentMonthMin(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static int toChineseWeek(int week) {
        return week == Calendar.SUNDAY ? Calendar.SATURDAY : --week;
    }

    public static Date currentWeekMax(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week = c.get(Calendar.DAY_OF_WEEK);
        week = toChineseWeek(week);
        c.add(Calendar.DATE, 7-week);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    public static Date currentWeekMin(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week = c.get(Calendar.DAY_OF_WEEK);
        week = toChineseWeek(week);
        c.add(Calendar.DATE, -(week-1));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date currentYearMax(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, 11);
        c.set(Calendar.DATE, 1);
        c.roll(Calendar.DATE, -1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    public static Date currentYearMin(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 计算月份
     *
     * @param date
     * @param changeValue 正数为增加，负数为减去
     * @return
     */
    public static Date changeMonth(Date date, int changeValue) {
        if (date == null || changeValue == 0) {
            return date;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, changeValue);
        return c.getTime();
    }

    /**
     * 计算天
     *
     * @param date
     * @param changeValue 正数为增加，负数为减去
     * @return
     */
    public static Date changeDay(Date date, int changeValue) {
        if (date == null || changeValue == 0) {
            return date;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_YEAR, changeValue);
        return c.getTime();
    }

    /**
     * 计算秒
     *
     * @param date
     * @param changeValue
     * @return Date
     * @throws
     * @Title: changeMin
     */
    public static Date changeMin(Date date, int changeValue) {
        if (date == null || changeValue == 0) {
            return date;
        }
        long flag = changeValue * 60 * 1000;
        return new Date(date.getTime() + flag);
    }

    /**
     * @param date
     * @return int
     * @throws
     * @Title: getYear
     * @Description: 获取年份
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);

        return year;
    }

    /**
     * @param date
     * @return int
     * @throws
     * @Title: getYear
     * @Description: 获取月份
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH) + 1;

        return month;
    }

    /**
     * @param date
     * @return int
     * @throws
     * @Title: getYear
     * @Description: 获日期
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);

        return day;
    }

    /**
     * 获取date日期的月的第一天
     *
     * @param date
     * @return Date
     * @throws
     * @Title: getMonthBeginDate
     * @Description: 获取date日期的月的第一天
     */
    public static Date getMonthBeginDate(Date date) {
        Calendar cal = Calendar.getInstance();
        // 当前月的第一天
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date beginTime = cal.getTime();

        return beginTime;
    }

    /**
     * 获取date日期的月的最后一天
     *
     * @param date
     * @return Date
     * @throws
     * @Title: getMonthEndDate
     * @Description: 获取date日期的月的最后一天
     */
    public static Date getMonthEndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        // 当前月的最后一天
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        Date endTime = cal.getTime();

        return endTime;
    }

    public static Date addYear(Date date, int year) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, year);
        return cal.getTime();
    }

    /**
     * @param @param date
     * @param @param begin
     * @param @param end
     * @return void
     * @throws
     * @Title: compareDateTime
     * @Description: 比较时间, 大于返回true，小于等于返回false
     */
    public static boolean compareDateTime(Date date, Date compareTo) {
        if (null == date || null == compareTo) {
            return false;
        }
        return date.getTime() > compareTo.getTime();
    }

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        sss = formatter.format(sdf.parse("20141221210202"));
        System.out.println(format(formatCurrentDayMax(new Date())));
        System.out.println(format(formatCurrentDayMin(new Date())));
        System.out.println(format(currentMonthMax(new Date())));
        System.out.println(format(currentMonthMin(new Date())));
        System.out.println(getYear(new Date()));
        System.out.println(format(currentWeekMin(new Date())));
        System.out.println(format(currentWeekMax(new Date())));
        System.out.println(format(currentYearMin(new Date())));
        System.out.println(format(currentYearMax(new Date())));

        int p="VIP/级别/50".lastIndexOf("/");
        System.out.println("VIP/级别/50".substring(0,p));
        System.out.println("VIP/级别/50".substring(p+1,"VIP/级别/50".length()));
    }
}