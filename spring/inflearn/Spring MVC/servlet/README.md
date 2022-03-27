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

## 서블릿과 JSP 의 한계

* 서블릿으로 개발할 때는 뷰 화면을 위한 HTML 을 생성하는 작업이 자바 코드에 섞여서 지저분하고 복잡하다
* JSP 를 사용하면 뷰를 생성하는 HTML 은 깔끔해지고, 중간에 자바 로직을 넣을 수 있다
    * 하지만, JSP 의 일부 코드는 비즈니스 로직이 들어가고, 나머지는 뷰를 위한 HTML 영역이 하나의 파일에 존재하게 된다
    * 이는 유지보스에 있어서 어려움을 가져온다
* 이를 해결하기 위해 MVC 패턴이 등장하였다
    * 비즈니스 로직은 서블릿처럼 다른 곳에서 처리하고, JSP 는 HTML 로 화면만 그리는 일에 집중하도록 할 수 있다
    
## MVC 패턴

* Model View Controller
* 기존의 하나의 서블릿이나 JSP 만으로 비즈니스 로직과 뷰 렌더링 모두를 처리하기에는 너무 많은 역할이 필요하고 결과적으로 유지보수가 어려워진다
* 비즈니스와 뷰의 변경 라이프 사이클은 다르다
    * 비즈니스가 변한다고 무조건 뷰가 변경되는 것이 아니며, 그 반대도 마찬가지이다
    * 이렇다 다른 변경 라이프 사이클을 가진 것들을 하나의 파일로 관리하면 유지보수하기 좋지 않다
* MVC 패턴은 서블릿/JSP 로 처리하던 것들을 Controller/View 라는 영역으로 서로 역할을 나눈 것을 말한다
    * 컨트롤러: HTTP 요청을 받아서 파라미터를 검증하고, 비즈니스 로직을 실행하고 뷰에 전달할 결과 데이터를 조회해서 모델에 담는다
    * 모델: 뷰에 출력할 데이터를 담아두고, 뷰가 필요한 데이터를 모두 모델에 담아서 전달해주는 덕분에 뷰는 비즈니스 로직이나 데이터 접근을 몰라도 된다
    * 뷰: 모델에 담겨있는 데이터를 사용해서 화면을 그리는 일을 집중한다

### MVC 패턴의 한계

* MVC 패턴을 적용함으로써 컨트롤러의 역할과 뷰를 렌더링 하는 역할을 명확하게 구분할 수 있다
    * 하지만 컨트롤러의 경우 중복과 불필요해 보이는 코드가 많다
* MVC 단점
    * **포워드 중복**: View 로 이동하는 코드가 항상 중복 호출되어야 한다
    * **ViewPath 중복**: prefix(/WEB-INF/...), suffix(.jsp) 중복
    * **사용하지 않는 코드**: `resp` 같은 경우에는 거의 사용되지 않으며, 다른 많은 것들도 사용되지 않는 경우가 많다
    * **공통 처리의 어려움**: 공통된 부분을 단순히 메서드로 뽑는다고 해도 해당 메서드를 호출해야 하며, 호출하지 않으면 문제가 발생하는 등의 문제가 있다
* 정리하면 공통 처리가 어렵다라는 문제점이 존재한다
    * 컨트롤러 호출 전에 공통 기능을 처리하는 어떠한 수문장과 같은 역할이 필요하다
    * 프론트 컨트롤러 패턴을 도입하면 이러한 문제를 깔끔히 해결할 수 있다