### 로그 사용시 장점

* 쓰레드 정보, 클래스 이름 등과 같은 부가 정보를 함께 볼수 있고 출력 모양을 조정할 수 있다
* 서버 환경에 따라 로그 레벨을 조절하여 설정할 수 있다 (운영에서는 `info`, 개발에서는 `debug` 와 같이)
* System out Console 에만 출력하는게 아닌, 파일/네트워크 등과 같은 별도의 위치에 로그를 남길 수 있다
    * 파일로 남길 때는 일별, 용량별 등의 조건으로 분할도 가능하다
* `System.out.print` 보다 성능이 좋다 (내부 버퍼링, 멀티 스레드 등)

### HTTP 응답

* 정적 리소스
    * 웹 브라우저에 정적인 HTML, css, js 을 제공
* 뷰 템플릿
    * 웹 브라우저에 동적인 HTML 을 제공
* HTTP 메시지
    * HTTP API 를 제공하는 경우 HTML 이 아닌 데이터를 전달하기 위해 HTTP Body 에 JSON 과 같은 형태의 데이터를 전송
  
#### 정적 리소스

* 스프링 부트의 경우 클래스패스의 다음 디렉토리에 있는 정적 리소스를 제공한다
    * `/static`, `/public`, `/resources`, `/META-INF/resources`
    * `/src/main/resources` 는 리소스를 보관한다 

#### 뷰 템플릿

* 뷰 템플릿을 거쳐서 HTML 이 생성되고 뷰가 응답을 만들어서 전달한다

### HTTP 메시지 컨버터

* 뷰 템플릿으로 HTML 을 생성해서 응답하는 것이 아닌, JSON 데이터를 메시지 바디에 직접 읽거나 쓰는 경우 HTTP 메시지 컨버터를 사용하면 편리하다
* `@ResponseBody` 사용
    * HTTP 의 Body 에 문자 내용을 직접 변환
    * `viewResolver` 대신 `HttpMessageConverter` 가 동작
    * 기본 문자처리: `StringHttpMessageConverter`
    * 기본 객체처리: `MappingJackson2HttpMessageConverter`
    * byte 등 처리: `HttpMessageConverter`
  
#### 스프링 부트 기본 메시지 컨버터

```
0 = ByteArrayHttpMessageConverter
1 = StringHttpMessageConverter
2 = MappingJackson2HttpMessageConverter
...
```

* 스프링 부트는 다양한 메시지 컨버터를 제공하며, 대상 클래스 타입과 미디어 타입을 체크하여 사용여부를 결정한다
    * `ByteArrayHttpMessageConverter`: `byte[]` 데이터를 처리한다
        * 클래스 타입: byte[], 미디어 타입: \*/*
    * `StringHttpMessageConverter`: `String` 문자로 데이터를 처리한다
        * 클래스 타입: String, 미디어 타입: \*/*
    * `MappingJackson2HttpMessageConverter`: application/json
        * 클래스 타입: 객체 또는 HashMap, 미디어 타입: application/json
  
#### HTTP 요청 데이터 읽기

* 컨트롤러에서 `@RequestBody`, `HttpEntity` 파라미터를 사용
* 메시지 컨버터가 메시지를 읽을 수 있는지 확인하기 위해 `canRead()` 호출
    * 대상 클래스 타입을 지원하는가
    * HTTP 요청의 Content-Type 미디어 타입을 지원하는가
* `canRead()` 조건을 만족하면 `read()` 를 호출해서 객체를 생성하고 변환

#### HTTP 응답 데이터 생성

* 컨트롤러에서 `@ResponseBody`, `HttpEntity` 로 값이 반환
* 메시지 컨버터가 메시지를 쓸 수 있는지 확인하기 위해 `canWrite()` 를 호출
    * 대상 클래스 타입을 지원하는가
    * HTTP 요청의 Accept 미디어 타입을 지원하는가
* `canWrite()` 조건을 만족하면 `write()` 를 호출해서 HTTP 응답 메시지 바디에 데이터를 생성