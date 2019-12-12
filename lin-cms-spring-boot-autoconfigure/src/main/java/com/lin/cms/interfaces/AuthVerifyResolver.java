package com.lin.cms.interfaces;

import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.response.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthVerifyResolver {

    boolean verifyLogin(HttpServletRequest request, HttpServletResponse response, Result result, RouteMeta meta);

    boolean verifyGroup(HttpServletRequest request, HttpServletResponse response, Result result, RouteMeta meta);

    boolean verifyAdmin(HttpServletRequest request, HttpServletResponse response, Result result, RouteMeta meta);

    boolean verifyRefresh(HttpServletRequest request, HttpServletResponse response, Result result, RouteMeta meta);
}
