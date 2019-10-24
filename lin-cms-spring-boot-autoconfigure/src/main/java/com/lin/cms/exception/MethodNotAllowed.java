package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Data;

@Data
public class MethodNotAllowed extends HttpException {
    private String msg = ErrorCode.METHOD_NOT_ALLOWED.getDescription();
    private Integer errorCode = ErrorCode.METHOD_NOT_ALLOWED.getCode();
    private Integer httpCode = 405;

    public MethodNotAllowed(String msg) {
        this.msg = msg;
    }

    public MethodNotAllowed() {
    }
}
