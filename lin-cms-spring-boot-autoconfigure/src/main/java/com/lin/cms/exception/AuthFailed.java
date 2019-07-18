package com.lin.cms.exception;

import com.lin.cms.beans.ErrCode;
import lombok.Data;
import com.lin.cms.core.enums.HttpStatus;

@Data
public class AuthFailed extends HttpException {
    private String msg = ErrCode.UNAUTHORIZED.getDescription();
    private Integer errorCode = ErrCode.UNAUTHORIZED.getCode();
    private Integer httpCode = HttpStatus.UNAUTHORIZED.value();

    public AuthFailed(String msg) {
        this.msg = msg;
    }

    public AuthFailed() {
    }
}
