package com.eBayJP.kuromoji.app.sd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eBayJP.kuromoji.app.sd.service.KuromojiSdService;
import com.eBayJP.kuromoji.common.entity.TokenEntity;
import com.eBayJP.kuromoji.common.entity.request.KuromojiRequestEntity;
import com.eBayJP.kuromoji.common.version.ApiVersion;

/**
 * <pre>
 * com.eBayJP.kuromoji.app.sd.controller_KuromojiSdController.java
 * </pre>
 * @date : 2019. 7. 31.
 * @author : hychoi
 */
@RestController
@RequestMapping(value = "api/{version}/brand")
public class KuromojiSdController {
	
	private final Logger log = LoggerFactory.getLogger(KuromojiSdController.class);
	
	@Autowired
	private KuromojiSdService kuromojiSdService;
	
	@ApiVersion(1)
	@GetMapping(value = "/tokenize")
    public ResponseEntity< Map<String, Object>> tokenize(@RequestParam("text") String text) {
        
		log.info("[KuromojiSdController]: >>>> @GetMapping :text: {}", text);
		Map<String, Object> model = new HashMap<String, Object>();
		List<TokenEntity> EntityList = new ArrayList<TokenEntity>();
		
		Map<String, List<TokenEntity>> inlineModel = kuromojiSdService.tokenizeBrandDic(text);
		
		EntityList.addAll(inlineModel.get("tokens"));
		EntityList.addAll(inlineModel.get("otherTokens"));
		
		model.put("Entitys",EntityList);

		return new ResponseEntity< Map<String, Object> >(model, HttpStatus.OK);
    }
	
	@ApiVersion(1)
	@PostMapping(value = "/tokenize")
	public ResponseEntity<Map<String, Object>> tokenizeByPost(@RequestBody KuromojiRequestEntity requestKuromojiEntity) {
		
		log.info("[KuromojiSdController]: >>>> @@PostMapping :texts: {}", requestKuromojiEntity.getTexts());
		
		List<String> textList = requestKuromojiEntity.getTexts();
		Map<String, Object> model = new HashMap<String, Object>();
		List<TokenEntity> EntityList = new ArrayList<TokenEntity>();
		
		textList.forEach(text -> {
			
			StringBuilder sb = new StringBuilder(text.length());
			Map<String, List<TokenEntity>> inlineModel = new HashMap<String, List<TokenEntity>>();
			sb.append(text);
			inlineModel = kuromojiSdService.tokenizeBrandDic(sb.toString());
			
			log.info("[KuromojiSdController]: >>>> result: {}", inlineModel);
			
			EntityList.addAll(inlineModel.get("tokens"));
			EntityList.addAll(inlineModel.get("otherTokens"));
			
	    });
		
		model.put("Entitys",EntityList);
		
		return new ResponseEntity< Map<String, Object> >(model, HttpStatus.OK);
	}
}
