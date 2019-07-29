package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Data;
import com.lin.cms.core.enums.HttpStatus;

@Data
public class Success extends HttpException {
    private String msg = ErrorCode.SUCCESS.getDescription();
    private Integer errorCode = ErrorCode.SUCCESS.getCode();
    private Integer httpCode = HttpStatus.OK.value();

}
