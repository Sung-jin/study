package com.example.dddspringdemo.order.domain

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class OrderNo (
    @Column(name = "order_number")
    val number: String
): Serializable