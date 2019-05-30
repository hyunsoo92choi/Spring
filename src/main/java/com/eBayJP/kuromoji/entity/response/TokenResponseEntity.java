package com.eBayJP.kuromoji.entity.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.eBayJP.kuromoji.entity.TokenEntity;

import io.swagger.annotations.ApiModelProperty;

public class TokenResponseEntity implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3415878730381986845L;
	
	@ApiModelProperty(required = false, value = "Tokens")
	private List<TokenEntity> tokens = new ArrayList<TokenEntity>();
	@ApiModelProperty(required = true, value = "Log message")
	private String log = "";
	@ApiModelProperty(required = true, value = "Start date")
	private String startTime = "";
	@ApiModelProperty(required = true, value = "End date")
	private String endTime = "";
	@ApiModelProperty(required = true, value = "Process time [millisecond]")
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
