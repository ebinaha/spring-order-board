package springjpa.order.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfig {
    @Bean
    // 필터들 간 순서를 조절하기 위해 Config에 @Bean을 등록하는 방식 택
    public FilterRegistrationBean LogUriFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        // 첫번째 필터로 LogUriFilter 등록
        filterRegistrationBean.setFilter(new LogUriFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");  // 모든 요청이 들어오는 uri에 대해서 다 적용
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean LoginCheckFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        // 두번째 필터로 LoginCheckFilter 등록
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");  // 모든 요청이 들어오는 uri에 대해서 다 적용 => 내부에 whitelist 통과 설정했기 때문에
        return filterRegistrationBean;

    }
}
