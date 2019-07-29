package com.lin.cms.exception;


import com.lin.cms.beans.ErrorCode;
import com.lin.cms.core.enums.HttpStatus;

public class HttpException extends RuntimeException {

    private String msg = ErrorCode.INTERNAL_SERVER_ERROR.getDescription();

    private Integer errorCode = ErrorCode.INTERNAL_SERVER_ERROR.getCode();

    private Integer httpCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    private String url;

    public HttpException(String msg, Integer errorCode, Integer httpCode) {
        this.msg = msg;
        this.errorCode = errorCode;
        this.httpCode = httpCode;
    }

    public HttpException(String msg) {
        this.msg = msg;
    }

    public HttpException(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public HttpException() {
    }


    public Object getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
