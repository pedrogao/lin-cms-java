package com.lin.cms.core.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 统一API响应结果封装
 */
public class Result<T> {
    @JSONField(name = "err_code")
    private int errCode;

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

    public int getErrCode() {
        return errCode;
    }

    public Result setErrCode(int errCode) {
        this.errCode = errCode;
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
