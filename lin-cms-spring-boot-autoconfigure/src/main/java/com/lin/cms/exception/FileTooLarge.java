package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Data;
import com.lin.cms.core.enums.HttpStatus;

@Data
public class FileTooLarge extends HttpException {

    private String msg = ErrorCode.FILE_TOO_LARGE.getDescription();
    private Integer errorCode = ErrorCode.FILE_TOO_LARGE.getCode();
    private Integer httpCode = HttpStatus.PAYLOAD_TOO_LARGE.value();

    public FileTooLarge(String msg) {
        this.msg = msg;
    }

    public FileTooLarge() {
    }
}
