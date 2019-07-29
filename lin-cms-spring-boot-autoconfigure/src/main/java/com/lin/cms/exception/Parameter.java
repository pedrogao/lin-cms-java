package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Data;
import com.lin.cms.core.enums.HttpStatus;

@Data
public class Parameter extends HttpException {
    private Object msg = ErrorCode.PARAMETER_ERROR.getDescription();

    private Integer errorCode = ErrorCode.PARAMETER_ERROR.getCode();

    private Integer httpCode = HttpStatus.BAD_REQUEST.value();

    public Parameter(String msg) {
        this.msg = msg;
    }

    public Parameter() {
    }
}
