package jpabook.jpashop.repository.custom;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import java.util.List;

/**
 * @author holyeye
 */
public interface CustomOrderRepository {
    public List<Order> search(OrderSearch orderSearch);

}
