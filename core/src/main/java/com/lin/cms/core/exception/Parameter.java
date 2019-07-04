package com.lin.cms.core.exception;

import com.lin.cms.core.result.ErrCode;
import lombok.Data;
import com.lin.cms.core.enums.HttpStatus;

@Data
public class Parameter extends HttpException {
    private Object msg = ErrCode.PARAMETER_ERROR.getDescription();

    private Integer errorCode = ErrCode.PARAMETER_ERROR.getCode();

    private Integer httpCode = HttpStatus.BAD_REQUEST.value();

    public Parameter(String msg) {
        this.msg = msg;
    }

    public Parameter() {
    }
}
