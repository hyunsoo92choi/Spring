package com.eBayJP.kuromoji.common.config;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.atilika.kuromoji.TokenizerBase;
import com.atilika.kuromoji.ipadic.Tokenizer;
import com.eBayJP.kuromoji.util.FileUtil;

import lombok.NoArgsConstructor;

/**
 * <pre>
 * com.eBayJP.kuromoji.common.config_KuromojiConfiguration.java
 * </pre>
 * @date : 2019. 6. 28.
 * @author : hychoi
 */
@Component
//@NoArgsConstructor
public class KuromojiConfiguration {
	
	private static final Logger log = LoggerFactory.getLogger(KuromojiConfiguration.class);
	
	/**
	 * <pre>
	 * 1. 개요 : kuromojiTokenizer Bean 정의.
	 * 2. 처리내용 : Tokenizer를 호출할때마다 생성하지 않고 Bean으로 등록하여 사용하고자 
	 * 				 kuromojiTokenizer를 생성하여 Bean 등록 하는 메서드
	 * </pre>
	 * @Method Name : kuromojiTokenizer
	 * @date : 2019. 6. 28.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 28.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @return Tokenizer
	 */ 
	@Bean(name="KuromojiTokenizer")
	@PostConstruct
    public Tokenizer kuromojiTokenizer() {
		
		log.info("[KuromojiConfiguration]: >>>> KuromojiTokenizer Bean 등록");
        return new Tokenizer.Builder()
        		.mode(TokenizerBase.Mode.SEARCH)
        		.build();
    }
	
	/**
	 * <pre>
	 * 1. 개요 : ebayTokenizer Bean 정의
	 * 2. 처리내용 : eBay Japan 사전을 이용한 Tokenizer를 Bean으로 등록하여 사용하고자
	 * 				 ebayTokenizer를 생성하여 Bean 등록
	 * 				 @RefreshScope을 지정한 이유는 eBay Japan 사전이 일정한 주기로 Update 되면
	 * 				 그에따라 Spring Container의 재시작 없이 해당 Bean만 Refresh 해주기 위함
	 * </pre>
	 * @Method Name : ebayTokenizer
	 * @date : 2019. 6. 28.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 28.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @return ebayTokenizer
	 * @throws Throwable
	 */ 
	@Bean(name="EbayJPTokenizer")
	@RefreshScope
	@PostConstruct
    public Tokenizer ebayTokenizer() throws Throwable {
		
		log.info("[KuromojiConfiguration]: >>>> ebayTokenizer Bean 등록");
		
        return new Tokenizer.Builder()
        		.mode(TokenizerBase.Mode.SEARCH)
        		.userDictionary(makeUserDictionaryStream(FileUtil.ebayJapanDictionary))
        		.build();
    }

	/**
	 * <pre>
	 * 1. 개요 : eBay Japan 사전 Contents를 ByteArrayInputStream으로 전환
	 * 2. 처리내용 : eBay Japan 사전 Contents를 ByteArrayInputStream으로 전환하여 return 함
	 * </pre>
	 * @Method Name : makeUserDictionaryStream
	 * @date : 2019. 6. 28.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 28.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param userDictionary
	 * @return ByteArrayInputStream
	 */ 
	private ByteArrayInputStream makeUserDictionaryStream(String userDictionary) {
        return new ByteArrayInputStream(
            userDictionary.getBytes(StandardCharsets.UTF_8)
        );
    }
}
