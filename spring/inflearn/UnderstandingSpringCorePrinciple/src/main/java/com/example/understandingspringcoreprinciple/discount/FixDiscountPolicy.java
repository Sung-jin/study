package com.example.understandingspringcoreprinciple.discount;

import com.example.understandingspringcoreprinciple.member.Grade;
import com.example.understandingspringcoreprinciple.member.Member;

public class FixDiscountPolicy implements DiscountPolicy {
    private int discountFixAmount = 1000;
    // 1000 원을 고정적으로 할인

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}
