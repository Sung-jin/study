package jpabook.jpashop.repository.custom;

import com.mysema.query.jpa.JPQLQuery;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.domain.QMember;
import jpabook.jpashop.domain.QOrder;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author holyeye
 */
public class OrderRepositoryImpl extends QueryDslRepositorySupport implements CustomOrderRepository {
    /*
    QueryDSL 의 다양한 기능을 사용하기 위해서는 JPAQuery 를 직접 사용하거나
    QueryDslRepositorySupport 를 사용해야 한다.

    스프링 데이터 JPA 가 제공하는 공통 인터페이스로는 QueryDslRepositorySupport 를 직접 구현할 수 없다.
    그래서 사용자 정의 repository 에 해당 인터페이스를 구현한다.
     */

    public OrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> search(OrderSearch orderSearch) {

        QOrder order = QOrder.order;
        QMember member = QMember.member;

        JPQLQuery query = from(order);

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query.leftJoin(order.member, member)
                    .where(member.name.contains(orderSearch.getMemberName()));
        }

        if (orderSearch.getOrderStatus() != null) {
            query.where(order.status.eq(orderSearch.getOrderStatus()));
        }

        return query.list(order);
    }
}
