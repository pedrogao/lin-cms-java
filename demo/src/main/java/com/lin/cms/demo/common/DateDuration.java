package com.lin.cms.demo.common;

import java.util.Calendar;
import java.util.Date;

public class DateDuration {

    /**
     * 在原日期的基础上增加小时数
     */
    public static Date addHourOfDate(Date date, int i) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, i);
        return c.getTime();
    }

    /**
     * 在原日期的基础上增加天数
     */
    public static Date addDayOfDate(Date date, int i) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, i);
        return c.getTime();
    }
}
