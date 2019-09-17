package com.lin.cms.demo.utils;

import java.util.Calendar;
import java.util.Date;

public class DateDuration {

    /**
     * 在原日期的基础上增加小时数
     *
     * @param date
     * @param i
     * @return      
     */
    public static Date addHourOfDate(Date date, int i) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, i);
        Date newDate = c.getTime();
        return newDate;
    }


    /**
     * 在原日期的基础上增加天数
     *
     * @param date
     * @param i
     * @return      
     */
    public static Date addDayOfDate(Date date, int i) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, i);
        Date newDate = c.getTime();
        return newDate;
    }
}
