package com.lin.cms.core.utils;

import com.lin.cms.core.exception.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Transformer {

    public static Map exception2Map(Exception ex, HttpServletRequest request) {
        Map res = new HashMap<String, Object>();
        Class<?> clazz = ex.getClass();
        Field errorCode = ReflectionUtils.findField(clazz, "errorCode");
        Field msg = ReflectionUtils.findField(clazz, "msg");
        errorCode.setAccessible(true);
        msg.setAccessible(true);
        try {
            Integer errCode = (Integer) errorCode.get(ex);
            Object msg1 = msg.get(ex);
            res.put("err_code", errCode);
            res.put("msg", msg1);
            res.put("url", request.getServletPath());
        } catch (IllegalAccessException e) {
            HttpException defaultEx = new HttpException();
            res.put("err_code", defaultEx.getErrorCode());
            res.put("msg", defaultEx.getMsg());
            res.put("url", request.getServletPath());
        }
        return res;
    }


    public static Integer getHttpCode(Exception ex) {
        Class<?> clazz = ex.getClass();
        Field httpCode = ReflectionUtils.findField(clazz, "httpCode");
        httpCode.setAccessible(true);
        try {
            Integer code = (Integer) httpCode.get(ex);
            return code;
        } catch (IllegalAccessException e) {
            return HttpStatus.OK.value();
        }
    }
}
