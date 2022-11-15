package springjpa.order.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import springjpa.order.controller.session.MemberSession;
import springjpa.order.controller.session.SessionConst;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {
    private static final String[] whitelists = {"/", "/members/new", "/login", "/css/*", "/logout" };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        String requestURI = httpRequest.getRequestURI();

        try {
            log.info("{ } 에 인증여부 확인 시작", requestURI);
            if (isLoginCheckPath(requestURI)) {
                if (session == null || (MemberSession)session.getAttribute(SessionConst.MEMBER_SESSION) == null){
                    log.info("{ } 에 미인증 요청 들어옴", requestURI);
                    httpResponse.sendRedirect("/login");    // 로그인 화면으로 보내기 위함  => 수정 필요
                    return; // 미인증 요청자는 다음(chain 필터, servlet)으로 진행하지 않고 종료
                }

            }

            chain.doFilter(request, response); // 인증 받았거나, 화이트리스트 URI에 접속했거나, 다음 체인으로 넘어감
        } catch (Exception e){
            throw e;
        } finally {
            log.info("인증여부 확인 종료");
        }

        // loginId, username 정보 : MemberSession으로 casting
//        MemberSession member = (MemberSession)session.getAttribute(SessionConst.MEMBER_SESSION);
    }

    /**
     * 화이트리스트의 경우 인증체크 하지 않기 위해 체크하는 메소드
     * @param requestUri
     * @return
     */
    private boolean isLoginCheckPath(String requestUri){
        return !PatternMatchUtils.simpleMatch(whitelists, requestUri);
    }

}
