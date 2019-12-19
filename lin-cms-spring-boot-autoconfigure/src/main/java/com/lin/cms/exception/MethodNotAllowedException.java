package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class MethodNotAllowedException extends HttpException {

    @Getter
    protected int errorCode = ErrorCode.METHOD_NOT_ALLOWED.getCode();

    @Getter
    protected int httpCode = HttpStatus.METHOD_NOT_ALLOWED.value();

    public MethodNotAllowedException(String message) {
        super(message);
    }

    public MethodNotAllowedException() {
        super(ErrorCode.METHOD_NOT_ALLOWED.getDescription());
    }

    public MethodNotAllowedException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
