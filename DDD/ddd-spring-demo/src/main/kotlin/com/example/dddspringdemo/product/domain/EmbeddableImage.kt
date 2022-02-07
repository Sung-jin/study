package com.example.dddspringdemo.product.domain

import java.util.*
import javax.persistence.*

@Embeddable
class EmbeddableImage (
    @Column(name = "image_type")
    val imageType: String,

    @Column(name = "image_path")
    val path: String,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "upload_time")
    val uploadTime: Date
) {
    fun hasThumbnail(): Boolean {
        return imageType == "II"
    }

    fun getURL(): String {
        return if (imageType == "II") "/images/original/" else "" + this.path
    }

    fun getThumbnailURL(): String? {
        return if (imageType == "II") "/images/thumbnail/" + this.path else null
    }
}

/*
다형성을 포기하고 embeddable 로 만들어서 사용할 수 있다

이때, Image 컬렉션을 clear/addAll 을 할 경우 이미지를 각각 select/delete 를 하지 않고
한번의 쿼리로 수행한다
 */