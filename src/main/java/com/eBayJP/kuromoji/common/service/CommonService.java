package com.eBayJP.kuromoji.common.service;

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
import com.eBayJP.kuromoji.common.code.pos.NounType;
import com.eBayJP.kuromoji.common.code.pos.PosType;
import com.eBayJP.kuromoji.common.code.pos.SymbolsType;
import com.eBayJP.kuromoji.common.entity.TokenEntity;

/**
 * <pre>
 * com.eBayJP.kuromoji.common.service_CommonService.java
 * </pre>
 * 
 * @date : 2019. 7. 19.
 * @author : hychoi
 */
@Service
public class CommonService {

	private final static Logger log = LoggerFactory.getLogger(CommonService.class);
	private final static String WHITESPACE = " ";
	@Autowired
	@Qualifier("KuromojiTokenizer")
	private Tokenizer tokenizer;

	/**
	 * <pre>
	 * 1. 개요 : kuromoji tokenizer를 이용한 tokenize method
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : tokenizer
	 * @date : 2019. 7. 19.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 7. 19.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param text
	 * @return List<TokenEntity>
	 */ 
	public List<TokenEntity> tokenizer(String text) {

		log.info("[KuromojiAnalyticsService]: >>>> Tokenize text: {}", text);

		List<Token> tokens = tokenizer
				.tokenize(text)
				.stream().filter(token -> isUsableSpeechLevel(token))
				.collect(Collectors.toList());

		List<TokenEntity> entityList = getTokenEntityList(tokens);

		return entityList;
	}

	/**
	 * <pre>
	 * 1. 개요 : 토큰화 한 토큰들을 TokenEntity의 List 로 돌려주는 메서드
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : getTokenEntityList
	 * @date : 2019. 7. 19.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 7. 19.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param tokens
	 * @return List<TokenEntity>
	 */ 
	public List<TokenEntity> getTokenEntityList(List<Token> tokens) {

		List<TokenEntity> entityList = new ArrayList<TokenEntity>();

		for (Token token : tokens) {
			TokenEntity tokenEntity = new TokenEntity(token);
			entityList.add(tokenEntity);
		}

		return entityList;
	}

	/**
	 * <pre>
	 * 1. 개요 : 일본어와 그 이외의 문자열로 구분하여 돌려주는 메서드
	 * 2. 처리내용 : Text를 일본어와 그 이외의 문자열로 구분하여 Map에 담아 돌려준다.
	 * </pre>
	 * @Method Name : getLanguages
	 * @date : 2019. 7. 19.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 7. 19.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param text
	 * @return Map<String, String>
	 */ 
	public Map<String, String> getLanguages(String text) {

		Map<String, String> model = new HashMap<String, String>();
		StringBuilder japanese = new StringBuilder();
		StringBuilder others = new StringBuilder();
		boolean isJapanese = false;

		char[] s = text.toCharArray();
		int len = s.length;

		for (char c : text.toCharArray()) {
			// 일본어 이거나, 이전에 isJapanese 이면서, \r 일 경우 공백 넣어 줌
			if (isJapanese(c) || (isJapanese && '\r' == c)) {
				isJapanese = true;
				japanese.append(c);

			} else {
				if (' ' == c) {
					if (isJapanese)
						japanese.append(WHITESPACE);
					else
						others.append(WHITESPACE);
				} else
					if (isJapanese)
						others.append(WHITESPACE);
					others.append(c);
				isJapanese = false;
			}
		}

		log.info("[KuromojiAnalyticsService]: >>>> japanese: {}", japanese);
		log.info("[KuromojiAnalyticsService]: >>>> others: {}", others);

		model.put("japanese", japanese.toString());
		model.put("others", others.toString());

		return model;
	}

