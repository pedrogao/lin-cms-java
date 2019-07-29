package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Data;
import com.lin.cms.core.enums.HttpStatus;

@Data
public class AuthFailed extends HttpException {
    private String msg = ErrorCode.UNAUTHORIZED.getDescription();
    private Integer errorCode = ErrorCode.UNAUTHORIZED.getCode();
    private Integer httpCode = HttpStatus.UNAUTHORIZED.value();

    public AuthFailed(String msg) {
        this.msg = msg;
    }

    public AuthFailed() {
    }
}
