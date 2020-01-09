package com.lin.cms.demo.common.utils;

import com.lin.cms.demo.vo.UnifyResponseVO;
import com.lin.cms.exception.HttpException;
import com.lin.cms.response.Created;
import com.lin.cms.response.Success;
import com.lin.cms.beans.Code;
import com.lin.cms.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * 响应结果生成工具
 */
@Slf4j
public class ResponseUtil {

    public static UnifyResponseVO generateUnifyResponse(HttpException e) {
        return UnifyResponseVO.builder()
                .message(e.getMessage())
                .code(e.getCode())
                .request(RequestUtil.getSimpleRequest())
                .build();
    }

    public static <T> UnifyResponseVO<T> generateSuccessResponse(T data) {
        Success success = new Success();
        return (UnifyResponseVO<T>) UnifyResponseVO.builder()
                .message(data)
                .code(success.getCode())
                .request(RequestUtil.getSimpleRequest())
                .build();
    }

    public static <T> UnifyResponseVO<T> generateUnifyResponse(int errorCode) {
        return (UnifyResponseVO<T>) UnifyResponseVO.builder()
                .code(errorCode)
                .request(RequestUtil.getSimpleRequest())
                .build();
    }

    public static <T> UnifyResponseVO<T> generateCreatedResponse(T data) {
        Created created = new Created();
        return (UnifyResponseVO<T>) UnifyResponseVO.builder()
                .message(data)
                .code(created.getCode())
                .request(RequestUtil.getSimpleRequest())
                .build();
    }

    public static <T> UnifyResponseVO<T> generateUnifyResponse(Code code, int httpCode) {
        return (UnifyResponseVO<T>) UnifyResponseVO.builder()
                .code(code.getCode())
                .message(code.getDescription())
                .request(RequestUtil.getSimpleRequest())
                .build();
    }
}
