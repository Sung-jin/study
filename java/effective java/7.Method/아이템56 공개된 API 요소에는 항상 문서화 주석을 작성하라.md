## 공개된 API 요소에는 항상 문서화 주석을 작성하라

* 문서화 규칙은 공식 언어 명세에 속하진 않으나, 알아야 할 표준 API 가 있다
    * 이 규칙은 문서화 주석 작성법 웹페이지에 기술되어 있으나, Java 4 이후로 갱신되지 않고 있지만 가치는 여전하다
    * 위 문서에 없으나 Java 5 의 `@literal`/`@code` 와 Java 8 의 `@implSpec` 와 Java 9 의 `@Index` 가 추가적으로 존재한다
* API 를 올바로 문서화하려면 공개된 모든 클래스, 인터페이스, 메서드, 필드 선언에 문서화 주석을 달아야 한다
* 직렬화할 수 있는 클래스라면 직렬화 형태에 관해서도 적어야 한다
* 문서화 주석이 없다면 자바독도 그저 공개 API 요소들의 선언만 나열해주는게 전부이다
* 이러한 문서화가 잘 이루어지지 않다면 사용시 헷갈려서 오류의 원인이 되기 쉬워진다
* 기본 생성자에는 문서화 주석을 추가할 수 없으므로 공개 클래스는 절대 기본 생성자를 사용하면 안된다
* 유지보수를 고려한다면 대다수의 공개되지 않은 클래스, 인터페이스, 생성자, 메서드, 필드에도 문서화 주석을 달아야 한다
* 메서드용 문서화 주석에는 해당 메서드와 클라이언트 사이의 규약을 명료하게 기술해야 한다
    * 상속용으로 설계된 클래스의 메서드가 아니라면 무엇을 하는지를 기술해야 한다
    
### 문서화 위치

* 문서화 주석에는 클라이언트가 해당 메서드를 호출하기 위한 전제조건을 모두 나열해야 한다
    * 일반적으로 전제조건은 `@throws` 태그로 비검사 예외를 선언하여 암시적으로 기술한다
    * 비검사 예외 하나가 전제조건 하나와 연결되는 것이다
    * `@params` 를 통해 조건에 영향받는 매개변수에 기술할 수 있다
* 부작용에 대한 문서화도 필요하다
    * 부작용이란 사후조건으로 명확히 나타나지는 않지만 시스템의 상태에 어떠한 변화를 가져오는 것을 뜻한다
* 메서드의 계약을 완벽히 기술하려면 모든 매개변수에 `@params` 태그를, 반환 타입이 void 가 아니라면 `@return` 태그를, 발생할 가능성이 있는 모든 예외에 `@throw` 태그를 달아야 한다
* `@params`/`@return` 태그의 설명은 해당 매개변수가 뜻하는 값이나 반환값을 설명하는 명사구를 사용한다
* `@throw` 태그의 설명은 if 로 시작하여 해당 예외를 던지는 조건을 설명하는 절이 뒤따른다
* `@params`/`@return`/`@throw` 은 관례상 설명에 마침표를 붙이지 않는다

### 문서화 변환

* 문서화 주석에 HTML 태그를 사용할 수 있으며, 이는 자바독 유틸리티가 문서화 주석을 HTML 로 변환하므로 문서화 주석 안의 HTML 요소들은 최종 HTML 문서에 반영된다
* `{@code ...}` 와 같은 형태로 태그의 효과는 내용을 코드용 폰트로 렌더링하고, 태그로 감싼 내용에 포함된 HTML 요소나 다른 자바독 태그를 무시한다
    * 따라서 `<` 와 같은 기호등을 별다른 처리 없이 사용이 가능하게 만들어준다
    * 여러줄의 코드를 삽입하기 위해서는 `<pre>{@code ... }</pre>` 와 같은 형태로 작성하면 된다
* `{@literal}` 태그를 감싸면 HTML 마크업이나 자바독 태그를 무시하게 해준다

### 문서화 작성 방식

* 문서화 주석의 첫 번째 문장은 해당 요소의 요약 설명으로 간주된다
    * 요약 설명은 반드시 대상의 기능을 고유하게 기술해야 한다
    * 따라서 한 클래스안에서 요약 설명이 똑같은 멤버 또는 생성자가 둘 이상이면 안된다
    * 특히 다중정의된 메서드는 더 조심해서 작성해야 한다
* 요약 설명에서 마침표에 주의해야 한다
    * 첫 번째 마침표가 나오는 부분까지만 요약 설명이 된다
    * 요약 설명이 끝나는 판단기준은 처음 발견되는 {<마침표> <공백> <다음 문장 시작>} 패턴의 <마침표> 까지이다
