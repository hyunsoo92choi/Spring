package com.eBayJP.kuromoji.common.entity.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class BaseResponseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4771668053395559443L;

	@ApiModelProperty(required = true, value = "Log message")
	private String log = "";
	@ApiModelProperty(required = true, value = "Start date")
	private String startTime = "";
	@ApiModelProperty(required = true, value = "End date")
	private String endTime = "";
	@ApiModelProperty(required = true, value = "Process time [millisecond]")
	private String processTime = "";

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private boolean error = false;
	
}
