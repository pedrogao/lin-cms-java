package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class TokenInvalidException extends HttpException {

    @Getter
    protected int errorCode = ErrorCode.TOKEN_INVALID.getCode();

    @Getter
    protected int httpCode = HttpStatus.UNAUTHORIZED.value();

    public TokenInvalidException(String message) {
        super(message);
    }

    public TokenInvalidException() {
        super(ErrorCode.TOKEN_INVALID.getDescription());
    }
}
