package springjpa.order.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogUriInterceptor implements HandlerInterceptor {
    
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        // 각각에 부여해주어야 하기 때문에 변수로 선언하지 않음? : 각각의 request 별로 uuid를 가짐
        // uuid를 각각의 요청별로 다룰 수 있는 가장 안전한 방법 : attribute에 심어놓는 방법 
        // 각 요청별로 pre, post, after 단계를 거지기 때문에 세곳에서 모두 동일하게 접근할 수 있음 : get attribute
        request.setAttribute("uuid", uuid);

        log.info("(prehandle) request {}, {}, {}", uuid, requestURI, handler);
        // true = 다음 컨트롤러(핸들러) 호출, false = 종료
        return true; 
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // uuid를 문자로 가져왔기 때문에 문자열로 casting 필요 
        String uuid = (String)request.getAttribute("uuid");
        log.info("(posthandle) {}, {}", uuid, modelAndView);

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = (String)request.getAttribute("uuid");
        log.info("(aftercompletion) response {}, {}, {}", uuid, requestURI, ex);
        if (ex != null){
            log.error("afterCompletion error", ex); // error가 있을 시
        }

    }
}
