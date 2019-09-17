package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Data;

@Data
public class TokenInvalid extends HttpException {
    private String msg = ErrorCode.TOKEN_INVALID.getDescription();
    private Integer errorCode = ErrorCode.TOKEN_INVALID.getCode();
    private Integer httpCode = 401;

    public TokenInvalid(String msg) {
        this.msg = msg;
    }

    public TokenInvalid() {
    }
}
