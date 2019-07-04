package com.lin.cms.demo.exception;

import com.lin.cms.core.result.Result;
import com.lin.cms.interfaces.ExceptionResultResolver;
import com.lin.cms.utils.ResultGenerator;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;

@Slf4j
public class ExceptionResultImpl implements ExceptionResultResolver {

    @Override
    public void rewrite(HttpServletResponse response, Result result, Exception e) {
        log.info("工作了。。。。。");
        ResultGenerator.writeResult(response, result);
    }
}
