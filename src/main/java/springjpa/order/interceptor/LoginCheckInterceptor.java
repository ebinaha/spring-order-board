package springjpa.order.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import springjpa.order.controller.session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    // prehandler 에서 체크하는게 효율적 : prehandle만 구현
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        // pre단계에서 끝나기 때문에 uuid 선언 안 함
        log.info("authority check start : {}", requestURI);
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.MEMBER_SESSION) == null) {
            log.info("unauthorized request");
            // 로그인 실패시 redirect
            response.sendRedirect("/login?redirectUrl="+requestURI);
            return false;   // 다음 핸들러로 넘어가지 않고 종료
        }

        return true;
    }
}
