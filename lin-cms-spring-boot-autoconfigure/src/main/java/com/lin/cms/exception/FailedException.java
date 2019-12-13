package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class FailedException extends HttpException {

    @Getter
    protected int errorCode = ErrorCode.FAIL.getCode();

    @Getter
    protected int httpCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public FailedException() {
        super(ErrorCode.FAIL.getDescription());
    }

    public FailedException(String message) {
        super(message);
    }
}
