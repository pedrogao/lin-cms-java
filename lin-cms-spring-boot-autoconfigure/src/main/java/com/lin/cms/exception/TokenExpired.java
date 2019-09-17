package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Data;

@Data
public class TokenExpired extends HttpException {
    private String msg = ErrorCode.TOKEN_EXPIRED.getDescription();
    private Integer errorCode = ErrorCode.TOKEN_EXPIRED.getCode();
    private Integer httpCode = 422;

    public TokenExpired(String msg) {
        this.msg = msg;
    }

    public TokenExpired() {
    }
}
