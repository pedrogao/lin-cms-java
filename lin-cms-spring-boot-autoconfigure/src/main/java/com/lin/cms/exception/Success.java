package com.lin.cms.exception;

import com.lin.cms.beans.ErrCode;
import lombok.Data;
import com.lin.cms.core.enums.HttpStatus;

@Data
public class Success extends HttpException {
    private String msg = ErrCode.SUCCESS.getDescription();
    private Integer errorCode = ErrCode.SUCCESS.getCode();
    private Integer httpCode = HttpStatus.OK.value();

}
