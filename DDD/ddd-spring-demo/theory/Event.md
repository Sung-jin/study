### 시스템 간 강결합의 문제

* 보통 결제 시스템은 외부에 존재하므로, 환불을 진행 할 때 외부의 환불 시스템 서비스를 호출하는데 다음과 같은 문제가 발생할 수 있다
    * 외부 서비스가 정상이 아닐 경우 트랜잭션 처리
        * 환불 기능을 실행하는 과정에서 예외가 발생하면, 환불을 실행하는 도중이므로 주문 취소 트랜잭션을 롤백하는게 맞아보인다
        * 하지만 주문은 취소 상태로 변경하고 환불만 나중에 다시 시도하는 방식으로도 처리할 수도 있다
    * 성능에 대한 문제
        * 환불 처리하는 외부 시스템의 응답 시간이 길어지면 그만큼 대기 시간이 발생한다
        * 이는 외부 서비스 성능에 직접적인 영향을 받는 문제가 발생한다
    * 이외에도 도메인 객체에 서비스를 전달하면 설계상 문제가 나타날 수도 있다
    
```java
@Transactional
public void cancel(OrderNo orderNo) {
    ...
    try {
        refundService.refund(order.getPaymentId()); // 외부 서비스 성능에 직접 영향을 받는다
        ...
    } catch () {}
}

public class Order {
    // Order 는 주문을 표현하는 도메인 개체이다
    public void cancel(RefundService refundService) {
        // 하지만 주문 도메인 객쳉에 결제 도메인의 ㅘㄴ루로직이 섞일 수 있다
        // 이는 환불 관련 기능이 변경되면 Order 에도 영향을 받게 된다는 것을 의미한다
        // 또한 파라미터를 추가하여 다른 서비스를 받아서 다른 동작을 해야 하는 요구사항이 발생하면
        // 로직이 더 섞이고 복잡해지고, 트랜잭션 처리가 복잡해지는 문제가 발생한다
        // 또한 주입받는 서비스 수 만큼 외부 서비스가 증가하게 된다
        ...
        
        try {
            refundService.refund(getPaymentId());
            // 
        } catch() {}
    }
}
```

* 위와 같은 이슈는 주문 Bounded Context 와 결제 Bounded Context 간의 강결합 때문에 발생하는 현상이다
* 이러한 강경합을 없앨 수 있는 방법 중 이벤트를 사용하는 것이다

### 이벤트 개요

* 이벤트가 발생한다는 것은 상태가 변경됐다는 것을 의미한다
* 이벤트는 발생하는 것에서 끝나지 않고, 해당 이벤트에 반응하여 원하는 동작을 수행하는 기능을 구현해야 한다
* 도메인 모델에서 도메인의 상태 변경을 이벤트로 표현할 수 있다

#### 이벤트 관련 구성요소

* 도메인 모델에서 이벤트 주체는 엔티티, 벨류, 도메인 서비스와 같은 도메인 객체이다
    * 이러한 도메인 객체는 도메인 로직을 실행해서 상태가 바뀌면 관련 이벤트를 발생한다
* 이벤트 핸들러는 이벤트 생성 주체가 발생한 이벤트에 반응한다
    * 이벤트 핸들러는 생성 주체가 발생한 이벤트를 전달받아 이벤트에 담긴 데이터를 이용해서 원하는 기능을 실행한다
* 이벤트 생성 주체와 이벤트 핸들러를 연결해 주는 것이 **이벤트 디스패처**이다
    * 이벤트 생성 주체는 이벤트를 생성해 디스패처에 이벤트를 전달한다
    * 이벤트를 전달받은 디스패처는 해당 이벤트를 처리할 수 있는 핸들러에 이벤트를 전파한다
    * 이벤트 디스패처의 구현 방식에 따라 이벤트 생성과 처리를 동기나 비동기로 실행한다

#### 이벤트의 구성

* 이벤트는 발생한 이벤트에 대한 정보를 담는다
    * 이벤트 종류: 클래스 이름으로 이벤트 종류를 표현
    * 이벤트 발생 시간
    * 추가 데이터: 주문번호, 신규 배송지 정보 등 이벤트와 관련된 정보
    
