package com.lin.cms.demo.common.aop;

import com.google.common.base.Strings;
import com.lin.cms.demo.common.configure.ErrorCodeConfig;
import com.lin.cms.demo.vo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class ResultAspect {


    @Pointcut("execution(public * com.lin.cms.demo.controller..*.*(..))")
    public void handlePlaceholder() {
    }

    @AfterReturning(returning = "ret", pointcut = "handlePlaceholder()")
    public void doAfterReturning(Object ret) throws Throwable {
        if (ret instanceof CommonResult) {
            CommonResult result = (CommonResult) ret;
            int errorCode = result.getErrorCode();
            String errorMessage = ErrorCodeConfig.getErrorMessage(errorCode);
            if (!Strings.isNullOrEmpty(errorMessage)) {
                result.setMsg(errorMessage);
            }
        }
    }
}
