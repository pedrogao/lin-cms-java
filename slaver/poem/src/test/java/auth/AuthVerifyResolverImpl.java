package auth;

import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.result.Result;
import com.lin.cms.interfaces.AuthVerifyResolver;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthVerifyResolverImpl implements AuthVerifyResolver {
    public boolean verifyLogin(HttpServletRequest request, HttpServletResponse response, Result result, RouteMeta meta) {
        return true;
    }

    public boolean verifyGroup(HttpServletRequest request, HttpServletResponse response, Result result, RouteMeta meta) {
        return true;
    }

    public boolean verifyAdmin(HttpServletRequest request, HttpServletResponse response, Result result, RouteMeta meta) {
        return false;
    }

    public boolean verifyRefresh(HttpServletRequest request, HttpServletResponse response, Result result, RouteMeta meta) {
        return true;
    }
}
