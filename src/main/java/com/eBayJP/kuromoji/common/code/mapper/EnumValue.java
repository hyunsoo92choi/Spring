package com.eBayJP.kuromoji.common.code.mapper;

import lombok.Getter;
import lombok.ToString;

/**
 * <pre>
 * com.eBayJP.kuromoji.common.code.mapper_EnumValue.java
 * </pre>
 * @date : 2019. 6. 13. 오전 11:57:43
 * @author : hychoi
 */
@Getter
@ToString
public class EnumValue {

	private String code;
    private String codeName;

    public EnumValue(EnumType enumType) {
        code = enumType.getCode();
        codeName = enumType.getCodeName();
    }
}
