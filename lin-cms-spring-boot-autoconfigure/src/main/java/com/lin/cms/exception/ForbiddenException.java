package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends HttpException {

    @Getter
    protected int errorCode = ErrorCode.FORBIDDEN.getCode();

    @Getter
    protected int httpCode = HttpStatus.FORBIDDEN.value();

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ForbiddenException() {
        super(ErrorCode.FORBIDDEN.getDescription());
    }
}
