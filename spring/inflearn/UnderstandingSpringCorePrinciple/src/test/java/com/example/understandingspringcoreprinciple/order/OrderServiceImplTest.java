package com.example.understandingspringcoreprinciple.order;

import com.example.understandingspringcoreprinciple.discount.FixDiscountPolicy;
import com.example.understandingspringcoreprinciple.member.Grade;
import com.example.understandingspringcoreprinciple.member.Member;
import com.example.understandingspringcoreprinciple.member.MemoryMemberRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OrderServiceImplTest {

    @Test
    void createOrder() {
//        OrderServiceImpl orderService = new OrderServiceImpl();
        // 위와 같이 자바로 테스트를 작성할 때, 생성자 주입인 경우에 필요한 주입 객체가 명시적으로 보인다
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        OrderServiceImpl orderService = new OrderServiceImpl(
                memberRepository,
                new FixDiscountPolicy()
        );

        memberRepository.save(new Member(1L, "name", Grade.VIP));
        Order order = orderService.createOrder(1L, "itemA", 10000);

        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
