package com.eBayJP.kuromoji.app.analytics.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;
import com.eBayJP.kuromoji.common.code.pos.PosType;
import com.eBayJP.kuromoji.common.entity.TokenEntity;

/**
 * <pre>
 * com.eBayJP.kuromoji.app.analytics.service_KuromojiAnalyticsService.java
 * </pre>
 * 
 * @date : 2019. 6. 28.
 * @author : hychoi
 */
@Service
public class KuromojiAnalyticsService {

	private final static Logger log = LoggerFactory.getLogger(KuromojiAnalyticsService.class);

	@Autowired
	@Qualifier("EbayJPTokenizer")
	private Tokenizer ebayJPTokenizer;
	
	@Autowired
	@Qualifier("KuromojiTokenizer")
	private Tokenizer tokenizer;
	
	@Autowired
	@Qualifier("EbayJPBrandDicTokenizer")
	private Tokenizer ebayJPBrandDicTokenizer;

	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : Tokenize
	 * @date : 2019. 6. 28.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 28.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param text
	 * @return
	 */ 
	public List<TokenEntity> tokenize(String text) {
		
		log.info("[KuromojiAnalyticsService]: >>>> Tokenize text: {}", text);

		List<Token> tokens = ebayJPTokenizer.tokenize(text)
				.stream()
				.filter(token -> isValidate(token))
				.collect(Collectors.toList());
		
		List<TokenEntity> entityList = getTokenEntityList(tokens);
		
		return entityList;
	}
	
	public List<TokenEntity> tokenizer(String text) {
		
		log.info("[KuromojiAnalyticsService]: >>>> Tokenize text: {}", text);

		List<Token> tokens = tokenizer.tokenize(text)
				.stream()
				.filter(token -> isValidate(token))
				.collect(Collectors.toList());
		
		List<TokenEntity> entityList = getTokenEntityList(tokens);
		
		return entityList;
	}
	
	public Map<String, Object> tokenizeJapanese(String text) {
		
		log.info("[KuromojiAnalyticsService]: >>>> TokenizeVerTwo text: {}", text);
		
		Map<String, String> languages = this.getLanguages(text);
		String japanese = languages.get("japanese");
		String others = languages.get("others");
		
		List<Token> tokens = ebayJPTokenizer.tokenize(japanese)
				.stream()
				.filter(token -> isValidate(token))
				.collect(Collectors.toList());
		
		List<TokenEntity> entityList = getTokenEntityList(tokens);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tokens", entityList);
		map.put("others", others);
		
		return map;
	}
	
public Map<String, Object> tokenizeBrandDic(String text) {
		
		log.info("[KuromojiAnalyticsService]: >>>> tokenizeBrandDic text: {}", text);
		
		Map<String, String> languages = this.getLanguages(text);
		String japanese = languages.get("japanese");
		String others = languages.get("others");
		
		List<Token> tokens = ebayJPBrandDicTokenizer.tokenize(japanese)
				.stream()
				.filter(token -> isValidate(token))
				.collect(Collectors.toList());
		
		List<TokenEntity> entityList = getTokenEntityList(tokens);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tokens", entityList);
		map.put("others", others);
		
		return map;
	}

	private boolean isValidate(Token token) {

		boolean result = false;

		// 동사, 명사, 형용사, 형용동사, 커스텀사전
		if (PosType.VERB.getCodeName().equals(token.getPartOfSpeechLevel1())
				|| PosType.NOUN.getCodeName().equals(token.getPartOfSpeechLevel1())
				|| PosType.ADJE.getCodeName().equals(token.getPartOfSpeechLevel1())
				|| PosType.ADVB.getCodeName().equals(token.getPartOfSpeechLevel1())
				|| PosType.CUST.getCodeName().equals(token.getPartOfSpeechLevel1()))
			result = true;

		return result;
	}

	private List<TokenEntity> getTokenEntityList(List<Token> tokens) {

		List<TokenEntity> entityList = new ArrayList<TokenEntity>();

		for (Token token : tokens) {
			TokenEntity tokenEntity = new TokenEntity(token);
			entityList.add(tokenEntity);
		}

		return entityList;
	}
	
	
	private Map<String, String> getLanguages(String text) {
		
		Map<String, String> model = new HashMap<String, String>();
		StringBuilder japanese = new StringBuilder();
		StringBuilder others = new StringBuilder();
		
		for (char c : text.toCharArray()) {
			if (isJapanese(c)) {
				japanese.append(c);
			} else {
				others.append(c);
			}
        }
		log.info("[KuromojiAnalyticsService]: >>>> japanese: {}", japanese);
		log.info("[KuromojiAnalyticsService]: >>>> others: {}", others);
		
		model.put("japanese", japanese.toString());
		model.put("others", others.toString());
		
		return model;
	}
	
	private boolean isJapanese(char c) {
		
		boolean hasJapanese = false;
		
		if ( Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HIRAGANA
                || Character.UnicodeBlock.of(c) == Character.UnicodeBlock.KATAKANA
                || Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) {
			hasJapanese = true;
        }
		
		return hasJapanese;
	}
	
}
