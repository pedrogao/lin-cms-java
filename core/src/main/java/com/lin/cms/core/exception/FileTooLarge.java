package com.lin.cms.core.exception;

import com.lin.cms.core.result.ErrCode;
import lombok.Data;
import com.lin.cms.core.enums.HttpStatus;

@Data
public class FileTooLarge extends HttpException {

    private Object msg = ErrCode.FILE_TOO_LARGE.getDescription();
    private Integer errorCode = ErrCode.FILE_TOO_LARGE.getCode();
    private Integer httpCode = HttpStatus.PAYLOAD_TOO_LARGE.value();

    public FileTooLarge(String msg) {
        this.msg = msg;
    }

    public FileTooLarge() {
    }
}
