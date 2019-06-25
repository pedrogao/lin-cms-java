package com.lin.cms.demo.exception;

import com.lin.cms.core.exception.FileTooLarge;
import com.lin.cms.core.exception.HttpException;
import com.lin.cms.core.exception.Parameter;
import com.lin.cms.core.result.ErrCode;
import com.lin.cms.core.result.Result;
import com.lin.cms.core.result.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ExceptionHandler implements HandlerExceptionResolver {


    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        Result result = new Result();
        if (e instanceof HttpException) {
            log.info(e.getMessage());
            result = ResultGenerator.genResult((HttpException) e);
        } else if (e instanceof NoHandlerFoundException) {
            result.setHttpCode(HttpStatus.NOT_FOUND.value())
                    .setErrCode(ErrCode.NOT_FOUND.getCode())
                    .setUrl(request.getServletPath())
                    .setMsg("未找到请求的接口");
        } else if (e instanceof ServletException) {
            // method not support
            result.setHttpCode(HttpStatus.BAD_REQUEST.value())
                    .setErrCode(ErrCode.FAIL.getCode())
                    .setUrl(request.getServletPath())
                    .setMsg(e.getMessage());
        } else if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            BindingResult bindingResult = exception.getBindingResult();
            List<ObjectError> errors = bindingResult.getAllErrors();
            Map<String, Object> msg = new HashMap<>();
            errors.forEach(error -> {
                if (error instanceof FieldError) {
                    FieldError fieldError = (FieldError) error;
                    msg.put(fieldError.getField(), fieldError.getDefaultMessage());
                } else {
                    msg.put(error.getObjectName(), error.getDefaultMessage());
                }
            });
            Parameter parameter = new Parameter();
            parameter.setMsg(msg);
            result = ResultGenerator.genResult(parameter);
        } else if (e instanceof HttpMessageNotReadableException) {
            // org.springframework.http.converter.HttpMessageNotReadableException
            Parameter parameter = new Parameter();
            parameter.setMsg("请求体不可为空");
            result = ResultGenerator.genResult(parameter);
        } else if (e instanceof TypeMismatchException) {
            // org.springframework.beans.TypeMismatchException
            Parameter parameter = new Parameter();
            parameter.setMsg(e.getMessage());
            result = ResultGenerator.genResult(parameter);
        }
        if (e instanceof MaxUploadSizeExceededException) {
            // 记录错误信息
            // 文件太大
            FileTooLarge fileTooLarge = new FileTooLarge();
            fileTooLarge.setMsg("总体文件大小不能超过" + maxFileSize);
            result = ResultGenerator.genResult(fileTooLarge);
        } else {
            result.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setErrCode(ErrCode.INTERNAL_SERVER_ERROR.getCode())
                    .setUrl(request.getServletPath())
                    .setMsg("服务器内部错误，正在抓紧排查");

            String message;
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                        request.getRequestURI(),
                        handlerMethod.getBean().getClass().getName(),
                        handlerMethod.getMethod().getName(),
                        e.getMessage());
            } else {
                message = e.getMessage();
            }
            log.error(message, e);
        }
        ResultGenerator.writeResult(response, result);
        return new ModelAndView();
    }
}
