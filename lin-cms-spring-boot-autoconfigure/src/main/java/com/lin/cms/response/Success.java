package com.lin.cms.response;

import com.lin.cms.beans.ErrorCode;
import com.lin.cms.interfaces.BaseResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class Success implements BaseResponse {

    @Getter
    protected String message = ErrorCode.SUCCESS.getDescription();

    @Getter
    protected int errorCode = ErrorCode.SUCCESS.getCode();

    @Getter
    protected int httpCode = HttpStatus.OK.value();


    public Success(String message) {
        this.message = message;
    }

    public Success() {
    }
}
