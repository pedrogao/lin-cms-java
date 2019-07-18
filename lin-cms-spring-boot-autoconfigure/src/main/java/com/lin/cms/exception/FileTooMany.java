package com.lin.cms.exception;

import com.lin.cms.beans.ErrCode;
import lombok.Data;
import com.lin.cms.core.enums.HttpStatus;

@Data
public class FileTooMany extends HttpException {

    private String msg = ErrCode.FILE_TOO_MANY.getDescription();
    private Integer errorCode = ErrCode.FILE_TOO_MANY.getCode();
    private Integer httpCode = HttpStatus.PAYLOAD_TOO_LARGE.value();

    public FileTooMany(String msg) {
        this.msg = msg;
    }

    public FileTooMany() {
    }
}
