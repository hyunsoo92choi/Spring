package com.eBayJP.kuromoji.common.util;

import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.AbstractFieldsSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * com.eBayJP.kuromoji.common.util_CustomResponseFieldsSnippet.java
 * </pre>
 * 
 * @date : 2019. 7. 1.
 * @author : hychoi
 */
public class CustomResponseFieldsSnippet extends AbstractFieldsSnippet {
	public CustomResponseFieldsSnippet(String type, PayloadSubsectionExtractor<?> subsectionExtractor,
			List<FieldDescriptor> descriptors, Map<String, Object> attributes, boolean ignoreUndocumentedFields) {
		super(type, descriptors, attributes, ignoreUndocumentedFields, subsectionExtractor);
	}

	@Override
	protected MediaType getContentType(Operation operation) {
		return operation.getResponse().getHeaders().getContentType();
	}

	@Override
	protected byte[] getContent(Operation operation) throws IOException {
		return operation.getResponse().getContent();
	}
}
