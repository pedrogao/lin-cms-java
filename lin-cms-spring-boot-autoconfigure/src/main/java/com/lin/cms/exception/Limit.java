package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Data;

@Data
public class Limit extends HttpException {
    private String msg = ErrorCode.LIMIT.getDescription();
    private Integer errorCode = ErrorCode.LIMIT.getCode();
    private Integer httpCode = 401;

    public Limit(String msg) {
        this.msg = msg;
    }

    public Limit() {
    }
}
