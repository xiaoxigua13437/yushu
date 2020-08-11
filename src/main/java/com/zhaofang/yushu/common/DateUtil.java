package com.zhaofang.yushu.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author yushu
 * @create 2020-05-12 11:13
 *
 * 时间工具类
 */
public class DateUtil  {

    private static Logger logger = Logger.getLogger(DateUtil.class.getName());
    /***
     * 一天的时间单位
     */
    public static final Long ONE_DAY_TIME = 24 * 60 * 60 * 1000L;
    /**
     * 英文简写（默认）如：2010-12-01
     */

    public static final String FORMAT_SHORT = "yyyy-MM-dd";
    /***
     * 英文简写,只到月如：2010-12
     */
    public static final String FORMAT_SHORT_WITH_MONTH = "yyyy-MM";
    /***
     * 英文简写,只到月,无中横线：201012
     */
    public static final String FORMAT_SHORT_WITH_MONTH_NO_MID_LINE = "yyyyMM";
    /***
     * 英文简写到天,无中横线：20101201,
     */
    public static final String FORMAT_SHORT_WITH_NO_MID_LINE = "yyyyMMdd";
    /***
     * 英文简写到小时,无中横线：2010120108,
     */
    public static final String FORMAT_SHORT_WITH_HOUR_NO_MID_LINE = "yyyyMMddHH";
    /***
     * 英文简写到分钟,无中横线：201012011033,
     */
    public static final String FORMAT_SHORT_WITH_MIN_NO_MID_LINE = "yyyyMMddHHmm";

    /**
     * 英文简写到秒 如：20101201231506
     */
    public static final String FORMAT_LONG_WITH_NO_MID_LINE = "yyyyMMddHHmmss";
    /**
     * 英文简写到毫秒 如：20101201231506999
     */
    public static final String FORMAT_LONG_WITH_MILL_NO_MID_LINE = "yyyyMMddHHmmssSSS";
    /***
     * 英文简写到小时：2010-12-01 12
     */
    public static final String FORMAT_SHORT_WITH_HOUR = "yyyy-MM-dd HH";
    /***
     * 英文简写到分钟：2010-12-01 12:12
     */
    public static final String FORMAT_SHORT_WITH_MINUTE = "yyyy-MM-dd HH:mm";

    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    public static final String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

    /***
     * 默认时间类型:2010-12-01 23:15:06
     */
    public static final String FORMAT_DEFAULT = FORMAT_LONG;

    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 中文简写 如：2010年12月01日
     */
    public static final String FORMAT_SHORT_CN = "yyyy年MM月dd日";

    /**
     * 中文全称 如：2010年12月01日 23时15分06秒
     */
    public static final String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";

    /**
     * 精确到毫秒的完整中文时间
     */
    public static final String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    private DateUtil() {
        super();
    }

    /**
     * 获得默认的 date pattern 2010-12-01 23:15:06
     */
    public static String getDefaultDatePattern() {
        return FORMAT_DEFAULT;
    }

    /**
     * 根据预设格式返回当前日期 2010-12-01 23:15:06
     */
    public static String getNowString() {
        return format(new Date());
    }

    /**
     * 根据用户格式返回当前日期
     */
    public static String getNowString(String format) {
        return format(new Date(), format);
    }

    /**
     * 使用预设格式格式化日期 2010-12-01 23:15:06
     */
    public static String format(Date date) {
        return format(date, getDefaultDatePattern());
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date 日期
     * @param pattern 日期格式
     */
    public static String format(Date date, String pattern) {
        String returnValue = null;
        if (date == null || pattern == null) {
        } else {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        System.out.println(returnValue);
        return returnValue;
    }

    /**
     * 使用预设格式提取字符串日期 2010-12-01 23:15:06
     *
     * @param strDate 日期字符串
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDefaultDatePattern());
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     */
    public static Date parse(String strDate, String pattern) {
        Date date = null;
        if (strDate == null || "".equals(strDate) || pattern == null) {
        } else {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            try {
                date = df.parse(strDate);
            } catch (ParseException e) {
                logger.log(Level.WARNING, "parse error:" + e.getMessage(), e);
            }
        }
        return date;
    }

    /**
     * 在日期上增加数个整年
     *
     * @param date 日期
     * @param n 要增加的月数
     */
    public static Date addYear(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, n);
        System.out.println(cal.getTime());
        return cal.getTime();
    }

    /**
     * 在日期上增加数个整月
     *
     * @param date 日期
     * @param n 要增加的月数
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加天数
     *
     * @param date 日期
     * @param n 要增加的天数
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    /**
     * 获取距现在某一小时的时刻
     *
     * @param format 格式化时间的格式
     * @param h 距现在的小时 例如：h=-1为上一个小时，h=1为下一个小时
     */
    public static String getpreHour(String format, int h) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date();
        date.setTime(date.getTime() + h * 60 * 60 * 1000);
        return sdf.format(date);
    }

