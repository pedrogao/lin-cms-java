package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class TokenExpiredException extends HttpException {

    @Getter
    protected int errorCode = ErrorCode.TOKEN_EXPIRED.getCode();

    @Getter
    protected int httpCode = HttpStatus.UNAUTHORIZED.value();

    public TokenExpiredException(String message) {
        super(message);
    }

    public TokenExpiredException() {
        super(ErrorCode.TOKEN_EXPIRED.getDescription());
    }

    public TokenExpiredException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
