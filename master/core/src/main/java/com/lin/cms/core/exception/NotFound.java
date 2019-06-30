package com.lin.cms.core.exception;

import com.lin.cms.core.result.ErrCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

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
