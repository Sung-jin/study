## clone 재정의는 주의해서 진행하라

* Cloneable 은 복제해도 되는 클래스임을 명시하는 용도의 믹스인 인터페이스지만, 의도한 목적을 제대로 이루지 못하였다
* clone 메서드가 선언된 곳이 Cloneable 이 아닌 Object 이고, protected 인 점이 가장 큰 문제이다
    * 결론적으로 Cloneable 을 구현하는 것만으로는 외부 객체에서 clone 매세드를 호출할 수 없다
* 하지만 이러한 여러 문제점이 있음에도, Cloneable 방식은 널리 쓰이고 있어서 잘 알아두는 것이 좋다 
* 메서드가 없는 Cloneable 을 사용하면, Object 의 protected 메서드인 clone 의 동작 방식을 결정한다
    * Cloneable 을 구현한 클래스의 인스턴스에서 clone 을 호출하면 해당 객체의 필드들을 모두 복사한 객체를 반환한다
    * 그렇지 않은 클래스의 인스턴스에서 호출하면 `CloneNotSupportedException` 예외가 발생한다
    * 일반적인 인터페이스는 특정 기능을 '제공' 한다고 선언하는 행위이지만, Cloneable 은 상위 클래스에 정의된 protected 메서드의 동작 방식을 변경한 형태이다
    * 명세에는 없지만, Cloneable 을 구현한 클래스는 clone 메서드를 public 으로 제공하며, 사용자는 복제가 제대로 이뤄진다고 예상한다
        * 이를 통해 생성자나 빌더 등과 같이 강제한 형태로 객체를 만드는 것이 아닌, 깨지기 쉽고 위험하고 모순적인 형태로 객체를 생성할 수 있게 된다
    
### Object 에서의 clone 규약

* 객체의 복사본을 생성하여 반환한다
    * 복사란 해당 객체를 구현한 클래스에 따라 달라질 수 있다
* 일반적인 의도는 아래와 같이 어떤 객체 x 에 대해 모두 참이다
    * `x.clone() != x`
    * `x.clone().getClass() == x.getClass()`
    * `x.clone().equals(x)` 는 일반적으로 참이지만, 필수는 아니다
    * 관례상, 반환된 객체와 원본 객체는 독립적이어야 하며, 이를 만족하기 위해서 super.clone 으로 얻은 객체의 필드 중 하나 이상을 반환 전에 수정해야 할 수도 있다
* 강제성이 없다는 점을 제외하면 생성자 연쇄와 비슷한 매커니즘이다
    * 결국 clone 메서드가 super.clone 이 아닌, 생성자를 호출해 얻은 인스턴스를 반환해도 문제는 없다
* 특정 클래스의 하위 클래스에서 super.clone 을 호출한다면, 잘못된 클래스의 객체가 만들어져, 하위 클래스의 clone 메서드가 제대로 동작하지 않게 된다

### 제대로 동작하는 clone 메서드를 가진 상위 클래스를 상속

* `super.clone` 을 호출하여 얻는 객체는 원본의 완벽한 복제본일 것이고, 클래스에 정의된 모든 필드는 원본 필드와 똑같은 값을 가진다
* 클래스의 모든 필드가 기본 타입이거나 불변 객체를 참조한다면 해당 객체는 원하는 형태를 가지지만, 굳이 불변 클래스를 복사를 지양하는 점이 좋다

### 가변 객체를 참조하는 클래스의 cloneable

```java
public class Stack {
    private OBject[] elements;
    
    ...
    
    public Stack() {
        this.elements = new Object[];
    }
    
    ...
}
```

