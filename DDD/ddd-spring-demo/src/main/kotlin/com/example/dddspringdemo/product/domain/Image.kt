package com.example.dddspringdemo.product.domain

import java.util.*
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "image_type")
@Table(name = "image")
abstract class Image (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    var id: Long? = null,

    @Column(name = "image_path")
    val path: String,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "upload_time")
    val uploadTime: Date,
) {
    abstract fun getURL(): String
    abstract fun hasThumbnail(): Boolean
    abstract fun getThumbnailURL(): String?
}