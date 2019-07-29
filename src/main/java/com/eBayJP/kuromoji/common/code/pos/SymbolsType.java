package com.eBayJP.kuromoji.common.code.pos;

import com.eBayJP.kuromoji.common.code.mapper.EnumType;

/**
 * <pre>
 * com.eBayJP.kuromoji.common.code.pos_SymbolsType.java
 * </pre>
 * @date : 2019. 7. 29.
 * @author : hychoi
 */
public enum SymbolsType implements EnumType {
	QUES("?")
  ,	EXCL("!")
  ,	ATSI("@")
  ,	PERC("%")
  ,	AMPE("&")
  ,	SHAR("#")
  ,	SLAS("/")
  ,	PERI(".")
  ,	COMM(",")
  ,	EQUA("=")
  ,	PLUS("+")
  ,	MINU("-")
  ,	MULT("×")
  ,	DIVI("÷")
  ,	LPAR("(")
  ,	RPAR(")")
  ,	CELS("℃")
  ,	DEGR("°")
  ,	GRAV("・")
  ;
	
	private String codeName;
	
	SymbolsType(String codeName) {
		this.codeName = codeName;
	}
	
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return  name();
	}

	@Override
	public String getCodeName() {
		// TODO Auto-generated method stub
		return codeName;
	}

}
