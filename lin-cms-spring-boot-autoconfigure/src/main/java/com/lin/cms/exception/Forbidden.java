package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Data;
import com.lin.cms.core.enums.HttpStatus;

@Data
public class Forbidden extends HttpException {
    private String msg = ErrorCode.FORBIDDEN.getDescription();
    private Integer errorCode = ErrorCode.FORBIDDEN.getCode();
    private Integer httpCode = HttpStatus.FORBIDDEN.value();

    public Forbidden(String msg) {
        this.msg = msg;
    }

    public Forbidden() {
    }
}
