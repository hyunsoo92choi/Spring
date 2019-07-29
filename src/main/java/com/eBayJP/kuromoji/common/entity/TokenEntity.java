package com.eBayJP.kuromoji.common.entity;

import java.io.Serializable;

import com.atilika.kuromoji.ipadic.Token;

import lombok.Data;

@Data
public class TokenEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2289282455745734041L;

	private String surface;
	private Integer position;
	private String partOfSpeechLevel1;
	private String partOfSpeechLevel2;
	private String partOfSpeechLevel3;
	private String partOfSpeechLevel4;
	private String conjugationType;
	private String conjugationForm;
	private String reading;
	private String baseForm;
	private String pronunciation;
//	@ApiModelProperty(required = true, value = "All features array")
//	private String[] allFeaturesArray;
	private Boolean known;
	private String allFeatures;
	private String others;
	

	/* Constructor */
	public TokenEntity(Token token) {
		this.surface = token.getSurface();
		this.position = token.getPosition();
		this.partOfSpeechLevel1 = token.getPartOfSpeechLevel1();
		this.partOfSpeechLevel2 = token.getPartOfSpeechLevel2();
		this.partOfSpeechLevel3 = token.getPartOfSpeechLevel3();
		this.partOfSpeechLevel4 = token.getPartOfSpeechLevel4();
		this.conjugationType = token.getConjugationType();
		this.conjugationForm = token.getConjugationForm();
		this.reading = token.getReading();
		this.baseForm = token.getBaseForm();
		this.pronunciation = token.getPronunciation();
//		this.allFeaturesArray = token.getAllFeaturesArray();
		this.known = token.isKnown();
		this.allFeatures = token.getAllFeatures();
	}
	
	public TokenEntity(String surface) {
		this.surface = surface;
		this.partOfSpeechLevel1 = "CUSTOM";
		this.reading = surface;
	}
}
