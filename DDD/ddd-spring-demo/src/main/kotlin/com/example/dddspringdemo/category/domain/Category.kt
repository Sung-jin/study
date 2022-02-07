package com.example.dddspringdemo.category.domain

import javax.persistence.EmbeddedId
import javax.persistence.Entity

@Entity
class Category (
    @EmbeddedId
    var id: CategoryId,

    val name: String
)