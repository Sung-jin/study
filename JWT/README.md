### Json Web Token

* 전자 서명 된 URL-safe 의 JSON
  * 전자 서명을 통해 JSON 의 변조를 체크할 수 있다
* JWT 는 속성 정보를 JSON 데이터 구조로 표현한 토큰이며, RFC7519 표준이다
* JWT 는 서버와 클라이언트 간 정보를 주고 받을 때 http 헤더에 JSON 토큰을 넣은 후 서버는 별도의 인증 과정없이 헤더에 포함되어 있는 JWT 정보를 통해 인증한다
  * JSON 데이터는 URL-safe 하도록 URL 에 포함할 수 있는 문자만 사용한다
* JWT 는 HMAC 알고리즘을 사용하여 비밀키 또는 RSA 를 이용한 Pub/Private Key 쌍으로 서명할 수 있다

#### Json Web Signature, Json Web Encryption

* JWS 는 JSON 데이터 구조를 사용하는 서명 표준으로 RFC7515 이다
  * JSON 으로 전자 서명하여 URL-safe 문자열로 표현
  * 해당 서명을 활용하여 JSON 이 손상 여부를 확인할 수 있다
* JWE 는 JSON 데이터 구조를 사용하는 암호화 방법으로 RFC7516 이다
  * JSON 을 암호화하여 URL-safe 문자로 표현
    
#### JWT 토큰 구성

> aaaaaaaaa.bbbbbbbbb.ccccccccc
> header    payload   signature

* Header
  * 토큰의 타입과 해시 암호화 알고리즘으로 구성되어 있다
    1. 토큰의 유형 (JWT)
    1. HMAC, SHA256, RSA 와 같은 해시 알고리즘
  * `alg` : Signature 를 해싱하기 위한 알고리즘 정보를 가지고 있음
  * `typ` : 토큰의 타입을 나타내며, 보통 JWT 를 사용한다

* Payload
  * 토큰에 담을 claim 정보를 포함하며, claim 은 payload 에 담는 정보의 한조각이라 한다
  * claim 은 name / value 의 한 쌍으로 이루어져 있다
  * 토큰에 여러개의 claim 을 넣을 수 있다
  * claim 의 정보는 등록된 claim, 공개 claim, 비공개 claim 과 같은 총 3 종류가 있다
  * JWT 가 기본적으로 가지고 있는 키워드가 있으며, 아래와 같은 값을 추가할 수 있다
    * `iss` : 토큰 발급자
    * `sub` : 토큰 제목
    * `aud` : 토큰 대상
    * `exp` : 토큰 만료시간
    * `nbf` : Not Before
    * `iat` : 토큰이 발급된 시간
    * `jti` : JWT 고유 식별자
* signature
  * secret key 를 포함하여 암호화 되어 있다
  * Header + Payload + Secret Key 로 값을 생성하므로 데이터 변조 여부를 판단 가능하다
    
#### 토큰 인증 타입

* `Authorization: <type> <credentials>` 의 type 에 들어가는 값
  * Basic : 사용자 아디이와 암호를 Base64 로 인코딩한 값을 토큰으로 사용
  * Bearer : JWT 또는 OAtuh 에 대한 토큰을 사용
  * Digest : 서버에서 난수 데이터 문자열을 클라이언트에 보내며, 클라이언트는 사용자 정보와 nonce 를 포함하는 해시값을 사용하여 응답
  * HOBA : 전자 서명 기반 인증
  * Mutual : 암호를 이용한 클라이언트 - 서버 상호 인증
  * AWS4-HMAC-SHA256 : AWS 전자 서명 기반 인증

#### refresh token

* 토큰이 탈취되면 누구나 API 를 호출할 수 있는 단점이 존재한다
  * 세션이 탈취 된 경우, 저장소에서 탈취된 세션 id 를 삭제할 수 있지만, JWT 의 경우 서버에서 관리되지 않기 때문에 삭제가 불가능하다
* 이를 방지하기 위해 유효시간을 짧게한다
  * 만료 시간이 30분이면 30분마다 새로 로그인을 해야 하지만, refresh token 을 활용하면 그러한 과정을 생략할 수 있다
* Refresh token 은 Access token 보다 유효 시간이 길며, Access token 이 만료된 사용자가 재발급을 원할 경우 Refresh Token 을 함께 전달한다
* Refresh token 의 경우 클라이언트 - 서버 통신에서 지속적으로 사용되지 않으며, 클라이언트에서 안전하게 보관하다가 Refresh 를 요청할 때만 사용한다
  * 지속적으로 사용하지 않기 때문에 탈취당할 위험이 적고, 요청 주기가 길기 떄문에 별도의 저장소에 보관해야 한다
