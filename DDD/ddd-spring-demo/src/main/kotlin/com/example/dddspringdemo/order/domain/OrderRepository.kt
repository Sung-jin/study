package com.example.dddspringdemo.order.domain

interface OrderRepository {
    fun findById(no: OrderNo): Order
    fun save(order: Order)
    fun findByOrdererId(ordererId: String, startRow: Int, size: Int): List<Order>
    // id 가 아닌 다른 형태로 조회 하는 등의 별도 쿼리를 작성할 수 있다
    fun delete(order: Order)
}