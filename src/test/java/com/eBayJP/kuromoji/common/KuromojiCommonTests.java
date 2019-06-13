package com.eBayJP.kuromoji.common;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.eBayJP.kurolib.commonCode.codeType.PartOfSpeech;
import com.eBayJP.kurolib.commonCode.mapper.EnumModel;

/**
 * <pre>
 * com.eBayJP.kuromoji.common_KuromojiCommonTests.java
 * </pre>
 * @date : 2019. 6. 12. 오후 12:10:37
 * @author : hychoi
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KuromojiCommonTests {

	@Test
    public void EnumModelTypeCheck() {
		
        List<EnumModel> enumModels = new ArrayList<>();
        enumModels.add(PartOfSpeech.PartOfSpeechType.VERB);
        enumModels.add(PartOfSpeech.PartOfSpeechType.NOUN);

        assertThat(enumModels.get(0).getValue(), is("動詞"));
        assertThat(enumModels.get(1).getValue(), is("名詞"));
    }
}
