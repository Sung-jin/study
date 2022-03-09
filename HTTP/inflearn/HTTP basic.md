## HyperText Transfer Protocol

* HTML, TEXT, IMAGE, 음성, 영상, 파일, JSON, XML 등등 거의 모든 형태의 데이터 전송이 가능하다
* 서버간에 데이터를 주고 받을 때도 대부분 HTTP 를 사용한다

### HTTP 역사

* HTTP/0.9: 1991 년 GET 메서드만 지원하였으며, HTTP 헤더가 없다
* HTTP/1.0: 1996 년 메서드와 헤더가 추가
* HTTP/1.1: 1997 년 가장 많이 사용
    * RFC2068(1997) -> RFC2616(1999) -> RFC7230 ~ 7235 (2014)
* HTTP/2: 2015 년 성능 개선
* HTTP/3 진행중이며, TCP 대신 UPD 사용 및 성능 개선

### 기반 프로토콜

* TCP: HTTP/1.1, HTTP/2
* UDP: HTTP/3
* 현재는 HTTP/1.1 을 주로 사용하며, HTTP/2, HTTP/3 도 점점 증가하고 있다

### HTTP 특징

* 클라이언트 서버 구조
* 무상태 프로토콜(stateless), 비연결성
* HTTP 메시지
* 단순함, 확장 가능

## 클라이언트 서버 구조

* Request/Response 구조
* 클라이언트는 서버에 요청을 보내고 응답을 대기한다
* 서버가 요청에 대한 결과를 만들어서 응답한다

## 무상태 프로토콜(stateless)

* 서버가 클라이언트의 상태를 보존하지 않는다
* 장점: 서버 확장성이 높다(scale-out)
* 단점: 클라이언트가 추가 데이터 전송이 필요하다

### stateless vs stateful

* stateful 은 상태를 유지한다
    * 상태를 유지하기 때문에, 중간에 대상이 변하면 안된다
    * 항상 같은 서버가 유지되어야 한다
* stateless 는 상태를 유지하지 않는다
    * 중간에 대상이 변하더라도 상관이 없다
    * 즉, 갑자기 클라이언트 요청이 많아지더라도 서버를 많이 투입하는 등의 방식으로 쉽게 대응이 가능하다
    * 무상태는 응답 서버를 쉽게 변경할 수 있다
    * scale-out(수평 확장)에 유리하다
    
### stateless 한계

* 모든 것을 무상태로 설계 할 수 없는 경우도 있을 수 있다
    * 로그인이 필요 없는 단순한 서비스 화면 소개 등은 무상태로 동작하기에 충분하다
    * 로그인 또는 로그인이 필요한 화면 등은 상태를 유지해야 한다
* 일반적으로 브라우저 쿠키와 서버 세션등을 사용해서 상태를 유지하며, 상태 유지는 최소한만 사용한다

## 비 연결성

* HTTP 는 기본적으로 연결을 유지하지 않는 모델이다
* 일반적으로 초 단위 이하의 빠른 속도로 응답한다
* 수많은 클라이언트가 서비스를 사용해도 실제 서버에서 동시에 처리하는 요청은 수십개 이하로 매우 작다
* 비 연결인 상태면 서버 자원을 매우 효율적으로 사용할 수 있다

### 비 연결성 한계와 극복

* TCP/IP 연결을 새로 맺어야 한다
    * 3 way handshake 시간이 추가된다
* 웹 브라우저로 사이트를 요청하면 HTML 뿐 아니라 js/css/image 등등 수 많은 자원이 함께 다운로드 된다
    * 이러한 문제는 현재 HTTP 지속 연결(Persistent Connections)로 해결하고 있다

## HTTP 메시지

* HTTP 요청 메시지
    * 아래와 같은 형태로 요청한다
    * body 본문이 존재할 수 있다

```
GET /search?q=hello&hl=ko HTTP/1.1
Host: www.google.com

<html>
  ...
</html>
```

* HTTP 응답 메시지

```
HTTP/1.1 200 OK
Content-Type: text/html;charset=UTF-8
Content-Length: 1234

<html>
  ....
<html>
```

* HTTP 메시지 구조

```
start-line 시작 라인
*(header 헤더)
empty line 공백 라인 (CRLF)
[message body]
```

### 요청 메시지

* 시작라인
    * start-line: request-line
        * request-line: method SP request-target SP HTTP-version CRLF
        * SP: 공백
        * CRLF: 줄바꿈
* HTTP 메서드
    * GET
    * POST
    * PUT
    * DELETE
    * 등등
    * 서버가 수행해야 할 동작을 지정한다
        * GET: 조회, POST: 요청 내역 처리...
* 요청 대상
    * 절대경로[?쿼리]
    * 절대경로는 `/` 로 시작하는 경로이다
  
### 응답 메시지

* 시작라인
    * start-line: status-line
        * status-line: HTTP-version SP status-code SP reason-phrase CRLF
* HTTP 상태 코드
    * 요청 성공, 실패 등을 나타냄
    * 200: 성공, 400: 클라이언트 요청 오류, 500: 서버 내부 오류...
* 이유구문: 사람이 애해할 수 있는 짧은 상태 코드 설명 글

### HTTP 헤더

* header-field = field-name ":" OWS field-value OWS
    * OWS: 띄어쓰기 허용
    * field-name 의 대소문자는 구분하지 않는다
* 용도
    * 메시지 바디의 내용, 메시지 바디의 크기, 인증, 요청 클라이언트 정보 등등 HTTP 전송에 필요한 모든 부가정보
    * [표준 헤더가 너무 많다](https://en.wikipedia.org/wiki/List_of_HTTP_header-fields)
    * 필요시 임의의 헤더를 추가할 수 있다
  
### 메시지 바디

* HTML 문서, 이미지, 영상, JSON 등 byte 로 표현할 수 있는 모든 실제 전송할 데이터
