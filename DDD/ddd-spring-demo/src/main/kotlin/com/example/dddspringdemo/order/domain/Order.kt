package com.example.dddspringdemo.order.domain

data class Order (
    var state: OrderState,
    var shippingInfo: ShippingInfo,
    var orderLines: List<OrderLine>
) {
//    val totalAmounts: Int
//        get() {
//
//        }

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

    fun completedPayment() {}

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

    @JvmName("setOrderLines1")
    fun setOrderLines(orderLines: List<OrderLine>) {
        verifyAttLeastOneOrMoreOrderLines(orderLines)
        this.orderLines = orderLines
        // Order 는 한개 이상의 OrderLine 을 가질 수 있으므로 Order 를 생성할 때 OrderLine 목록을 List 로 전달한다
        // 생성자에서 setOrderLines 메서드를 호출할 때 제약 조건을 검사하도록 할 수 있다
        // 요구사항에는 최소 한 종류 이상의 상품을 주문해야 하므로, setter 메서드에서 validation 함수를 호출하여 체크한다
        // 예제에서는 totalAmount 에 대해서 setter 가 완료되었을 때 계산하였지만, 여기에서는 getter 를 활용한다
        // 또한, data class 의 getter, setter override 이슈 등으로 추후 구현을 하면서 적절하게 리펙토링이 필요하다 (현재시점에서는 해당 함수를 사용하지도, 용도에 맞지도 않게 구현되어 있다)
    }
    // shippingInfo 에 대한 setter 도 예제에는 있지만, 이도 위와 같은 이유로 우선 추후 용도에 맞게 리펙토링 예정
    // 해당 setter 는 shippingInfo 가 null 인 경우 exception 이 발생하게 구현하였지만,
    // shippingInfo 는 nullable 이 아니므로.. 여기서는 딱히..?

    private fun verifyNotYetShipped() {
        if (this.state != OrderState.PAYMENT_WAITING && this.state != OrderState.PREPARING) throw IllegalStateException("Already shipped. state: $state")
        // 기존에 배송지 정보 변경에 대한 제약 조건만 있었을 때는 isShippingChangeable 를 통해 배송지 정보 변경에 대한 제약 조건만 판단하였으나
        // 배송지 정보 변경과 주문 취소가 둘다 '출고 전에 가능' 하다라는 제약조건이 존재하기에
        // verifyNotYetShipped 으로 이름을 변경하고 해당 메서드를 사용하여 검사한다
    }

    private fun verifyAttLeastOneOrMoreOrderLines(orderLines: List<OrderLine>) {
        if (orderLines.isEmpty()) throw IllegalArgumentException("No OrderLine")
    }
}