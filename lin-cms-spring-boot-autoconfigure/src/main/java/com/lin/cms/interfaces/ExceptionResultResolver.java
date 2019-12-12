package com.lin.cms.interfaces;

import com.lin.cms.response.Result;

import javax.servlet.http.HttpServletResponse;

public interface ExceptionResultResolver {
    void rewrite(HttpServletResponse response, Result result, Exception e);
}
