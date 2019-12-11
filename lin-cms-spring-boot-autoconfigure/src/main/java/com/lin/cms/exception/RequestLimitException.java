package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class RequestLimitException extends ApiException {

    @Getter
    protected int errorCode = ErrorCode.REQUEST_LIMIT.getCode();

    @Getter
    protected int httpCode = HttpStatus.TOO_MANY_REQUESTS.value();

    public RequestLimitException(String message) {
        super(message);
    }

    public RequestLimitException() {
        super(ErrorCode.REQUEST_LIMIT.getDescription());
    }
}
