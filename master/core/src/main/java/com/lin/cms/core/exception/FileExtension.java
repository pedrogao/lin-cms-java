package com.lin.cms.core.exception;

import com.lin.cms.core.result.ErrCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

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
