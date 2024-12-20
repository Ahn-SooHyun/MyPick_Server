package kr.co.MyPick_server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class to customize the default Spring Web MVC settings.
 * Implements WebMvcConfigurer to provide custom configurations such as CORS mappings.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    Logger logger = LoggerFactory.getLogger(WebConfig.class);

    /**
     * Overrides the default method to add custom CORS mappings.
     *
     * @param registry the CorsRegistry to configure CORS settings
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 모든 경로에 대해 CORS를 허용
                .allowedOriginPatterns("*");
    }


}
