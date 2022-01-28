package com.example.dddspringdemo.order.domain

data class OrderLine (
    val product: Product,
    val price: Int,
    val quantity: Int,
    val amounts: Int
) {
    private fun calculateAmounts(): Int {
        return this.price * this.quantity
    }

    fun getAmounts() {}
}

/*
OrderLine 의 경우 한 상품을 얼마에 몇개를 살지를 필드에 담고 있으며,
calculateAmounts 메서드를 통해 구매 가격을 구하는 로직이 존재한다

Order 와 OrderLine 과의 관계는
- 최소 한 종류 이상의 상품을 주문해야 한다
- 총 주문 금액은 각 상품의 구매 가격 합을 모두 더한 금액이다
- Order 는 최소 한개 이상의 주문이 가능하므로, OrderLine 을 배열로 가져야 한다
- Order 의 모든 주문의 총 주문을 구할 수 있으며, 이는 Order 에서 구현해야 한다
*/