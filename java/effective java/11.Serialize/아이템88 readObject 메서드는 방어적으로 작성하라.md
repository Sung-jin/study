## readObject 메서드는 방어적으로 작성하라

```java
public final class Period {
    private final Date start;
    private final Date end;
    
    public Period(Date start, Date end) {
        ...
    }
    
    public Date start() { return new Date(start.getTime()); }
    public Date end() { return new Date(end.getTime()); }
    public String toString() { return start + " - " + end; }
}
```

* 위의 클래스는 물리적 표현이 논리적 표현과 부합하므로 기본 직렬화 형태를 사용하여도 나쁘지 않다
    * 즉, `implements Serializable` 을 추가하는 것으로 직렬화를 할 수 있다
    * 하지만 이렇게 해서 해당 클래스의 주요한 불변식을 더는 보장하지 못하게 된다
* 문제는 readObject 메서드가 실질적으로 또 다른 public 생성자이기 때문이다
    * 따라서 다른 생성자와 똑같은 수준으로 주의를 기울여야 한다
    * 이는 보통의 생성자처럼 readObject 메서드에도 인수가 유효한지 검사해야 하고, 피요하다면 매개변수를 방어적으로 복사해야 한다
    * readObject 가 이러한 작업을 제대로 수행하지 못하면 공격자는 이주 쉽게 해당 클래스의 불변식을 깨뜨릴 수 있다
* readObject 는 매개변수로 바이트 스트림을 받는 생성자라 할 수 있다
    * 보통의 경우 바이트 스트림은 정상적으로 생성된 인스턴스를 직렬화하여 만들어진다
    * 하지만 불변식을 깨뜨릴 의도로 임의 생성한 바이트 스트림을 건네면 문제가 발생한다
    * 이는 정상적인 생성자로 만들어낼 수 없는 객체를 생성해낼 수 있기 때문이다
    
### 기본 직렬화의 유효성 검사

* 위의 상황처럼 직렬화할 수 있도록 선언한 것만으로 크래스의 불변식을 깨뜨릴 객체를 만들 수 있는 경우를 해결하기 위해서는 역직렬화된 객체가 유효한지 검사해야 한다
    * 이는 readObject 메서드가 defaultReadObject 를 호출한 다음 역직렬화된 객체가 유효한지 검사해야 한다
    
```java
private void readOBject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    s.defaultReadObject();
    
    if (checkInvalidObject) throw new InvalidObjectExcpetion("...");
}
```

* 위와 같은 유효성 체크로 허용되지 않는 인스턴스를 생성하는 일을 막을 수 있으나, private 필드에 대한 참조를 추가하면 가변 인스턴스를 생성할 수 있다
    * 이는 공격자가 ObjectInputStream 에서 Period 인스턴스를 읽은 후 스트림 끝에 추가된 악의적인 객체 참조를 읽어 객체 내부 정보를 얻을 수 있다
    * 이러한 참조를 통해 인스턴스들을 수정할 수 있고, 이는 더이상 불변이 아니게 된다
    
```java
public class MutablePeriod {
    public final Period period;
    public final Date start;
    public final Date end;
    
    public MutablePeriod() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            
            out.writeObject(new Peirod(new Date(), new Dat()));
            
            /*
             * 악의적인 이전 객체 참조
             */
            byte[] ref = { 0x71, 0, 0x8e, 0, 5 };
            bos.write(ref); // start 필드
            ref[4] = 4;
            bos.write(ref); // end 필드
            
            // Period 역직렬화 후 Date 참조를 훔친다
            ObjectInputStream in = new ObjetinputStream(new ByteArrayInputStream(bos.toByteArray()));
            period = (Period) in.readObject();
            start = (Date) in.readObject();
            end = (Date) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new AssertionError(e);
        }
    }
}

public static void main(String[] args) {
    MutablePeriod mp = new MutablePeriod();
    Period p = mp.period;
    Date pEnd = mp.end;
    
    pEnd.setYead(78);
    System.out.println(p);
    // ... PST 1978
    
    pEnd.setYear(69);
    System.out.println(p);
    // ... PST 1969
}
```

