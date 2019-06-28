package com.eBayJP.kuromoji.app.analytics.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atilika.kuromoji.ipadic.Token;
import com.eBayJP.kuromoji.app.analytics.service.KuromojiAnalyticsService;
import com.eBayJP.kuromoji.common.entity.TokenEntity;
import com.eBayJP.kuromoji.common.entity.request.KuromojiRequestEntity;
import com.eBayJP.kuromoji.common.version.ApiVersion;

/**
 * <pre>
 * com.eBayJP.kuromoji.app.analytics.controller_KuromojiAnalyticsController.java
 * </pre>
 * @date : 2019. 6. 28.
 * @author : hychoi
 */
@RestController
@RequestMapping("api/{version}/analytics")
public class KuromojiAnalyticsController {
	
	private final Logger log = LoggerFactory.getLogger(KuromojiAnalyticsController.class);
	
	private final KuromojiAnalyticsService kuromojiAnalyticsService;
	
	public KuromojiAnalyticsController(KuromojiAnalyticsService kuromojiAnalyticsService) {
	    this.kuromojiAnalyticsService = kuromojiAnalyticsService;
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : tokenize
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
	@ApiVersion(1)
	@GetMapping("/tokenize")
    public ResponseEntity<Map<String, Object>> tokenize(@RequestParam("text") String text) {
        
		log.info("[KuromojiAnalyticsController]: >>>> @GetMapping :text: {}", text);
		
		Map<String, Object> model = new HashMap<String, Object>();
		Long startTime = System.currentTimeMillis();
		
		List<TokenEntity> entityList = kuromojiAnalyticsService.Tokenize(text);
		
		Long endTime = System.currentTimeMillis();
		Long processTime = endTime - startTime;
		
		model.put("tokens", entityList);
		model.put("startTime",startTime.toString());
		model.put("endTime",endTime.toString());
		model.put("processTime",processTime.toString());
		
		return new ResponseEntity< Map<String, Object> >(model, HttpStatus.OK);
    }
	
	/**
	 * <pre>
	 * 1. 개요 : Post 방식으로 Text를 받아 Tokenized 된 결과를 return
	 * 2. 처리내용 : Post 방식으로 Texts 를 받아 Tokenized 된 결과를 model 에 담고 모델을 return
	 * </pre>
	 * @Method Name : tokenizeByPost
	 * @date : 2019. 6. 28.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 28.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param requestKuromojiEntity
	 * @return
	 */ 
	@ApiVersion(1)
	@PostMapping("/tokenize")
	public ResponseEntity<Map<String, Object>> tokenizeByPost(@RequestBody KuromojiRequestEntity requestKuromojiEntity) {
		
		List<String> textList = requestKuromojiEntity.getTexts();
		
		StringBuilder sb = new StringBuilder(textList.size());
		
		textList.forEach(text -> {
	        sb.append(text);
	        sb.append(System.lineSeparator());
	    });
		
		log.info("[KuromojiAnalyticsController]: >>>> @@PostMapping :text: {}", sb.toString());
		
		Long startTime = System.currentTimeMillis();
		
		List<TokenEntity> entityList = kuromojiAnalyticsService.Tokenize(sb.toString());
		
		Long endTime = System.currentTimeMillis();
		Long processTime = endTime - startTime;
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("tokens", entityList);
		model.put("startTime",startTime.toString());
		model.put("endTime",endTime.toString());
		model.put("processTime",processTime.toString());
		System.out.println(processTime);
		
		return new ResponseEntity< Map<String, Object> >(model, HttpStatus.OK);
	}
	
}
