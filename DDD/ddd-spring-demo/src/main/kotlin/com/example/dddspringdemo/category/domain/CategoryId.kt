package com.example.dddspringdemo.category.domain

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class CategoryId : Serializable {
    @Column(name = "category_id")
    var value: Long? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as CategoryId
        return if (value != null) value == that.value else that.value == null
    }

    override fun hashCode(): Int {
        return if (value != null) value.hashCode() else 0
    }
}