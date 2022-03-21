## HttpServletRequest 개요

* HttpServletRequest 역할
    * HTTP 요청 메시지를 개발자가 직접 파싱해서 사용해도 되지만, 매우 불편하다
    * 이러한 불편을 서블릿을 통해 HTTP 요청 메시지를 편리하게 사용할 수 있도록 HTTP 요청 메시지를 파싱하고, 그 결과를 `HttpServletRequest` 객체에 담아서 제공한다
    * `HttpServletRequest` 객체는 여러가지 부가기능도 제공한다
        * 해당 HTTP 요청이 시작부터 끝날 때 까지 유지되는 임시 저장소 기능을 제공
            * 저장: `request.setAttribute(name, value)`
            * 조회: `request.getAttribute(name)`
        * 세션 관리 기능
            * `request.getSession(create: true)`
* `HttpServlerRequest/Response` 를 사용할 때 가장 중요한 점은 해당 객체들이 HTTP 요청/응답 메시지를 편리하게 사용하도록 도와주는 개체라는 점이다
    * 즉, 해당 기능에 대해 깊이있는 이해를 하려면 HTTP 스펙이 제공하는 요청/응답 메시지 자체를 이해해야 한다

```
POST /save HTTP/1.1
Host: localhost: 8080
Content-Type: application/x-www-form-urlencoded

username=foo&age=20
```

* HTTP 요청 메시지
    * start line
        * HTTP 메소드
        * URL
        * 쿼리 스트링
        * 스키마, 프로토콜
    * 헤더
    * 바디
        * form 파라미터 형식
        * message body 데이터

### HTTP 요청 데이터

* GET - 쿼리 파라미터
    * `url?param1=xxx&param2=xxx` 와 같은 형태로 사용된다
* POST - HTML Form
    * content-type: application/x-www-form-urlencoded
    * 메시지 바디에 쿼리 파라미터 형식으로 전달한다
    * `param1=xxx&param2=xxx`
* HTTP message body 에 데이터를 담아서 요청
    * HTTP API 에서 주로 사용된다
    * JSON, XML, TEXT...
    * `POST`/`PUT`/`PATCH`

### HTTP 응답

* HttpServletResponse 를 통해 HTTP 응답 메시지를 생성할 수 있다
    * HTTP 응답코드를 지정
    * 헤더 생성
    * 바디 생성
    * 그외 편의 기능 (Content-Type, 쿠키, Redirect...)