```java
public class ShippingInfoChangedEvent {
    // changed 라는 과거 시제를 사용한다
    private String orderNumber;
    private long timestamp;
    private ShippingInfo newShippingInfo;
}

public class Order {
    public void changeShippingInfo(ShippingInfo newShippingInfo) {
        setShippingInfo(newShippingInfo);
        Events.raise(new ShippingInfoChangedEvent(number, newShippingInfo));
        // 위와 같이 이벤트를 발생하는 주체인 Order 에서 배송지 정보 변경에 대한 이벤트를
        // 이벤트 디스패처를 통해 이벤트를 전파하였다
        // ShippingInfoChangedEvent 를 처리하는 핸들러는 디스패처로부터 이벤트를 전달받아 필요한 작업을 수행할 것이다
    }
}

public class shippingInfoChangedHandler implements EventHandler<ShippingInfoChangedEvent> {
    @Override
    public void handle(ShippingInfoChangedEvent evt) {
        // 해당 로직은 이벤트가 발생될 때 동작한다
        // 이벤트 핸들러가 작업을 수행하는데 필요한 최소한의 데이터를 가지고 있어야 한다
        // 필요한 데이터가 부족할 경우 관련 API 나 DB 에서 데이터를 불러와야 한다
    }
}
```

#### 이벤트 용도

* 이벤트는 크게 두가지 용도로 쓰인다
    1. 도메인의 상태가 변할 때 다른 후처리를 실행하기 위한 **트리거**로 사용할 수 있다
        * 주문 취소가 되었을 때 환불 처리 및 알림
        * 예매 결과에 대한 알림
    1. 서로 다른 시스템 간의 데이터 동기화에 사용될 수 있다
        * 배송지 변경 시 외부 배송 서비스에 바뀐 배송지 정보를 전달하여 동기화 진행
    
#### 이벤트의 장점

```java
public class Order {
    public void cancel() {
        ...
        this.refundStatus = State.REFUND_STARTED;
        Events.raise(new ORderCanceledEvent(number.getNuber()));
        // 위와 같이 외부 서비스를 받을 필요 없이 이벤트를 발생시켜 처리할 수 있다
        // 즉, 서로 다른 도메인 로직이 섞이는 것을 방지할 수 있다
        
        // 외부 서비스를 받을 필요가 없기 떄문에 다른 도메인으로의 의존이 제거된다
        // 또한 이벤트 핸들러를 사용한다면 기능 확장에도 용이하다
    }
}
```

### 이벤트, 핸들러, 디스패처 구현

#### 이벤트 클래스

* 이벤트 자체를 위한 상위 타입은 존재하지 않는다
    * 하지만, 모든 이벤트가 공통으로 가지는 프로퍼티가 존재한다면 상위 클래스를 만들수도 있다
* 이벤트는 과거에 벌어진 상태 변화나 사건을 의미하므로 이벤트 클래스의 이름을 결정할 때에는 과거 시제를 사용해야한다
* 이벤트 클래스는 이벤트를 처리하기 위해 필요한 최소한의 데이터를 포함해야 한다

```java
public abstract class Event { // 이벤트가 공통으로 가져야 할 상위 클래스가 있다면 선언하여 모든 이벤트에서 상속받아 사용할 수 있다 }

public class OrderCanceledEvent extends Event {
    private String orderNumber;
    public OrderCanceledEvent(String number) {
        ...
    }
}
```

#### EventHandler 인터페이스

* EventHandler 인터페이스는 이벤트 핸들러를 위한 상위 인터페이스이다

```java
public interface EventHandler<T> {
    void handle(T event);
    // EventHandler 인터페이스를 상속받는 클래스는 handle() 메서드를 이용해서 필요한 기능을 구현하면 된다
    default boolean canHandle(Object event) {
        Class<?>[] typeArgs = TypeResolver.resolveRawArgumnets(
                EventHandler.class, this.getClass()
        );
        return typeArgs[0].isAssignableFrom(event.getClass());
    }
    // canHandle 메서드는 핸들러가 이벤트를 처리할 수 있는지 여부를 검사한다
}

EventHandler<?> handler = new EventHandler<PasswordChangedEvent>() {
    @Override
    public void handle(PasswordChagneEvent event) {}
};

boolean result = handler.canHandle(new PasswordChangedEvent(someId, newPw));
// handler 의 파라미터화 타입은 PasswordChangedEvent 이므로, canHandle() 메서드에
// PasswordChangedEvent 객체를 파라미터로 전달하면 true 를 리턴한다
// 파라미터화 타입 대신 다른 타입을 이용해서 처리 가능 여부를 검사하고 싶다면, 핸들러 구현 클래스에서 canHandle() 메서드를 재정의한다
```

