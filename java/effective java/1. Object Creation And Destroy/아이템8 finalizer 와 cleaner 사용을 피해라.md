## finalizer 와 cleaner 사용을 피하라

* `finalizer`/`cleaner` 는 자바에서 제공하는 두가지 객체 소멸자이다
    * `finalizer` 는 예측할 수 없고 상황에 따라 위험할 수 있어 일반적으로 불필요하다
    * `clenaer` 는 Java9 에서 `finalizer` 가 deprecated 되면서 나온 대안이지만, 덜 위험할 뿐 여전히 예측할 수 없고 느리고 일반적으로 불필요하다
    * C++ 의 destructor(특정 객체와 관련된 자원을 회수하는 보편적인 방법) 와는 다른 개념이다
        * destructor 는 비메모리 자우너을 회수하는 용도로 쓰이고, 자바에서는 try-with-resource 와 try-finally 를 사용해서 해결한다
* 자바에서는 접근할 수 없게 된 객체를 회수하는 역할을 가비지 컬렉터가 담당하고, 프로그래머는 아무런 작업이 필요로 하지 않는다
* `finalizer`/`cleaner` 는 자바 언어 명세에서도 수행 시점뿐 아니라 수행 여부조차 보장하지 않는다

### 문제점

* `finalizer`/`cleaner` 는 즉시 수행됨을 보장하지 않으므로 해당 명령이 실행되기까지 얼마나 걸릴지 알수 없다
* 이는 파일 닫기와 같은 행위를 `finalizer`/`cleaner` 에 맡기면 오류가 발생할 수 있다
    * 시스템이 동시에 열 수 있는 파일 개수에 한계까 있기 때문에 파일 닫기가 실행되지 않고 나중에 작업이 된다면 오류가 발생할 수 있다
* `finalizer`/`cleaner` 의 수행하는 시점은 전적으로 가비지 컬렉터 알고리즘에 달려있으며, 가비지 컬렉터 구현마다 모두 다르다
* 이는 테스트한 환경의 JVM 에서 정상 동작하더라도 특정 환경에서는 문제가 발생할 수 있음을 의미한다
* 클래스에 `finalizer` 를 달아두면 해당 인스턴스의 자원 회수가 제멋대로 지연될 수 있고, 이로인해 OutOfMemoryError 등이 발생할 수 있다
    * `finalizer` 의 작업 스레드의 우선순위가 낮아서 자원 회수가 늦어진 경우에 위와 같은 문제가 발생할 수 있다
    * `cleaner` 는 자신을 수행할 스레드를 제어할 수 있다는 면에서 조금 낫지만, 여전히 백그라운드에서 수행되며 가비지 컬렉터의 통제하에 있으니 즉각 수행된다는 보장은 없다
* 프로그램 생애주기와 상관없는 상태를 영구적으로 수정하는 작업에서는 절대 `finalizer`/`cleaner` 에 의존하면 안된다
    * 접근할 수 없는 일부 객체에 딸린 종료 작업을 전혀 수행하지 못한 채 프로그램이 중단될 수 있기 때문이다
    * 데이터베이스 lock 해제와 같은 행위를 `finalizer`/`cleaner` 에 맡기면 분산 시스템 전체가 서서히 멈출 것이다
* `finalizer` 동작중 발생한 예외는 무시되고, 처리할 작업이 남아있더라도 그 순간 종료된다
    * 이는 해당 객체가 마무리 되지 못한 상태로 남을 수 있음을 의미한다
    * 또한 이러한 잘못된 상태의 객체를 다른 스레드가 사용할 경우 어떠한 문제가 발생할지 예상할 수 없다
    * 일반적으로 예외가 발생한다면, 해당 스레드는 종료가 되면서 stack trace 를 출력하지만, `finalizer` 에서는 경고조차 출력하지 않는다
