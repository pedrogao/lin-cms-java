package auth;

import com.lin.cms.core.annotation.Logger;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.interfaces.LoggerResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoggerImpl implements LoggerResolver {


    private static String REG_XP = "(?<=\\{)[^}]*(?=\\})";


    @Override
    public void handle(RouteMeta meta, Logger logger, HttpServletRequest request, HttpServletResponse response) {
        // parse template and extract properties from request,response and modelAndView

    }
}
