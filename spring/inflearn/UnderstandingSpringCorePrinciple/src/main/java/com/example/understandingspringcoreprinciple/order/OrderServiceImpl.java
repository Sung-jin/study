package com.example.understandingspringcoreprinciple.order;

import com.example.understandingspringcoreprinciple.discount.DiscountPolicy;
import com.example.understandingspringcoreprinciple.discount.FixDiscountPolicy;
import com.example.understandingspringcoreprinciple.member.Member;
import com.example.understandingspringcoreprinciple.member.MemberRepository;
import com.example.understandingspringcoreprinciple.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {
    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
