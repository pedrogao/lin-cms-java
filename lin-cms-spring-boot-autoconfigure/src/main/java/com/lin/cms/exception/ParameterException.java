package com.lin.cms.exception;

import com.lin.cms.beans.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ParameterException extends HttpException {

    @Getter
    protected int httpCode = HttpStatus.BAD_REQUEST.value();

    @Getter
    protected int errorCode = ErrorCode.PARAMETER_ERROR.getCode();

    private Map<String, Object> errors = new HashMap<>();

    public ParameterException() {
        super(ErrorCode.PARAMETER_ERROR.getDescription());
    }

    public ParameterException(String message) {
        super(message);
    }

    public ParameterException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ParameterException(Map<String, Object> errors) {
        this.errors = errors;
    }

    public ParameterException addError(String key, Object val) {
        this.errors.put(key, val);
        return this;
    }

    @Override
    public String getMessage() {
        if (errors.isEmpty())
            return super.getMessage();
        return errors.toString();
    }
}
