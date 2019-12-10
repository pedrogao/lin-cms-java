package com.lin.cms.beans;


/**
 * 错误码
 */
public enum ErrorCode {

    SUCCESS(0, "成功"),

    FAIL(9999, "失败"),

    UNAUTHORIZED(10000, "认证失败"),

    NOT_FOUND(10020, "资源不存在"),

    PARAMETER_ERROR(10030, "参数错误"),

    TOKEN_INVALID(10040, "令牌失效"),

    TOKEN_EXPIRED(10050, "令牌过期"),

    REPEAT(10060, "字段重复"),

    INTERNAL_SERVER_ERROR(999, "服务器内部错误"),

    FORBIDDEN(10070, "禁止操作"),

    METHOD_NOT_ALLOWED(10080, "请求方法不允许"),

    REFRESH_FAILED(10100, "刷新令牌获取失败"),

    FILE_TOO_LARGE(10110, "文件体积过大"),

    FILE_TOO_MANY(10120, "文件数量过多"),

    File_Extension(10130, "文件扩展名不符合规范"),

    LIMIT(10140, "请求过于频繁，请稍后重试");

    private int code;
    private String description;

    ErrorCode(int code, String description) {
        // HttpStatus
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