#### 이벤트 디스패처인 Events 구현

* 도메인을 사용하는 응용 서비스는 이벤트를 받아 처리할 핸들러를 Events.handle() 로 등록하고, 도메인 기능을 실행한다

```java
import java.beans.EventHandler;

public class Events {
    private static ThreadLocal<List<EventHandler<?>>> handlers = new ThreadLocal<>();
    // EventHandler 목록을 보관하는 변수
    private static threadLocal<Boolean> publishing = new ThreadLocal<Boolean>() {
        @Overriderotected
        Boolean initialValue() {
            return Boolean.FALSE;
        }
    };
    // 이벤트를 처리 중인지 여부를 보관하는 변수

    public static void raise(Object event) {
        if (publishing.get()) return;
        // 이벤트를 이미 출판 중이면 출판하지 않는다
        // 이벤트 출판의 무한 재귀를 방지한다

        try {
            publishing.set(Boolean.TRUE);
            // 이벤트를 출판 상태로 변경한다

            List<EventHandler<?>> eventHandlers = handlers.get();
            if (eventHandlers == null) return;
            for (EventHandler handler : eventHandlers) {
                if (handler.canHandle(event)) {
                    // handlers 에 담긴 EventHandler 가 파라미터로 전달받은 이벤트를 처리할 수 있는지 확인한다
                    handler.handle(event);
                    // 처리가 가능하다면 이벤트를 처리한다
                }
            }
        } finally {
            publishing.set(Boolean.FALSE);
            // 이벤트 처리가 끝나면 출판 상태를 false 로 변경한다
        }
    }

    public static void handle(EventHandler<?> handler) {
        // 이벤트 핸들러를 등록하는 메서드
        if (publishing.get()) return;
        // 이벤트를 처리 중이면 등록하지 않는다

        List<EventHandler<?>> eventHandlers = handlers.get();
        if (eventHandlers == null) {
            // 핸들 리스트가 null 이면 새로운 List 객체를 생성하고 handler 의 값으로 설정한다
            eventHandlers = new ArratyList<>();
            handlers.set(eventHandlers);
        }
        eventHandlers.add(handler);
        // List 에 핸들러를 등록한다
    }
    
    public static void reset() {
        if (!publishing.get()) {
            handlers.remove();
            // handlers 에 보관된 List 객체를 삭제한다
        }
    }
}

public class CancelOrderService {
    ...

    @Transactional
    public void cancel(OrderNo orderNo) {
        Events.handle((OrderCanceledVent evt) -> refundService.refund(evt.getOrderNumber()));
        // OrderCanceledEvent 가 발생하면 Events.handle() 메서드에 전달한 EventHandler 를 이용해서 이벤트를 처리하게 된다
        // 해당 메서드는 인자로 전달받은 EventHandler 를 List 에 보관한다
        // 이벤트가 발생하면 이벤트를 처리할 EventHandler 를 List 에서 찾아 EventHandler 의 handle() 메서드를 호출해서 이벤트를 처리한다
        ...
        Events.reset();
        // 스레드 handler 가 담고 있는 List 에 계속 핸들러 객체가 쌓이게 되면 메모리 부족 예외가 발생한다
    }
}

public class Order {
    public void cancel() {
        ...
        Events.raise(new OrderCanceledEvent(number.getNumber()));
        // 위와 같이 이벤트를 발생시킬 때 Events.raise() 메서드를 사용한다
        // 위 메서드를 실행하면 해당 이벤트를 처리할 핸들러를 찾아 handle() 메서드를 실행한다
    }
}
```

#### AOP 를 이용한 Events.reset() 실행

* 응용 서비스가 끝나면 ThreadLocal 에 등록된 핸들러 목록을 초기화하기 위해 Events.reset() 메서드를 실행한다
    * 모든 이벤트가 포함된 메서드에 `Events.reset()` 을 실행하는 코드는 중복에 해당한다
    * AOP 를 이용하면 이러한 중복을 제거할 수 있다
    
