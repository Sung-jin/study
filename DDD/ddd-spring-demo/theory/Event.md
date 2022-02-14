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