* 위와 같은 클래스는 clone 메서드가 단순히 super.clone 의 결과를 그대로 반환한다면, `elements` 필드는 원본 Stack 인스턴스와 같으므로 같은 배열을 참조한다
* 즉, 원본 또는 clone 된 객체의 `elements` 가 변경되면 불변식이 깨지고 문제가 발생한다
* 위와 같은 stack 클래스의 하나뿐인 생성자를 호출한다면, 이러한 현상을 발생할 수 없다
    * clone 메서드는 생성자와 같은 효과를 내며, clone 은 원본 객체에 아무런 해를 끼치지 않는 동시에 복제된 객체의 불변식을 보장해야 한다
    * 이는 stack 의 내부 정보를 복사해야 하고, 이때는 `elements` 배열의 clone 을 재귀적으로 호출하는 형태로 구현하면 된다
    * `elements` 가 final 이라면, 새로운 값을 할당할 수 없기 때문에, 재귀적으로 호출하는 형태는 불가능하다
    * Cloneable 아키텍처는 '가변 객체를 참조하는 필드는 final 로 선언하라' 라는 일반 용법과 충돌된다
* 배열의 clone 은 런타임 타입과 컴파일타임 타입 모두가 원본 배열과 똑같은 배열을 반환하고, 배열을 복제할 떄는 배열의 clone 메서드를 사용하라고 권장된다

#### 재귀적 호출로 clone 이 어려운 케이스

* 해시테이블 내부는 버킷들의 배열이고, 각 버킷은 키-값 쌍을 담는 연결 리스트의 첫 번째 엔트리를 참조한다

```java
public class HashTable implements Cloneable {
    private Entry[] buckects = ...;
    
    private static class Entry {
        final Object key;
        Object value;
        Entry next;
        
        Entry (Object key, Object value, Entry next) {
            ...
        }
    }
    ...
    
    @Override
    public HashTable clone() {
        try {
            HashTable result = (HashTAble) super.clone();
            result.buckects = buckets.clone();
            return result;
        } catch (...) { ... }
    }
}
```

* 복제본은 자신만의 버킷 배열을 가지지만, 해당 배열은 원본과 같은 연결 리스트를 참조하여 원본과 복제본 모두 예기치 못한 동작할 가능성이 생긴다
* 이러한 부분을 해결하기 위해서는 각 버킷을 구성하는 연결 리스트를 복사해야 한다

```java
@Override
public class HashTable implements Cloneable {
    ...
    
    private static class Entry {
        ...
        
        Entry deepCopy() {
            return new Entry(key, value, next == null ? null : next.deepCopy());
        }
    }
    
    try {
        HashTable result = (HashTAble) super.clone();
        result.buckets = new Entry[buckets.length];
        for (int i = 0; i < buckets.length: i++) {
            if (buckets[i] != null) result.buckets[i] = buckets[i].deepCopy();
            
            return result
        }
    } catch (...) { ... }
}
```

* 위와 같이 deep copy 를 제공하는 형태로 해결할 수 있다
* 버킷이 길 경우 재귀 호출로 인해 스택 오버플로를 일으킬 가능성이 존재한다

```java
Entryt deepCopy() {
    Entry result = new Entry(key, value, next);
    for (Entry p = result; p.next != null; p = p.next) {
        p.next = new Entry(p.next.key, p.nexct.value, p.next.next);
    }
    
    return next;
}
```

* 재귀 호출 대신 반복자를 사용하여 순회하는 형태로 수정을 하면, 스택 오버플로우에 대한 문제는 해결된다

### 가변 객체 복제

* super.clone 을 호출하여 얻은 객체의 모든 필드를 초기 상태로 설정한 다음, 원본 객체의 상태를 다시 생성하는 고수준 메서드들을 호출한다
    * HashTable 의 `put(key, value)` 와 같은 메서드가 고수준 메서드이다
    * 간단하고 깔끔하게 복제가 가능하나, 저수준 API 를 활용할 때에 비해 처리속도가 느리다
    * 또한, Cloneable 아키텍처의 기초가 되는 필드 단위 객체 복사를 우회하기 때문에 전체 Cloneable 아키텍처와는 어울리지 않는 방식이다
