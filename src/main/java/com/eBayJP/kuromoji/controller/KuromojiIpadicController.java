<<<<<<< Updated upstream
package com.eBayJP.kuromoji.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atilika.kuromoji.ipadic.Token;
import com.eBayJP.kuromoji.entity.TokenEntity;
import com.eBayJP.kuromoji.entity.request.KuromojiRequestEntity;
import com.eBayJP.kuromoji.entity.response.TokenResponseEntity;
import com.eBayJP.kuromoji.service.KuromojiIpadicService;
import com.eBayJP.kuromoji.util.FileUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;


/**
 * <pre>
 * com.eBayJP.kuromoji.controller KuromojiIpadicController.java 
 * </pre>
 * @date : 2019. 5. 23. 오전 10:04:53
 * @description : KuromojiIpadic Library를 이용
 * @author : hychoi
 */
@RestController
@RequestMapping(value = "/ebayjp-ipadic")
public class KuromojiIpadicController {
	
	private final KuromojiIpadicService kuromojiIpadicService;
	
	public KuromojiIpadicController(KuromojiIpadicService kuromojiIpadicService) {
	    this.kuromojiIpadicService = kuromojiIpadicService;
	}

	@RequestMapping(value = "/v1/tokenize", method = RequestMethod.GET)
	@ApiIgnore
	public ResponseEntity<Map<String, Object>> tokenize(@RequestParam("text") String text) throws IOException {

		Map<String, Object> model = new HashMap<String, Object>();
		Long startTime = System.currentTimeMillis();

		// FIXME 하드코딩 -> 공통코드화
		if (text.length() > 400) {
			text = text.substring(0, 400);
		}
		
		List<Token> tokens = kuromojiIpadicService.getUserDicToken(text);
		List<TokenEntity> rtnTokens = new ArrayList<TokenEntity>();
		
		for (Token token : tokens) {
			rtnTokens.add(new TokenEntity(token));
		}
		
		Long endTime = System.currentTimeMillis();
		Long processTime = endTime - startTime;
		
		model.put("tokens", rtnTokens);
		model.put("startTime",startTime.toString());
		model.put("endTime",endTime.toString());
		model.put("processTime",processTime.toString());
		
		return new ResponseEntity< Map<String, Object> >(model, HttpStatus.OK);
	}

	
	/**
	 * <pre>
	 * 1. 개요 : 공개용 API 더미 메소드
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : tokenize
	 * @date : 2019. 5. 23.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 5. 23.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param access_token
	 * @param text
	 * @return TokenResponseEntity
	 */ 	
	@RequestMapping(value = { "/tokenize" }, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ApiOperation(value = "Kuromoji IPADIC", notes = "Kuromoji IPADIC dictionary")
	public TokenResponseEntity tokenize(
			@ApiParam(value = "Access Token", required = true) @RequestParam("access_token") String access_token,
			@ApiParam(value = "Text [up to 400 characters]", required = true) @RequestParam("text") String text) {
		return new TokenResponseEntity();
	}
	
	/**
	 * <pre>
	 * 1. 개요 : Post 방식으로 Text를 받아 Tokenized 된 결과를 return
	 * 2. 처리내용 : Post 방식으로 Texts 를 받아 Tokenized 된 결과를 model 에 담고 모델을 return
	 * </pre>
	 * @Method Name : tokenizeByPost
	 * @date : 2019. 5. 30.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 5. 30.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param requestKuromojiEntity
	 * @return ResponseEntity
	 * @throws IOException
	 */ 
	@RequestMapping(value = "/v1/tokenize", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> tokenizeByPost(@RequestBody KuromojiRequestEntity requestKuromojiEntity) throws IOException {
		
		List<String>textList = requestKuromojiEntity.getTexts();
		
		List<TokenEntity> rtnTokens = new ArrayList<TokenEntity>();
		
		Long startTime = System.currentTimeMillis();
		
		for (String text : textList) {
			
			List<Token> tokens = kuromojiIpadicService.getUserDicToken(text);
			
			if( !(tokens == null || tokens.isEmpty()) ) {
				for (Token token : tokens) {
					rtnTokens.add(new TokenEntity(token));
				}
			}
		}
		Long endTime = System.currentTimeMillis();
		Long processTime = endTime - startTime;
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("tokens", rtnTokens);
		model.put("startTime",startTime.toString());
		model.put("endTime",endTime.toString());
		model.put("processTime",processTime.toString());
		System.out.println(processTime);
		
		return new ResponseEntity< Map<String, Object> >(model, HttpStatus.OK);
	}

	/**
	 * <pre>
	 * 1. 개요 : Rest 호출 시 File에서 처리할 text를 불러와 Token 화를 수행 (다수의 text를 처리할 때 시간을 보기 위한 메서드)
	 * 2. 처리내용 : File에서 처리할 text를 불러와 Token화 후 return
	 * </pre>
	 * @Method Name : tokenizeFromFile
	 * @date : 2019. 5. 30.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 5. 30.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @return ResponseEntity
	 * @throws IOException
	 */ 
	@RequestMapping(value = "/v1/tokenizeFromFile", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> tokenizeFromFile() throws IOException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		// File Read
		List<String> goodsNmList = FileUtil.getGoodsNmList();
		Long startTime = System.currentTimeMillis();
		
		List<TokenEntity> rtnTokens = new ArrayList<TokenEntity>();
		
		for (String goodsNm : goodsNmList) {
			List<Token> tokens = kuromojiIpadicService.getUserDicToken(goodsNm);
			
			if( !(tokens == null || tokens.isEmpty()) ) {
				for (Token token : tokens) {
					rtnTokens.add(new TokenEntity(token));
				}
			}
		}
		
		Long endTime = System.currentTimeMillis();
		
		Long processTime = endTime - startTime;
		
		model.put("startTime",startTime.toString());
		model.put("endTime",endTime.toString());
		model.put("processTime",processTime.toString());
		System.out.println(processTime);
		return new ResponseEntity< Map<String, Object> >(model, HttpStatus.OK);
	
	}
}
=======
package com.eBayJP.kuromoji.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atilika.kuromoji.ipadic.Token;
import com.eBayJP.kuromoji.entity.TokenEntity;
import com.eBayJP.kuromoji.entity.request.KuromojiRequestEntity;
import com.eBayJP.kuromoji.entity.response.TokenResponseEntity;
import com.eBayJP.kuromoji.service.KuromojiIpadicService;
import com.eBayJP.kuromoji.util.FileUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;


/**
 * <pre>
 * com.eBayJP.kuromoji.controller KuromojiIpadicController.java 
 * </pre>
 * @date : 2019. 5. 23. 오전 10:04:53
 * @description : KuromojiIpadic Library를 이용
 * @author : hychoi
 */
@RestController
@RequestMapping(value = "/kuromoji-ipadic")
public class KuromojiIpadicController {
	
	private final KuromojiIpadicService kuromojiIpadicService;
	
	public KuromojiIpadicController(KuromojiIpadicService kuromojiIpadicService) {
	    this.kuromojiIpadicService = kuromojiIpadicService;
	}

	@RequestMapping(value = "/v1/tokenize", method = RequestMethod.GET)
	@ApiIgnore
	public ResponseEntity<Map<String, Object>> tokenize(@RequestParam("text") String text) throws IOException {

		Map<String, Object> model = new HashMap<String, Object>();
		Long startTime = System.currentTimeMillis();

		// FIXME 하드코딩 -> 공통코드화
		if (text.length() > 400) {
			text = text.substring(0, 400);
		}
		
		List<Token> tokens = kuromojiIpadicService.getUserDicToken(text);
		List<TokenEntity> rtnTokens = new ArrayList<TokenEntity>();
		
		for (Token token : tokens) {
			rtnTokens.add(new TokenEntity(token));
		}
		
		Long endTime = System.currentTimeMillis();
		Long processTime = endTime - startTime;
		
		model.put("tokens", rtnTokens);
		model.put("startTime",startTime.toString());
		model.put("endTime",endTime.toString());
		model.put("processTime",processTime.toString());
		
		return new ResponseEntity< Map<String, Object> >(model, HttpStatus.OK);
	}

	
	/**
	 * <pre>
	 * 1. 개요 : 공개용 API 더미 메소드
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : tokenize
	 * @date : 2019. 5. 23.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 5. 23.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param access_token
	 * @param text
	 * @return TokenResponseEntity
	 */ 	
	@RequestMapping(value = { "/tokenize" }, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ApiOperation(value = "Kuromoji IPADIC", notes = "Kuromoji IPADIC dictionary")
	public TokenResponseEntity tokenize(
			@ApiParam(value = "Access Token", required = true) @RequestParam("access_token") String access_token,
			@ApiParam(value = "Text [up to 400 characters]", required = true) @RequestParam("text") String text) {
		return new TokenResponseEntity();
	}
	
	/**
	 * <pre>
	 * 1. 개요 : Post 방식으로 Text를 받아 Tokenized 된 결과를 return
	 * 2. 처리내용 : Post 방식으로 Texts 를 받아 Tokenized 된 결과를 model 에 담고 모델을 return
	 * </pre>
	 * @Method Name : tokenizeByPost
	 * @date : 2019. 5. 30.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 5. 30.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param requestKuromojiEntity
	 * @return ResponseEntity
	 * @throws IOException
	 */ 
	@RequestMapping(value = "/v1/tokenize", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> tokenizeByPost(@RequestBody KuromojiRequestEntity requestKuromojiEntity) throws IOException {
		
		List<String>textList = requestKuromojiEntity.getTexts();
		
		List<TokenEntity> rtnTokens = new ArrayList<TokenEntity>();
		
		Long startTime = System.currentTimeMillis();
		
		for (String text : textList) {
			
			List<Token> tokens = kuromojiIpadicService.getUserDicToken(text);
			
			if( !(tokens == null || tokens.isEmpty()) ) {
				for (Token token : tokens) {
					rtnTokens.add(new TokenEntity(token));
				}
			}
		}
		Long endTime = System.currentTimeMillis();
		Long processTime = endTime - startTime;
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("tokens", rtnTokens);
		model.put("startTime",startTime.toString());
		model.put("endTime",endTime.toString());
		model.put("processTime",processTime.toString());
		System.out.println(processTime);
		
		return new ResponseEntity< Map<String, Object> >(model, HttpStatus.OK);
	}

	/**
	 * <pre>
	 * 1. 개요 : Rest 호출 시 File에서 처리할 text를 불러와 Token 화를 수행 (다수의 text를 처리할 때 시간을 보기 위한 메서드)
	 * 2. 처리내용 : File에서 처리할 text를 불러와 Token화 후 return
	 * </pre>
	 * @Method Name : tokenizeFromFile
	 * @date : 2019. 5. 30.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 5. 30.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @return ResponseEntity
	 * @throws IOException
	 */ 
	@RequestMapping(value = "/v1/tokenizeFromFile", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> tokenizeFromFile() throws IOException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		// File Read
		List<String> goodsNmList = FileUtil.getGoodsNmList();
		Long startTime = System.currentTimeMillis();
		
		List<TokenEntity> rtnTokens = new ArrayList<TokenEntity>();
		
		for (String goodsNm : goodsNmList) {
			List<Token> tokens = kuromojiIpadicService.getUserDicToken(goodsNm);
			
			if( !(tokens == null || tokens.isEmpty()) ) {
				for (Token token : tokens) {
					rtnTokens.add(new TokenEntity(token));
				}
			}
		}
		
		Long endTime = System.currentTimeMillis();
		
		Long processTime = endTime - startTime;
		
		model.put("startTime",startTime.toString());
		model.put("endTime",endTime.toString());
		model.put("processTime",processTime.toString());
		System.out.println(processTime);
		return new ResponseEntity< Map<String, Object> >(model, HttpStatus.OK);
	
	}
}
>>>>>>> Stashed changes
