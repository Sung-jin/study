## 비트 필드 대신 EnumSet 을 사용하라

```java
public class Text {
    public static final int STYLE_BOLD          = 1 << 0; // 1
    public static final int STYLE_ITALIC        = 1 << 1; // 2
    public static final int STYLE_UNDERLINE     = 1 << 2; // 4
    public static final int STYLE_STRIKETHROUGH = 1 << 3; // 8
    
    public void applyStyle(int styles) { ... }
    // 매개변수 style 은 0 개 이상의 STYLE_ 상수를 비트별 OR 한 값이다
}

text.applyStles(STYLE_BOLD | STYLE_ITALIC);
// 위와 같이 비트별 OR 를 사용하여 여러 상수를 하나의 집합으로 모을 수 있으며,
// 이렇게 만들어진 것을 비트 필드라고 한다
```

* 비트 필드를 사용하면 비트별 연산을 사용하여 합집합과 교집합 등과 같은 집합 연산을 효율적으로 수행할 수 있다
    * 이는 정수 열거 상수의 단점을 그대로 가진다
    * 비트 필드 값이 그대로 출력되면 단순한 정수 열거 상수를 출력할 때보다 해석하기가 훨씬 어렵다
    * 또한 비트 필드에 있는 모든 원소를 순회하기도 어렵다
    * 최대 몇 비트가 필요한지를 API 작성 시점에 미리 예측하여 적절한 타입을 선택해야 한다
    
### EnumSet

* 열거 타입 상수의 값으로 구성된 집합을 효과적으로 표현한다
* Set 인터페이스를 완벽히 구현하였으며, 타입 안전하고 다른 Set 구현체와 함께 사용이 가능하다
* EnumSet 내부는 비트 벡터로 구현되어 있다
* 원소가 총 64 개 이하라면 대부분의 경우에 EnumSet 전체를 long 변수 하나로 표현하여 비트 필드에 비견되는 성능을 보여준다
* `removeAll`/`retainAll` 등과 같은 대량 작업은 비트를 효율적으로 처리할 수 있는 산술 연산을 이용하여 구현하였다

```java
public class Text {
    public enum Style { BOLD, ITALIC, UNDERLINE, STRIKETHROUGH }
    
    public void applyStyles(Set<Style> styles) { ... }
    // 어떤 Set 을 넘겨도 되나, EnumSet 이 가장 좋다
    // EnumSet 이 아닌 Set 을 받는 이유는 EnumSet 을 전달할 것을 예상하지만,
    // 그럼에도 인터페이스로 받는게 일반적으로 좋은 습관이다
}

text.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC));
```

## 정리

* 열거할 수 있는 타입을 한데 모아 집합 형태로 사용한다고 해도 비트 필드를 사용할 이유는 없다
* EnumSet 클래스는 비트 필드 수준의 명료함과 성능을 제공하며, 열거 타입의 장점을 모두 가지고 있다
* EnumSet 의 단점은 불변 EnumSet 을 만들 수 없다
    * 명확성과 성능은 조금 희생되지만, `Collections.unmodifiableSet` 으로 감싸서 사용할 수 있다
