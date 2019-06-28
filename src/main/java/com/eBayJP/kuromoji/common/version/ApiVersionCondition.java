package com.eBayJP.kuromoji.common.version;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * <pre>
 * com.eBayJP.kuromoji.common.version_ApiVersionCondition.java
 * </pre>
 * @date : 2019. 6. 28.
 * @author : hychoi
 */
@RequiredArgsConstructor
@ToString
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {

	//인터페이스 경로의 버전 번호 접두사 (예: api/v[1-n]/test)
	private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile("/v(\\d+)/");

    private int apiVersion;

    ApiVersionCondition(int apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        //우선순위 원칙의 최종 정의를 사용하면 메소드 정의가 클래스 정의보다 우선함.
    	return new ApiVersionCondition(other.getApiVersion());
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
    	// 일치하는 ApiVersionCondition을 가져옴.
    	Matcher m = VERSION_PREFIX_PATTERN.matcher(request.getRequestURI());

        if (m.find()) {
            int version = Integer.valueOf(m.group(1));
            if (version >= getApiVersion()) {
                return this;
            }
        }
        return null;
    }

    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
    	// 만약 일치하는 ApiVersionConditions이 다수 존재 한다면 비교  
    	return other.getApiVersion() - getApiVersion();
    }
    
    public int getApiVersion() {
        return apiVersion;
    }
}
