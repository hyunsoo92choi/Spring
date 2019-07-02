package com.eBayJP.kuromoji.app.analytics.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.eBayJP.kuromoji.common.TestConfig;

/**
 * <pre>
 * com.eBayJP.kuromoji.app.analytics.controller_KuromojiAnalyticsControllerTest.java
 * </pre>
 * @date : 2019. 7. 1.
 * @author : hychoi
 */
/**
 * 스프링 기본 컨텍스트를 사용 하기 위한 어노테이션
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
public class KuromojiAnalyticsControllerTest {

	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	private RestDocumentationResultHandler document;
    
	@Before
    public void setUp() {
        this.document = document(
                "{class-name}/{method-name}",
                preprocessResponse(prettyPrint())
        );
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply( documentationConfiguration(this.restDocumentation) )
                .alwaysDo(document)
                .build();
    }
	
	@Test
    public void 토큰화_리스트_조회() throws Exception {
        mockMvc.perform(
                get("/api/{version}/analytics/tokenize","v2")
                        .param("text", "ドリップコーヒー")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestParameters(
                                parameterWithName("text").description("토큰화 할 text")
                        ),
                        responseFields(
                        		fieldWithPath("[].surface").description("surface"),
                        		fieldWithPath("[].position").description("position"),
                        		fieldWithPath("[].partOfSpeechLevel1").description("partOfSpeechLevel1"),
                        		fieldWithPath("[].partOfSpeechLevel2").description("partOfSpeechLevel2"),
                        		fieldWithPath("[].partOfSpeechLevel3").description("partOfSpeechLevel3"),
                        		fieldWithPath("[].partOfSpeechLevel4").description("partOfSpeechLevel4"),
                        		fieldWithPath("[].conjugationType").description("conjugationType"),
                        		fieldWithPath("[].conjugationForm").description("conjugationForm"),
                        		fieldWithPath("[].reading").description("reading"),
                        		fieldWithPath("[].baseForm").description("baseForm"),
                        		fieldWithPath("[].pronunciation").description("pronunciation"),
                        		fieldWithPath("[].known").description("known"),
                        		fieldWithPath("[].allFeatures").description("allFeatures") )                        		
						));
    }
	
	@Deprecated
	private static final List<FieldDescriptor> TOKENS_FIELD_DESCRIPTORS = Arrays.asList(
			PayloadDocumentation.fieldWithPath("surface").description("Surface")
					.type(JsonFieldType.STRING),
			PayloadDocumentation.fieldWithPath("position").description("position")
					.type(JsonFieldType.NUMBER),
			PayloadDocumentation.fieldWithPath("partOfSpeechLevel1").description("partOfSpeechLevel1")
					.type(JsonFieldType.STRING),
			PayloadDocumentation.fieldWithPath("partOfSpeechLevel2").description("partOfSpeechLevel2")
					.type(JsonFieldType.STRING),
			PayloadDocumentation.fieldWithPath("partOfSpeechLevel3").description("partOfSpeechLevel3")
					.type(JsonFieldType.STRING),
			PayloadDocumentation.fieldWithPath("partOfSpeechLevel4").description("partOfSpeechLevel4")
					.type(JsonFieldType.STRING),
			PayloadDocumentation.fieldWithPath("conjugationType").description("conjugationType")
					.type(JsonFieldType.STRING),
			PayloadDocumentation.fieldWithPath("conjugationForm").description("conjugationForm")
					.type(JsonFieldType.STRING),

			PayloadDocumentation.fieldWithPath("reading").description("reading")
					.type(JsonFieldType.STRING),
			PayloadDocumentation.fieldWithPath("baseForm").description("baseForm")
					.type(JsonFieldType.STRING),
			PayloadDocumentation.fieldWithPath("pronunciation").description("pronunciation")
					.type(JsonFieldType.STRING),
			PayloadDocumentation.fieldWithPath("allFeaturesArray").description("Array of all Features")
					.type(JsonFieldType.ARRAY),
			PayloadDocumentation.fieldWithPath("known").description("known")
					.type(JsonFieldType.BOOLEAN),
			PayloadDocumentation.fieldWithPath("user").description("user")
					.type(JsonFieldType.BOOLEAN),					
			PayloadDocumentation.fieldWithPath("allFeatures").description("allFeatures")
					.type(JsonFieldType.STRING));
}
