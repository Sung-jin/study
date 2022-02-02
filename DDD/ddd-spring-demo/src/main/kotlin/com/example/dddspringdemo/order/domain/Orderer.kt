package com.example.dddspringdemo.order.domain

import com.example.dddspringdemo.member.domain.MemberId
import javax.persistence.*

@Embeddable
class Orderer (
    // MemberId 에 정의된 컬럼 이름을 변경하기 위함
    @Embedded
    @AttributeOverrides(AttributeOverride(name = "id", column = Column(name = "orderer_id"))) // Member 애그리거트를 ID 로 참조한다
    // MemberId 는 id 프로퍼티와 매핑되는 테이블 칼럼 이름으로 "member_id" 를 지정하고 있다
    private val memberId: MemberId,

    @Column(name = "orderer_name")
    private val name: String
)