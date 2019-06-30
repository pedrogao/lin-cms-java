package com.lin.cms.interfaces;

import com.lin.cms.core.annotation.Logger;
import com.lin.cms.core.annotation.RouteMeta;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoggerResolver {
    void handle(RouteMeta meta, Logger logger, HttpServletRequest request, HttpServletResponse response);
}
