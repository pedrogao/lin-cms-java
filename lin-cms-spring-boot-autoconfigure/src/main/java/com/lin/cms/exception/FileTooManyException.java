package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class FileTooManyException extends HttpException {

    @Getter
    protected int errorCode = ErrorCode.FILE_TOO_MANY.getCode();

    @Getter
    protected int httpCode = HttpStatus.PAYLOAD_TOO_LARGE.value();


    public FileTooManyException(String message) {
        super(message);
    }

    public FileTooManyException() {
        super(ErrorCode.FILE_TOO_MANY.getDescription());
    }

    public FileTooManyException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
