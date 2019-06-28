package com.eBayJP.kuromoji.common.version;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * com.eBayJP.kuromoji.common.version_ApiVersion.java
 * @ApiVersion 정의
 * </pre>
 * @date : 2019. 6. 28.
 * @author : hychoi
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiVersion {
	 int value();
}
