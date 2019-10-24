package com.lin.cms.demo.exception;

import com.lin.cms.core.result.Result;
import com.lin.cms.interfaces.ExceptionResultResolver;
import com.lin.cms.utils.ResultGenerator;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;

/**
 * 如出现了异常，通用的返回结果为
 * {
 * "url": "",
 * "error_code": 10000,
 * "msg": "^^^"
 * }
 * 你可以在此处拿到response和result修改返回后的结果
 * 默认有实现，如需使用自定义，可在此处修改逻辑，然后打上 @Component 注解
 */
@Slf4j
public class ExceptionResultImpl implements ExceptionResultResolver {

    @Override
    public void rewrite(HttpServletResponse response, Result result, Exception e) {
        log.info("工作了。。。。。");
        ResultGenerator.writeResult(response, result);
    }
}
