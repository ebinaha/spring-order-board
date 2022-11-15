package springjpa.order.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;


@Slf4j // log에 찍어주는
public class LogUriFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("filter init");
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 요청이 들어온 uri와 각 요청을 구분할 수 있는 uuid  생성해서 이를 출력하고 다음 필터 체인으로 넘김
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String requestURI = httpRequest.getRequestURI();
        // 요청을 요청별로 식별하는 식별자 필요
        String uuid = String.valueOf(UUID.randomUUID());
                                // 포지션대로 들어가게 숫자 삽입해도 됨     ,로 구분 가능
        try {
            log.info("request from : {0} , {1} ", uuid, requestURI);
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("response from : {} , {} ", uuid, requestURI);
        }
//        log.info("request from : {0} , {1} ", uuid, requestURI);
//        chain.doFilter(request, response);
//        log.info("response from : {} , {} ", uuid, requestURI);


    }
    
    @Override
    public void destroy() {
        log.info("filter destroy");
    }

    
}
