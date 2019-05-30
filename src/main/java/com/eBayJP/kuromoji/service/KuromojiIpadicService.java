package com.eBayJP.kuromoji.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;
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
	
	static final  String userDictionary = FileUtil.getUserDictionary();
    		
	public List<Token> Tokenize(String text) {

		Tokenizer tokenizer = new Tokenizer.Builder().build();
		List<Token> tokens = tokenizer.tokenize(text);

		return tokens;
	}
	
	public List<Token> getUserDicToken(String text) {
		
		Tokenizer tokenizer = null;
		
//		String userDictionary = FileUtil.getUserDictionary();
		
		try {

			tokenizer = makeTokenizer(userDictionary);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		List<Token> tokens = tokenizer.tokenize(text);
		return tokens;
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
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
	 * 
	 * @param resource
	 * @return
	 */ 	
	@Deprecated
	private InputStream getResource(String resource) {
        return this.getClass().getClassLoader().getResourceAsStream(resource);
    }
	
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
