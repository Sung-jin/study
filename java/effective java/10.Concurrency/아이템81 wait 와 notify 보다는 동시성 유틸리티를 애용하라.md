## wait 와 notify 보다는 동시성 유틸리티를 애용하라

* Java 5 에서 도입된 고수준의 동시성 유틸리티로 인해 wait/notify 를 사용해야 할 이유가 많이 줄었다
* wait 와 notify 는 올바르게 사용하기가 아주 까다로우며, 고수준 동시성 유틸리티를 이용하는게 좋다

### java.util.concurrent

* 고수준 유틸리티는 세 범주로 나눌 수 있다
    1. 실행자 프레임워크 (아이템 80 에서 다뤘다)
    2. 동시성 컬렉션
    3. 동기화 장치

#### 동시성 컬렉션

* List, Queue, Map 등과 같은 표준 컬렉션 인터페이스에 동시성을 추가한 고성능 컬렉션이다
* 높은 동시성에 도달하기 위하여 동기화를 각자의 내부에서 수행한다
* 따라서 동시성 컬렉션에서 동시성을 무력화하는 건 불가능하며, 외부에서 락을 추가로 사용하면 오히려 속도가 느려진다
    * 동시성을 무력화할 수 없으므로, 여러 메서드를 원자적으로 묶어 호출하는 행동은 불가능하다
    * 이를 위해 여러 기본 동작을 하나의 원자적 동작으로 묶는 '상태 의존적 수정' 메서드들이 추가되었다
    * 이러한 메서드들은 유용하여 Java 8 에서 일반 컬렉션 인터페이스에도 디폴트 메서드 형태로 추가되었다
    
```java
private static final ConcurrentMap<String, String> map = new ConcurrentHashMap<>();

public static String intern(String s) {
    String result = map.get(s);
    // ConcurrentHashMap 은 get 와 같은 검색 기능에 최적화 되어 있으므로
    // 필요할 때 putIfAbsent 를 호출하는 형태로 최적화가 가능하다
    if (result == null) {
        result = map.putIfAbsent(s, s);
        if (result == null) result = s;
    }
}
// 위와 같은 구현하면, String 에 내부에 추가적인 기능을 감안하더라도, 더 빠르게 동작한다
```

* 이러한 동시성 컬렉션은 동기화한 컬렉션을 낡은 유산으로 만들었다
    * `Collections.synchronizedMap` -> `ConcurrentHashMap` 을 사용하는게 훨씬 좋다
* 동기화된 맵을 동시성 맵으로 교체하는 것만으로 동시성 어플레키에션의 성능은 극적으로 개선된다

#### 컬렉션 인터페이스

* 컬렉션 인터페이스 중 일부는 작업이 성공적으로 완료될 때까지 기다리도록 확장되었다
    * Queue 를 확장한 BlockingQueue 에 추가된 메서드 중 take 는 큐의 첫 원소를 꺼낸다
    * 만약 큐가 비었다면 새로운 원소가 추가될 때까지 기다린다
    * 이러한 특성으로 작업 큐로 사용하기에 적합하다
    * BlockingQueue 는 대표적으로 ThreadPoolExecutor 를 포함한 대부분의 실행자 서비스 구현체에서 사용된다
    
### 동기화 장치

* 동기화 장치는 스레드가 다른 스레드를 기다릴 수 있게 하여, 서로 작업을 조율할 수 있게 해준다
* 가장 자주 사용되는 동기화 장치는 countDownLatch 와 Semaphore 이다
    * CyclicBarrier 와 Exchanger 는 덜 쓰이며, 가장 강력한 동기화 장치는 Phaser 이다
    * countDownLatch 은 일회성 장벽으로, 하나 이상의 스레드가 또 다른 하나 이상의 스레드 작업이 끝날 때까지 기다리게 한다
* 동기화 장치를 이용한다면 어떠한 동작들을 동시에 시작하여 모두 완료하기까지의 시간을 재는 간단한 프레음워크와 같은 기능을 쉽게 만들 수 있다

```java
public static long time(Executor executor, int concurrency, Runnable action) throws InterruptedException {
    CountDownLatch ready = new CountDownLatch(concurrency);
    CountDownLatch start = new CountDownLatch(1);
    CountDownLatch done = new CountDownLatch(concurrency);
    
    for (int i = 0l i < concurrency; i++) {
        executor.execute(() -> {
            // 타이머에게 준비를 마쳤음을 알린다
            try {
                // 모든 작업자가 준비될 때까지 기다린다
                start.await();
                action.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                // 타이머에게 작업 마침을 알림
                done.countDown();
            }
        })
    }
    
    ready.start(); // 모든 작업자가 준비될 때까지 기다림
    long startNanos = Stystem.nanoTime();
    start.countDown(); // 작업자들을 깨움
    done.await(); // 모든 작업자가 일을 끝마치기를 기다림
    
    return Stystem.nanoTime() - startNanos;
    // 참고로 시간 측정시 System.currentTimeMillis 가 아닌 System.nanoTime 을 사용해야 한다
    // System.nanoTime 은 더 정확하고 정밀하여 시스템의 실시간 시계의 시간 보정에 영향받지 않는다
}
```

