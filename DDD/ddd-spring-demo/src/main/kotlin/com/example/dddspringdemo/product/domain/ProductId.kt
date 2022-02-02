package com.example.dddspringdemo.product.domain

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class ProductId (
    @Column(name = "product_id")
    private val id: String
): Serializable