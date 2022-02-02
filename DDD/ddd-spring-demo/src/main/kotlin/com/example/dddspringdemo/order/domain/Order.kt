package com.example.dddspringdemo.order.domain

import com.example.dddspringdemo.common.model.Money
import javax.persistence.*

@Entity
@Table(name = "purchase_order")
class Order (
    @EmbeddedId
    val number: OrderNo,
    // String 등의 타입이 id 라면 'id' 라는 이름만으로는 해당 필드가 주문 번호인지 여부를 알 수 없다
    // OrderNo 라는 이름으로 필드를 정의함으로써 어떠한 필드인지 식별할 수 있게 한다
    state: OrderState,
    shippingInfo: ShippingInfo,
    orderLines: List<OrderLine>,
    orderer: Orderer
) {
    init {
        verifyAtLeastOneOrMoreOrderLines(orderLines)
        // constructor validation 이 필요하면 해당 스콥에서 체크
        // 기존에는 정의한 생성자에서 setter 를 통해서 validation 을 일괄 체크하였으나,
        // kotlin 에서는 init block 을 통해 초기화 단계에서 validation 과
        // setter 에 별도 validation 을 추가하여 별도로 체크할 수 있다
    }

    @Enumerated(EnumType.STRING)
    var state = state
        private set(newState) { field = newState }

    @Embedded
    var shippingInfo = shippingInfo
        private set(newShippingInfo) {
            field = newShippingInfo
        }

    @ElementCollection // 벨류 컬렉션을 매핑하기 위한 어노테이션
    @CollectionTable(name = "order_line", joinColumns = [JoinColumn(name = "order_number")])
    // 벨류를 저장할 때 테이블을 지정할 때 사용한다
    // name: 테이블 이름, join column: 외부키로 사용하는 컬럼을 지정
    @OrderColumn(name = "line_idx")
    // OrderLine 에 인덱스 값을 저장하기 위한 프로퍼티가 존재하지 않지만, List 타입 자체가 인덱스를 가지고 있다
    // JPA 는 @OrderColumn 애노테이션을 이용해서 지정한 컬럼에 리스트 인덱스값을 저장한다
    var orderLines = orderLines
        private set(newOrderLines) {
            verifyAtLeastOneOrMoreOrderLines(newOrderLines)
            field = newOrderLines
            // Order 는 한개 이상의 OrderLine 을 가질 수 있으므로 Order 를 생성할 때 OrderLine 목록을 List 로 전달한다
            // 생성자에서 setOrderLines 메서드를 호출할 때 제약 조건을 검사하도록 할 수 있다
            // 요구사항에는 최소 한 종류 이상의 상품을 주문해야 하므로, setter 메서드에서 validation 함수를 호출하여 체크한다
        }

    var orderer = orderer

    val totalAmount: Money
        get() = Money(
            orderLines.sumOf { it.amounts.value }
        )

    fun changeShipped() {
        // 로직 검사
        this.state = OrderState.SHIPPED
    }

    fun changeShippingInfo(newShippingInfo: ShippingInfo) {
        this.verifyNotYetShipped()
        this.shippingInfo = newShippingInfo
    }

    fun cancel() {
        this.verifyNotYetShipped()
        this.state = OrderState.CANCELED
    }

    fun completedPayment() {
        // 결제 완료와 관련된 처리 코드를 함께 구현하기 때문에 결제 완료와 관련된 도메인 지식을 코드로 구현하는 것이 자연스럽다
        // setState 가 존재한다면, 해당 메서드에서 단순히 값만 변경할지 아니면 상태값에 따라 추가 처리를 할지를 함께 구현하기가 애매한다
    }

    @Deprecated("The method has been extended",
        ReplaceWith("Changed to use method verifyNotYetShipped")
    )
    private fun isShippingChangeable(): Boolean {
        return this.state == OrderState.PAYMENT_WAITING || this.state == OrderState.PREPARING
        // 위 메서드는 OrderState 를 변경할 수 있는지 여부를 검사하는 함수이다
        // PAYMENT_WAITING(주문 대기중), PREPARING(상품 준비중) 상태에서만 변경이 가능하므로, 해당 상태에서는 true 가 리턴된다
        // 즉, OrderState 는 주문 대기 중이거나 상품 준비 중에는 배송지를 변경할 수 있다는 도메인 규칙을 구현하고 있다

        // OrderState enum 보다 order 에서 변경 가능 여부 로직을 구현한 이유는,
        // OrderState 는 Order 에 속해 있으며, OrderState 상태 뿐 아니라 다른 어떠한 것과 함께 주문 상태 변경 가능 여부를 구해야 한다면
        // OrderState 에서만 할 수 없으므로 Order 에서 구현하였다

        // 하지만 OrderState 나 Order 나 결국 주문과 관련된 중요 업무 규칙을 주문 도메인 모델인
        // Order 나 OrderState 에서 구현한다는 점이다
        // 핵심 규칙을 구현한 코드는 도메인 모델에만 위치하기 때문에 규칙이 변경되거나 규칙을 확장해야 할 때는 다른 코드에 영향을 덜 주고 변경 내역을 모델에 반영할 수 있다
    }

    private fun verifyNotYetShipped() {
        if (this.state != OrderState.PAYMENT_WAITING && this.state != OrderState.PREPARING) throw IllegalStateException("Already shipped. state: $state")
        // 기존에 배송지 정보 변경에 대한 제약 조건만 있었을 때는 isShippingChangeable 를 통해 배송지 정보 변경에 대한 제약 조건만 판단하였으나
        // 배송지 정보 변경과 주문 취소가 둘다 '출고 전에 가능' 하다라는 제약조건이 존재하기에
        // verifyNotYetShipped 으로 이름을 변경하고 해당 메서드를 사용하여 검사한다
    }

    private fun verifyAtLeastOneOrMoreOrderLines(orderLines: List<OrderLine>) {
        if (orderLines.isNullOrEmpty()) throw IllegalArgumentException("no OrderLine")
    }
}
