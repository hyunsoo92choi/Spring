package com.eBayJP.kuromoji.app.poc.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;
import com.eBayJP.kuromoji.common.code.mapper.EnumMapper;
import com.eBayJP.kuromoji.common.code.mapper.EnumValue;
import com.eBayJP.kuromoji.common.code.pos.PosType;
import com.eBayJP.kuromoji.common.entity.TokenEntity;
import com.eBayJP.kuromoji.util.FileUtil;

/**
 * <pre>
 * com.eBayJP.kuromoji.service KuromojiIpadicService.java 
 * </pre>
 * @date : 2019. 5. 23. 오전 10:14:15
 * @description :  	KuromojiIpadicService Class
 * 					User Dictionary template
 * 					String userDictionary = "東京タワー,東京タワー,トウキョウタワー,カスタム地名\n" +
	            							"東京スカイツリー,東京スカイツリー,トウキョウスカイツリー,カスタム地名\n" +
	            							"sk-2,sk-2,sk-2,カスタム地名";
 * @author : hychoi
 */
@Service
public class KuromojiIpadicService {

	//TODO: File 처리는 Common-lib로 이동하여 Static을 최대한 없앨 것
	static final  String userDictionary = FileUtil.getUserDictionary();
	
	private final static Logger logger = LoggerFactory.getLogger(KuromojiIpadicService.class);
	
	private EnumMapper enumMapperFactory;
	
	public List<Token> Tokenize(String text) {

		Tokenizer tokenizer = new Tokenizer.Builder().build();
		List<Token> tokens = tokenizer.tokenize(text);

		return tokens;
	}
	
	public List<Token> getUserDicToken(String text) {
		
		Tokenizer tokenizer = null;
		
		try {
			tokenizer = makeTokenizer(userDictionary);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		List<Token> tokens = tokenizer.tokenize(text);
//		List<TokenEntity> entityList = getTokenEntityList(tokens);
		return tokens;
	}
	@Deprecated
	private Tokenizer makeTokenizer(String userDictionaryEntry) throws IOException {
		
        return new Tokenizer.Builder()
            .userDictionary(makeUserDictionaryStream(userDictionaryEntry))
            .build();
    }

    private ByteArrayInputStream makeUserDictionaryStream(String userDictionary) {
        return new ByteArrayInputStream(
            userDictionary.getBytes(StandardCharsets.UTF_8)
        );
    }
    
    /**
     * <pre>
     * 1. 개요 : Token화 된 Input을 가지고 관심있는 POS에 대한 Entity를 생성한다.
     * 2. 처리내용 : Token화 된 Input을 가지고 관심있는 POS에 대한 Entity를 생성
     * </pre>
     * @Method Name : getTokenEntityList
     * @date : 2019. 6. 12.
     * @author : hychoi
     * @history : 
     *	-----------------------------------------------------------------------
     *	변경일				작성자						변경내용  
     *	----------- ------------------- ---------------------------------------
     *	2019. 6. 12.		hychoi				최초 작성 
     *	-----------------------------------------------------------------------
     * 
     * @param tokens
     * @return List<TokenEntity>
     */ 
    private List<TokenEntity> getTokenEntityList(List<Token> tokens) {
    	
    	List<TokenEntity> entityList = new ArrayList<TokenEntity>();
    	
    	for(Token token : tokens) { 
    		TokenEntity tokenEntity = new TokenEntity(token);
   			entityList.add(tokenEntity);
    	}
    	
    	return entityList;
    }
    
    /**
     * <pre>
     * 1. 개요 : TokenEntity에 대한 POS 체크
     * 2. 처리내용 : 관심있는 POS 인지 확인
     * </pre>
     * @Method Name : isValidate
     * @date : 2019. 6. 12.
     * @author : hychoi
     * @history : 
     *	-----------------------------------------------------------------------
     *	변경일				작성자						변경내용  
     *	----------- ------------------- ---------------------------------------
     *	2019. 6. 12.		hychoi				최초 작성 
     *	-----------------------------------------------------------------------
     * 
     * @param tokenEntity
     * @return
     */ 
    private boolean isValidate(TokenEntity tokenEntity) {
    	boolean result = false;
    	
    	// 동사, 명사, 형용사, 형용동사
    	if ( PosType.VERB.getCodeName().equals(tokenEntity.getPartOfSpeechLevel1())
    			|| PosType.NOUN.getCodeName().equals(tokenEntity.getPartOfSpeechLevel1()) 
    			|| PosType.ADJE.getCodeName().equals(tokenEntity.getPartOfSpeechLevel1())
    			|| PosType.ADVB.getCodeName().equals(tokenEntity.getPartOfSpeechLevel1())
    	   )
    		result = true;
    	
    	return result;
    }
    
    /**	
	 * @Method Name : getResource
	 * @date : 2019. 5. 23.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 5. 22.		hychoi				최초 작성
	 *  2019. 5. 23.		hychoi				Deprecated Not Use Any More 
	 *	-----------------------------------------------------------------------
	 * @param resource
	 * @return
	 */ 	
	@Deprecated
	private InputStream getResource(String resource) {
        return this.getClass().getClassLoader().getResourceAsStream(resource);
    }
	
	
    public List<Token> getUserDicTokensForGoodsName(String goodsNm) {
    	
    	Tokenizer tokenizer = null;
    	
    	List<Token> tokens = new ArrayList<Token>();
		
		String userDictionary = FileUtil.getUserDictionary();
		
		try {
			tokenizer = makeTokenizer(userDictionary);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tokens;
    	
    }
}