* `finalizer`/`cleaner` 는 심각한 성능 문제가 동반된다
    * AutoCloseable 객체를 생성하고 가비지 컬렉터가 수거하기까지 걸린 시간
        * try-with-resources: 12ns
        * `finalier`: 550ns
    * `finalizer` 는 가비지 컬렉터의 효율을 떨어뜨리기 때문이다

#### finalizer 의 보안 문제

* `finalizer` 를 사용한 클래스는 `finalizer` 공격에 노출되어 심각한 보안 문제를 일으킬 수 있다
* 생성자나 직렬화 과정에서 예외가 발생하면, 생성되다 만 객체에서 악의적인 하위 클래스의 `finalizer` 가 수행될 수 있게 된다
* 정적 필드에 자신의 참조를 할당하여 가비지 컬렉터가 수집하지 못하게 막을 수 있다
* 객체의 잘못된 생성을 막을때 생성자에서 예외로 처리를 할 수 있지만, `finalizer` 를 사용한 클래스에서는 그렇지 않다
    * final 클래스들은 ㅇ어떠한 하위 클래스를 만들 수 없으므로 이러한 공격에서 안전하다
    * 즉, `finalizer` 공격으로부터 방어하려면 아무 일도 하지 않는 `finalize` 메서드를 만들고 final 을 선언하면 된다
    
#### 어... 그래서 finalizer/cleaner 는 왜 존재하는데?

* 자원의 소유자가 close 메서드를 호출하지 않는 것에 대비한 안전망 역할을 할 수 있다
    * `finalizer`/`cleaner` 가 즉시 호출되리라는 보장은 없지만, 클라이언트가 하지 않은 자원 회수를 늦게라도 해주는 것이 아에 하지 않는 것보다는 낫다
    * 하지만 이러한 안전망 역할을 `finalizer` 를 작성할 때는 그럴만한 값어치가 있는지 심사숙고 해야 한다
    * 일부 자바 라이브러리의 일부 클래스는 안전망 역할의 `finalizer` 를 제공한다ㄴ
        * 대표적으로 `FileInputStream`/`FileOutputStream`/`threadPoolExecutor` 가 존재한다
* 네이티브 피어와 연결된 객체에서 활용할 수 있다
    * 네이티브 피어: 일반 자바 객체가 네이티브 메서드를 통해 기능을 위임한 네이티브 객체를 말한다
    * 네이티브 피어는 자바 객체가 아니므로 가비지 컬렉터는 해당 객체의 존재를 알 수 없고, 이러한 객체의 자원 회수는 `finalizer`/`cleaner` 가 담당하기에 적절하다
        * 단, 성능 저하를 감당할 수 있고, 네이티브 피어가 심각한 자원을 가지고 있지 않을 때에만 해당한다
        * 성능 저하 또는 자원 회수가 중요하다면 close 메서드를 사용해야 한다

### cleaner

```java
public class Room implements AutoCloseable {
    private static final Cleaner cleaner = Cleaner.create();
    
    // 청소가 필요한 자원이며, Room 을 참조해서는 안된다
    private static class State implements Runnable {
        int numJunkPiles;
        
        State(int numJunkPiles) {
            this.numJunkPiles = numJunkPiles;
        }
        
        // close 메서드 또는 cleaner 가 호출한다
        @Override public void run() {
            System.out.println("방 청소");
            numJunkPiles = 0;
        }
    }
    
    // 바으이 상태이며, cleanable 과 공유한다
    private final State state;
    
    // cleanable 객체이며, 수거 대상이 되면 방을 청소한다
    private final Cleaner.Cleanable cleanable;
    
    public Room(int numJunkPiles) {
        state = new State(numJunkPiles);
        cleanable = cleaner.register(this, state);
    }
    
    @Override
    public void close() {
        cleanable.clean();
    }
}

// Room 자원을 수거하기 전에 반드시 clean 해야 한다는 가정을 한다
// Room 클래스는 AutoCloseable 을 구현한다
// finalizer 와 달리 cleaner 는 클래스의 public API 에 나타나지 않는다
```

