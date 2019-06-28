package com.eBayJP.kuromoji.app.analytics.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eBayJP.kuromoji.app.analytics.service.KuromojiAnalyticsService;
import com.eBayJP.kuromoji.common.entity.TokenEntity;
import com.eBayJP.kuromoji.common.version.ApiVersion;

/**
 * <pre>
 * com.eBayJP.kuromoji.app.analytics.controller_KuromojiAnalyticsController.java
 * </pre>
 * @date : 2019. 6. 28.
 * @author : hychoi
 */
@RestController
@RequestMapping("api/{version}/test")
//@RequestMapping(value = "/api/{version}/analytics")
public class KuromojiAnalyticsController {
	
	private final Logger log = LoggerFactory.getLogger(KuromojiAnalyticsController.class);
	
	private final KuromojiAnalyticsService kuromojiAnalyticsService;
	
	public KuromojiAnalyticsController(KuromojiAnalyticsService kuromojiAnalyticsService) {
	    this.kuromojiAnalyticsService = kuromojiAnalyticsService;
	}
	
	@ApiVersion(1)
	@GetMapping("/tokenize")
    public ResponseEntity<Map<String, Object>> tokenize(@RequestParam("text") String text) {
        
		log.info("[KuromojiAnalyticsController]: >>>> text: {}", text);
		
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
	
	@GetMapping
    public String test01(@PathVariable String version) {
        return "test01 : " + version;
    }

    @GetMapping
    @ApiVersion(2)
    public String test02(@PathVariable String version) {
        return "test02 : " + version;
    }


}
