package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 为什么不把每一种异常都定义为一个类？
 * 1. 麻烦，每有一个新的异常便要新建一个类
 * 2. 每个异常类都需要有自己的errorCode和message，以及诸多构造函数
 * 这些东西不能从父类直接继承，每次都需要重新写，不方便
 * 3. 不能为每个异常定义getter和setter，严重破坏了封装性
 * 4. 不好用
 *
 * @see HttpException 的生产器
 */
public final class ExceptionGenerator {

    public static HttpException createApiException() {
        return new HttpException();
    }

    public static HttpException createApiException(String message) {
        return new HttpException(message);
    }

    public static HttpException createApiException(String message, int errorCode, int httpCode) {
        return new HttpException(message, errorCode, httpCode);
    }

    public static HttpException createApiException(ErrorCode errorCode, int httpCode) {
        return new HttpException(errorCode.getDescription(), errorCode.getCode(), httpCode);
    }

    public static HttpException createNotFoundException() {
        return createApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND.value());
    }

    public static HttpException createNotParameterException() {
        return createApiException(ErrorCode.PARAMETER_ERROR, HttpStatus.BAD_REQUEST.value());
    }

    public static HttpException createFailedException() {
        return createApiException(ErrorCode.FAIL, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public static HttpException createFileExtensionException() {
        return createApiException(ErrorCode.FILE_EXTENSION, HttpStatus.NOT_ACCEPTABLE.value());
    }

    public static HttpException createFileTooLargeException() {
        return createApiException(ErrorCode.FILE_TOO_LARGE, HttpStatus.PAYLOAD_TOO_LARGE.value());
    }

    public static HttpException createFileTooManyException() {
        return createApiException(ErrorCode.FILE_TOO_MANY, HttpStatus.PAYLOAD_TOO_LARGE.value());
    }

    public static HttpException createForbiddenException() {
        return createApiException(ErrorCode.FORBIDDEN, HttpStatus.FORBIDDEN.value());
    }

    public static HttpException createMethodNotAllowedException() {
        return createApiException(ErrorCode.METHOD_NOT_ALLOWED, HttpStatus.METHOD_NOT_ALLOWED.value());
    }

    public static HttpException createRefreshFailedException() {
        return createApiException(ErrorCode.REFRESH_FAILED, HttpStatus.UNAUTHORIZED.value());
    }

    public static HttpException createRepeatException() {
        return createApiException(ErrorCode.REPEAT, HttpStatus.BAD_REQUEST.value());
    }

    public static HttpException createSuccessException() {
        return createApiException(ErrorCode.SUCCESS, HttpStatus.OK.value());
    }

    public static HttpException createTokenExpiredException() {
        return createApiException(ErrorCode.TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED.value());
    }

    public static HttpException createTokenInvalidException() {
        return createApiException(ErrorCode.TOKEN_INVALID, HttpStatus.UNAUTHORIZED.value());
    }
}