* static 으로 선언된 State 클래스는 cleaner 가 방을 청소할 때 수거할 자원을 담고 있다
    * 단순히 numJunkPiles 필드가 수거할 자원에 해당하지만, 현실적으로 만든다면 해당 필드는 네이티브 피어를 가르키는 포인터를 담은 final long 변수이여야 한다
* State 는 Runnable 을 구현하고, run 메서드는 cleanable 에 의해 딱 한번만 호출 될 것이다
    * cleanable 객체는 Room 생성자에서 cleaner 에 Room 과 State 를 등록할 때 얻는다
* run 메서드가 호출되는 상황은 둘중 하나이다
    1. 보통 Room 의 close 메서드를 호출할 때이다
        * close 메서드에서 cleanable 의 clean 을 호출하면, 해당 메서드 안에서 run 을 호출한다
    2. 가비지 컬렉터가 Room 을 회소할 때까지 클라이언트가 close 를 호출하지 않는다면, cleaner 가 State 의 run 메서드를 호출할 것이다
* State 인스턴스는 절대로 Room 인스턴스를 참조해서는 안된다
    * Room 인스턴스를 참조할 경우 순환참조가 발생하여 가비지 컬렉터가 Room 인스턴스를 회수해갈 기회가 오지 않는다
    * State 가 정적 중첩 클래스인 이유는 중첩 클래스는 자동으로 바깥 객체의 참조를 가지기 때문이다
    * 람다 역시 바깥 객체의 참조를 가지기 쉬우니 사용하지 않는 것이 좋다
* Room 의 cleaner 는 단지 안전망으로만 쓰였고, 클라이언트가 모든 Room 생성을 try-with-resources 블록으로 감쌋다면 자동 청소는 전혀 필요하지 않다
 
```java
public class Adult {
    public static void main(String[] args) {
        try (Room myRoom = new Room(7)) {
            System.out.println("blah");
        }
        // blah -> 방 청소 순으로 출력된다
    }
}

public class Teenager {
    public static void main(String[] args) {
        new Room(99);
        System.out.println("blah blah");
        // 방 청소 라는 출력은 한번도 출력되지 않고, 이러한 상황이 예측할 수 없다고 한 상황이다
    }
}
```

> cleaner 명세 내용
> 
> System.exist 을 호출할 때의 cleaner 동작은 구현하기 나름이다 <br/>
> 청소가 이루어질지는 보장하지 않는다

#### System.gc 와 System.runFinalization

* `System.gc`/`System.runFinalization` 메서드의 경우에 `finalizer`/`cleaner` 가 실행될 가능성을 높여줄 뿐 보장하지는 않는다
* `System.runFinalizersOnExist`/`Runtime.runFinalizersOnEixt` 의 경우 `finalizer`/`cleaner` 의 실행을 보장하지만 심각한 결함에 의해 지탄받아 왔다

### finalizer/cleaner 의 대안

* AutoCloseable 을 구현해주고, 클라이언트에서 인스턴스를 다 쓰고 나면 close 메서드를 호출하기만 하면 된다
    * 일반적으로 예외가 발생해도 제대로 종료되도록 try-with-resource 를 사용해야 한다
* 각 인스턴스는 자신이 닫혔는지를 추적하는 것이 좋다
    * 이는 close 메서드에서 해당 객체는 더이상 유효하지 않음을 필드에 기록하고, 다른 메서드는 해당 필드를 검사해서 객체가 닫힌 후 불렸다면 `IllegalStateException` 예외를 던지는 것이다

## 정리

* `finalizer`/`cleaner` 는 안전망 역할이나 중요하지 않는 네이티브 자원 회수용으로만 사용하자
* 이러한 경우라도 불확실성과 성능 저하에는 주의해야 한다
