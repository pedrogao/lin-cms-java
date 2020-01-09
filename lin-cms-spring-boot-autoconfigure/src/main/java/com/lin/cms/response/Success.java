package com.lin.cms.response;

import com.lin.cms.beans.Code;
import com.lin.cms.interfaces.BaseResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class Success implements BaseResponse {

    @Getter
    protected String message = Code.SUCCESS.getDescription();

    @Getter
    protected int code = Code.SUCCESS.getCode();

    @Getter
    protected int httpCode = HttpStatus.OK.value();


    public Success(String message) {
        this.message = message;
    }

    public Success() {
    }
}
