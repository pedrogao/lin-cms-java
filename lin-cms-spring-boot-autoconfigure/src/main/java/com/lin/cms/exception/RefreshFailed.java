package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Data;

@Data
public class RefreshFailed extends HttpException {
    private String msg = ErrorCode.REFRESH_FAILED.getDescription();
    private Integer errorCode = ErrorCode.REFRESH_FAILED.getCode();
    private Integer httpCode = 401;

    public RefreshFailed(String msg) {
        this.msg = msg;
    }

    public RefreshFailed() {
    }
}
