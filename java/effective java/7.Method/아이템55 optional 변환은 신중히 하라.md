## optional 변환은 신중히 하라

* Java 8 이전에 값을 반환할 수 없을 때 예외를 던지거나 null 을 반환하는 방법이 존재한다
    * 예외의 경우 정말 예외인 경우에만 사용해야 하며, 예외를 생성할 때 스택 추적 전체를 캡쳐하므로 비용이 크다
    * null 을 반환할 수 있는 경우 별도의 null 처리 코드를 추가해야 하며, 처리하지 않는 경우 해당 객체에 접근할 때 NPE 예외가 발생한다
* 이러한 상황을 처리할 때 Java 8 에 추가된 Optional\<T> 를 통해 처리할 수 있다
    * Optional 은 null 이 아닌 T 를 참조를 담거나 담고있지 않는 래핑 객체이다
    * Optional 은 원소를 최대 1개 가질 수 있는 불변 컬렉션이다
    
### Optional

* 보통 T 를 반환하애 하지만 특정 조건에서는 아무것도 반환하지 않아야 할 때 T 대신 Optional\<T> 를 반환하도록 선언하면 된다
* Optional 을 활용하면 예외를 던지는 메서드보다 유연하고 사용하기 쉬우며, null 반환하는 메서드보다 오류 가능성이 적다

```java
public static <E extends Comparable<E>> Optional<E> max(Collection<E> c) {
//    if (c.isempty()) throw new IllegalArguemntException("empty collection");
    if (c.isempty()) return Optional.empty();
    // 위와 같이 예외가 아닌 빈 Optional 을 반환

    E result = null;
    for (E e: c) {
        if (result == null || e.compareTo(result > 0)) result = Objects.requireNonNull(e);
    }
    
//    return result;
    return Optional.of(result);
    // 결과 자체가 아닌 Optional 을 감싼 객체를 반환
}
```

* 위와 같이 Optional 을 사용하는 방법은 어렵지 않지만 더 안전하고 효율적인 형태로 사용이 가능하게 된다
    * 단, `Optional.of(null)` 인 경우 NPE 예외가 발생한다
    * null 도 허용하는 Optional 을 허용하는 객체를 만들려면 `Optional.ofNullable(value)` 형태로 사용해도 되지만, Optional 을 사용한다면 null 을 감싼 객체를 반환하지 않아야 한다
* null 또는 예외가 아닌 optional 을 반환을 선택하는 기준은 검사 예외와 취지가 비슷하다고 생각하면 된다
    * 반환값이 없을 수도 있음을 API 사용자에게 명확하게 알려주는 경우이다
    * 비검사 예외를 던지거나 null 을 반환한다면 사용자가 그 사실을 인지하지 못한 상태로 사용이 가능하며, 이는 문제가 발생될 가능성이 충분하다
    
### optional 활용

```java
Optional<String> somethingOptional = ...;

String foo = somethingOptional.orElse("default");
// optional 에 값이 없을 경우 기본 값을 지정한다
String bar = somethingOptional.orElseThrow(IllegalArgumentException::new);
// optional 에 값이 없을 경우 예외를 발생시킨다
String fuz = somethingOptional.get();
// 항상 값이 있다는 가정하에 바로 사용할 수 있으나, 실제 값이 없다면 NoSuchElementException 예외가 발생한다
// 그 외에 filter, map, flatMap, ifPresent 등을 활용할 수 있다

if (somethingOptional.isPresnet()) {
    // doSomething...
    // 위와 같이 내부 값이 있는지 여부를 체크한 후 사용하는 형태도 존재한다
}

streamOfOptionals.flatMap(Optional::stream)...
// 위와 같이 Java 9 에서 optional 을 스트림으로 변환하는 정적 메서드가 추가되었으며, 이를 이용하여 스트림을 사용할 수 있다
```

### Optional 을 사용해야 할 때

* 결과가 없을 수 있으며, 클라이언트가 이 상황을 특별하게 처리해야 한다면 Optional\<T> 를 반환한다
* Optional 을 반환하는데 새로 할당해야 하며, 초기화도 해야 하므로 자원이 소모된다
    * 또한, Optional 에 대한 처리 메서드의 추가적인 단계가 필요하다
* 따라서 성능이 중요한 상황에서는 Optional 이 맞지 않을 수 있다

### Optional 을 사용하지 말아야 할 때

* 컬렉션, 스트림, 배열, 옵셔널 같은 컨테이너 타입은 옵셔널로 감싸면 안된다
    * 컬렉션의 경우 `Optional<List<T>>` 보다 빈 리스트를 반환하는게 좋다
    * 단, ProcessHandle.Info 인터페이스의 arguments 메서드는 Optional\<String[]> 을 반환하나, 이는 예외적인 케이스이다
* Optional 을 컬렉션의 키, 값, 원소나 배열의 원소로 사용하는 상황은 거의 없다
    * 이는 체크하는 방식이 빈 값, 실제 값이 없는 경우 등 여러 케이스가 존재하므로 복잡성과 혼란성, 오류 가능성만 키우게 된다
* 인스턴스 필드에 Optional 을 사용하는 경우도 대부분 필요없으나, 상황에 따라 필수가 아닌 인스턴스 필드가 존재할 경우 getter 로 Optional 을 반환하는 케이스는 존재할 수 있다

### primitive type 을 위한 Optional

* Optional 도 박싱된 객체이므로 기본 자체보다 무겁다
* 자바 API 는 이를 위해 int, long, double 전용 옵셔널 클래스가 존재한다
    * OptionalInt, OptionalLong, OptionalDouble
* 이는 Optional 이 제공하는 모든 메서드를 거의다 제공하며, 대체제가 있으므로 박싱된 기본 타입을 담은 옵셔널을 반환하는 일은 없어야 한다

## 정리

* 값을 반환하지 못할 가능성이 있고, 호출할 때마다 반환값이 없을 가능성을 염두에 둬야 하는 메서드라면 옵셔널을 반환해야 할 상황일 수 있다
* 옵셔널 반환에는 성능 저하가 뒤따르므로 성능이 중요할 경우 null 을 반환하거나 예외를 던지는 편이 좋다
* 옵셔널을 반환값 이외의 용도로 사용하는 경우는 매우 드물다
