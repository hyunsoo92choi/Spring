package com.eBayJP.kuromoji.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

/**
 * <pre>
 * com.eBayJP.kuromoji.config_WebConfiguration.java
 * </pre>
 * @date : 2019. 6. 13. 오후 3:28:01
 * @author : hychoi
 */
@Configuration
public class WebConfiguration {
	
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        
		PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setOneIndexedParameters(true);
        resolver.setMaxPageSize(10);
        argumentResolvers.add(resolver);
	}
}
