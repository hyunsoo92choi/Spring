package com.eBayJP.kuromoji.common.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.eBayJP.kuromoji.common.security.JwtTokenInterceptor;

/**
 * <pre>
 * com.eBayJP.kuromoji.common.security.config_WebSecurityConfig.java
 * </pre>
 * @date : 2019. 6. 13
 * @author : hychoi
 */

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

	@Autowired
    private JwtTokenInterceptor jwtTokenInterceptor;
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenInterceptor)
        		.addPathPatterns("/ebayjp-ipadic/v1/**")
                .addPathPatterns("/ebayjp-dic/v1/**")
                .excludePathPatterns("/ebayjp/v1/users/**");
    }
}
