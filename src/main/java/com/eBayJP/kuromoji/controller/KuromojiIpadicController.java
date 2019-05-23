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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atilika.kuromoji.ipadic.Token;
import com.eBayJP.kuromoji.entity.TokenEntity;
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
	
	
	/** 
	 * Constructor
	 * Autowired -> Construct DI
	 * @param kuromojiIpadicService
	 */
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
}
