package com.lin.cms.exception;

import com.lin.cms.beans.ErrCode;
import lombok.Data;
import com.lin.cms.core.enums.HttpStatus;

@Data
public class FileExtension extends HttpException {
    private Object msg = ErrCode.File_Extension.getDescription();
    private Integer errorCode = ErrCode.File_Extension.getCode();
    private Integer httpCode = HttpStatus.NOT_ACCEPTABLE.value();

    public FileExtension(String msg) {
        this.msg = msg;
    }

    public FileExtension() {
    }
}
