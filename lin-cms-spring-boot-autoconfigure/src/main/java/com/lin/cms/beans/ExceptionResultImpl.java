package com.lin.cms.beans;

import com.lin.cms.core.result.Result;
import com.lin.cms.interfaces.ExceptionResultResolver;
import com.lin.cms.utils.ResultGenerator;

import javax.servlet.http.HttpServletResponse;

public class ExceptionResultImpl implements ExceptionResultResolver {

    @Override
    public void rewrite(HttpServletResponse response, Result result, Exception e) {
        ResultGenerator.writeResult(response, result);
    }
}
