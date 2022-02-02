package com.example.dddspringdemo.order.infra.repository

import com.example.dddspringdemo.order.domain.Order
import com.example.dddspringdemo.order.domain.OrderNo
import com.example.dddspringdemo.order.domain.OrderRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class JpaOrderRepository: OrderRepository {
    @PersistenceContext
    lateinit var entityManager: EntityManager

    override fun findById(id: OrderNo): Order {
        return entityManager.find(Order::class.java, id)
    }

    override fun findByOrdererId(ordererId: String, startRow: Int, size: Int): List<Order> {
        val query = entityManager.createQuery(
            "select o from Order o " +
                    "where o.orderer.memberId.id = :ordererId " +
                    "order by o.number.number desc",
            Order::class.java
        )
        query.setParameter("ordererId", ordererId)
        query.firstResult = startRow
        query.maxResults = size

        return query.resultList
    }

    override fun save(order: Order) {
        entityManager.persist(order)
    }

    override fun delete(order: Order) {
        entityManager.remove(order)
    }
}