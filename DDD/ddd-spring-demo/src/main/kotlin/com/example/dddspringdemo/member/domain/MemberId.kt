package com.example.dddspringdemo.member.domain

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class MemberId (
    @Column(name = "member_id")
    private val id: String
): Serializable