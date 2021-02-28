package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * User: HolyEyE
 * Date: 2013. 12. 3. Time: 오후 9:48
 */
//public interface ItemRepository extends JpaRepository<Item, Long> {
public interface ItemRepository extends JpaRepository<Item, Long>, QueryDslPredicateExecutor<Item> {
    @Lock
//    QueryDSLPredicateExecutor<T> 를 상속받으면, QueryDSL 을 사용할 수 있다.
//    해당 인터페이스는 QueryDslPredicateExecutor 를 사용할 수 있다.
//    해당 인터페이스를 상속받으면 QueryDSL 을 쉽게 사용할 수 있지만, join, fetch 를 사용할 수 없다.
}
