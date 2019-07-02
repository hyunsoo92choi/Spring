package com.eBayJP.kuromoji.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * <pre>
 * com.eBayJP.kuromoji.common.config_CommonConfig.java
 * </pre>
 * @date : 2019. 7. 2.
 * @author : hychoi
 */
//@Configuration
public class CommonConfig extends WebMvcConfigurationSupport  {
	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }
}
