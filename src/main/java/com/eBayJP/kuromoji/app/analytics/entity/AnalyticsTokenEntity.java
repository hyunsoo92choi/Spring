package com.eBayJP.kuromoji.app.analytics.entity;

import java.io.Serializable;

import com.atilika.kuromoji.ipadic.Token;
import com.ibm.icu.text.Transliterator;

import lombok.Data;

/**
 * <pre>
 * com.eBayJP.kuromoji.app.analytics.entity_AnalyticsTokenEntity.java
 * </pre>
 * @date : 2019. 7. 30.
 * @author : hychoi
 */
@Data
public class AnalyticsTokenEntity implements Serializable {

	private static final Transliterator kataToHiraTrans = Transliterator.getInstance("Katakana-Hiragana");
	/**
	 * 
	 */
	private static final long serialVersionUID = -2649768924156824743L;
	
	private String surface;
	private String hiraReading;
	private String kataReading;
	private String baseForm;
	
	public AnalyticsTokenEntity(Token token) {
		this.surface = token.getSurface();
		this.hiraReading = kataToHiraTrans.transliterate(token.getReading());
		this.kataReading = token.getReading();
		this.baseForm = token.getBaseForm();
	}

}
