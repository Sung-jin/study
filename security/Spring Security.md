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

##### UsernamePasswordAuthenticationToken

* Authentication 을 implements 한 AbstractAuthenticationToken 의 하위 클래스
* User 의 Id 가 Principal, Password 가 Credential 역할을 한다

```java
public class UsernamePasswordAuthenticationToken extends AbstractAuthenticationToken {
    // 주로 사용자의 ID
    private final Object principal;

    // 주로 사용자의 PW에 해당함
    private Object credentials;

    // 인증 완료 전의 객체 생성
    public UsernamePasswordAuthenticationToken(Object principal, Object credentials) {
        uper(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    // 인증 완료 후의 객체 생성
    public UsernamePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
        // must use super, as we override
    }
}

public abstract class AbstractAuthenticationToken implements Authentication, CredentialsContainer { }
```

##### AuthenticationProvider

* 실제 인증에 대한 부분을 처리
* 인증 전의 Authentication 객체를 받아서 인증이 완료된 객체를 반환하는 역할을 한다

```java
public interface AuthenticationProvider {
    // 인증 전의 Authentication 객체를 받아 인증된 Authentication 객체를 반환
    Authentication authenticate(Authentication var1) throws AuthenticationException;
    
    boolean supports(Class<?> var1);
}
```

##### AuthenticationManager

* AuthenticationManager 에 등록된 AuthenticationProvider 에 의해 인증에 대한 처리
* 인증에 성공하면 두번째 생성자를 이용하여 `isAuthenticated=true` 인 객체를 생성하여 SecurityContext 에 저장
* 인증 상태를 유지하기 위해 세션에 보관하며, 인증이 실패한 경우에 AuthenticationException 발생

```java
public interface AuthenticationManager {
    Authentication authenticate(Authentication authentication) throws AuthenticationException;
}
```

* AuthenticationManager 를 implements 한 ProviderManager 는 실제 인증 과정에 대한 로직을 가지고 있다
* ProviderManager 는 실제 인증 로직인 AuthenticationProvider list 를 가지고 있으며, 해당 리스의 모든 provider 를 조회하면서 authenticate 를 처리한다

```java
public class ProviderManager implements AuthenticationMAnager, MessageSourceAware, InitializingBean {
    public List<AuthenticationProvider> getProviders() {
        return providers;
    }
    
    public Authentication authenticate(Authentication authenticate) throws AuthenticationException {
        ...
        for (AuthenticationProvider provider: getProviders()) {
            try {...}
        }
    }
}
```

* CustomAuthenticationProvider 를 등록하기 위해서는 WebSecurityConfigurerAdapter 를 상속한 SecurityConfig 에서 가능하다
  * WebSecurityConfigurerAdapter 의 상위 클래스에서는 AuthenticationManager 를 가지고 있기 때문에 CustomAuthenticationProvider 를 등록할 수 있다

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public AuthenticationManger getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() throws Exception {
        return new CustomAuthtneticationProvider();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider());
    }
}
```

##### UserDetails

* 인증에 성공하여 생성된 객체이며, Authentication 객체를 구현한 UsernamePasswordAuthenticationToken 을 생성하기 위해 사용된다
* 직접 설정한 유저 객체 모델에 UserDetails 를 implements 하여 처리할 수 있다

```java
public interface UserDetails extends Serializable {
    collection<? extends GrantedAuthority> getAuthorities();
    
    String getPassword();
    
    String getUsername();
    
    boolean isAccountNonExpired();
    
    boolean isAccountNonLocked();
    
    boolean isCredentialsNonExpired();
    
    boolean isEnabled();
}
```

##### UserDetailsService

* UserDetails 객체를 반환하는 단 하나의 메서드를 가지고 있으며, 이를 구현한 클래스의 내부에 UserRepository 를 주입받아 DB 연결 처리를 한다

```java
public interface UserDetailsService {
    UserDetails loadUserByUsername(String var1) throws UsernameNotFoundException;
}
```

##### Password Encoding

* `AuthenticationManagerBuilder.userDetailsService().passwordEncoder()` 를 통해 패스워드 암호화에 사용될 PasswordEncoder 구현체를 지정할 수 있다

```java
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
}

@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

##### GrantedAuthority

* 현재 사용자인 Principal 이 가지고 있는 권한을 의미한다
* `ROLE_ADMIN`/`ROLE_USER` 등과 같이 `ROLE_...` 형태로 사용한다
* GrantedAuthority 객체는 UserDetailsService 에 의해 불러올 수 있으며, 특정 자원에 대한 권한 검사하여 접근 허용 여부를 결정한다
