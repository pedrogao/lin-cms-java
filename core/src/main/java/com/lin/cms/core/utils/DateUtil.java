package com.lin.cms.core.utils;

import java.util.Date;

public class DateUtil {

    /**
     * 获得过期时间
     *
     * @param duration 延时时间
     * @return Date
     */
    public static Date getDurationDate(long duration) {
        long nowTime = new Date().getTime();
        long expireTime = nowTime + duration;
        return new Date(expireTime);
    }
}
