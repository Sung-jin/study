package com.example.dddspringdemo.product.domain

import java.util.*
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("EI")
class ExternalImage(
    id: Long? = null,
    path: String,
    uploadTime: Date
) : Image(id, path, uploadTime) {
    override fun getURL(): String {
        return this.path
    }

    override fun hasThumbnail(): Boolean {
        return false
    }

    override fun getThumbnailURL(): String? {
        return null
    }
}