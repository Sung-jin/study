package com.example.dddspringdemo.board.domain

import javax.persistence.*

@Entity
@Table(name = "article")
@SecondaryTable(name = "article_content", pkJoinColumns = [PrimaryKeyJoinColumn(name = "id")])
// name: 저장할 테이블을 지정
// pkJoinColumns: 벨류 테이블에서 엔티티 테이블로 조인할 때 사용할 컬럼을 지정
class Article (
    @Id
    val id: Long,

    val title: String,

    @AttributeOverrides(
        AttributeOverride(name = "content", column = Column(table = "article_content")),
        AttributeOverride(name = "contentType", column = Column(table = "article_content"))
        // 해당 벨류 데이터가 저장된 테이블 이름을 지정
    ) @Embedded
    val content: ArticleContent
)