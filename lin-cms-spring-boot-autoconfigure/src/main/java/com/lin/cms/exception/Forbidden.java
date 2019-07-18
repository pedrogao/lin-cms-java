package com.lin.cms.exception;

import com.lin.cms.beans.ErrCode;
import lombok.Data;
import com.lin.cms.core.enums.HttpStatus;

@Data
public class Forbidden extends HttpException {
    private String msg = ErrCode.FORBIDDEN.getDescription();
    private Integer errorCode = ErrCode.FORBIDDEN.getCode();
    private Integer httpCode = HttpStatus.FORBIDDEN.value();

    public Forbidden(String msg) {
        this.msg = msg;
    }

    public Forbidden() {
    }
}
