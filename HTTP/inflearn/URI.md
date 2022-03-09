## Uniform Resource Identifier

* URI 는 locator, name 또는 둘다 추가로 분류될 수 있다
    * URI 라는 큰 범위에서 URL(resource Locator), URN(Resource Name) 을 나눌 수 있다
    * locator: 리소스가 있는 위치를 지정
    * name: 리소스에 이름을 부여
    * 위치는 변할 수 있으나, 이름은 변하지 않는다
    * URN 이름만으로 실제 리소스를 찾을 수 있는 방법이 보편화 되지 않음
* Uniform: 리소스 식별하는 통일된 방식
* Resource: 자원, URI 로 식별할 수 있는 모든 것 (제한 없음)
* Identifier: 다른 항목과 구분하는데 필요한 정보
   
> foo://example.com:8080/some/path?param1=abc#header
> 
> URL: foo://example.com:8080/some/path?param1=abc#header
> 
> URN: urn:example.com:path:abc:header

* foo: scheme
* example.com:8080: authority
* some/path: path
* param1=abc: query
* \#header: fragment

### URL

* https://www.google.com:443/search?q=hello&hl=ko
    * https: 프로토콜
    * www.google.com: 호스트명
    * 443: 포트번호
    * /search: 패스
    * q=hello&hl=ko: 쿼리 파라미터
    
#### scheme

* 주로 프로토콜 사용한다
    * 프로토콜은 어떤 방식으로 자원에 접근할 것인가 하는 약속 규칙
    * http, https, ftp...
* http 인 경우 80 포트, https 인 경우 443 포트를 주로 사용하며 기본 포트일 경우 생략이 가능하다
* https 는 http 에 보안을 추가한 것이다 (http secure)

#### userinfo

* URL 에 사용자정보를 포함해서 인증
* 거의 사용하지 않는다

#### host

* 호스트명
* 도메인명 또는 IP 주소를 직접 사용가능

#### port

* 접속 포트
* 일반적으로 생략이 가능하며, 생략시 http 는 80, https 는 443 으로 동작한다

#### path

* 리소스 경로이며 계층적 구조이다

#### query

* `key=value` 형태
* ? 로 시작이 되며, & 로 `key=value` 를 추가할 수 있다
    * `...?foo=bar&fuz=baz`
* query parameter, query string 등으로 불리며 웹서버에 제공하는 파라미터이며 문자 형태이다

#### fragment

* html 내부 북마크 등에서 사용되며, 서버에 전송하는 정보는 아니다

