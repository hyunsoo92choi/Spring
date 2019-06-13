package com.eBayJP.kuromoji.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.eBayJP.kuromoji.util.FileUtil;

@RestController
@RequestMapping(value = "/ebayjp-dic")
public class HomeRestController {

	@RequestMapping(value = "/v1/tokenize", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> tokenizeFromFile() throws IOException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		String url;
		
		// File Read
		List<String> goodsNmList = FileUtil.getGoodsNmList();
		Long startTime = System.currentTimeMillis();
		RestTemplate restTemplate = new RestTemplate();
		System.out.println("상품수 : " + goodsNmList.size());
		
		for (String goodsNm : goodsNmList) {
			url = "http://extract.qoo10.jp/TermExtractorServlet?ext=JAPANESE&ext_opt=NONE&symbol=ON&input=" + goodsNm;
			
			HttpHeaders headers = new HttpHeaders();
	        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
	        HttpEntity<String> response =
	                restTemplate.exchange(url,HttpMethod.GET, entity, String.class);
	        
		}
		
		Long endTime = System.currentTimeMillis();
		
		Long processTime = endTime - startTime;
		
//		model.put("tokens", rtnTokens);
		model.put("startTime",startTime.toString());
		model.put("endTime",endTime.toString());
		model.put("processTime",processTime.toString());
		System.out.println(processTime);
		return new ResponseEntity< Map<String, Object> >(model, HttpStatus.OK);
	
	}
}
