package com.eBayJP.kuromoji.app.analytics.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.eBayJP.kuromoji.app.analytics.service.KuromojiAnalyticsService;
import com.eBayJP.kuromoji.common.config.KuromojiConfiguration;
import com.eBayJP.kuromoji.common.entity.request.KuromojiRequestEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@WebMvcTest({KuromojiAnalyticsController.class})
@EnableSpringDataWebSupport
@ComponentScan(basePackages = "com.eBayJP.kuromoji")
public class KuromojiAnalyticsControllerTest {

	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	private RestDocumentationResultHandler document;
	
	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
	        MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8 );
	
	private static final String ROOT_URI = "/api/v1/analytics";
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private KuromojiAnalyticsService kuromojiAnalyticsService;

	private KuromojiRequestEntity requestDto;
	StringBuilder sb = new StringBuilder();
	
	

	
	
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
        List<String> texts = new ArrayList<String>();
        
        
        sb.append("美の白くま10日間のみと肌が白く");
        sb.append(System.lineSeparator());
        sb.append("【ニコン】NIKON AF-S NIKKOR 24-70mm F2.8G ED ←【新品");
        sb.append(System.lineSeparator());
        sb.append("敏感肌をしっかり防ぐ、サンベアーズセンシティブS30ml");
        sb.append(System.lineSeparator());
        
        texts.add(sb.toString());
        
//        requestDto = new KuromojiRequestEntity(texts);
    }
	
	@Test
	public void 토큰화_성공() throws Exception {
		doResultActions("/brandDic/tokenize", requestDto).andExpect(status().isOk())
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
	
	private ResultActions doResultActions(String url, KuromojiRequestEntity requestDto) throws Exception {
		String resultJson = new ObjectMapper().writeValueAsString(requestDto);
		RequestBuilder rb = MockMvcRequestBuilders.post(ROOT_URI + url).contentType(APPLICATION_JSON_UTF8)
				.content(resultJson);
		return mvc.perform(rb).andDo(print());
	}
}