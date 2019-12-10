package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import com.lin.cms.core.enums.HttpStatus;
import lombok.Getter;

public class HttpException1 extends RuntimeException {

    private static final long serialVersionUID = 2359767895161832954L;

    @Getter
    private int httpCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    @Getter
    private int errorCode = ErrorCode.INTERNAL_SERVER_ERROR.getCode();


    public HttpException1(String message) {
        super(message);
    }

    public HttpException1(int errorCode) {
        super(ErrorCode.INTERNAL_SERVER_ERROR.getDescription());
        this.errorCode = errorCode;
    }

    public HttpException1(int errorCode, int httpCode) {
        super(ErrorCode.INTERNAL_SERVER_ERROR.getDescription());
        this.httpCode = httpCode;
        this.errorCode = errorCode;
    }

    public HttpException1(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public HttpException1(int errorCode, int httpCode, String message) {
        super(message);
        this.httpCode = httpCode;
        this.errorCode = errorCode;
    }

    public HttpException1(int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public HttpException1(int errorCode, int httpCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.httpCode = httpCode;
    }

    public HttpException1(String message, Throwable cause) {
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
}
