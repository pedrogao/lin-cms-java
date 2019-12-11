package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class RepeatException extends ApiException {

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
}
