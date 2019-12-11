package com.lin.cms.demo.common.exception;

import com.lin.cms.core.result.Result;
import com.lin.cms.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    /**
     * ApiException
     */
    @ExceptionHandler({ApiException.class})
    public Result processException(ApiException exception, HttpServletRequest request, HttpServletResponse response) {
        log.info("自定义异常处理-RuntimeException");
        Result result = new Result();
        result.setUrl(request.getServletPath());
        result.setMsg(exception.getMessage());
        result.setErrorCode(exception.getErrorCode());
        response.setStatus(exception.getHttpCode());
        return result;
    }

    /**
     * Exception
     */
    @ExceptionHandler({Exception.class})
    public Result processException(Exception exception) {
        log.info("自定义异常处理-Exception");
        Result result = new Result();
        result.setMsg("我怎么知道出现了什么错误");
        return result;
    }
}
