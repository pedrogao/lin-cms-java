package com.lin.cms.utils;

import com.lin.cms.exception.ApiException;
import com.lin.cms.response.Success;
import com.lin.cms.beans.ErrorCode;
import com.lin.cms.core.result.Result;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 响应结果生成工具
 */
@Slf4j
public class ResultGenerator {

    public static Result genResult(ApiException e) {
        String url = RequestHelper.getRequestUrl();
        return new Result()
                .setMsg(e.getMessage())
                .setErrorCode(e.getErrorCode())
                .setUrl(url)
                .setHttpCode(e.getHttpCode());
    }

    public static <T> Result<T> genSuccessResult(T data) {
        Success success = new Success();
        String url = RequestHelper.getRequestUrl();
        return new Result().setUrl(url)
                .setErrorCode(success.getErrorCode())
                .setMsg(data)
                .setHttpCode(success.getHttpCode());
    }

    public static <T> Result<T> genResult(ErrorCode errorCode, int httpCode) {
        String url = RequestHelper.getRequestUrl();
        return new Result().setUrl(url)
                .setErrorCode(errorCode.getCode())
                .setMsg(errorCode.getDescription())
                .setHttpCode(httpCode);
    }

    public static Result genFailResult(String message) {
        return new Result();
    }

    public static void writeResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(result.getHttpCode());
        try {
            //TODO  response.getWriter().write(JSON.toJSONString(result));
            response.getWriter().write("");
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    public static void genAndWriteResult(HttpServletResponse response, ApiException e) {
        Result result = genResult(e);
        writeResult(response, result);
    }
}