    /**
     * 获取默认时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_DEFAULT);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取完整的时间格式 yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String getTimeFullString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取日期年份
     *
     * @param date 日期
     */
    public static String getYear(Date date) {
        return format(date).substring(0, 4);
    }

    /**
     * 功能描述：返回月
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功能描述：返回日
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能描述：返回小
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫
     *
     * @param date 日期
     * @return 返回毫
     */
    public static long getMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 按默认格式的字符串距离今天的天数
     *
     * @param date 日期字符串
     */
    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 按用户格式字符串距离今天的天数
     *
     * @param date 日期字符串
     * @param format 日期格式
     */
    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 将 时间 转化成 改时间所属的 上一个批次时间
     */
    public static String parseDateToLastBatchTime(Date time) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(time);
        cl.add(Calendar.MINUTE, -10);
        cl.add(Calendar.MINUTE, 10 - cl.get(Calendar.MINUTE) % 10);
        time = cl.getTime();
        return format(time, FORMAT_SHORT_WITH_MIN_NO_MID_LINE);
    }

    /**
     * 将 时间 转化成 改时间所属的 批次时间
     */
    public static String parseDateToBatchTime(Date time) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(time);
        cl.add(Calendar.MINUTE, 10 - cl.get(Calendar.MINUTE) % 10);
        time = cl.getTime();
        return format(time, FORMAT_SHORT_WITH_MIN_NO_MID_LINE);
    }

    /**
     * 将 时间 转化成 改时间所属的 上一个包号
     */
    public static String parseDateToLastFileNum(Date time) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(time);
        cl.add(Calendar.MINUTE, -10);
        int hours = cl.get(Calendar.HOUR_OF_DAY);
        int mins = cl.get(Calendar.MINUTE);
        int result = 6 * hours + mins / 10 + 1;
        if (result == 0) {
            result = 144;
        }
        return String.format("%03d", result);
    }

    /**
     * 将 时间 转化成 改时间所属的 包号
     */
    public static String parseDateToFileNum(Date time) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(time);
        int hours = cl.get(Calendar.HOUR_OF_DAY);
        int mins = cl.get(Calendar.MINUTE);
        int result = 6 * hours + mins / 10 + 1;
        if (result == 0) {
            result = 144;
        }
        return String.format("%03d", result);
    }

    /**
     * 将 时间 转化成 改时间所属的 包号
     */
    public static String parseDateToFileNum(Date time, int mode) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(time);
        int hours = cl.get(Calendar.HOUR_OF_DAY);
        int mins = cl.get(Calendar.MINUTE);
        int result = (60 / mode) * hours + mins / mode + 1;
        if (result == 0) {
            result = 24 * 60 / mode;
        }
        return String.format("%03d", result);
    }

    /**
     * 将 批次时间 转化为 包号
     */
    public static String parseBatchTimeToFileNum(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(FORMAT_SHORT_WITH_MIN_NO_MID_LINE);
            Date a;
            a = format.parse(time);
            Calendar cl = Calendar.getInstance();
            cl.setTime(a);
            int hours = cl.get(Calendar.HOUR_OF_DAY);
            int mins = cl.get(Calendar.MINUTE);
            int result = 6 * hours + mins / 10;
            if (result == 0) {
                result = 144;
            }
            return String.format("%03d", result);
        } catch (ParseException e) {
            logger.log(Level.WARNING, "parseBatchTimeToFileNum error:" + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 将包号和日期转化为批次时间
     */
    public static Date parseFileNumToDate(String date, String packageNo, String dateFormat) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            int packageNoInt = Integer.parseInt(packageNo);
            int hours = packageNoInt / 6;
            int mins = packageNoInt % 6 * 10;

            Date time;
            time = format.parse(date);
            Calendar cl = Calendar.getInstance();
            cl.setTime(time);
            cl.set(Calendar.HOUR_OF_DAY, hours);
            cl.set(Calendar.MINUTE, mins);

            if (packageNoInt == 0) {
                cl.add(Calendar.DATE, 1);
            }
            return time;
        } catch (ParseException e) {
            logger.log(Level.WARNING, "parseFileNumToDate error" + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据本次批次时间获得下一批次时间
     */
    public static String getNextBatchTimeByBatchTime(String batchTime) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(FORMAT_SHORT_WITH_MIN_NO_MID_LINE);
            Date date = format.parse(batchTime);
            Calendar cl = Calendar.getInstance();
            cl.setTime(date);
            cl.add(Calendar.MINUTE, 10);
            date = cl.getTime();
            return format(date, FORMAT_SHORT_WITH_MIN_NO_MID_LINE);
        } catch (ParseException e) {
            logger.log(Level.WARNING, "getNextBatchTimeByBatchTime error:" + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据本次批次时间获得下一批次时间
     *
     * @param timeSize 粒度间隔
     */
    public static String getNextBatchTimeByBatchTime(String batchTime, int timeSize) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(FORMAT_SHORT_WITH_MIN_NO_MID_LINE);
            Date date = format.parse(batchTime);
            Calendar cl = Calendar.getInstance();
            cl.setTime(date);
            cl.add(Calendar.MINUTE, timeSize);
            date = cl.getTime();
            return format(date, FORMAT_SHORT_WITH_MIN_NO_MID_LINE);
        } catch (ParseException e) {
            logger.log(Level.WARNING, "getNextBatchTimeByBatchTime error:" + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据本次批次时间获得上一批次时间
     */
    public static String getLastBatchTimeByBatchTime(String batchTime) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(FORMAT_SHORT_WITH_MIN_NO_MID_LINE);
            Date date = format.parse(batchTime);
            Calendar cl = Calendar.getInstance();
            cl.setTime(date);
            cl.add(Calendar.MINUTE, -10);
            date = cl.getTime();
            return format(date, FORMAT_SHORT_WITH_MIN_NO_MID_LINE);
        } catch (ParseException e) {
            logger.log(Level.WARNING, "getLastBatchTimeByBatchTime error:" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 针对包号对压缩包的日期做特殊处理-主要是跨天包 201401110000 改为 201401102400
     */
    public static String getChangeDateForCrossDay(String batchTime2) {
        String date = "";
        if (batchTime2.endsWith("0000")) {
            date = DateUtil.getLastBatchTimeByBatchTime(batchTime2);
            date = date.replace("2350", "2400");
        } else {
            date = batchTime2;
        }
        return date;
    }

    /**
     * 在日期上增加数秒
     */
    public static Date addSecond(String date, int n) {
        Date time = parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.SECOND, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加数分
     */
    public static Date addMin(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加数分
     */
    public static Date addMin(String date, String format, int n) {
        Date time = parse(date, format);
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.MINUTE, n);
        return cal.getTime();
    }

    /**
     * 计算两个时间之间的小时差
     *
     * @return t1 - t2的小时差
     */
    public static int timeReduceHour(Date t1, Date t2) {
        long reduce = t1.getTime() - t2.getTime();
        return (int) (reduce / 1000 / 60 / 60);

    }

    public static int timeReduceDay(Date start, Date end) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(start);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(end);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
    }

    /***
     * 计算开始到结束时间内的月份的差列表 输入: dateStart=201601 dateEnd=201612 输出: default:201601
     */
    public static List<String> monthCalculate(String dateStart, String dateEnd) {
        return monthCalculate(dateStart, dateEnd, DateUtil.FORMAT_SHORT_WITH_MONTH_NO_MID_LINE);
    }

    /***
     * 计算开始到结束时间内的月份的差列表 输入: dateStart=201601 dateEnd=201612
     * returnFormat=yyyy-MM-dd 输出:2016-01
     */
    public static List<String> monthCalculate(String dateStart, String dateEnd, String returnFormat) {

        List<String> monthList = new LinkedList<String>();
        /***
         * 时间换算，输出开始月份的第一天和截止月份的最后一天
         */
        Calendar startDate = GregorianCalendar.getInstance();
        startDate.set(GregorianCalendar.YEAR, Integer.valueOf(dateStart.substring(0, 4)));
        startDate.set(GregorianCalendar.MONTH, Integer.valueOf(dateStart.substring(4, 6)) - 1);
        monthList.add(DateUtil.format(startDate.getTime(), returnFormat));// 开始的月份加入

        Calendar endDate = GregorianCalendar.getInstance();
        endDate.set(GregorianCalendar.YEAR, Integer.valueOf(dateEnd.substring(0, 4)));
        endDate.set(GregorianCalendar.MONTH, Integer.valueOf(dateEnd.substring(4, 6)) - 1);
        endDate.set(GregorianCalendar.DAY_OF_MONTH, 1);
        while (startDate.getTime().getTime() < endDate.getTime().getTime()) {
            startDate.add(GregorianCalendar.MONTH, 1);
            monthList.add(DateUtil.format(startDate.getTime(), returnFormat));// 中间的月份加入
        }
        return monthList;
    }

    /***
     * 计算开始到结尾日期间的天差列表 dateStart=201601 dateEnd=201612 输出: default:20160101
     */
    public static List<String> dayCalculate(String dateStart, String dateEnd) {
        return dayCalculate(dateStart, dateEnd, DateUtil.FORMAT_SHORT_WITH_NO_MID_LINE);
    }

    /***
     * 计算开始到结尾日期间的天差列表 dateStart=201601 dateEnd=201612 returnFormat=yyyy-MM-dd
     * 输出:2016-01
     */
    public static List<String> dayCalculate(String dateStart, String dateEnd, String returnFormat) {
        List<String> dayList = new LinkedList<String>();
        /***
         * 时间换算，输出开始月份的第一天和截止月份的最后一天
         */
        Calendar startDate = GregorianCalendar.getInstance();
        startDate.set(GregorianCalendar.YEAR, Integer.valueOf(dateStart.substring(0, 4)));
        startDate.set(GregorianCalendar.MONTH, Integer.valueOf(dateStart.substring(4, 6)) - 1);
        startDate.set(GregorianCalendar.DATE, 1);

        Calendar endDate = GregorianCalendar.getInstance();
        endDate.set(GregorianCalendar.YEAR, Integer.valueOf(dateEnd.substring(0, 4)));
        endDate.set(GregorianCalendar.MONTH, Integer.valueOf(dateEnd.substring(4, 6)));// 定位到下月
        endDate.set(GregorianCalendar.DATE, 1);
        endDate.add(GregorianCalendar.DAY_OF_MONTH, -1);// 下月往前推一天,即为本月的最后一天
        while ((startDate.getTime().getTime() <= endDate.getTime().getTime())) {
            dayList.add(
                    DateUtil.format(startDate.getTime(), DateUtil.FORMAT_SHORT_WITH_NO_MID_LINE));// 中间的月份日期加入
            startDate.add(GregorianCalendar.DAY_OF_MONTH, 1);
        }
        return dayList;
    }

    /**
     * 将时间戳转化为日期时间格式yyyy-MM-DD HH:mm:ss
     */
    public static Date getDateByLong(long timeStr) throws ParseException {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sd.parse(sd.format(new Date(timeStr)));
    }

    /**
     * 判断时间是否在时间段内
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        return nowTime.getTime() >= beginTime.getTime() && nowTime.getTime() <= endTime.getTime();
    }

    public static void main(String[] args) {
        // List<String> dayCalculate = DateUtil.dayCalculate("201606",
        // "201606");
        // for (String s : dayCalculate) {
        // System.out.println(s);
        // }
        // try {
        // System.out.println(getDateByLong(150457689257l));
        // } catch (ParseException e) {
        // e.printStackTrace();
        // }
        /*SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sd.format(addYear(new Date(), 1)));*/

        DateUtil.getNowString("yyyy-MM-dd");


    }


}