### wait 와 notify

* 새로운 코드라면 언제나 wait, notify 가 아닌 동시성 유틸리티를 사용해야 한다
    * 단, 어쩔수 없이 사용해야 하는 레거시 코드도 존재한다
* wait 메서드는 스레드가 어떠한 조건이 충족되기를 기다리게 할 때 사용한다
    * 락 객체의 wait 메소드는 반드시 해당 객체를 감근 동기화 영역 안에서 호출해야 한다
    
```java
synchronized (obj) {
    while (isLockConditionNotDone) obj.wait();
    
    doSomething(); // 조건이 충족되었을 때의 동작 수행
}
```

* wait 메서드를 사용할 때는 반드시 대기 반복문 관용구를 사용하고, 반복문 밖에서는 절대 호출하지 말아야 한다
    * 해당 반복문은 wait 호출 전후로 조건이 만족하는지 검사하는 역할을 한다
* 대기 전에 조건을 검사하여 조건이 이미 충족되었다면 wait 를 건너뛰게 한 것은 응답 불가 상태를 예방하는 조치이다
    * 조건이 이미 충족되었는데 스레드가 notify 혹은 notifyAll 메서드를 먼저 호출한 후 대기 상태로 빠지면, 해당 스레드를 다시 깨울 수 있다고 보장할 수 없다
* 대기 후에 조건을 검사하여 조건이 충족되지 않았따면 다시 대기하게 하는 것은 안전 실패를 막는 조치이다
    * 조건이 충족되지 않았는데 스레드가 동작을 이어가면 락이 보호하는 불변식을 깨뜨릴 위험이 있다
    
#### 조건이 만족되지 않아도 스레드가 깨어날 수 있는 상황

* 스레드가 notify 를 호출한 다음 대기 중이던 스레드가 깨어나는 사이에 다른 스레드가 락을 얻어 해당 락이 보호하는 상태를 변경
* 조건이 만족되지 않았음에도 다른 스레드가 실수로 혹은 악의적으로 notify 를 호출
    * 공개된 락으로 사용해 대기하는 클래스는 이런 위험에 노출된다
    * 외부에 노출된 객체의 동기화된 메서드 안에서 호출하는 wait 는 모두 이러한 문제에 영향을 받는다
* 깨우는 스레드는 관대하여, 대기 중인 스레드 중 일부만 조건이 충족되어도 notifyAll 을 호출하여 모든 스레드를 깨울 수 있다
* 대기 주인 스레드가 드물게 notify 없이 깨어나느 경우가 있으며, 이를 허위 각성이라는 현상이라 한다

#### notify vs notifyAll

* notify 는 스레드 하나, notifyAll 은 모든 스레드를 깨운다
* 일반적으로 언제나 notifyAll 을 사용하라는게 합리적이고 안전한 조언이다
    * 깨어나야 하는 모든 스레드가 깨어남을 보장하니 항상 정확한 결과를 얻을 것이다
    * 다른 스레드가 깨어나지만, 기다리던 조건이 충족되었는지 여부를 알아서 판단하기 때문에 이는 프로그램의 정확성에는 영향을 주지 않을 것이다
* 단, 모든 스레드가 같은 조건을 기다리고, 조건이 한 번 충족될 때마다 단 하나의 스레드만 혜택을 받을 수 있다면 notify 를 통해 최적화를 할 수 있다
* 그래도 이상의 전제조건들이 만족되더라도 notify 대신 notifyAll 을 사용해야 하는 이유는 다음과 같다
    * 외부로 공개된 객체에 대해 실수로 혹은 악의적으로 notify 를 호출하는 상황에 대비하기 위하여 wait 를 반복문 안에서 호출하듯, notify 대신 notifyAll 을 사용하면 관련 없는 스레드가 실수 또는 악의적으로 wait 를 호출하는 공격으로부터 보호할 수 있다
    * 이러한 스레드가 중요한 ㅜotify 를 삼킨다면 꼭 깨어났어야 할 스레드들이 영원히 대기하게 될 수 있다
    
## 정리

* wait 와 notify 를 직접 사용하는 것을 동시성 '어셈블리 언어' 로 프로그래밍하는 것에 비유할 수 있다
* java.util.concurrent 는 고수준 언어에 비유될 수 있다
* 코드를 새로 작성한다면 wait 와 notify 를 쓸 이유가 거의 없다
    * 이를 사용하는 레거시 코드가 있다면, wait 는 항상 표준 관용구에 따라 while 문 안에서 호출해야 한다
* 일반적으로 notify 보다 notifyAll 을 사용해야 한다
    * notify 를 사용하게 될 경우, 응답 불가 상태에 빠지지 않도록 갈별히 주의해야 한다
