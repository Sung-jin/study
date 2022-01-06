### Spring Security

* 스프링 기반의 애플리케이션의 인증/권한/인가 등을 담당하는 스프링 하위 프레임워크
  * 인증/권한과 관련된 부분을 Filter 흐름에 따라 처리한다
  * Filter 는 Dispatcher Servlet 으로 가기전에 적용되므로 URL 요청을 가장 먼저 받는다
  * Interceptor 는 Dispatcher 와 Controller 사이에 위치한다
* 세션 기반 인증
* 로그인 뿐 아니라 보안 관련된 여러가지 설정이 가능하므로 개발자는 보안과 관련된 로직을 작성하지 않아도 되는 장점이 있다

#### 인증과 인가

* 인증 (Authorization) - 해당 사용자가 본인이 맞는지를 확인하는 절차
* 인가 (Authorization) - 인증된 사용자가 요청한 자원에 접근 가능한지를 결정하는 절차
* Spring Security 에서 기본적으로 인증 절차 후 인가 절차를 가지며, 인가 과정에서 리소스에 대한 접근 권한을 확인한다
* Principal 을 활용하여 아이디와 Credential 을 비밀번호로 사용하는 Credential 기반의 인증/인가 방식을 사용한다

#### Spring Security 모듈

##### SecurityContextHolder

* 보안 주체의 세부 정보를 포함하여 응용프로그램의 현재 보안 컨텍스트에 대한 세부 정보가 저장

##### SecurityContext

* Authentication 을 보관하는 역할
* SecurityContext 에서 Authentication 객체를 꺼내올 수 있다

##### Authentication

* 현재 접근하는 주체의 정보와 권한을 담는 인터페이스
* SecurityContext 에 저장된다
* SecurityContextHolder 를 통해 SecurityContext 에 접근하고 SecurityContext 를 통해 Authentication 에 접근할 수 있다

```java
public interface Authentication extends Principal, Serializable {
    // 현재 사용자의 권한 목록
    Collection<? extends GrantedAuthority> getAuthorities();
    
    //credentials(주로 비밀번호)
    Object getCredentials();

    // Principal 객체
    Object getDetails();

    // 인증 여부
    Object getPrincipal();

    // 인증 여부를 설정
    boolean isAuthenticated();
  
    void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException;
}
```
