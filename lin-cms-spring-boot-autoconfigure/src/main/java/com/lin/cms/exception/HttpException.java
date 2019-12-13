package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import com.lin.cms.interfaces.BaseResponse;
import org.springframework.http.HttpStatus;


public class HttpException extends RuntimeException implements BaseResponse {

    private static final long serialVersionUID = 2359767895161832954L;

    protected int httpCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    protected int errorCode = ErrorCode.INTERNAL_SERVER_ERROR.getCode();

    public HttpException() {
        super(ErrorCode.INTERNAL_SERVER_ERROR.getDescription());
    }

    public HttpException(String message) {
        super(message);
    }

    public HttpException(int errorCode) {
        super(ErrorCode.INTERNAL_SERVER_ERROR.getDescription());
        this.errorCode = errorCode;
    }

    public HttpException(int errorCode, int httpCode) {
        super(ErrorCode.INTERNAL_SERVER_ERROR.getDescription());
        this.httpCode = httpCode;
        this.errorCode = errorCode;
    }

    public HttpException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public HttpException(String message, int errorCode, int httpCode) {
        super(message);
        this.httpCode = httpCode;
        this.errorCode = errorCode;
    }

    public HttpException(Throwable cause, int errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public HttpException(Throwable cause, int errorCode, int httpCode) {
        super(cause);
        this.errorCode = errorCode;
        this.httpCode = httpCode;
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * for better performance
     *
     * @return Throwable
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public Throwable doFillInStackTrace() {
        return super.fillInStackTrace();
    }

    public int getHttpCode() {
        return this.httpCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
