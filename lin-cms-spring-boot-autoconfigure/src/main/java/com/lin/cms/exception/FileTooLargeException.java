package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;


public class FileTooLargeException extends HttpException {

    @Getter
    protected int errorCode = ErrorCode.FILE_TOO_LARGE.getCode();

    @Getter
    protected int httpCode = HttpStatus.PAYLOAD_TOO_LARGE.value();


    public FileTooLargeException(String message) {
        super(message);
    }

    public FileTooLargeException() {
        super(ErrorCode.FILE_TOO_LARGE.getDescription());
    }

    public FileTooLargeException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
