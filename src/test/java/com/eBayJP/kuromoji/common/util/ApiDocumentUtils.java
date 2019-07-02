package com.eBayJP.kuromoji.common.util;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

/**
 * <pre>
 * com.eBayJP.kuromoji_ApiDocumentUtils.java
 * </pre>
 * @date : 2019. 7. 1.
 * @author : hychoi
 */
public interface ApiDocumentUtils {
	
//	static OperationRequestPreprocessor getDocumentRequest() {
//        return preprocessRequest(
//                modifyUris()
//                        .scheme("https")
//                        .host("docs.api.com")
//                        .removePort(),
//                prettyPrint());
//    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint());
    }
}
