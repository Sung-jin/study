package com.example.dddspringdemo.order.domain

import com.example.dddspringdemo.common.model.Address
import javax.persistence.*

@Embeddable
class ShippingInfo (
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "zipCode", column = Column(name = "shipping_zipcode")),
        AttributeOverride(name = "address1", column = Column(name = "shipping_addr1")),
        AttributeOverride(name = "address2", column = Column(name = "shipping_addr2")),
    )
    val address: Address,
    // Receiver 라는 '받는 사람' 이라는 도메인 개념을 표현하고,
    // 해당 도메인에는 '받는 사람' 의 정보인 이름과 번호가 들어가 있다
    // 위와 같이 벨류 타입을 사용함으로써 개념적으로 완전한 하나를 잘 표현할 수 있다

    @Embedded
    val receiver: Receiver,

    @Column(name = "shipping_message")
    val message: String
)

/*
요구사항 중 '주문할 때 배송지 정보를 반드시 지정해야 한다' 를 적용하기 위한 도메인
해당 도메인은 Order 를 생성할 때 OrderLines 목록뿐 아니라 ShippingInfo 도 존재해야 한다
*/

// @AttributeOverrides(AttributeOverride(name = "id", column = Column(name = "orderer_id"))) // Member 애그리거트를 ID 로 참조한다