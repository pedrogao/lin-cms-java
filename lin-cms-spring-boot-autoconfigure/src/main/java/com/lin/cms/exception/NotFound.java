package com.lin.cms.exception;

import com.lin.cms.beans.ErrCode;
import lombok.Data;
import com.lin.cms.core.enums.HttpStatus;

@Data
public class NotFound extends HttpException {
    private Object msg = ErrCode.NOT_FOUND.getDescription();
    private Integer errorCode = ErrCode.NOT_FOUND.getCode();
    private Integer httpCode = HttpStatus.NOT_FOUND.value();

    public NotFound(String msg) {
        this.msg = msg;
    }

    public NotFound() {
    }
}
