//package com.lin.cms.demo.controller;
//
//import com.lin.cms.core.exception.FileTooLarge;
//import com.lin.cms.core.result.Result;
//import com.lin.cms.core.result.ResultGenerator;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MaxUploadSizeExceededException;
//
///**
// * ControllerAdvice 处理的异常会在 configureHandlerExceptionResolvers 之后
// */
//@ControllerAdvice
//public class AdviceController {
//
//    @Value("${spring.servlet.multipart.max-file-size}")
//    private String maxFileSize;
//
//    @ResponseBody
//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    public Result handleException(MaxUploadSizeExceededException e) {
//        // 记录错误信息
//        // 文件太大
//        FileTooLarge fileTooLarge = new FileTooLarge();
//        fileTooLarge.setMsg("总体文件大小不能超过" + maxFileSize);
//        Result result = ResultGenerator.genResult(fileTooLarge);
//        return result;
//    }
//}
