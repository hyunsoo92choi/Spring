package com.eBayJP.kuromoji.common.entity.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.eBayJP.kuromoji.common.entity.TokenEntity;

public class TokenResponseEntity implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3415878730381986845L;
	
	private List<TokenEntity> tokens = new ArrayList<TokenEntity>();
	private String log = "";
	private String startTime = "";
	private String endTime = "";
	private String processTime = "";

	/* Constructor */
	public TokenResponseEntity() {
	}

	public TokenResponseEntity(Map<String, Object> model) {
		this.tokens = (List<TokenEntity>) model.get("tokens");
		this.log = (String) model.get("log");
		this.startTime = (String) model.get("startTime");
		this.endTime = (String) model.get("endTime");
		this.processTime = (String) model.get("processTime");
	}
}
