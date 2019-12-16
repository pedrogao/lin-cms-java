package com.lin.cms.demo.common.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "lin.cms")
@PropertySource(value = "classpath:error-code.properties", encoding = "UTF-8")
public class ErrorCodeConfig {

    private static Map<Integer, String> errorMessage = new HashMap<>();

    public static String getErrorMessage(Integer errorCode) {
        return errorMessage.get(errorCode);
    }

    public Map<Integer, String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Map<Integer, String> errorMessage) {
        ErrorCodeConfig.errorMessage = errorMessage;
    }
}
