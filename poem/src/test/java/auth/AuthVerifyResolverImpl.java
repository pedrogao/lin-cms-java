package auth;

import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.interfaces.AuthorizeVerifyResolver;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthVerifyResolverImpl implements AuthorizeVerifyResolver {

    @Override
    public boolean handleLogin(HttpServletRequest request, HttpServletResponse response, RouteMeta meta) {
        return false;
    }

    @Override
    public boolean handleGroup(HttpServletRequest request, HttpServletResponse response, RouteMeta meta) {
        return false;
    }

    @Override
    public boolean handleAdmin(HttpServletRequest request, HttpServletResponse response, RouteMeta meta) {
        return false;
    }

    @Override
    public boolean handleRefresh(HttpServletRequest request, HttpServletResponse response, RouteMeta meta) {
        return false;
    }

    @Override
    public boolean handleNotHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return false;
    }
}
