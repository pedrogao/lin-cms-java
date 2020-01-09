package com.lin.cms.exception;

import com.lin.cms.beans.Code;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 授权异常
 */
public class AuthenticationException extends HttpException {

    @Getter
    protected int httpCode = HttpStatus.UNAUTHORIZED.value();

    @Getter
    protected int code = Code.UN_AUTHENTICATION.getCode();

    public AuthenticationException() {
        super(Code.UN_AUTHENTICATION.getDescription());
    }

    public AuthenticationException(int code) {
        super(Code.UN_AUTHENTICATION.getDescription());
        this.code = code;
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, int code) {
        super(message);
        this.code = code;
    }
}