* 재정의될 수 있는 메서드를 clone 메서드에서 호출하면 안된다
    * clone 이 하위 클래스에서 재정의한 메서드를 호출하면, 하위 클랫느느 복제 과정에서 자신의 상태를 교정할 기회를 잃게 되어 원본과 복제본의 상태가 달라질 가능성이 크다
    * 결국 `put(key, value)` 와 같은 고수준 메서드는 final 이거나 private 이어야 한다
* 상속해서 사용하기 위한 클래스 설계 방식 중 어디에도 상속용 클래스는 Cloneable 을 구현해서는 안된다
    * Object 의 방식을 모방하여, Object 를 바로 상속할 때처럼 Cloneable 구현 여부를 하위 클래스에서 선택하는 형태로 할 수 있다
    * 또는 clone 을 동작하지 않게 구현하고, 하위 클래스에서 재정의하지 못하게 할 수도 있다
    ```java
    @Override
    protected final Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
    ```

### 스레드와 clone

* Cloneable 을 구현한 스레드 안전 클래스를 작성할 때는 clone 메서드 역시 동기화를 해줘야 한다
    * Object 의 clone 메서드는 동기화를 신경쓰지 않았으므로, super.clone 만 호출하고 끝나더라도 clone 을 재정의하고 동기화해줘야 한다
    
## 정리

* Cloneable 을 구현하는 모든 클래스는 clone 을 재정의해야 한다
    * 접근 제한자는 public, 반환 타입은 클래스 자신으로 변경해야 한다
    * 재정의한 clone 메서드는 가장 먼저 `super.clone` 을 호출한 후 필요한 필드를 전부 적절히 수정해야 한다
        * 일반적으로 객체 내부에 숨어있는 모든 가변 객체를 복사하고, 복제본이 가진 객체 참조 모두가 복사된 객체들을 가리키게 함을 뜻한다
* 내부 복사는 주로 clone 을 재귀적으로 호출하여 복사해야 한다
    * 기본 타입 필드와 불변 객체 참조만을 가지는 필드는 단순히 재귀적 호출 하나만으로 처리가 가능하다
* 이러한 clone 에 관련된 작업이 필수는 아니며, Cloneable 을 구현한 클래스가 아니라면 복사 생성자와 복사 팩토리라는 더 나은 객체 복사 방식을 제공할 수 있다
    * 언어 모순적이고 위험한 형태의 객체 생성 메커니즘을 사용하지 않는다
    * 엉성하게 문서화된 규약을 기대지 않고 정상적인 final 필드 용법과도 충돌하지 않는다
    * 불필요한 검사 예외를 던지지 않고, 형변환도 필요하지 않다
    * 복사 생성자와 복사 팩토리는 해당 클래스가 구현한 '인터페이스' 타입의 인스턴스를 인수로 받을 수 있다
        * 인터페이스 기반 복사 생성자는 '변환 생성자'/ 인터페이스 기반 복사 팩토리는 '변환 팩토리' 라는 이름이 존재한다
    * 이러한 것을 이용하여 원본의 구현 타입에 얽매이지 않고 복제본의 타입을 직접 선택할 수 있다
    ```java
    public Yum(Yum yum) {...}
    // 복사 생성자
    
    public static Yum newInstance(Yum yum) {...}
    // 복사 팩토리
    ```
* Cloneable 이 가지고 온 모든 문제를 확인하였을 때, 새로운 인터페이스를 만들 때는 절대 Cloneable 을 확장해서는 안 되며, 새로운 클래스도 이를 구현해서는 안된다
* final 클래스는 Cloneable 을 구현해도 위험이 크지 않으나, 성능 최적화 관점에서 검토한 후 문제가 없을 때만 드물게 허용해야 한다
* 기본 원칙은 '복제 기능은 생성자와 팩토리를 이용하는게 최고' 라는 점이다
    * 단, 배열만은 clone 메서드 방식이 가장 깔끔한 예외이다
