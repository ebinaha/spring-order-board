package springjpa.order;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springjpa.order.filter.LogUriFilter;
import springjpa.order.filter.LoginCheckFilter;
import springjpa.order.interceptor.LogUriInterceptor;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //@Bean
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

    // @Bean에 LogUriFilter 필터가 등록되어 있을 떄 : 2개의 필터 통과 => 인터셉터 호출 (pre/post/after)
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 등록 후 순서(1번), 적용범위(모든 path에 대해 전부 적용), whitelist(css에 있는 전부, home 등 로그를 남기지 않을 path)
        registry.addInterceptor(new LogUriInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**","/");
    }

}