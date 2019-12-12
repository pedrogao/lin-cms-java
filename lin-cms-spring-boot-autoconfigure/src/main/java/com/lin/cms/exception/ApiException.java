package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import com.lin.cms.interfaces.BaseResponse;
import org.springframework.http.HttpStatus;


public class ApiException extends RuntimeException implements BaseResponse {

    private static final long serialVersionUID = 2359767895161832954L;

    protected int httpCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    protected int errorCode = ErrorCode.INTERNAL_SERVER_ERROR.getCode();

    public ApiException() {
        super(ErrorCode.INTERNAL_SERVER_ERROR.getDescription());
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(int errorCode) {
        super(ErrorCode.INTERNAL_SERVER_ERROR.getDescription());
        this.errorCode = errorCode;
    }

    public ApiException(int errorCode, int httpCode) {
        super(ErrorCode.INTERNAL_SERVER_ERROR.getDescription());
        this.httpCode = httpCode;
        this.errorCode = errorCode;
    }

    public ApiException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApiException(String message, int errorCode, int httpCode) {
        super(message);
        this.httpCode = httpCode;
        this.errorCode = errorCode;
    }

    public ApiException(Throwable cause, int errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ApiException(Throwable cause, int errorCode, int httpCode) {
        super(cause);
        this.errorCode = errorCode;
        this.httpCode = httpCode;
    }

    public ApiException(String message, Throwable cause) {
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
