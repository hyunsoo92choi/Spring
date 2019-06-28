package com.eBayJP.kuromoji.common.entity.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <pre>
 * com.eBayJP.kuromoji.entity.request_KuromojiRequestEntity.java
 * </pre>
 * @date : 2019. 5. 30. 오후 2:40:28
 * @author : hychoi
 */
@ApiModel
@Data
public class KuromojiRequestEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6536757080348425840L;

	@ApiModelProperty(required=true, value="texts [max 1MB]")
	List<String> texts= new ArrayList<>();
}
