package com.lin.cms.core.exception;

import com.lin.cms.core.result.ErrCode;
import lombok.Data;
import com.lin.cms.core.enums.HttpStatus;

@Data
public class AuthFailed extends HttpException {
    private Object msg = ErrCode.UNAUTHORIZED.getDescription();
    private Integer errorCode = ErrCode.UNAUTHORIZED.getCode();
    private Integer httpCode = HttpStatus.UNAUTHORIZED.value();

    public AuthFailed(String msg) {
        this.msg = msg;
    }

    public AuthFailed() {
    }
}
