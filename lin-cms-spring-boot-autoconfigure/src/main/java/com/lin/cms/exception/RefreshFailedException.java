package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class RefreshFailedException extends HttpException {

    @Getter
    protected int errorCode = ErrorCode.REFRESH_FAILED.getCode();

    @Getter
    protected int httpCode = HttpStatus.UNAUTHORIZED.value();

    public RefreshFailedException(String message) {
        super(message);
    }

    public RefreshFailedException() {
        super(ErrorCode.REFRESH_FAILED.getDescription());
    }

    public RefreshFailedException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
