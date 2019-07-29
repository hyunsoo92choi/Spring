package com.eBayJP.kuromoji.common.code.pos;

import com.eBayJP.kuromoji.common.code.mapper.EnumType;

/**
 * <pre>
 * com.eBayJP.kuromoji.common.code.pos_NounType.java
 * </pre>
 * @date : 2019. 7. 18.
 * @author : hychoi
 */
public enum NounType implements EnumType {
	
	NORMAL("一般")
  ,	PROPER("固有名詞")
  ,	CONNECTION("サ変接続")
  ,	NUMBER("数")
  ,	POSIBLE("副詞可能");	
	
	private String codeName;

	NounType(String codeName) {
		this.codeName = codeName;
	}

	@Override
	public String getCode() {
		return  name();
	}
	
	@Override
    public String getCodeName() {
        return codeName;
    }

}
