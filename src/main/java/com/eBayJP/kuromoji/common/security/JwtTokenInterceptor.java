package com.eBayJP.kuromoji.common.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <pre>
 * com.eBayJP.kuromoji.common.security_JwtTokenInterceptor.java
 * </pre>
 * @date : 2019. 6. 13. 오후 2:39:41
 * @author : hychoi
 */
@Component
public class JwtTokenInterceptor extends HandlerInterceptorAdapter {
	
	private final Logger log = LoggerFactory.getLogger(JwtTokenInterceptor.class);
    private static final String HEADER_AUTH = "Authorization";
    
    //DI
    
}
