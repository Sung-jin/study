## Http Header

* HTTP 전송에 필요한 모든 부가정보
  * 메시지 바디의 내용, 크기, 압축, 인증, 요청 클라이언트, 서버 정보, 캐시 관리 정보...
* [표준 헤더의 종류는 다양하다](https://en.wikipedia.org/wiki/List_of_HTTP_header_fields) 
* 필요시 임의의 헤더 추가가 가능하다


### RFC2616(과거)

* General 헤더
    * 메시지 전체에 적용되는 정보
    * ex) `Connection: closee`
* Request 헤더
    * 요청 정보
    * ex) `User-Agnet: Mozilla/5.0 ...`
* Response 헤더
    * 응답 정보
    * ex) `ServerL Apache`
* Entity 헤더
    * 엔티티 바디 정보
    * ex) `Content-Type: text/html`
* 메시지 본문
    * 엔티티 본문을 전달하는데 사용
    * 엔티티 본문은 요청이나 응답에서 전달할 실제 데이터
    * 엔티티 헤더는 엔티티 본문의 데이터를 해석할 수 있는 정보를 제공
    * 데이터 유형: html, json...
* 2014 년에 RFC7230~7235 가 등장하면서 폐기된다

### RFC723x

* 엔티티(Entity) -> 표현(Representation)
    * Representation: Representation Metadata + Representation Data
* 메시지 본문을 통해 표현 데이터 전달
    * 메시지 본문은 payload
* 표현은 요청이나 응답에서 전달할 실제 데이터를 의미한다
* 표현 헤더는 표현 데이터를 해석할 수 있는 정보를 제공한다
    * 데이터 유형, 길이, 압축 정보...

### 표현

* 표현 헤더는 전송/응답 둘다 사용된다
* Content-Type
    * 표현 데이터 형식
    * 미디어 타입, 문자 인코딩
    * ex) `text/html; charset=urf-8`/`application/json`/`image/png`
* Content-Encoding
    * 표현 데이터의 압축 방식
    * 표현 데이터를 압축하기 위해서 사용이 되며, 데이터를 전달하는 곳에서 압축 후 인코딩 헤더에 추가하고 읽는 쪽에서 해당 정보로 압축을 해제한다
    * ex) `gzip`/`deflate`/`identity`
* Content-Language
    * 표현 데이터의 자연 언어
    * ex) `ko`/`en`/`en-US`
* Content-Length
    * 표현 데이터(payload) 길이 (바이트 단위)
    * Transfer-Encoding 을 사용하면 Content-Length 를 사용하면 안된다
    
### 콘텐트 협상

* 클라이언트가 선호하는 표현 요청
* 협상 헤더는 요청시에만 사용된다
* Accept: 클라이언트가 선호하는 미디어 타입 전달
* Accept-Charset: 클라이언트가 선호하는 문자 인코딩
* Accept-Encoding: 클라이언트가 선호하는 압축 인코딩
* Accept-Language: 클라이언트가 선호하는 자연 언어

#### 협상과 우선순위 1

* Quality Values(q)
* 0~1 을 사용하며, 클수록 높은 우선순위를 가지고 생략하면 1을 기본값으로 가진다
* ex) `Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7`

#### 협상과 우선순위 2

* 구체적인 것이 우선이다
* `Accept: text/*, text/plain, text/plain;format=flowed, */*`
    1. `text/plain;format=flowed`
    1. `text/plain`
    1. `text/*`
    1. `*/*`

#### 협상과 우선순위 3

* 구체적인 것을 기준으로 미디어 타입을 맞춘다
* `Accept: text/*;q=0.3, text/html;q=0.7, text/html;level=1, text/html;level=2;q=0.4, */*;q=0.5`
    * `text/html;level=1`: 1
    * `text/html`: 0.7
    * `text/plain`: 0.3 (`text/*` 과 매칭되기 때문)
    
### 단순 전송

* 전달하는 내용의 길이를 알고서 단순히 한번에 전송할 때
    * `Content-Length` 정보와 함께 전달한다
    
### 압축 전송

* 압축 정보와 함께 내용의 길이를 전달한다
    * `Content-Encoding` 값도 같이 전달하여 어떠한 방식으로 압축하였는지에 대한 정보도 보내야 한다
    
### 분할 전송

* `Transfer-Encoding` 값을 같이 전달하여 분할 전송임을 전달해야 한다
    * chunked 가 있으며, 분할하여 보내기 때문에 `Content-Length` 를 보내면 안된다
    
### 범위 전송

* `Content-Range: bytes 1001-2000 / 2000` 형태로 보낸다
    * 1001-2000: 현재 보내고 있는 부분
    * 2000: 전체 길이

### 일반 정보

