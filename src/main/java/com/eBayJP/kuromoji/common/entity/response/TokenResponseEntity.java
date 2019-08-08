package com.eBayJP.kuromoji.common.entity.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.eBayJP.kuromoji.common.entity.TokenEntity;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class TokenResponseEntity implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3415878730381986845L;
	
	private List<TokenEntity> tokens = new ArrayList<TokenEntity>();
	private String processTime = "";

	public TokenResponseEntity(Map<String, Object> model) {
		this.tokens = (List<TokenEntity>) model.get("tokens");
		this.processTime = (String) model.get("processTime");
	}
}
