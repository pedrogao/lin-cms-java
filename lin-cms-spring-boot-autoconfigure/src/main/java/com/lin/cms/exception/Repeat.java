package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Data;

@Data
public class Repeat extends HttpException {
    private String msg = ErrorCode.REPEAT.getDescription();
    private Integer errorCode = ErrorCode.REPEAT.getCode();
    private Integer httpCode = 400;

    public Repeat(String msg) {
        this.msg = msg;
    }

    public Repeat() {
    }
}
