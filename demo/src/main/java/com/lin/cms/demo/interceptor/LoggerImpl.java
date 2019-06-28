package com.lin.cms.demo.interceptor;

import com.lin.cms.demo.model.UserPO;
import com.lin.cms.interfaces.LoggerResolver;
import com.lin.cms.core.annotation.Logger;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.utils.BeanUtil;
import com.lin.cms.demo.service.LogService;
import com.lin.cms.demo.utils.LocalUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class LoggerImpl implements LoggerResolver {

    @Autowired
    private LogService logService;

    private static String REG_XP = "(?<=\\{)[^}]*(?=\\})";


    @Override
    public void handle(RouteMeta meta, Logger logger, HttpServletRequest request, HttpServletResponse response) {
        // parse template and extract properties from request,response and modelAndView
        String template = logger.template();
        UserPO user = LocalUser.getLocalUser(UserPO.class);
        template = this.parseTemplate(template, user, request, response);
        logService.createOneLog(template, meta, user, request, response);
    }

    private String parseTemplate(String template, UserPO user, HttpServletRequest request, HttpServletResponse response) {
        Pattern pattern = Pattern.compile(REG_XP);
        // 调用 get 方法
        Matcher m = pattern.matcher(template);
        while (m.find()) {
            String group = m.group();
            String property = this.extractProperty(group, user, request, response);
            template = template.replace("{" + group + "}", property);
        }
        return template;
    }

    private String extractProperty(String item, UserPO user, HttpServletRequest request, HttpServletResponse response) {
        int i = item.lastIndexOf('.');
        String obj = item.substring(0, i);
        String prop = item.substring(i + 1);
        switch (obj) {
            case "user":
                if (user == null) {
                    return "";
                }
                String val = BeanUtil.getValueByPropName(user, prop);
                return val;
            case "request":
                String val1 = BeanUtil.getValueByPropName(request, prop);
                return val1;
            case "response":
                String val2 = BeanUtil.getValueByPropName(response, prop);
                return val2;
            default:
                return "";
        }
    }
}
