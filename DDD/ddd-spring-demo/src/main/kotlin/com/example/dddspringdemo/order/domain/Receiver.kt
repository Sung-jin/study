package com.example.dddspringdemo.order.domain

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Receiver (
    @Column(name = "name")
    val name: String,

    @Column(name = "receiver_phone")
    val phone: String
) {
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        if (other !is Receiver) return false
        val that: Receiver = other

        return this.name == that.name && this.phone == that.phone
        // 위와 같이 엔티티 타입의 두 객체가 같은지 비교할 때 식별자가 아닌 값으로 비교할 경우
        // 모든 속성이 같은지 비교한다
    }
}