package com.example.dddspringdemo.order.domain

data class ShippingInfo (
    val receiverName: String,
    val receiverPhoneNumber: String,
    val shippingAddress1: String,
    val shippingAddress2: String,
    val shippingZipcode: String
)

/*
요구사항 중 '주문할 때 배송지 정보를 반드시 지정해야 한다' 를 적용하기 위한 도메인
해당 도메인은 Order 를 생성할 때 OrderLines 목록뿐 아니라 ShippingInfo 도 존재해야 한다
*/