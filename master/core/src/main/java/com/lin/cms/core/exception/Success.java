package com.lin.cms.core.exception;

import com.lin.cms.core.result.ErrCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Success extends HttpException {
    private Object msg = ErrCode.SUCCESS.getDescription();
    private Integer errorCode = ErrCode.SUCCESS.getCode();
    private Integer httpCode = HttpStatus.OK.value();

}
