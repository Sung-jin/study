### 스프링 메시지 소스 설정

* 스프링은 기본적인 메시지 관리 기능을 제공한다
    * `MessageSource` 라는 스프링 빈에 메시지 관리 기능을 등록하면 사용할 수 있다

```java
@Bean
public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasenames("messages", "errors");
    // 설정 파일의 이름을 지정한다
    // messages.properties, errors.properties 와 같은 파일을 읽어서 사용한다
    // 파일의 위치는 /resources 하위에 두면 된다
    messageSource.setDefaultEncoding("utf-8");
    // 인코딩 정보를 저장한다
    return messageSource;
}
```

### 스프링 부트

* 스프링 부트가 `MessageSource` 를 빈으로 자동 등록한다
* `spring.messages.basenames=messages,config.i18m.messages` 와 같이 설정이 가능하다
    * 별도의 설정이 없으면 `messages` 가 기본으로 설정된다