## 인스턴스 수를 통제해야 한다면 readResolve 보다는 열거 타입을 사용하라

```java
public class Elvis {
    public static final Elvis INSTANCE = new Elvis();
    private Elvis() { ... }
    
    public void leaveTheBuilding() { ... }
}
// 위와 같은 싱글톤 클래스에 implements Serializable 을 추가하는 순간 더이상 싱글톤이 아니게 된다
// 이는 기본 직렬화를 사용하지 않고 명시적인 readObject 를 제공하여도 소용이 없다
// 어떤 readObject 를 사용하든 이 클래스가 초기화될 때 만들어진 인스턴스와는 별개인 인스턴스를 반환하게 된다
```

### readResolve

* readResolve 를 사용하면 readObject 가 만들어낸 인스턴스를 다른 것으로 대체할 수 있다
    * 역직렬화한 객체의 클래스가 readResolve 메서드를 적절히 정의하였따면, 역직렬화 후 새로 생성된 객체를 인수로 해당 메서드가 호출되고, 이 메서드가 반환한 객체 참조가 새로 생성된 객체를 대신해 반환된다
    * 대부분의 경우 새로 생성된 객체의 참조는 유지하지 않으므로 바로 가비지 컬렉션 대상이 된다
    
```java
private Object readResolve() {
    return INSTANCE;
    // 이를 통해 진짜 Elvis 를 반환하고, 가짜 Elvis 는 가비지 컬렉터에 맡긴다
}
```

* 위와같이 정의하면 역직렬화한 객체는 무시하고 클래스 초기화 때 만들어진 객체를 반환한다
    * Elvis 인스턴스의 직렬화 형태는 아무런 실 데이터를 가질 이유가 없으니 모든 인스턴스 필드를 transient 로 선언해야 한다
* readResolve 를 인스턴스 통제 목적으로 사용한다면 객체 참조 타입 인스턴스 필드는 모두 transient 로 선언해야 한다
    * transient 로 선언하지 않으면 readResolve 메서드가 수행되기 전에 역직렬화된 객체의 참조를 공격할 여지가 남는다
    
#### 역직렬화된 객체의 참조

* 싱글턴이 transient 가 아닌 참조 필드를 가지고 있다면, 해당 필드의 내용은 readResolve 메서드가 실행되기 전에 역직렬화된다
* 이는 잘 조작된 스트림을 사용하여 해당 참조 필드의 내용이 역직렬화되는 시점에 그 역직렬화된 인스턴스의 참조를 훔쳐올 수 있다

```java
public class Elvis implements Serializable {
    public static final Elvis INSTANCE = new Elvis();
    private Elvis() { }
    
    private String[] favoriteSongs = { ... }

    private void printFavorites() {
        System.out.println(Arrays.toString(favoriteSongs));
    }
    
    private Object readResolve() {
        return INSTANCE;
    }
}

public class ElvisStealer implements Serializable {
    static Elvis impersonator;
    private Elvis payload;
    
    private Object readResolve() {
        // resolve 되기 전 Elvis 인스턴스의 참조를 저장
        impersonator = payload;
        
        // favoriteSongs 필드에 맞는 타입의 객체를 반환
        return new String[] { "... " };
    }
    private static final long serialVersionUID = 0;
}

public class ElvisImpersonator {
    // 진짜 Elvis 인스턴스로 만들어질 수 없는 바이트 스트림
    private static final byte[] serializedForm = { (byte)... };
    
    public static void main(String[] args) {
        // ElvisStealer.impersonator 를 초기화한 다음, 진짜 Elvis 를 반환
        Elvis elvis = (Elvis) desrialize(serializedForm);
        Elvis impersonator = ElvisStealer.impersonator;
        
        elvis.printFavorites();         // Elvis 에 정의된 favoriteSongs 출력
        impersonator.printFavorites();  // ElvisStealer 에 정의된 favoriteSongs 출력
    }
}
```

1. 이는 readResolve 메서드와 인스턴스 필드 하나를 포함한 도둑 클래스를 작성하고, 이는 도둑이 숨길 직렬화된 싱글턴을 참조하는 역할을 한다
2. 직렬화된 스트림에서 싱글턴의 비휘발성 필드를 도둑의 인스턴스로 교체
3. 싱글턴은 도둑을 참조하고 도둑은 싱글턴을 참조하는 순환고리가 만들어진다

### 열거 타입을 활용한 싱글톤의 직렬화 처리

* ElvisStealer 공격처럼 readResolve 메서드를 사용하여 순간적으로 만들어진 역직렬화된 인스턴스에 접근하지 못하게 하는 방법은 깨지기 쉽고 신경을 많이 써야 하는 작업이다
* 직렬화 가능 인스턴스 통제 클래스를 열거 타입을 이용하여 구현하면 선언한 상수 외의 다른 객체는 존재하지 않음을 자바가 보장해준다
    * 공격자가 `AccessibleObject.setAccessible` 과 같은 특권 메서드를 악용할 경우 이야기가 달라진다
    * 임의의 네이티브 코드를 수행할 수 있는 특권을 가로챈 공격자에게는 모든 방어가 무력화된다
    
```java
public enum Elvis {
    INSTANCE;
    private STring[] favoriteSongs = { ... };
    public void printFavorites() {
        System.out.println(Arrays.toString(favoriteSongs));
    }
}
```

#### readResolve 는 필요없는가

* readResolve 를 사용하는 형태는 완전 쓸모없는 것은 아니다
* 이는 직렬화 가능 인스턴스 통제 클래스를 작성해야 하는데, 컴파일타임에 어떤 인스턴스들이 있는지 알 수 없는 상황이라면 열거 타입으로 표현하는 것이 불가능하기 때문이다
* readResolve 메서드의 접근성은 매우 중요하다
    * final 클래스에서라면 readResolve 메서드는 private 메서드어이야 한다
* final 이 아닌 경우 다음과 같은 주의사항을 고려해야 한다
    * private 으로 선언하면 하위 클래스에서 사용할 수 없다
    * package-private 으로 선언하면 같은 패키지에 속한 하위 클래스에서만 사용할 수 있다
    * protected 나 public 으로 선언하면 이를 재정의하지 않은 모든 하위 클래스에서 사용할 수 있다
    * protected 나 public 이면서 하위 클래스에서 재정의하지 않았따면, 하위 클래스의 인스턴스를 역직렬화하면 상위 클래스의 인스턴스를 생성하여 ClassCastException 이 발생할 수 있다

## 정리

* 불변식을 지키기 위해 인스턴스를 통제해야 한다면 가능한 한 열거 타입을 사용하자
* 여의치 않은 상황에서 직렬화와 인스턴스 통제가 모두 필요하다면 readResolve 메서드를 작성하여 넣어야 하고, 그 클래스에서 모든 참조 타입 인스턴스 필드를 transient 로 선언해야 한다
