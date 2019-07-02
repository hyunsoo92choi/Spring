package com.eBayJP.kuromoji.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;

import com.eBayJP.kuromoji.common.version.VersionedRequestMappingHandlerMapping;

/**
 * <pre>
 * com.eBayJP.kuromoji.common.config_WebConfiguration.java
 * </pre>
 * @date : 2019. 6. 28.
 * @author : hychoi
 */
@Configuration
public class WebMvcConfiguration extends DelegatingWebMvcConfiguration  {

	@Override
    public VersionedRequestMappingHandlerMapping requestMappingHandlerMapping() {
        return new VersionedRequestMappingHandlerMapping();
    }

}
