package com.lin.cms.core.utils;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilTest {

    @Test
    public void getDurationDate() {
        Date durationDate = DateUtil.getDurationDate(10);
        assertTrue(durationDate.getTime() > new Date().getTime());
    }
}