```java
@Aspect
@Order(0)
// 우선순위를 0 으로 지정한다
// 트랜잭션 관련 AOP 보다 우선 순위를 높여 이 AOP 가 먼저 적용되도록 설정한다
@Component
public class EventsResetProcessor {
    private ThreadLocal<Integer> nestedCount = new threadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return new Integer(0);
        }
    };
    // 서비스 메서드의 중첩 실행 개수를 저장하기 위한 ThreadLocal 변수를 생성
    
    @Around("@target(org.springframework.stereotype.Service) and within(some.path..*)")
    // @Around("execution(public * some.path..*Service.*(..))") 와 같이 execution() 명시자를 사용해도 된다
    public Object doReset(ProceedingJoinPoint joinPoint) throws Throwable {
        nestedCount.set(nestedCount.get() + 1);
        // 중첩 실행 횟수를 1 증가 한다
        try {
            return joinPoint.proceed();
            // 대상 메서드를 실행한다
        } finally {
            nestedCount.set(nestedCount.get() - 1);
            // 중첩 실행 횟수를 1 감소 한다
            if (nestedCount.get() == 0) {
                Events.reset();
                // 중첩 횟수가 0 이면 Events.reset() 을 실행한다
            }
        }
    }
}

@Service
public class CancelOrderService {
    @Transactional
    public void cancel(OrderNo orderNo) {
        Events.handle((OrderCanceledEvent evt) -> refundService.refund(evt.getOrderNumber()));
        ...
        
        // AOP 가 적용되었기 때문에 @Service 가 붙은 클래스는 Events.reset() 을 명시적으로 호출하지 않아도 된다
    }
}
```

### 동기 이벤트 처리 문제

```java
@Transactional
// 외부 연동 과정 중 예외가 발생할 경우 트랜잭션 처리가 필요하다
public void someFunc(OrderNo orderNo) {
    Events.handle(...);
    // 이벤트 처리가 외부 서비스와 연동 될 경우, 해당 처리가 문제가 발생하거나 느려질 경우
    // 해당 함수 전체가 완료될 때 까지 대기한다
}
```

* 성능저하와 트랜잭션처리에 대한 고려를 해야 한다
    * 외부 서비스 실행 실패에 따라 무조건 롤백을 할 필요는 없다.
    * 취소에 대한 처리하고, 후 처리는 수동으로 처리가 가능하다
* 외부 시스템과의 연동을 비동기로 처리하는 형태로 성능과 트랜잭션 범위 문제를 해소할 수 있다

#### 비동기 이벤트 처리

* 회원가입 완료 검증 이메일과 같은 기능은 요청과 동시에 바로 도착 할 필요 없다
    * 일부 시간이 지난 이후에 도착하여도 유효하고, 받지 못했을 경우에도 다시 발송요청할 수 있다
* **'어떠한 기능' 을 하면 '무엇을 해라'** 는 결국 **'어떠한 기능' 을 하면 '언제까지 무엇을 해라'** 인 경우가 많다
    * 이러한 요구사항은 실패시에도 재시도를 하거나 수동 처리해도 상관 없는 경우도 많다
    * 이러한 요구사항은 비동기 이벤트로 처리할 수 있다
* 비동기 방식으로 이벤트 처리를 구현하는 방법  
    1. 로컬 핸들러를 비동기로 실행
    1. 메시지 큐를 사용
    1. 이벤트 저장소와 이벤트 포워더 사용
    1. 이벤트 저장소와 이벤트 제공 API 사용
    
##### 로컬 핸들러의 비동기 실행

* 이벤트 핸들러를 별도 스레드로 실행할 경우 비동기로 처리할 수 있다

