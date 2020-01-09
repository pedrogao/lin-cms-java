package com.lin.cms.exception;

import com.lin.cms.beans.Code;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class FailedException extends HttpException {

    @Getter
    protected int code = Code.FAIL.getCode();

    @Getter
    protected int httpCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public FailedException() {
        super(Code.FAIL.getDescription());
    }

    public FailedException(String message) {
        super(message);
    }

    public FailedException(int code) {
        super(Code.FAIL.getDescription());
        this.code = code;
    }

    public FailedException(String message, int code) {
        super(message);
        this.code = code;
    }
}
