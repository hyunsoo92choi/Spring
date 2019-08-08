package com.eBayJP.kuromoji.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * <pre>
 * com.eBayJP.kuromoji.common.config_EnvironmentConfiguration.java
 * </pre>
 * @date : 2019. 6. 28.
 * @author : hychoi
 */
//@Configuration
//@PropertySource(value = "classpath:ebayJPDictionary.csv")
public class EnvironmentConfiguration {
	
	@Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
