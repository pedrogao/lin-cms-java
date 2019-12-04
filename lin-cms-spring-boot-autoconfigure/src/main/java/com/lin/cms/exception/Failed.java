package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import com.lin.cms.core.enums.HttpStatus;
import lombok.Data;

@Data
public class Failed extends HttpException {
    private String msg = ErrorCode.FAIL.getDescription();
    private Integer errorCode = ErrorCode.FAIL.getCode();
    private Integer httpCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public Failed(String msg) {
        this.msg = msg;
    }
}
