package com.lin.cms.core.exception;

import com.lin.cms.core.result.ErrCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Forbidden extends HttpException {
    private Object msg = ErrCode.FORBIDDEN.getDescription();
    private Integer errorCode = ErrCode.FORBIDDEN.getCode();
    private Integer httpCode = HttpStatus.FORBIDDEN.value();

    public Forbidden(String msg) {
        this.msg = msg;
    }

    public Forbidden() {
    }
}
