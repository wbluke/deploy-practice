package techcourse.myblog.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static techcourse.myblog.util.SessionKeys.USER;

@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = request.getSession();
        if (isNotLogin(httpSession)) {
            log.debug("send redirect to '/login' cause user does not login before");
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }

    private boolean isNotLogin(HttpSession httpSession) {
        return httpSession.getAttribute(USER) == null;
    }
}
