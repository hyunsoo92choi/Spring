package com.eBayJP.kuromoji.common.entity.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class BaseResponseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4771668053395559443L;

	private String log = "";
	private String startTime = "";
	private String endTime = "";
	private String processTime = "";

	@JsonIgnore
	private boolean error = false;
	
}