* From
    * 유저 에이전트의 이메일 정보
    * 일반적으로 잘 사용되지 않으나, 검색 엔진 같은 곳에서 주로 사용된다
    * 요청에서 사용한다
* Referer
    * 이전 웹 페이지 주소
    * 현재 요청된 페이지의 이전 웹 페이지 주소이며, Referer 를 통해 유입 경로 분석이 가능하다
    * 요청에서 사용된다
* User-Agent
    * 유저 에이전트 어플리케이션 정보
    * 클라이언트의 어플리케이션 정보 (웹 브라우저...)
    * 통계 정보
    * 어떤 종류의 브라우저에서 장애가 발생하는지 파악이 가능하다
    * 요청에서 사용된다
* Server
    * 요청을 처리하는 ORIGIN 서버의 소프트웨어 정보
    * 응답에서 사용한다
* Date
    * 메시지가 발생한 날짜와 시간
    * 응답에서 사용된다
    
### 특별한 정보

* Host
    * 요청한 호스트 정보 (도메인)
    * 필수값이다
    * 하나의 서버가 여러 도메인을 처리해야 할 때 (하나의 IP 에 여러 도메인이 적용되었을 떄)
    * 요청에서 사용한다
* Location
    * 201 에서 Location 헤더가 존재하면, 생성된 리소스 URI
    * 3xx 에서 Location 헤더가 존재하면, 자동으로 리다이렉션하기 위한 대상 리소스
* Allow
    * 허용 가능한 HTTP 메서드
    * 405 (Method Not Allowed) 에서 응답에 포함해야 한다
* Retry-After
    * 유저 에이전트가 다음 요청을 하기까지 기다려야 하는 시간
    * 503(Service Unavailable) 에서 서비스가 언제까지 불능인지 알려줄 수 있음

### 인증

* Authorization
    * 클라이언트 인증 정보를 서버에 전달
    * `Authorization: Basic xxxx` 와 같이 사용된다
    * 인증을 하는 방법은 여러가지가 존재한다
* WWW-Authentication
    * 리소스 접근시 필요한 인증 방법 정의
    * 401 Unauthorized 응답과 함께 사용된다

### 쿠키

* Set-Cookie: 서버에서 클라이언트로 쿠키 전달(응답)
    * 해당 값을 클라이언트는 쿠키 저장소에 저장을 한다
* Cookie: 클라이언트가 서버에서 받은 쿠키를 저장하고 HTTP 요청시 서버로 전달
    * 리소스를 요청할 때 마다 쿠키 저장소에 있는 값을 서버에 전달한다
    * 모든 요청에 쿠키 정보를 자동으로 포함한다
        * 이로인해 네트워크 트래픽이 추가로 유발한다
        * 최소한의 정보만 사용해야 한다 (세션 id, 인증 토큰...)
        * localStorage/sessionStorage 등을 통해 웹 브라우저 내부에 데이터를 저장할 수 있다
* 사용처
    * 사용자 로그인 세션 관리
    * 광고 정보 트래킹
* 보안에 민감한 데이터는 저장하면 안된다

#### 쿠키 생명주기

* `Set-Cookie: expires=Sat, 26-...` 와 같이 expires 정보로 만료기간을 설정할 수 있으며, 만료일이 지나면 쿠키가 삭제된다
* `Set-Cookie: max-age=3600` 와 같이 max-age 를 통해 만료 시간을 지정할 수 있으며, 해당 시간이 지나거나 음수이면 쿠키가 삭제된다
* 세션쿠키는 만료 날짜를 생략하면 브라우저 종료시 까지만 유지된다
* 영속 쿠키는 만료 날짜를 입력하면 해당 날짜까지 유지된다

#### 쿠키-도메인

* `domain=example.org`
* 명시
    * 명시한 문서 기준 도메인 + 서브 도메인 포함
    * `domain=example.org`: example.org, dev.example.org 등에 접근 가능
* 생략
    * 현재 문서 기준 도메인만 적용
    * `example.org`: example.org 에서만 쿠키 접근

#### 쿠키-경로

* `path=/home`
* 해당 경로를 포함한 하위 경로 페이지만 쿠키 접근이 가능하다
* 일반적으로 `path=/` 형태로 루트를 지정한다

#### 쿠키-보안

* Secure
    * 쿠키는 http/https 를 구분하지 않고 전송
    * Secure 를 적용하면 Https 에서만 전송된다
* HttpOnly
    * XSS 공격 방지
    * 자바스크립트에서 접근 불가
    * HTTP 전송에만 사용
* SameSite
    * XSRF 공격 방지
    * 요청 도메인과 쿠키에 설정된 도메인이 같은 경우만 쿠키를 발송