```java
public class Events {
    private static ThreadLocal<List<EventHandler<?>>> asyncHandlers = new ThreadLocal<>();
    // 비동기로 실행할 이벤트 핸들러 목록을 보관
    private static ExecutorService executorService;
    // 비동기로 이벤트 핸들러를 실행할 서비스

    public static void init(ExecutorService executorService) {
        Events.executorService = executorService;
    }
    
    public static void close() {
        // excutor 를 셧다운
        if (executorService != null) {
            executor.shutdown();
            try {
                executor.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {}
        }
    }
    
    public static void raise(Object event) {
        ...
        
        List<EventHandler<?>> asyncEventHandlers = asyncHandlers.get();
        // 이벤트를 처리할 수 있는 핸들러를 비동기로 실행한다
        if (asyncEventHandlers != null) {
            for (EventHandler handler: asyncEventHandler) {
                if (handler.canHandle(event)) {
                    executor.submit(() -> handler.handle(event));
                    // 기존 이벤트 핸들러와 비슷하지만, 이벤트 서비스의 submit 에 람다식을 전달한다
                    // 이는 executorService 의 내부적으로 사용하는 스레드 풀을 이용해서 인자로 전달받은 람다식을 실행한다
                    // 결과적으로 raise 메서드를 실행하는 스레드가 아닌 다른 스레드를 이용해서 이벤트 핸들러를 비동기로 실행한다
                }
            }
        }
        ...
    }
    
    public static void handleAsync(EventHandler<?> handler) {
        // 비동기로 처리할 이벤트 핸들러를 등록
        if (publishing.get()) return;
        
        List<EventHandler<?>> eventHandlers = asyncHandlers.get();
        if (eventHanlders == null) {
            eventHandlers = new ArrayList<>();
            asyncHandlers.set(eventHandlers);
        }
        eventHandlers.add(handler);
    }
    
    public static void reset() {
        if (!publishing.get()) {
            asyncHandlers.remove();
            // 비동기 핸들러에 보관된 값 제거
        }
    }
}

@Transactional
public void someFunc(OrderNo orderNo) {
    Events.handleAsync(...);
    ...
    Events.raise(new SomeEvent());
    // 비동기로 실행 된 해당 별도의 스레드로 실행되므로
    // raise 된 이벤트는 해당 트랜잭션 범위에 묶이지 않는다
}
```

* 별도 스레드를 이용해서 이벤트 핸들러를 실행하면, 해당 이벤트의 트랜잭션과 실행한 메서드의 트랜잭션은 다른 트랜잭션이다
    * 즉, 같은 트랜잭션으로 동작해야 한다면 비동기로 실행하면 안된다

##### 메시징 시스템을 이용한 비동기 구현

* 비동기로 이벤트를 처리할 때 RabbitMQ, Kafka 와 같은 메시징 큐를 사용할 수 있다
* 이벤트가 발생하면 이벤트 디스패치는 이벤트 큐에 이벤트를 전송한다
    * 메시지 큐는 이벤트를 메시지 리스너에 전달하고, 메시지 리스너는 알맞은 이벤트 핸들러를 이용해서 이벤트를 처리한다
* 필요하다면 이벤트를 발생하는 도메인 기능과 메시지 큐에 이번테를 저장하는 절차를 한 트랜잭션으로 묶어야 한다
* 메시징 시스템을 이용할 경우 보통 이벤트를 발생하는 주체와 이벤트 핸들러가 별도 프로세스에서 동작한다

##### 이벤트 저장소를 이용한 비동기 처리

* 이벤트를 DB 와 같은 별도의 스토리지에 저장하고, 별도 프로그램을 이용해서 이벤트 핸들러에 전달할 수 있다
* 포워더를 이용한 방식
    1. 이벤트가 발생하면 핸들러는 스토리지에 이벤트를 저장한다
    1. 포워더는 주기적으로 이벤트 저장소에서 이벤트를 가져와 이벤트 핸들러를 실행한다
    1. 포워더는 별도 스레드를 이용하기 때문에 이벤트 발행과 처리가 비동기로 처리된다
    * 해당 방식은 이벤트가 물리적인 공간에 저장되므로, 이벤트 처리 실패시 포워더는 다시 이벤트를 가져와서 이벤트 핸들러를 실행시킬 수 있다
* API 방식
    * 모든 방식은 같으나, 포워더를 이용해서 이벤트를 외부에 전달하는 방식을 API 방식에서는 외부 핸들러가 API 를 통해 이벤트 목록을 가져온다
    
### 이벤트 적용 시 추가 고려사항

* 이벤트 적용 시 소스에 대한 정보를 추가
  * 특정 이벤트나 특정 소스만 처리하는 등의 처리를 할 수 있다
* 포워더에서 전송 실패 retry 횟수 제한 설정 여부
  * 대부분 특정 이벤트가 계속 반복하여 실패한다면, 그 이벤트는 시스템적으로 처리가 불가능 할 것이다
  * 이러한 반복된 실패를 몇번까지 시도하는 지 등의 처리가 필요할 수 있다
* 비동기 로컬 핸들러를 이용할 경우와 같은 상황에서, 이벤트 처리가 실패하면 해당 이벤트는 유실된다
* 외부 이벤트, 내부 이벤트, 이벤트 우선 순위 등 처리 순서를 고려해야 할 수 있다
* 동일한 이벤트를 재 처리 여부
