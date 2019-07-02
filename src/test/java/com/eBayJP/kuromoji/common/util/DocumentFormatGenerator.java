package com.eBayJP.kuromoji.common.util;

import org.springframework.restdocs.snippet.Attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
/**
 * <pre>
 * com.eBayJP.kuromoji.common.util_DocumentFormatGenerator.java
 * </pre>
 * @date : 2019. 7. 1.
 * @author : hychoi
 */
public interface DocumentFormatGenerator {
	static Attributes.Attribute getDateFormat() {
        return key("format").value("yyyy-MM-dd");
    }
}