* 위와 같이 Period 인스턴스는 불변식을 유지한 채 성도었지만, 의도적으로 내부의 값을 수정할 수 있다
* 이를 통해 공격자는 인스턴스가 불변이라고 가정하는 클래스에 넘겨 엄청난 보안 문제를 일으킬 수 있다
    * 이는 실제로도 보안 문제를 String 이 불변이라는 사실에 기댄 클래스들이 존재한다
    
#### 충분한 방어적 복사

* 위의 상황은 객체를 역직렬화할 때 클라이언트가 소유해서는 안 되는 객체 참조를 가지는 필드를 모두 반드시 방어적으로 복사해야 한다
    * 따라서 readObject 에서는 불변 클래스 안의 모든 private 가변 요소를 방어적으로 복사해야 한다
    
```java
private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    s.defaultReadObject();
    
    // 가변 요소들을 방어적으로 복사한다
    start = new Date(start.getTime());
    end = new Date(end.getTime());
    
    if (start.compareTo(end) > 0) throw new InvalidObjectExcpetion("...");
}
```

* 위와 같이 방어적 복사를 유효성 검사보다 앞서 수행하면, Date 의 clone 메서드는 사용하지 않았음을 주목해야 한다
    * 이는 Period 를 공격으로부터 보호하는 데 필요하다
    * final 필드는 방어적 복사가 불가능하므로, readObject 메서드를 사용하려면 start, end 필드에서 final 한정자를 제거해야 한다
    
### 기본 readObject 메서드 사용여부 판단

* transient 필드를 제외한 모든 필드의 값을 매개변수로 받아 유효성 검사 없이 필드에 대입하는 public 생성자를 추가해도 괜찮은가의 답에 따라 결정된다
    * 아니오일 경우 커스텀 readObject 메서드를 만들어 생성자에서 수행해야 할 모든 유효성 검사와 방어적 복사를 수행해야 한다
* 또는 직렬화 프록시 패턴을 사용하는 방법이 있다
    * 이는 역직렬화를 안전하게 만드는데 필요한 노력을 상당히 경감해준다
    
### final 이 아닌 직렬화 가능 클래스

* readObject 와 생썽자의 공통점이 하나 더 있다
    * 생성자처럼 readObject 메서드도 재정의 가능 메서드를 호출해서는 안된다
* 이를 어겼을 경우 해당 메서드가 재정의되면 하위 클래스의 상태가 완전히 역직렬화되기 전에 하위 클래스에서 재정의된 메서드가 실행된다
* 이는 프로그램의 오작동으로 이어지게 된다

## 정리

* readObject 메서드를 작성할 때는 언제나 public 생성자를 작성하는 자세로 임해야 한다
    * readObject 는 어떠한 바이트 스트림이 넘어오더라도 유효한 인스턴스를 만들어내야 한다
* 바이트 스트림이 진짜 직렬화된 인스턴스라고 가정하면은 안된다
    * 이는 기본 직렬화 형태 뿐 아니라 커스텀 직렬화를 사용하더라도 모든 문제가 그대로 발생할 수 있다
* 안전한 readObject 메서드를 작성하는 지침 요약
    1. private 이어야 하는 객체 참조 필드는 각 필드가 가리키는 객체를 방어적으로 복사해야 하며, 불변 클래스 내의 가변 요소가 여기에 속한다
    2. 모든 불변식을 검사하여 어긋나는 게 발견되면 InvalidObjectException 예외를 던지고, 방어적 복사 다음에는 반드시 불변식 검사가 뒤따라야 한다
    3. 역직렬화 후 객체 그래프 전체의 유효성을 검사해야 한다면 ObjectInputValidation 인터페이스를 사용하라
    4. 직접적이든 간적접이든 재정의할 수 있는 메서드는 호출하지 말아야 한다