	/**
	 * <pre>
	 * 1. 개요 : 일본어 여부 확인
	 * 2. 처리내용 : character가 일본어이면 true 아니면 false를 반환함.
	 * </pre>
	 * @Method Name : isJapanese
	 * @date : 2019. 7. 19.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 7. 19.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param c
	 * @return boolean
	 */ 
	private boolean isJapanese(char c) {

		boolean hasJapanese = false;

		if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HIRAGANA
				|| Character.UnicodeBlock.of(c) == Character.UnicodeBlock.KATAKANA
				|| Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) {
			hasJapanese = true;
		}

		return hasJapanese;
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 유효한 품사만 return 결과를 받기 위해 유효한 품사인지 체크해 주는 메서드
	 * 2. 처리내용 : 동사, 명사 형용사, 형용동사 커스텀사전의 내용만 처리
	 * 				 단 명사이면서 Unknown이고 명사타입이 사변접속사 품사인 경우 기호로 인식하여 기호는 제외 한다.
	 * </pre>
	 * @Method Name : isUsableSpeechLevel
	 * @date : 2019. 7. 19.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 7. 19.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param token
	 * @return boolean
	 */ 
	public boolean isUsableSpeechLevel(Token token) {

		boolean result = false;

		// 동사, 명사, 형용사, 형용동사, 커스텀사전, 또는 유효한 17까지 부호
		if ( PosType.VERB.getCodeName().equals(token.getPartOfSpeechLevel1())				
				|| PosType.ADJE.getCodeName().equals(token.getPartOfSpeechLevel1())
				|| PosType.ADVB.getCodeName().equals(token.getPartOfSpeechLevel1())
				|| PosType.CUST.getCodeName().equals(token.getPartOfSpeechLevel1())
				|| PosType.NOUN.getCodeName().equals(token.getPartOfSpeechLevel1())
				|| PosType.ADVE.getCodeName().equals(token.getPartOfSpeechLevel1())
				|| ( isUsableSymbol(token)) )
			result = true;

		return result;
	}
	public List<TokenEntity> getUserTokenEntityList(List<String> surfaces) {
		
		List<TokenEntity> entityList = new ArrayList<TokenEntity>();
		
		for (String surface : surfaces) {
			TokenEntity tokenEntity = new TokenEntity(surface);
			entityList.add(tokenEntity);
		}
		
		return entityList;
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 유효한 부호 정의
	 * 2. 처리내용 : 유효한 부호이면 true 리턴
	 * </pre>
	 * @Method Name : isUsableSymbol
	 * @date : 2019. 7. 29.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 7. 29.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param token
	 * @return boolean
	 */ 
	public boolean isUsableSymbol(Token token) {
		
		boolean result = false;
		
		if (SymbolsType.AMPE.getCodeName().equals(token.getSurface())				
				|| SymbolsType.ATSI.getCodeName().equals(token.getSurface())
				|| SymbolsType.CELS.getCodeName().equals(token.getSurface())
				|| SymbolsType.COMM.getCodeName().equals(token.getSurface())
				|| SymbolsType.DEGR.getCodeName().equals(token.getSurface())
				|| SymbolsType.DIVI.getCodeName().equals(token.getSurface())
				|| SymbolsType.EQUA.getCodeName().equals(token.getSurface())
				|| SymbolsType.EXCL.getCodeName().equals(token.getSurface())
				|| SymbolsType.GRAV.getCodeName().equals(token.getSurface())
				|| SymbolsType.LPAR.getCodeName().equals(token.getSurface())
				|| SymbolsType.MINU.getCodeName().equals(token.getSurface())
				|| SymbolsType.MULT.getCodeName().equals(token.getSurface())
				|| SymbolsType.PERC.getCodeName().equals(token.getSurface())
				|| SymbolsType.PERI.getCodeName().equals(token.getSurface())
				|| SymbolsType.PLUS.getCodeName().equals(token.getSurface())
				|| SymbolsType.QUES.getCodeName().equals(token.getSurface())
				|| SymbolsType.RPAR.getCodeName().equals(token.getSurface())
				|| SymbolsType.SHAR.getCodeName().equals(token.getSurface())
				|| SymbolsType.SLAS.getCodeName().equals(token.getSurface())
				)
			result = true;
		
		return result;
		
	}
}
