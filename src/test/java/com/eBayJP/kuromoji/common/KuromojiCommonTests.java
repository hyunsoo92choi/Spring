package com.eBayJP.kuromoji.common;



import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.eBayJP.kuromoji.common.code.mapper.EnumMapper;
import com.eBayJP.kuromoji.common.code.mapper.EnumValue;
import com.eBayJP.kuromoji.common.code.pos.PosType;

//import com.eBayJP.kurolib.commonCode.codeType.PartOfSpeech;
//import com.eBayJP.kurolib.commonCode.mapper.EnumModel;

/**
 * <pre>
 * com.eBayJP.kuromoji.common_KuromojiCommonTests.java
 * </pre>
 * @date : 2019. 6. 12. 오후 12:10:37
 * @author : hychoi
 */
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = "com.eBayJP.kuromoji")
public class KuromojiCommonTests {
	
	private EnumMapper enumMapperFactory;
	
	@Before
    public void setup () {
        enumMapperFactory = new EnumMapper();
    }
	
	@Test
    public void PosType_show() throws Exception {
        //given
        final String KEY_POS = "POS";
        enumMapperFactory.put(KEY_POS, PosType.class);

        //when
        List<EnumValue> enumValues = enumMapperFactory.get(KEY_POS);

        //then
        assertThat(enumValues.size()).isEqualTo(11);

//        enumValues.forEach(e -> System.out.println(e.toString()));
    }
	
	@Test
    public void getAll() throws Exception {
        //given
		final String KEY_POS = "POS";

		enumMapperFactory.put(KEY_POS, PosType.class);

        //when
        Map<String, List<EnumValue>> types = enumMapperFactory.getAll();

        //then
        types.get(KEY_POS).forEach(e -> System.out.println(e.toString()));
        
    }
}
