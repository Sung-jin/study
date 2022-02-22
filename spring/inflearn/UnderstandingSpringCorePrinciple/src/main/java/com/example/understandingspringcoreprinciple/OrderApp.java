package com.example.understandingspringcoreprinciple;

import com.example.understandingspringcoreprinciple.member.Grade;
import com.example.understandingspringcoreprinciple.member.Member;
import com.example.understandingspringcoreprinciple.member.MemberService;
import com.example.understandingspringcoreprinciple.member.MemberServiceImpl;
import com.example.understandingspringcoreprinciple.order.Order;
import com.example.understandingspringcoreprinciple.order.OrderService;
import com.example.understandingspringcoreprinciple.order.OrderServiceImpl;

public class OrderApp {
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);
        System.out.println("order.calculatePrice = " + order.calculatePrice());
    }
}
