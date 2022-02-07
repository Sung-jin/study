package com.example.dddspringdemo.board.domain

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class ArticleContent  (
    @Column(name = "content")
    val content: String,

    @Column(name = "content_type")
    val contentType: String
)