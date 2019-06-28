package com.eBayJP.kuromoji.common.version;

import java.lang.reflect.Method;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * <pre>
 * com.eBayJP.kuromoji.common.version_VersionedRequestMappingHandlerMapping.java
 * </pre>
 * @date : 2019. 6. 28.
 * @author : hychoi
 */
public class VersionedRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
	
	@Override
    protected RequestCondition<ApiVersionCondition> getCustomTypeCondition(Class<?> handlerType) {
		// 스캔 클래스의 @ApiVersion
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        return createCondition(apiVersion);
    }

    @Override
    protected RequestCondition<ApiVersionCondition> getCustomMethodCondition(Method method) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        return createCondition(apiVersion);
    }

    private RequestCondition<ApiVersionCondition> createCondition(ApiVersion apiVersion) {
        return apiVersion == null ? null : new ApiVersionCondition(apiVersion.value());
    }
}
