package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;


/**
 * 授权异常
 */
public class AuthorizationException extends HttpException {

    @Getter
    protected int httpCode = HttpStatus.UNAUTHORIZED.value();

    @Getter
    protected int errorCode = ErrorCode.UN_AUTHORIZATION.getCode();

    public AuthorizationException() {
        super(ErrorCode.UN_AUTHORIZATION.getDescription());
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
