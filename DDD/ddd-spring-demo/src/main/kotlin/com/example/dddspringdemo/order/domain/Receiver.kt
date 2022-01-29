package com.example.dddspringdemo.order.domain

data class Receiver (
    val name: String,
    val phoneNumber: String
) {
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        if (other !is Receiver) return false
        val that: Receiver = other

        return this.name == that.name && this.phoneNumber == that.phoneNumber
        // 위와 같이 엔티티 타입의 두 객체가 같은지 비교할 때 식별자가 아닌 값으로 비교할 경우
        // 모든 속성이 같은지 비교한다
    }
}