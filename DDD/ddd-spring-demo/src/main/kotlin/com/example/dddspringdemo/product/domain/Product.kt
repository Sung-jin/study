package com.example.dddspringdemo.product.domain

import com.example.dddspringdemo.category.domain.CategoryId
import com.example.dddspringdemo.common.jpa.MoneyConverter
import com.example.dddspringdemo.common.model.Money
import javax.persistence.*

@Entity
@Table(name = "product")
class Product (
    @EmbeddedId
    var id: ProductId? = null,

    @ElementCollection(fetch = FetchType.LAZY)
    // default 가 lazy 임
    // 일반적으로 애플리케이션은 상태를 변경하는 빈도보다 조회하는 기능을 실행하는 빈도가 훨씬 높다
    // 즉, 상태 변경을 위해 지연 로딩을 사용할 때 밣생하는 추가 쿼리로 인한 실행 속도 저하는 문제가 되지 않는다
    @CollectionTable(name = "product_category", joinColumns = [JoinColumn(name = "product_id")])
    val categoryIds: Set<CategoryId> = mutableSetOf(),

    @Convert(converter = MoneyConverter::class)
    val price: Money,

    val detail: String,

    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.REMOVE], orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @OrderColumn(name = "list_idx")
    private val images: MutableList<Image> = mutableListOf()

//    @ElementCollection
//    private val images: MutableList<EmbeddableImage> = mutableListOf()
) {
    fun changeImages(newImages: List<Image>) {
        images.clear()
        images.addAll(newImages)
    }
}
