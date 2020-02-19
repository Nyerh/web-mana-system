package com.weblite.webmanasystem.constant;

import com.weblite.webmanasystem.utils.SnowFlakeIdWorker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ：Beatrice
 * @date ：Created in 2020/2/9 16:56
 * @Description:工具方法
 */
public class Common {

    public static Long workCode=0L;
    public static Long dataCode=0L;
    public static SnowFlakeIdWorker snowFlakeIdWorker = new SnowFlakeIdWorker(workCode, dataCode);


    //获取序列号作为id
    public static Long getFlowNum()
    {
        return snowFlakeIdWorker.nextId();
    }

    /**
     * 是否为空
     *
     * @param string
     * @return
     */
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.trim().equals("");
    }


    /**
     * string转date（错误返回NULL）
     *
     * @param string
     * @param simpleDateFormat
     * @return
     */
    public static Date str2Date(String string, SimpleDateFormat simpleDateFormat) {
        try {
            return simpleDateFormat.parse(string);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * date日期加减
     *
     * @param date
     * @param day
     * @return
     */
    public static Date computeDateByDay(Date date, int day) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, day);
            return calendar.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 生成随机字符
     *
     * @param length 字符长度
     * @param str    生成字符
     * @param num    生成数字
     * @return
     */
    public static String getRandomString(int length, boolean str, boolean num) {
        String res = null;
        if (str == true && num == true) {
            //定义一个字符串（A-Z，a-z，0-9）即62位；
            res = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        }
        if (str == false && num == true) {
            //定义一个字符串（0-9）即10位；
            res = "1234567890";
        }
        if (str == true && num == false) {
            //定义一个字符串（A-Z，a-z）即52位；
            res = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM";
        } else {
            return "";
        }
        //由Random生成随机数
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(res.length());
            //将产生的数字通过length次承载到sb中
            sb.append(res.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }

    /**
     * list去重（LinkHastSet）
     *
     * @param list 集合
     * @return
     */
    public static <T> List<T> removeDuplicate(List<T> list) {
        if (list == null || list.size() == 0) {
            return list;
        }
        LinkedHashSet<T> set = new LinkedHashSet<>(list.size());
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    /**
     * list去除NULL
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> removeNull(List<T> list) {
        if (list == null || list.size() == 0) {
            return list;
        }
        list.removeAll(Collections.singleton(null));
        return list;
    }

    /**
     * 获取当前日期的格式化字符串
     *
     * @param formatString 格式化字符串
     * @return
     */
    public static String getNowDateString(String formatString) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
            return simpleDateFormat.format(new Date());
        } catch (Exception e) {
            return null;
        }
    }
}
