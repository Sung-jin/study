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
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(
                memberRepository()
        );
        // 위와 같이 AppConfig 가 전체적으로 필요한 구현체를 넣어준다
        // 위와 같은 경우는 MemberServiceImpl 의 생성자를 통해서 주입하므로 '생성자 주입' 이라고 한다
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy()
        );
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
        // 해당 new MemoryMemberRepository(); 이라는 중복이 제거되었다
    }
    // 메서드 이름을 통해서 어떠한 역할을 하지는 잘 나타나있다
    // 즉, 전체적인 구성이 어떻게 되었는지 빠르게 파악할 수 있다

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
        // 위와 같이 변경만 하면, 사용하는 모든 곳에 알아서 해당 구현체가 주입되도록 변경된다
    }
}

/*
@Bean memberService -> new MemoryMemberRepository();
@Bean orderService -> new MemoryMemberRepository();

위와 같이 memberRepository 메서드를 2번 호출하면서, 객체 생성을 2번 진행한다
객체가 2개 생성되면서 싱글톤이 깨지는 것 처럼 보인다

하지만, ConfigurationSingletonTest 를 확인해보면 인스턴스는 모두 같은 인스턴스가 공유되어 사용된다
call xxx 가 MemberRepository 를 3 번 호출할 것 같으나, 1번만 호출이 된다
하지만 @Configuration 어노테이션을 제거하면, 빈은 등록되지만 같은 빈을 여러번 호출하는 것에 대해서 싱글톤을 보장하지 못한다 (이때는 MemberRepository 를 3번 호출한다)
 */