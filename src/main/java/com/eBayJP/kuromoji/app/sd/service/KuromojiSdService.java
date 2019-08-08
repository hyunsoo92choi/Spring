package com.eBayJP.kuromoji.app.sd.service;

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
import com.eBayJP.kuromoji.common.entity.TokenEntity;
import com.eBayJP.kuromoji.common.service.CommonService;

/**
 * <pre>
 * com.eBayJP.kuromoji.app.sd.service_KuromojiSdService.java
 * </pre>
 * @date : 2019. 7. 31.
 * @author : hychoi
 */
@Service
public class KuromojiSdService {
	
	private final static Logger log = LoggerFactory.getLogger(KuromojiSdService.class);
	
	@Autowired
	@Qualifier("EbayJPBrandDicTokenizer")
	private Tokenizer ebayJPBrandDicTokenizer;
	
	@Autowired
	private CommonService commonService;
	
	public Map<String, List<TokenEntity>> tokenizeBrandDic(String text) {
		
		log.info("[KuromojiSdService]: >>>> tokenizeBrandDic text: {}", text);
		
		Map<String, String> languages = commonService.getLanguages(text);
		String japanese = languages.get("japanese");
		String others = languages.get("others");
		
		List<Token> tokens = ebayJPBrandDicTokenizer.tokenize(japanese)
				.stream()
				.filter(token -> commonService.isUsableSpeechLevel(token))
				.collect(Collectors.toList());
		
		List<Token> otherTokens = ebayJPBrandDicTokenizer.tokenize(others).stream()
				.filter(otherToken -> commonService.isUsableSpeechLevel(otherToken)).collect(Collectors.toList());
		
		List<TokenEntity> entityList = commonService.getTokenEntityList(tokens);
		List<TokenEntity> otherTokensEntityList = commonService.getTokenEntityList(otherTokens);
		
		Map<String, List<TokenEntity>> map = new HashMap<String, List<TokenEntity>>();
		
		map.put("tokens", entityList);
		map.put("otherTokens", otherTokensEntityList);
		
		return map;
	}
}
