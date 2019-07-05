package com.eBayJP.kuromoji.common.code.pos;

import com.eBayJP.kuromoji.common.code.mapper.EnumType;

import lombok.Getter;

/**
 * <pre>
 * com.eBayJP.kuromoji.common.code.pos_PosType.java
 * </pre>
 * @date : 2019. 6. 13. 오전 11:35:57
 * @author : hychoi
 */
@Getter
public enum PosType implements EnumType {
	/**
	 * <pre>
	 * 1. 개요 : 품사 타입 정의
	 * 2. 처리내용 : 품사 정의
	 * 	VERB(동사),ADJE(형용사),ADVB(형용동사),NOUN(명사),ADVE(부사)
	 * 	CONJ(접속사),POST(조사),AUXI(조동사),ADJW(연체사),INTE(감동사)
	 * </pre>
	 * @date : 2019. 6. 13.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 13.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 */
	
	VERB("動詞")
  ,	ADJE("形容詞")
  ,	ADVB("形容動詞")
  , NOUN("名詞")
  , ADVE("副詞")
  , CONJ("接續詞")
  , POST("助詞")
  , AUXI("助動詞")
  , ADJW("連体詞")
  , INTE("感動詞")
  , CUST("CUSTOM");	

	private String codeName;

	PosType(String codeName) {
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
