package com.example.understandingspringcoreprinciple;

import com.example.understandingspringcoreprinciple.discount.DiscountPolicy;
import com.example.understandingspringcoreprinciple.discount.RateDiscountPolicy;
import com.example.understandingspringcoreprinciple.member.MemberRepository;
import com.example.understandingspringcoreprinciple.member.MemberService;
import com.example.understandingspringcoreprinciple.member.MemberServiceImpl;
import com.example.understandingspringcoreprinciple.member.MemoryMemberRepository;
import com.example.understandingspringcoreprinciple.order.OrderService;
import com.example.understandingspringcoreprinciple.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    // 의존 관계에 대한 고민은 외부(AppConfig 와 같은)에 맡기고 실행에만 집중할 수 있다
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(
                getMemberRepository()
        );
        // 위와 같이 AppConfig 가 전체적으로 필요한 구현체를 넣어준다
        // 위와 같은 경우는 MemberServiceImpl 의 생성자를 통해서 주입하므로 '생성자 주입' 이라고 한다
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(
                getMemberRepository(),
                getDiscountPolicy()
        );
    }

    @Bean
    public MemberRepository getMemberRepository() {
        return new MemoryMemberRepository();
        // 해당 new MemoryMemberRepository(); 이라는 중복이 제거되었다
    }
    // 메서드 이름을 통해서 어떠한 역할을 하지는 잘 나타나있다
    // 즉, 전체적인 구성이 어떻게 되었는지 빠르게 파악할 수 있다

    @Bean
    public DiscountPolicy getDiscountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
        // 위와 같이 변경만 하면, 사용하는 모든 곳에 알아서 해당 구현체가 주입되도록 변경된다
    }
}
