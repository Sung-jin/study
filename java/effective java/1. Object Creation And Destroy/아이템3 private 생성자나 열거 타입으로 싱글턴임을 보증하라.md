## private 생성자나 열거 타입으로 싱글턴임을 보증하라

* 싱글턴이란 인스턴스를 오직 하나만 생성할 수 있는 클래스를 말한다
* 싱글턴의 전형적인 예는 무상태 객체나 설계상 유일해야 하는 시스템 컴포넌트 등에서 사용할 수 있다
* 클래스를 싱글턴으로 생성하면 해당 객체를 사용하는 클라이언트를 테스트하기 어려워질 수 있다
    * 타입을 인터페이스로 정의한 다음 그 인터페이스를 구현해서 만든 싱글턴이 아니라면 싱글턴 인스턴스를 mock 구현으로 대체할 수 없기 때문이다
* 싱글톤을 만드는 방식은 모두 생성자를 `private` 로 감춰두고 싱글톤 객체를 접근하는 방식이 존재한다

### public static 멤버로 유일한 인스턴스에 접근할 수단 활용하기

```java
public class Elvis {
    public static final Elvis INSTANCE = new Elvis();
    // private 생성자는 public static final 필드인 INSTANCE 를 초기화할 때 한번만 사용된다
    // 생성자가 private 만 존재하므로, Elvis 클래스가 초기화될 때 만들어진 인스턴스가 전체 시스템에서 하나뿐임이 보장된다
    // 이는 클라이언트에서 변경할 방법이 없으나, reflection API 를 통해서 private 생성자를 호출할 수 있다
    // 이러한 공격을 방어하기 위해서 생성자를 수정하여 둡번째 객체가 생성되려 하면 예외를 던지게 하면 된다
    private Elvis() { ... }
    
    public void leaveTheBuilding() { ... }
}
```

* 위의 방식은 해당 클래스가 싱글턴임이 API 에서 명백히 드러나며, 간결하다
    * `Elvis.INSTANCE` 로 접근한다

### 정적 팩토리 메서드를 public static 멤버로 제공

```java
public class Elvis {
    private static final Elvis INSTANCE = new Elvis();
    private Elvis() { ... }
    public static Elvis getInstance() { return INSTANCE; }
    // Elvis.getInstance() 의 결과는 항상 같은 객체의 참조를 반환하므로 같은 Elvis 인스턴스는 생성되지 않으나,
    // reflection API 를 통해 추가 생성에 대한 이슈는 같다
    
    public void leaveTheBuilding() { ... }
}
```

* API 의 변경없이 싱글턴이 아니게 변경이 가능하다
* 정적 팩토리를 제네릭 싱글턴 팩토리로 만들 수 있다
* 정적 팩토리의 메서드 참조를 공급자로 사용이 가능하다
    * `Elvis::getInstance` 를 `Supplier<Elvis>` 로 사용할 수 있다
    
### 원소가 하나인 열거 타입을 선언하여 싱글톤 만들기

```java
public enum Elvis {
    INSTANCE;
    
    public void leaveTheBuilding() { ... }
}
```

* `public` 필드 방식과 비슷하지만, 간결하고 추가 노력없이 직렬화할 수 있으며, 복잡한 직렬화 상황이나 리플렉션 공격에도 제 2의 인스턴스가 생기는 것을 완벽하게 방지해준다
* 부자연수러워 보일 수 있으나, 대부분의 상황에서 원소가 하나뿐인 열거 타입이 싱글턴을 만드는 가장 좋은 방법이다
* 단, 싱글턴이 Enum 외의 클래스를 상속해야 한다면 이러한 방법은 사용할 수 없다
    * 열거 타입이 다른 인터페이스를 구현하도록 선언할 수는 있다

### 싱글턴 클래스의 직렬화

* 단순히 Serializable 을 구현한다고 선언하는 것만으로 부족하다
* 모든 인스턴스 필드를 `transient` 라고 선언하고 readResolve 메서드를 제공해야 한다
    * 이렇게 하지 않으면 직렬화된 인스턴스를 역직렬화할 때마다 새로운 인스턴스가 생성된다
    
```java
private Object readResolve() {
    return INSTANCE;
    // 싱글턴임을 보장해준ms readResolve 메서드
        // 진짜 Elvis 를 반환하고, 가짜 Elvis 는 갑비지 컬렉터에 맡긴다
}
```
