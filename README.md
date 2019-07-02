# kuromoji-rest
일본어 형태소 분석기인 kuromoji-ipadic 를 Web API화한 Java 프로젝트 입니다.

## Getting Started
형태소 분석 이란 형태소를 비롯하여, 어근, 접두사/접미사, 품사(POS, part-of-speech) 등 다양한 언어적 속성의 구조를 파악하는 것입니다.
품사 태깅 은 형태소의 뜻과 문맥을 고려하여 그것에 마크업을 하는 일입니다.
본 프로젝트는 kuromoji-ipadic 에서 제공하는 사전정보 이외에 사용자가 등록한 사전정보를 이용하여 품사 태깅한 결과를 얻는 것을 목표로 합니다.

### 실행환경
- JDK 1.8이상
- Maven 3이상

### Build
Git Download or Clone
mvn clean install
mvn clean package
java -jar kuromoji-rest-server.jar

## APIs
### Text 품사 Tagging 결과
<blockquote>
<p>URL : /api/{version}/analytics/tokenize, v2 </p>
<p>Method : GET </p>
</blockquote>

예시
https://kuromoji-rest.herokuapp.com/kuromoji-ipadic/v1/tokenize?text=ZEN FOOD ZE:A YSL サーモス

### Texts 품사 Tagging 결과
<blockquote>
<p>URL : /api/{version}/analytics/tokenize, v1 </p>
<p>Method : POST </p>
</blockquote>
<table>
<thead>
<tr>
<th align="left">Property</th>
<th align="left">Data Type</th>
<th align="left">Mandatory</th>
</tr>
</thead>
<tbody>
<tr>
<td align="left">texts</td>
<td align="left">List<String></td>
<td align="left">Y</td>
</tr>
</tbody>
</table>




## 마치며
파일럿 형식으로 만들어 본 프로젝트로 API 형태로 제공하면, 고사양의 CPU와 메모리를 이용 할 수 없는 단말에서도 사용할 수 있으며
RestClient로 결과를 보기 쉽도록 개발 하였습니다. 
향후 Post 방식으로 Text를 받을 수 있도록 할 예정이며(완료), 
검색 모드를 선택하여 Tokenizer를 생성 할 수 있도록 할 예정(완료) 입니다.
추가사항s:
    Tokenizer를 요청할때마다 생성하였으나, Bean으로 등록하여 Container에 위임하였습니다.
    사전정보를 주기적으로 갱신할 수 있게 Schedule를 추가하였습니다(매 정시 사전정보 갱신).
    사전정보 갱신 시 Tokenizer 또한 Refresh 됩니다.
    rest-doc 이용한 api 명세서 생성기능을 추가하였습니다.

* Kuromoji is licensed under the Apache License, Version 2.0
