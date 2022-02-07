package com.example.dddspringdemo.product.domain

import java.util.*
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("II")
class InternalImage(
    id: Long? = null,
    path: String,
    uploadTime: Date
) : Image(id, path, uploadTime) {
    override fun getURL(): String {
        return "/images/original/" + this.path
    }

    override fun hasThumbnail(): Boolean {
        return true
    }

    override fun getThumbnailURL(): String {
        return "/images/thumbnail/" + this.path
    }
}