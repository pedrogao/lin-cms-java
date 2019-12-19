package com.lin.cms.demo.common.utils;

import com.lin.cms.demo.vo.CommonResult;
import com.lin.cms.exception.HttpException;
import com.lin.cms.response.Created;
import com.lin.cms.response.Success;
import com.lin.cms.beans.ErrorCode;
import com.lin.cms.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * 响应结果生成工具
 */
@Slf4j
public class ResultUtil {

    public static CommonResult generateResult(HttpException e) {
        return CommonResult.builder()
                .msg(e.getMessage())
                .errorCode(e.getErrorCode())
                .url(RequestUtil.getRequestUrl())
                .build();
    }

    public static <T> CommonResult<T> generateSuccessResult(T data) {
        Success success = new Success();
        return (CommonResult<T>) CommonResult.builder()
                .msg(data)
                .errorCode(success.getErrorCode())
                .url(RequestUtil.getRequestUrl())
                .build();
    }

    public static <T> CommonResult<T> generateResult(int errorCode) {
        return (CommonResult<T>) CommonResult.builder()
                .errorCode(errorCode)
                .url(RequestUtil.getRequestUrl())
                .build();
    }

    public static <T> CommonResult<T> generateCreatedResult(T data) {
        Created created = new Created();
        return (CommonResult<T>) CommonResult.builder()
                .msg(data)
                .errorCode(created.getErrorCode())
                .url(RequestUtil.getRequestUrl())
                .build();
    }

    public static <T> CommonResult<T> generateResult(ErrorCode errorCode, int httpCode) {
        return (CommonResult<T>) CommonResult.builder()
                .errorCode(errorCode.getCode())
                .msg(errorCode.getDescription())
                .url(RequestUtil.getRequestUrl())
                .build();
    }
}
