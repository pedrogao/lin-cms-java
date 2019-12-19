package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class FileExtensionException extends HttpException {

    @Getter
    protected int errorCode = ErrorCode.FILE_EXTENSION.getCode();

    @Getter
    protected int httpCode = HttpStatus.NOT_ACCEPTABLE.value();

    public FileExtensionException(String message) {
        super(message);
    }

    public FileExtensionException() {
        super(ErrorCode.FILE_EXTENSION.getDescription());
    }

    public FileExtensionException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