* 주석 작성 규약에 따르면 요약 설명은 완전한 문장이 되는 경우가 드물다
    * 메서드와 생성자의 요약 설명은 해당 메서드와 생성자의 동작을 설명하는 동사구여야 한다
    * 단, 클래스와 인터페이스와 필드의 요약 설명은 대상을 설명하는 명사절이어야 한다
    
### @Index

* Java 9 부터 자바독이 생성한 HTML 에 검색 기능이 추가되어 광대한 API 문서들을 찾는 일이 수월해졌다
* API 문서 페이지 오른쪽 위에 있는 검색창에 키워드를 입력하면 관련 페이지들이 드롭다운 메뉴로 나타난다
* 클래스, 메서드, 필드 같은 API 요소의 색인은 자동으로 만들어지며 원한다면 `{@index}` 태그를 사용하여 중요한 용어를 추가로 색인화할 수 있다

### 문서화시 유의사항

* 문서화 주석에서 제네릭, 열거타입, 어노테이션은 특별히 주의해야 한다
* 제네릭 타입이나 제네릭 멘서드를 문서화할 때는 모든 타입 매개변수에 주석을 달아야 한다
* 열거 타입을 문서화할 때는 상수들에도 주석을 달아야 한다
* 어노테이션 타입을 문서화할 때는 멤버들에도 모두 주석을 달아야 한다
    * 어네토이션 타입 자체에도 달아야 하며, 필드 설명은 명사구로 한다
* API 문서화에서 자주 누락되는 설명 두가지
    1. 스레드 안정성: 클래스 혹은 정적 메서드가 스레드 안전하든 그렇지 않든 스레드 안전 수준을 반드시 API 설명에 포함해야 한다
    2. 직렬화 가능성: 직렬화할 수 있는 클래스라면 직렬화 형태도 API 설명에 포함해야 한다
* 모든 공개된 API 에 문서화 주석을 추가하였어도 여러 클래스가 상호작용하는 복잡한 API 이면 전체 아키텍처를 설명하는 별도의 설명이 필요한 경우가 많다

```java
/**
 * @param <K> ...
 * @param <V> ...
 */
public interface Map<K, V> { ... }

/**
 * ...
 */
public enum SomeEnum {
    /** .... */
    FOO, ...
}

/**
 * ...
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Bar {
    /**
     * ...
     */
    Class<? extends Something> value();
}
```

### 패키지 설명

* 패키지를 설명하는 문서화 주석은 `package-info.java` 파일에 작성한다
    * 해당 파일은 패키지 선언을 반드시 포함해야 하며 패키지 선언 관련 어노테이션을 추가로 포함할 수도 있다
* Java 9 부터 지원하는 모듈 시스템을 사용한다면 모듈 관련 설명은 `mudle-info.java` 파일에 작성하면 된다

### 문서화 상속

* 자바독은 메서드 주석을 상속시킬 수 있다
* 문서화 주석이 없는 API 요소가 있다면 가장 가까운 문서화 주석을 찾아준다
    * 상위 클래스보다 그 클래스가 구현한 인터페이스를 먼저 찾는다
* `{@inheriDoc}` 태그를 통해 상위 타입의 문서화 주석 일부를 상속할 수 있다
    * 클래스는 자신이 구현한 인터페이스의 문서화 주석을 재사용할 수 있다는 의미이다

### 문서화 주석 검사

* 자바독 문서를 올바르게 작성하였는지 확인하는 기능을 자바독에서 제공한다
* Java 7 에서 명령줄에 `-Xdoclint` 스위치를 켜주면 해당 기능은 활성화되며, Java 8 부터는 자동으로 작동한다
* checkstyle 등과 같은 IDE 플러그인의 도움을 받을 수도 있다
* 자바독이 생성한 HTML 을 HTML 유효성 검사기로 돌려서 문서화 주석의 오류를 한층 더 줄일 수 있다

## 정리

* 문서화 주석은 API 를 문서화하는 가장 훌륭하고 효과적인 방법이다
* 정말 잘 쓰인 문서인지를 확인하는 유일한 방법은 자바독 유틸리티가 생성한 웹페이지를 읽어보는게 좋다
* 다른 사람이 사용할 API 라면 반드시 모든 API 요소를 검토해야 한다
* 공개 API 라면 모든 설명을 달아야 하며 표준 규약을 일관되게 지켜야 한다
* 문서화 주석에 임의의 HTML 태그를 사용할 수 있으나, HTML 메타문자는 특별하게 취급해야 한다
