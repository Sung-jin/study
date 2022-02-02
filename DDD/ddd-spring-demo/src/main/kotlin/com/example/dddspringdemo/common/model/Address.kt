package com.example.dddspringdemo.common.model

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Address (
    @Column(name = "zip_code")
    val zipCode: String,

    @Column(name = "address1")
    val address1: String,

    @Column(name = "address2")
    val address2: String
)