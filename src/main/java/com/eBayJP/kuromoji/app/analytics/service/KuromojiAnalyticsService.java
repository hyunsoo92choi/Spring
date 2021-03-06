package com.eBayJP.kuromoji.app.analytics.service;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.eBayJP.kuromoji.app.analytics.entity.AnalyticsTokenEntity;
import com.eBayJP.kuromoji.common.entity.TokenEntity;
import com.eBayJP.kuromoji.common.service.CommonService;

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
	private CommonService commonService;
	
	private List<Token> keywords = new ArrayList<Token>();

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
	 *	2019. 7. 31.		hychoi				Deprecated
	 *	-----------------------------------------------------------------------
	 * 
	 * @param text
	 * @return
	 */ 
	@Deprecated
	public List<TokenEntity> tokenize(String text) {
		
		log.info("[KuromojiAnalyticsService]: >>>> Tokenize text: {}", text);

		List<Token> tokens = ebayJPTokenizer.tokenize(text)
				.stream()
				.filter(token -> commonService.isUsableSpeechLevel(token))
				.collect(Collectors.toList());
		
		List<TokenEntity> entityList = commonService.getTokenEntityList(tokens);
		
		return entityList;
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 사용자 사전을 이용하여 일본어만 Tokenize 한다. 
	 * 2. 처리내용 : 사용자 사전을 이용하여 일본어만 Tokenize 한다. 
	 * </pre>
	 * @Method Name : tokenizeUserDic
	 * @date : 2019. 7. 31.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 7. 31.		hychoi				최초 작성(코멘트 추가)
	 *	-----------------------------------------------------------------------
	 * 
	 * @param text
	 * @return Map<String, List<TokenEntity>>
	 */ 
	public Map<String, List<TokenEntity>> tokenizeUserDic(String text) {

		log.info("[KuromojiAnalyticsService]: >>>> tokenizeUserDic text: {}", text);

		Map<String, String> languages = commonService.getLanguages(text);
		String japanese = languages.get("japanese");
		String others = languages.get("others");

		List<Token> tokens = ebayJPTokenizer.tokenize(japanese).stream()
				.filter(token -> commonService.isUsableSpeechLevel(token)).collect(Collectors.toList());
		
		List<TokenEntity> entityList = commonService.getTokenEntityList(tokens);
		
		List<TokenEntity> otherTokensEntityList = commonService.getUserTokenEntityList(Arrays.stream(others.replaceAll("\\s{2}", " ").split("\\s+")).collect(Collectors.toList()));

		Map<String, List<TokenEntity>> map = new HashMap<String, List<TokenEntity>>();
		map.put("tokens", entityList);
		map.put("otherTokens", otherTokensEntityList);

		return map;
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 일본어와 영어를 전부 다 형태소 분석기로 처리 한다.
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : tokenizeUserDicAll
	 * @date : 2019. 7. 26.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 7. 26.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param text
	 * @return
	 */ 
	public Map<String, List<AnalyticsTokenEntity>> tokenizeUserDicAll(String text) {

		log.info("[KuromojiAnalyticsService]: >>>> tokenizeUserDic text: {}", text);

		Map<String, String> languages = commonService.getLanguages(text);
		String japanese = languages.get("japanese");
		String others = languages.get("others");

		List<Token> tokens = ebayJPTokenizer.tokenize(japanese).stream()
				.filter(token -> commonService.isUsableSpeechLevel(token)).collect(Collectors.toList());
		
		List<Token> otherTokens = ebayJPTokenizer.tokenize(others).stream()
				.filter(otherToken -> commonService.isUsableSpeechLevel(otherToken)).collect(Collectors.toList());
		
		List<AnalyticsTokenEntity> entityList = this.getAnaliticsTokenEntityList(tokens);
		List<AnalyticsTokenEntity> otherTokensEntityList = this.getAnaliticsTokenEntityList(otherTokens);
		

		Map<String, List<AnalyticsTokenEntity>> map = new HashMap<String, List<AnalyticsTokenEntity>>();
		map.put("tokens", entityList);
		map.put("otherTokens", otherTokensEntityList);

		return map;
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 브랜드 사전을 이용하여 Tokenize 한다.
	 * 2. 처리내용 : 브랜드 사전을 이용하여 Tokenize 한다.
	 * </pre>
	 * @Method Name : tokenizeBrandDic
	 * @date : 2019. 7. 31.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 7. 31.		hychoi				최초 작성 
	 *	2019. 7. 31. 		hychoi				Deprecated
	 *										    Brand 관련 내용은 분리 
	 *											@See sd Package
	 *	-----------------------------------------------------------------------
	 * 
	 * @param text
	 * @return
	 */ 
	@Deprecated
	public Map<String, Object> tokenizeBrandDic(String text) {
		
		log.info("[KuromojiAnalyticsService]: >>>> tokenizeBrandDic text: {}", text);
		/*
		Map<String, String> languages = commonService.getLanguages(text);
		String japanese = languages.get("japanese");
		String others = languages.get("others");
		
		List<Token> tokens = ebayJPBrandDicTokenizer.tokenize(japanese)
				.stream()
				.filter(token -> commonService.isUsableSpeechLevel(token))
				.collect(Collectors.toList());
		
		List<TokenEntity> entityList = commonService.getTokenEntityList(tokens);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tokens", entityList);
		map.put("others", others);
		
		return map;
		*/
		return null;
	}
	
	private void getKeyWord(String text) {

		String[] splited = text.split("\\s+");

		List<Token> tokens = new ArrayList<Token>();

		if (!text.isEmpty()) {
			
			for (int i = 0; i < splited.length; i++) {
				StringBuilder sb = new StringBuilder();

				tokens = ebayJPTokenizer.tokenize(splited[i].trim())
						.stream()
						.collect(Collectors.toList());
				
				if (!tokens.isEmpty()) {
					
					if ( tokens.size() == 1 && commonService.isUsableSpeechLevel(tokens.get(0)) ) {
						keywords.add(tokens.get(0));
						log.info("keywords: {}", keywords);
					} else {
						
						if (commonService.isUsableSpeechLevel(tokens.get(0))) {
							keywords.add(tokens.get(0));
							log.info("keywords: {}", keywords);
						}
						
						for (int j = 1; j < tokens.size(); j++) {
							sb.append(tokens.get(j).getSurface());
						}
						
						log.info("texts: {}", sb.toString());
						getKeyWord(sb.toString());
					}
				}
			}
		}
	}
	private List<AnalyticsTokenEntity> getAnaliticsTokenEntityList(List<Token> tokens) {

		List<AnalyticsTokenEntity> entityList = new ArrayList<AnalyticsTokenEntity>();

		for (Token token : tokens) {
			AnalyticsTokenEntity tokenEntity = new AnalyticsTokenEntity(token);
			entityList.add(tokenEntity);
		}

		return entityList;
	}
}
