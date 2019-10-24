package com.lin.cms.core.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 统一API响应结果封装
 */
public class Result<T> {
    @JSONField(name = "error_code")
    private int errorCode;

    private String url;

    private T msg;

    @JSONField(serialize = false)
    private int httpCode;

    public int getHttpCode() {
        return httpCode;
    }

    public Result setHttpCode(int httpCode) {
        this.httpCode = httpCode;
        return this;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public Result setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Result setUrl(String url) {
        this.url = url;
        return this;
    }

    public T getMsg() {
        return msg;
    }

    public Result setMsg(T msg) {
        this.msg = msg;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
