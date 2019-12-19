package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 授权异常
 */
public class AuthenticationException extends HttpException {

    @Getter
    protected int httpCode = HttpStatus.UNAUTHORIZED.value();

    @Getter
    protected int errorCode = ErrorCode.UN_AUTHENTICATION.getCode();

    public AuthenticationException() {
        super(ErrorCode.UN_AUTHENTICATION.getDescription());
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