* Refresh token 은 서버에서 별도의 저장소에 보관하는 것이 좋다
  * Refresh token 은 사용자 정보가 없기 때문에, 어떠한 값에 대한 요청인지 판단할 수 없기 때문에 별도로 보관해야 한다
  * Access token 이 탈취당한 경우 Refresh token 저장소에서 해당 토큰을 삭제하면, Access token 이 만료될 때 강제 로그아웃 처리를 할 수 있다

#### JWT process

* 기존의 토큰 방식
  1. 기존의 토큰 인증 방식은 모든 서비스 호출에 사용된다
  1. 서비스를 받기 위해서는 토큰의 유효성을 확인하여 세부 정보를 쿼리하며, 참조에 의한 호출 형태로 모든 서비스는 항상 상호 작용할 때 다시 접속한다
* JWT 의 경우 갑셍 의한 호출이 가능한 토큰이며, 토큰이 필요한 모든 정보를 가지고 있다
  * 모든 정보를 가지고 있기 때문에 참조가 필요없으며, 마이크로서비스 자체에 유효성을 검증한다
* JWT 동작
  1. 사용자가 id, pw 를 입력하여 로그인 시도
  1. 서버는 요청을 확인 후 secret key 를 통해 access token 을 발급하여 클라이언트에 전달
  1. 클라이언트에서 API 를 요청할 때 **Authorization header 에 access token** 을 담아서 전달
  1. 서버에서 해당 access token 을 체크 후 payload 로부터 사용자 정보를 확인해서 데이터를 반환
  1. 클라이언트의 로그인 정보를 서버에 메모리에 저장하지 않기 떄문에 토큰기반 인증 메커니즘을 제공

#### Refresh token process

1. 클라이언트는 Access token 으로 API 요청하며 서비스 제공
1. Access token 이 만료시 서버에서 Access token 만료 응답
1. 클라이언트 Access token 만료 응답 수신후, Access token + Refresh token 을 함께 보냄
1. 서버는 요청된 Refresh token 의 만료 여부를 확인
1. 유효할 경우 Access token 으로 유저 정보를 획득한 후, 저장소에서 해당 유저 정보를 바탕으로 찾은 Refresh token 과 일치하는지 확인
1. 검증이 성공적으로 완료되면 새로운 Access token + Refresh token 발급 후, Refresh 저장소의 Refresh token 을 최신화 한다

#### JWT 특징

* JWT 에는 필요한 모든 정보를 토큰에 포함하기 때문에 데이터베이스와 같은 서버와의 커뮤니케이션 오버 헤드를 최소화 할 수 있다
* CORS 는 쿠키를 사용하지 않기 때문에, JWT 를 채용 한 인증 메커니즘은 두 도메인에서 API 를 제공하더라도 문제가 발생하지 않는다
* 처음 사용자를 등록할 때 Access token 과 Refresh token 이 모두 발급되어야 한다

#### JWT 장점

* 사용자 인증에 필요한 모든 정보는 토큰 자체에 포함하기 때문에 별도의 인증 저장소가 필요 없다
* 분산 마이크로 서비스 환경에서 중앙 집중식 인증 서버와 데이터베이스에 의조하지 않는 인증 및 인가 방법을 제공
* 토큰의 유효성 검사 시점에 CPU 사이클을 필요로 하고, IO/네트워크 액세스가 필요하지 않으며, 최신 웹 서버 하드웨어에서 확장하기 쉽다
* 인증 정보를 OAuth 등의 다른 곳에서도 사용 가능
* Stateless 한 서버 구현 가능
* JSON 웹 토큰의 사용을 권장하는 이유
  1. URL 파라미터와 헤더로 사용
  1. 수평 스케일이 용이
  1. 디버깅 및 관리가 용이 
  1. 트래픽에 대한 부담이 적음 
  1. REST 서비스로 제공 가능
  1. 내장된 만료 
  1. 독립적인 JWT

#### JWT 단점

* 토큰은 클라이언트에 저장되어 데이터베이스에 사용자 정보를 조작하더라도 토큰에 직접 적용할 수 없다
* 필드가 추가되면 토큰이 커진다
* 비상태 애플리케이션에서 토큰은 거의 모든 요청에 대해 전송되므로 데이터 트래픽 크기에 영향을 미칠 수 있다
* 다른 사람이 토큰을 decode 하여 데이터 확인이 가능
* 토큰을 탈취당한 경우 대처하기 어려움
  * 서버에서 키를 관리하는 형태가 아니기 때문에, 강제 로그아웃 처리가 불가능 하다
  * 토큰 유효시간이 만료되기 전까지 탈취자는 자유롭게 인증이 가능하다
    * 이를 대처하기 위해 유효시간을 짧게 가져가고 refresh token 을 발급하는 방식으로 많이 사용한다
