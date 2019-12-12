package com.lin.cms.utils;

import com.lin.cms.exception.ApiException;
import com.lin.cms.response.Created;
import com.lin.cms.response.Success;
import com.lin.cms.beans.ErrorCode;
import com.lin.cms.response.Result;
import lombok.extern.slf4j.Slf4j;


/**
 * 响应结果生成工具
 */
@Slf4j
public class ResultGenerator {

    public static Result generateResult(ApiException e) {
        return Result.builder()
                .msg(e.getMessage())
                .errorCode(e.getErrorCode())
                .url(RequestHelper.getRequestUrl())
                .build();
    }

    public static <T> Result<T> generateSuccessResult(T data) {
        Success success = new Success();
        return (Result<T>) Result.builder()
                .msg(data)
                .errorCode(success.getErrorCode())
                .url(RequestHelper.getRequestUrl())
                .build();
    }

    public static <T> Result<T> generateCreatedResult(T data) {
        Created created = new Created();
        return (Result<T>) Result.builder()
                .msg(data)
                .errorCode(created.getErrorCode())
                .url(RequestHelper.getRequestUrl())
                .build();
    }

    public static <T> Result<T> generateResult(ErrorCode errorCode, int httpCode) {
        return (Result<T>) Result.builder()
                .errorCode(errorCode.getCode())
                .msg(errorCode.getDescription())
                .url(RequestHelper.getRequestUrl())
                .build();
    }
}
