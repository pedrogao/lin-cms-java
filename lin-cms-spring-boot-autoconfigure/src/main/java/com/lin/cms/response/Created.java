package com.lin.cms.response;

import com.lin.cms.beans.Code;
import com.lin.cms.interfaces.BaseResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class Created implements BaseResponse {

    @Getter
    protected String message = Code.CREATED.getDescription();

    @Getter
    protected int code = Code.CREATED.getCode();

    @Getter
    protected int httpCode = HttpStatus.CREATED.value();


    public Created(String message) {
        this.message = message;
    }

    public Created() {
    }
}
