package kr.co.MyPick_server.config;

import kr.co.MyPick_server.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The WebConfig class is a configuration class that customizes the default Spring Web MVC settings.
 * It implements WebMvcConfigurer to define custom configurations such as CORS mappings.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Logger to log information related to web configurations
    Logger logger = LoggerFactory.getLogger(WebConfig.class);

    /**
     * Configures Cross-Origin Resource Sharing (CORS) settings.
     * This method allows requests from any origin to access all API endpoints, which is useful
     * for development or when the API needs to be accessed by clients from different domains.
     *
     * @param registry The CorsRegistry to define CORS configurations.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Allows CORS for all URL paths
                .allowedOriginPatterns("*"); // Allows requests from any origin
    }

    @Bean
    public Interceptor testInterceptor() {
        return new Interceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(testInterceptor())
        // .addPathPatterns("/api/**") // 이 인터셉터가 적용될 경로 패턴을 지정할 수 있습니다.
        // .excludePathPatterns("/api/login", "/api/register") // 이 인터셉터에서 제외할 경로 패턴을 지정할 수 있습니다.
        ;
    }
}
