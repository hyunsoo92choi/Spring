package com.eBayJP.kuromoji.app.analytics.service;

import java.util.ArrayList;
import java.util.List;
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
	public List<TokenEntity> Tokenize(String text) {
		
		log.info("[KuromojiAnalyticsService]: >>>> Tokenize text: {}", text);

		List<Token> tokens = ebayJPTokenizer.tokenize(text)
				.stream()
				.filter(token -> isValidate(token))
				.collect(Collectors.toList());
		
		List<TokenEntity> entityList = getTokenEntityList(tokens);
		
		return entityList;
	}
	
	public List<Token> TokenizeVerTwo(String text) {
		log.info("[KuromojiAnalyticsService]: >>>> TokenizeVerTwo text: {}", text);

		List<Token> tokens = ebayJPTokenizer.tokenize(text)
				.stream()
				.filter(token -> isValidate(token))
				.collect(Collectors.toList());
		
		return tokens;
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

}
