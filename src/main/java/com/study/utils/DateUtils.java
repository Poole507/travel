package com.study.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static Double getBetweenDaysHours(String startTime,String endTime) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = dateFormat.parse(startTime);
        Date endDate = dateFormat.parse(endTime);
        Integer datePoor = getDatePoor(startDate,endDate);
        BigDecimal b = new BigDecimal((double)datePoor/60);
        Double hour = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();//第一个参数是保留小数的位数
        return hour;
    }

    public static Integer getDatePoor(Date startDate, Date endDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        Long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        Long mymint = diff/1000/60;
        int intValue = mymint.intValue();
        return intValue;
    }

}
