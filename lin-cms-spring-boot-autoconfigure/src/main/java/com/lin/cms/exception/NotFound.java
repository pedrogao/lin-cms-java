package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Data;
import com.lin.cms.core.enums.HttpStatus;

@Data
public class NotFound extends HttpException {
    private String msg = ErrorCode.NOT_FOUND.getDescription();
    private Integer errorCode = ErrorCode.NOT_FOUND.getCode();
    private Integer httpCode = HttpStatus.NOT_FOUND.value();

    public NotFound(String msg) {
        this.msg = msg;
    }

    public NotFound() {
    }
}
