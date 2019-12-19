package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class RepeatException extends HttpException {

    @Getter
    protected int errorCode = ErrorCode.REPEAT.getCode();

    @Getter
    protected int httpCode = HttpStatus.BAD_REQUEST.value();

    public RepeatException(String message) {
        super(message);
    }

    public RepeatException() {
        super(ErrorCode.REPEAT.getDescription());
    }

    public RepeatException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
