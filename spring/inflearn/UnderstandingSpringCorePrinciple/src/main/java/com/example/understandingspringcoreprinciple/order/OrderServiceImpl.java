package com.example.understandingspringcoreprinciple.order;

import com.example.understandingspringcoreprinciple.discount.DiscountPolicy;
import com.example.understandingspringcoreprinciple.member.Member;
import com.example.understandingspringcoreprinciple.member.MemberRepository;

public class OrderServiceImpl implements OrderService {
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    // 할인 정책을 변경하려면 코드를 직접 변경해야 한다
    // 1. 역할과 구현을 충실하게 분리
    // 2. 다형성을 활용하고 인터페이스와 구현 객체를 분리
    // 3. DiscountPolicy 등과 같은 추상뿐 아니라 RateDiscountPolicy 와 같은 구현 클래스를 직접 의존하고 있다 -> DIP, OCP 위반

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
    // 위와 같이 선언만 하고 실제 구현 객체를 대신 생성하고 주입해주는 무언가가 존재하면 가능하다
    // 이는 인터페이스만 사용하여 DIP 를 준수한다

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
