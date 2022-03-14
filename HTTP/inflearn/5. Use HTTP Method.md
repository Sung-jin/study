### 정적 데이터 조회

* `GET` method 를 이용하여 이미지, 정적 텍스트 문서 등을 조회한다
* 정적 데이터는 일반적으로 쿼리 파라미터 없이 리소스 경로로 단순하게 조회 가능하다

### 동적 데이터 조회

* `GET` method 를 이용하여 검색, 게시판 목록에서 정렬 필터(검색어), 조회 결과를 정렬하는 정렬 조건/필터 등에 주로 사용한다
* 쿼리 파라미터를 통해 데이터를 조회한다

### HTML Form 데이터 전송

* HTML Form 은 `GET`/`POST` 만 가능하다
* 기본적으로 form 태그에서 submit 을 하면 POST 메서드로 `Content-Type: application/x-www-form-urlencoded` 로 셋팅되어 전송된다
    * 기존의 `GET` 쿼리 파라미터처럼 `key-value` 로 url encoding 되어 전송된다
    * `username=kim&age=20`
    * `GET` 으로 설정하여 전송도 가능하다
* `enctype` 에 타입을 설정하면, 해당 타입으로 셋팅되어 전달된다
    * `multipart/form-data`: boundary 를 기준으로 다른 종류의 여러 파일과 폼의 내용을 나눠서 데이터가 전송된다

### HTTP API 데이터 전송

* 서버사이드에서 서버사이드간의 시스템 통신
* 앱 클라이언트간의 시스템 통신
* 웹 클라이언트간의 시스템 통신
* `POST`, `PUT`, `PATCH` 메소드의 메시지 바디를 통해 데이터 전송
* `GET` 을 통해 조회 및 쿼리 파라미터로 데이터 전달
* `Content-Type: Application/json` 을 주로 사용한다

### 회원 관리 시스템

#### POST 기반 신규 등록

* 회원 목록: /members [`GET`]
* 회원 등록: /members [`POST`]
* 회원 조회: /members/{id} [`GET`]
* 회원 수정: /members/{id} [`PATCH`, `PUT`, `POST`]
* 회원 삭제: members/{id} [`DELETE`]

##### POST 신규 자원 등록 특징

* 클라이언트는 등록될 리소스의 URI 를 모른다
    * 회원 등록: /members [`POST`]
* 서버가 새로 등록된 리소스 URI 를 생성한다
    * HTTP/1.1 201 Created Location: /members/100
* 컬렉션
    * 서버가 관리하는 리소스 디렉토리
    * 서버가 리소스의 URI 를 생성하고 관리
    * 회원 관리 시스템에서 컬렉션은 `/members`
  
#### PUT 기반 신규 등록

* 파일 목록: /files [`GET`]
* 파일 조회: /files/{filename} [`GET`]
* 파일 등록: /files/{filename} [`PUT`]
* 파일 삭제: /files/{filename} [`DELETE`]
* 파일 대량 목록: /files [`POST`]

##### PUT 신규 자원 등록 특징

* 클라이언트가 리소스 URI 를 알고 있어야 한다
    * 파일 등록: /files/{filename} [`PUT`]
* 클라이언트가 직접 리소스의 URI 를 지정한다
* 스토어
    * 클라이언트가 관리하는 리소스 저장소 
    * 클라이언트가 리소스의 URI 를 알고 관리
    * 파일에서의 스토어는 `/files`

### HTML Form 사용

* HTML Form 은 GET/POST 만 지원하나, `AJAX` 같은 기술을 활용하여 해결이 가능하다

#### HTML Form 사용

> 순수한 HTML Form 만 사용한다는 가정

* 회원 목록: /members [`GET`]
* 회원 등록 폼: /members/new [`GET`]
* 회원 등록: /members/new,/members [`POST`]
* 회원 조회: /members/{id} [`GET`]
* 회원 수정 폼: /members/{id}/edit [`GET`]
* 회원 수정: /members/{id}/edit,/members/{id} [`POST`]
* 회원 삭제: /members/{id}/delete [`POST`]

##### HTML Form 정리

* `GET`/`POST` 만 지원
* 컨트롤 URI
    * GET/POST 만 지원하기 때문에 많은 제약이 존재하며, 해결하기 위해 동사로 된 리소스 경로를 사용한다
    * POST 의 `/new`, `/edit`, `/delete` 등이 컨트롤 URI 이다
    * HTTP 메서드로 해결하기 애매한 경우에 사용한다
    * 컨트롤 URI 의 단어는 동사를 사용한다

### 정리

* HTTP API - 컬렉션
    * `POST` 기반 등록
    * 서버가 리소스 URI 를 결정
* HTTP API - 스토어
    * `PUT` 기반 등록
    * 클라이언트가 리소스 URI 결정
* HTML FORM 사용
    * `GET`/`POST` 만 지원

#### 참고하면 좋은 URI 설계 개념

* 문서(document)
    * 단일 개념(파일 하나/객체 인스턴스/데이터베이스 row)
    * ex) /members/100, /files/foo.jpg
* 컬렉션(collection)
    * 서버가 관리하는 리소스 디렉터리
    * 서버가 리소스의 URI 를 생성하고 관리
    * ex) /members
* 스토어(store)
    * 클라이언트가 관리하는 자원 저장소
    * 클라이언트가 리소스의 URI 를 알고 관리
    * ex) /files
* 컨트롤러(controller), 컨트롤 URI
    * 문서, 컬렉션, 스토어로 해결하기 어려운 추가 프로세스 실행
    * 동사를 직접 사용한다
    * ex) /members/{id}/delete